#!/usr/bin/env python3
import sys
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from math import log2, log
from collections import Counter
from scipy.stats import entropy, ttest_rel

def counterJaccardSim(c1, c2):
    cU = c1 | c2
    cI = c1 & c2
    sum_cU = sum(cU.values())
    if sum_cU == 0:
        return 0
    return sum(cI.values()) / sum_cU

def getCounter(x):
    return eval(x)

def transform(csv_file):
    """ Apply evaluation functions and simple count enhancements and return """
    df = pd.read_csv(csv_file, usecols=['Rank','Sample_Concepts','Codereco_Concepts','Baseline_Concepts'])
    df.loc[:,'Sample_Concepts'] = df['Sample_Concepts'].apply(getCounter)
    df.loc[:,'Sample_Concepts_Count'] = df['Sample_Concepts'].apply(len)
    df.loc[:,'Codereco_Concepts'] = df['Codereco_Concepts'].apply(getCounter)
    df.loc[:,'Codereco_Concepts_Count'] = df['Codereco_Concepts'].apply(len)
    df.loc[:,'Baseline_Concepts'] = df['Baseline_Concepts'].apply(getCounter)
    df.loc[:,'Baseline_Concepts_Count'] = df['Baseline_Concepts'].apply(len)
    return df

def entropy(counter):
    ent = 0
    if len(counter) is None:
        return ent
    total = sum(counter.values())
    for key in counter:
        p = counter[key] / total
        ent -= p * log(p)
    return ent

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Provide csv file containing data")
        sys.exit(1)
    
    df = transform(sys.argv[1])
    
    _ = df[['Sample_Concepts_Count','Baseline_Concepts_Count','Codereco_Concepts_Count']].plot.box(title='Unique Concepts Distribution')
    print(ttest_rel(df['Baseline_Concepts_Count'],df['Codereco_Concepts_Count']))
    plt.show()

    df['Codereco_Similarity'] = df.apply(lambda x: counterJaccardSim(x['Sample_Concepts'],x['Codereco_Concepts']), axis=1)
    df['Baseline_Similarity'] = df.apply(lambda x: counterJaccardSim(x['Sample_Concepts'],x['Baseline_Concepts']), axis=1)
    print(ttest_rel(df['Baseline_Similarity'],df['Codereco_Similarity']))
    _ = df[['Baseline_Similarity','Codereco_Similarity']].plot.box(title='Sample Concepts Jaccard Similarity')
    plt.show()

    df['Baseline_Entropy'] = df['Baseline_Concepts'].apply(entropy)
    df['Codereco_Entropy'] = df['Codereco_Concepts'].apply(entropy)
    print(ttest_rel(df['Baseline_Entropy'],df['Codereco_Entropy']))
    _ = df[['Baseline_Entropy','Codereco_Entropy']].plot.box(title='Concepts Entropy')
    plt.show()
    
    df.to_csv('evaluation_data.csv')
    df.describe().to_csv('evaluation_summary.csv')
    print(df.describe())
    
