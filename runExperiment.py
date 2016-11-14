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
    for i, solutionMethod in enumerate(allMethods):
        if solutionMethod == method:
            continue
        thisJaccardScore = method.getJaccardSimilarity(solutionMethod)
        if thisJaccardScore > highestScore:
            closestVectorIndex = i
            highestScore = thisJaccardScore
    return closestVectorIndex


def getClosestCosine(vector, solutionVectors):
    """ Provided a single vector and a list of vectors with the same dimensions, finds closest
        based on cosine similarity"""
    similarities = cosine_similarity(vector,solutionVectors)
    return np.argmax(similarities)

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
        textClosestIndex = getClosestCosine(textVector, textMatrix)
        tokenClosestIndex = getClosestCosine(tokenVector, tokenMatrix)
        jaccardClosestIndex = getClosestJaccard(vector, solutionVectors)
        outList.append([i+1,vector.rawText,solutionVectors[textClosestIndex].rawText, solutionVectors[tokenClosestIndex].rawText,\
                solutionVectors[jaccardClosestIndex].rawText])
        print(i)
    output = pd.DataFrame(outList, columns=['#', 'Sample','Text-TfIdf', 'Token-Tf-Idf', 'Jaccard'])
    output.to_csv("output.csv")

if __name__ == "__main__":
    start_time = time.time()
    runExperiment()
    print("--- %s seconds ---" % (time.time() - start_time))

