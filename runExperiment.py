import re
import pdb
import sys
import os
import time
import pickle

import method

import pandas as pd
import numpy as np
from sklearn.feature_extraction.text import CountVectorizer, TfidfTransformer, TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

from similarity import jaccardSimilarity

solutionInputFile = "solutionSet.txt"
studentInputFile = "studentSet.txt"

textModelFile = "models/textTf-Idf.model"
textMatrixFile = "models/textTf-Idf.dat"

tokenModelFile = "models/tokenTf-Idf.model"
tokenMatrixFile = "models/tokenTf-Idf.dat"

solutionListFile = "models/solutionVectors.pck"

kNearest = 3

def getUniqueNonEmptyMethodVectors(inputFile):
    # pdb.set_trace()
    docVectors = {}
    with open(inputFile,"r") as f:
        for line in f:
            if re.search(r'^[^#].+$', line):
                vector = method.MethodFeatureVector(line.strip())
                if not vector.isEmpty:
                    docVectors[vector.rawText.strip()] = vector
    return docVectors.values()

def getBOWs(vectors):
    textBOW = []
    langBOW = []
    for vector in vectors:
        textBOW.append(' '.join(vector.textTokens))
        langBOW.append(' '.join(vector.langTokens))
    return textBOW, langBOW

def createModels(solutionsFile):
    """ From the solutions file, create the tf-idf models """
    print("Reading Solutions...")
    vectors = getUniqueNonEmptyMethodVectors(solutionsFile)
    vectors = list(filter((lambda x : not x.isEmpty), vectors))
    
    print("Total Samples:",len(vectors))
    
    # We will have two different bag of words:
    # 1. Text tokenized bag of words
    # 2. NLP bag of words
    textBOW, tokenBOW = getBOWs(vectors)
    
    if not os.path.exists("models"):
        os.makedirs("models")

    textTfIdfModel = TfidfVectorizer(min_df=1).fit(textBOW)
    textTfIdfData = textTfIdfModel.transform(textBOW)
    pickle.dump(textTfIdfModel, open(textModelFile, "wb")) 
    pickle.dump(textTfIdfData, open(textMatrixFile, "wb"))

    tokenTfIdfModel = TfidfVectorizer(min_df=1).fit(tokenBOW)
    tokenTfIdfData = tokenTfIdfModel.transform(tokenBOW)
    pickle.dump(tokenTfIdfModel, open(tokenModelFile, "wb"))
    pickle.dump(tokenTfIdfData, open(tokenMatrixFile, "wb"))

    pickle.dump(vectors, open(solutionListFile, "wb"))

    return (vectors, textTfIdfModel, textTfIdfData, tokenTfIdfModel, tokenTfIdfData)

def jaccardKnearest(method, solutions, k = 1, featureWeights=dict()):
    """ Return K Nearest methods based on jaccard similarity.
        Returns a list of tuples containing (score, similarityDictionary)
    """
    results = [] 
    
    similarities = [ jaccardSimilarity(method, solution, featureWeights) for solution in solutions ]
    similarityScores = [tup[0] for tup in similarities]
    sortedIndices = np.argsort(similarityScores)

    kNearestIndices = sortedIndices[-k:]

    for i in kNearestIndices[::-1]:
        results.append((float(np.round(similarities[i][0], decimals = 3)), i, similarities[i][1]))

    return results


def cosineKnearest(vector, solutionVectors, featureNames, k = 1):
    """ Provided a single vector and a list of vectors with the same dimensions, finds closest
        based on cosine similarity. Features is just a list of features, so it also provides
        a list of which features contributed to the cosine """
    # pdb.set_trace()
    similarities = cosine_similarity(vector,solutionVectors).ravel()
    
    # since we need indexes
    sortedIndices = np.argsort(similarities)
    kNearestIndices = sortedIndices[-k:]
    results = []
    for i in kNearestIndices[::-1]:
        intersectingTokens = []
        for j, feature in enumerate(featureNames):
            if vector[0,j] > 0 and solutionVectors[i,j] > 0:
                intersectingTokens.append(feature)
        results.append((float(np.round(similarities[i], decimals = 3)), i, intersectingTokens))
    return results

