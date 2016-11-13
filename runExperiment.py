import re
import pdb
import sys
import numpy as np
from sklearn.feature_extraction.text import TfidfVectorizer
import math
import method
import time
import pandas as pd

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

def runPipe(inputFile):
    """ Run whole pipeline """
    vectors = getUniqueNonEmptyMethodVectors(inputFile)
    vectors = list(filter((lambda x : not x.isEmpty), vectors))
    print("Total Samples:",len(vectors))
    
    # We will have two different bag of words:
    # 1. Text tokenized bag of words
    # 2. NLP bag of words
    textBOW, langBOW = getBOWs(vectors)

    # use simple x.split() as tokenizer because default calls lower() which is not correct for our case
    textVectorizer = TfidfVectorizer(min_df=1)
    textTfIdfs = textVectorizer.fit_transform(textBOW)
    
    langVectorizer = TfidfVectorizer(min_df=1)
    langTfIdfs = langVectorizer.fit_transform(langBOW)

    pdb.set_trace()
    for i, _ in enumerate(vectors):
        print(i)
        print(vectors[i])


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Enter an input file containing method vectors")
    else:
        start_time = time.time()
        runPipe(sys.argv[1])
        print("--- %s seconds ---" % (time.time() - start_time))

