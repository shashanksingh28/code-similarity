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

solutionInputFile = "solutionSet.txt"
studentInputFile = "studentSet.txt"

textModelFile = "models/textTf-Idf.model"
textMatrixFile = "models/textTf-Idf.dat"

tokenModelFile = "models/tokenTf-Idf.model"
tokenMatrixFile = "models/tokenTf-Idf.dat"

solutionListFile = "models/solutionVectors.pck"

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
    # pdb.set_trace()
    
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

def getClosestJaccard(method, allMethods):
    """ Given a method as a vector, compare feature set intersections.
        Mathematically, Intersection(A,B)/ Union(A,B) """
    # pdb.set_trace()
    closestVectorIndex = 0
    highestScore = float('-inf')
    highestIntersections = []
    for i, solutionMethod in enumerate(allMethods):
        if solutionMethod == method:
            continue
        thisJaccardScore, intersections = method.getJaccardSimilarity(solutionMethod)
        if thisJaccardScore > highestScore:
            closestVectorIndex = i
            highestScore = thisJaccardScore
            highestIntersections = intersections
    return closestVectorIndex, highestIntersections


def getClosestCosine(vector, solutionVectors, features):
    """ Provided a single vector and a list of vectors with the same dimensions, finds closest
        based on cosine similarity. Features is just a list of features, so it also provides
        a list of which features contributed to the cosine """
    # pdb.set_trace()
    similarities = cosine_similarity(vector,solutionVectors)
    closest = np.argmax(similarities)
    intersectingFeatures = []
    for i, feature in enumerate(features):
        if vector[0,i] > 0 and solutionVectors[closest,i] > 0:
            intersectingFeatures.append(feature)

    return closest, intersectingFeatures

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
    studentVectors = getUniqueNonEmptyMethodVectors(studentInputFile)
    outList = []
    for i, vector in enumerate(studentVectors):
        textVector = textModel.transform([' '.join(vector.textTokens)])
        tokenVector = tokenModel.transform([' '.join(vector.langTokens)])
        textClosestIndex, textIntersections = getClosestCosine(textVector, textMatrix, textModel.get_feature_names())
        tokenClosestIndex, tokenIntersections = getClosestCosine(tokenVector, tokenMatrix, tokenModel.get_feature_names())
        jaccardClosestIndex, jaccardIntersections = getClosestJaccard(vector, solutionVectors)
        closest = set([textClosestIndex, tokenClosestIndex, jaccardClosestIndex])        
        outList.append([i+1,vector.rawText,solutionVectors[textClosestIndex].rawText + "\n\n---------\n" + str(textIntersections),\
                solutionVectors[tokenClosestIndex].rawText + "\n\n----------\n" + str(tokenIntersections),\
                solutionVectors[jaccardClosestIndex].rawText + "\n\n----------\n" + str(jaccardIntersections), len(closest) != 1 ])
        print(i)
    output = pd.DataFrame(outList, columns=['#', 'Sample','Text-TfIdf', 'Token-Tf-Idf', 'Jaccard', 'Different Recommendations'])
    output.to_csv("output.csv")

if __name__ == "__main__":
    start_time = time.time()
    runExperiment()
    print("--- %s seconds ---" % (time.time() - start_time))