def runExperiment():
    # Load the models 
    if not os.path.isfile(textModelFile):
        print("Creating models")
        solutionVectors, textModel, textMatrix, tokenModel, tokenMatrix = createModels(solutionInputFile)
    else:
        print("Loading models")
        solutionVectors = pickle.load(open(solutionListFile, "rb"))
        textModel = pickle.load(open(textModelFile, "rb"))
        textMatrix = pickle.load(open(textMatrixFile, "rb"))
        tokenModel = pickle.load(open(tokenModelFile, "rb"))
        tokenMatrix = pickle.load(open(tokenMatrixFile, "rb"))
    
    # For every sample in student set, product predictions
    print("Reading samples...")

    boostLanguage = {'variables': 2, 'javaDoc' : 3, 'comments' : 3}
    boostStructure = {'concepts': 3, 'methodCalls' : 2, 'expressions': 2, 'statements' : 2}

    studentVectors = getUniqueNonEmptyMethodVectors(studentInputFile)
    outList = []
    for i, vector in enumerate(studentVectors):
        textVector = textModel.transform([' '.join(vector.textTokens)])
        tokenVector = tokenModel.transform([' '.join(vector.langTokens)])
        textCosineKnearest = cosineKnearest(textVector, textMatrix, textModel.get_feature_names(), kNearest)
        tokenCosineKnearest = cosineKnearest(tokenVector, tokenMatrix, tokenModel.get_feature_names(), kNearest)
        equalJaccardKnearest = jaccardKnearest(vector, solutionVectors, kNearest)
        langJaccardKnearest = jaccardKnearest(vector, solutionVectors, kNearest, boostLanguage)
        structJaccardKnearest = jaccardKnearest(vector, solutionVectors, kNearest, boostStructure)

        print(i)
        # pdb.set_trace()
        for j in range(kNearest):
            if j == 0:
                outList.append([i + 1,\
                        len(set([textCosineKnearest[j][1],tokenCosineKnearest[j][1], equalJaccardKnearest[j][1], langJaccardKnearest[j][1], structJaccardKnearest[j][1]])) > 1, 
                        vector.rawText,\
                        solutionVectors[textCosineKnearest[j][1]].rawText + "\n\n-----\nScore:" + str(textCosineKnearest[j][0]) + "\n" + str(textCosineKnearest[j][2]),\
                        solutionVectors[tokenCosineKnearest[j][1]].rawText + "\n\n-----\nScore:" + str(tokenCosineKnearest[j][0]) + "\n" + str(tokenCosineKnearest[j][2]),\
                        solutionVectors[equalJaccardKnearest[j][1]].rawText + "\n\n-----\nScore:" + str(equalJaccardKnearest[j][0]) + "\n" + str(equalJaccardKnearest[j][2]),\
                        solutionVectors[langJaccardKnearest[j][1]].rawText + "\n\n-----\nScore:" + str(langJaccardKnearest[j][0]) + "\n" + str(langJaccardKnearest[j][2]),\
                        solutionVectors[structJaccardKnearest[j][1]].rawText + "\n\n-----\nScore:" + str(structJaccardKnearest[j][0]) + "\n" + str(structJaccardKnearest[j][2])
                        ])
            else:
                outList.append([None,\
                        None,
                        "",\
                        solutionVectors[textCosineKnearest[j][1]].rawText + "\n\n-----\nScore:" + str(textCosineKnearest[j][0]) + "\n" + str(textCosineKnearest[j][2]),\
                        solutionVectors[tokenCosineKnearest[j][1]].rawText + "\n\n-----\nScore:" + str(tokenCosineKnearest[j][0]) + "\n" + str(tokenCosineKnearest[j][2]),\
                        solutionVectors[equalJaccardKnearest[j][1]].rawText + "\n\n-----\nScore:" + str(equalJaccardKnearest[j][0]) + "\n" + str(equalJaccardKnearest[j][2]),\
                        solutionVectors[langJaccardKnearest[j][1]].rawText + "\n\n-----\nScore:" + str(langJaccardKnearest[j][0]) + "\n" + str(langJaccardKnearest[j][2]),\
                        solutionVectors[structJaccardKnearest[j][1]].rawText + "\n\n-----\nScore:" + str(structJaccardKnearest[j][0]) + "\n" + str(structJaccardKnearest[j][2]) 
                        ])

    
    output = pd.DataFrame(outList, columns=['#', 'Difference', 'Sample','Text-TfIdf', 'Token-Tf-Idf', 'EqualJaccard','Language boosted', 'Syntax boosted'])
    output.to_csv("output.csv")

if __name__ == "__main__":
    start_time = time.time()
    runExperiment()
    print("--- %s seconds ---" % (time.time() - start_time))

