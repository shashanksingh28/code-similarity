import pdb
import re
import json
import sys
import numpy as np
from sklearn.feature_extraction.text import TfidfVectorizer
import math

def getListIntersection(list1, list2):
    """ Returns list of elements in both list1 and list2 (duplicates will have multiple entries) """
    wordList1 = list1[:]
    wordList2 = list2[:]
    # TODO: check for more efficient way
    commonKeys = set(wordList1).intersection(set(wordList2))
    matchList = []

    for key in commonKeys:
        while key in wordList1 and key in wordList2:
            wordList1.remove(key)
            wordList2.remove(key)
            matchList.append(key)
    
    return matchList

def getCountIntersection(dict1, dict2):
    """ if there are two dictionaries with values as counts of keys, return a list of matches (duplicates will have multiple entries)"""
    valueSet1 = set(dict1.keys())
    valueSet2 = set(dict2.keys())
    commonKeys = valueSet1.intersection(valueSet2)
    matchList = []
    for key in commonKeys:
        num = min(dict1[key], dict2[key])
        matchList += [key] * num

    return matchList

def getListFromCounts(dict1):
    """ Given a dictionary with key as elements and their counts, return a flat list with duplicates """
    elements = []
    for key in dict1:
        elements += [key] * dict1[key]
    return elements
    
def getVectors(inputFile):
    # pdb.set_trace()
    docVectors = []
    with open(inputFile,"r") as f:
        for line in f:
            if re.search(r'^[^#].+$', line):
                docVectors.append(json.loads(line.strip()))
    return docVectors

def getBagOfWords(vectors):
    excludeKeys = ['concepts','className','text','isEmpty','size','methods']
    bagOfWords = []
    for vector in vectors:
        docWords = []
        for key in vector:
            if key in excludeKeys:
                continue
            if type(vector[key]) is dict:
                docElements = getListFromCounts(vector[key])
                docWords += docElements
            elif type(vector[key]) is list:
                docWords += vector[key]
            else:
                docWords += [str(vector[key])]
        bagOfWords.append(' '.join(docWords))
    return bagOfWords

def textMatchList(dict1, dict2):
    """ Return the pure text similarity; not unique matches but total matches """
    # Do not use lower() because Java is case sensitive
    # Double not equal to double
    # pdb.set_trace()
    text1 = dict1['text']
    text2 = dict2['text']
    wordList1 = re.sub(r"(\r\n|\n|\.(?!\d)|[,;#?!$]|\s\s+)"," ", text1).split()
    wordList2 = re.sub(r"(\r\n|\n|\.(?!\d)|[,;#?!$]|\s\s+)"," ", text2).split()
    matchList = getListIntersection(wordList1, wordList2)
    # print("Text :", matchList)
    # print(len(matchList))
    # return matchSize / totalSize
    return matchList


def tokenMatchList(dict1, dict2):
    """ Return the token matching similarity """
    # pdb.set_trace()
    excludeKeys = ['concepts','className','text','isEmpty','size','methods']
    matchList = []
    for key in dict1:
        if key in excludeKeys or key not in dict2:
            continue
        if type(dict1[key]) is dict:
            matchList += getCountIntersection(dict1[key], dict2[key])
        elif type(dict1[key]) is list:
            matchList += getListIntersection(dict1[key], dict2[key])
        else:
            if dict1[key] == dict2[key]:
                matchList += [str(dict1[key])]
    
    # print("Tokens :", matchList)
    # print(len(matchList))
    # return matchSize / totalSize
    return matchList


def runPipe(inputFile):
    """ Run whole pipeline """
    allVectors = getVectors(inputFile)
    vectors = list(filter((lambda x : not x['isEmpty']),allVectors))
    bagOfWords = getBagOfWords(vectors)
    vectorizer = TfidfVectorizer(min_df=1)
    tfIdfWeights = vectorizer.fit_transform(bagOfWords).toarray()
    
    textSimGrid = np.zeros([len(vectors), len(vectors)])
    tokenSimGrid = np.copy(textSimGrid)
    tfIdfSimGrid = np.copy(textSimGrid)

    for i, _ in enumerate(vectors):
        # print()
        # print("########## Comparing ########")
        # print(vectors[i]['text'])
        
        for j in range(i + 1, len(vectors)):
            # print("----------- With ----------")
            # print(vectors[j]['text'])
            textSimGrid[i][j] = textSimGrid[j][i] = len(textMatchList(vectors[i], vectors[j]))
            tokenSimGrid[i][j] = tokenSimGrid[j][i] = len(tokenMatchList(vectors[i], vectors[j]))
            tfIdfSimGrid[i][j] = tfIdfSimGrid[j][i] = np.dot(tfIdfWeights[i],tfIdfWeights[j]) / (math.sqrt(sum(np.square(tfIdfWeights[i]))) * math.sqrt(sum(np.square(tfIdfWeights[j]))))
            
    # print(textSimGrid)
    # print(tokenSimGrid)

    textClosest = np.argmax(textSimGrid, axis = 1)
    tokenClosest = np.argmax(tokenSimGrid, axis = 1)
    tfIdfClosest = np.argmax(tfIdfSimGrid, axis = 1)

    for i, _ in enumerate(vectors):
        print("######## Sample :",i)
        if textClosest[i] != tokenClosest[i] or tokenClosest[i] != tfIdfClosest[i]:
            print(vectors[i]['text'])
            print("----- Text Closest :", textClosest[i]," Score:", textSimGrid[i][textClosest[i]])
            print(vectors[textClosest[i]]['text'])
            print(textMatchList(vectors[i], vectors[textClosest[i]]))
            print()
            print("----- Token Closest :", tokenClosest[i], "Score:", tokenSimGrid[i][tokenClosest[i]])
            print(vectors[tokenClosest[i]]['text'])
            print(tokenMatchList(vectors[i], vectors[textClosest[i]]))
            print()
            print("----- Token Tf-IDF Closest :", tfIdfClosest[i], "Score:", tfIdfSimGrid[i][tfIdfClosest[i]])
            print(vectors[tfIdfClosest[i]]['text'])
            print()

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Enter an input file containing method vectors")
    else:
        runPipe(sys.argv[1])

