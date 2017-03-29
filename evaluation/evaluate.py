#!/usr/bin/env python3
import sys
import numpy as np
import pandas as pd

from collections import Counter

ontology_file = "ontologytree.txt"

depths = Counter()

def getDepthDict(ontology_file):
    depths = Counter()
    with open(ontology_file, "r") as f:
        for line in f:
            d = 0
            while line.startswith("----"):
                d += 1
                line = line[4:]
            concept = line.split(":")[1]
            depths[concept.strip()] = d
    return depths

def avg_depth(concept_list):
    if len(concept_list) == 0:
        return 0
    total_depth = 0
    for concept in concept_list:
        total_depth += depths[concept]
    return total_depth / len(concept_list)

def evaluate(csv_file):
    df = pd.read_csv(csv_file, usecols=['Rank','Sample_Concepts','Codereco_Concepts','Baseline_Concepts'])
    df.loc[:,'Sample_Concepts'] = df['Sample_Concepts'].apply(eval)
    df.loc[:,'Sample_Concepts_Count'] = df['Sample_Concepts'].apply(len)
    df.loc[:,'Codereco_Concepts'] = df['Codereco_Concepts'].apply(eval)
    df.loc[:,'Codereco_Concepts_Count'] = df['Codereco_Concepts'].apply(len)
    df.loc[:,'Baseline_Concepts'] = df['Baseline_Concepts'].apply(eval)
    df.loc[:,'Baseline_Concepts_Count'] = df['Baseline_Concepts'].apply(len)
   
    """df.loc[:,'Sample_Concepts_Avg_Depth'] = df['Sample_Concepts'].apply(avg_depth)
    df.loc[:,'Codereco_Concepts_Avg_Depth'] = df['Codereco_Concepts'].apply(avg_depth)
    df.loc[:,'Baseline_Concepts_Avg_Depth'] = df['Baseline_Concepts'].apply(avg_depth)"""
     
    df.loc[:,'Codereco-Sample-int'] = df.apply(lambda x: len(set(x['Sample_Concepts']) & set(x['Codereco_Concepts'])), axis=1)
    df.loc[:,'Baseline-Sample-int'] = df.apply(lambda x: len(set(x['Sample_Concepts']) & set(x['Baseline_Concepts'])), axis=1)
    df.loc[:,'Codereco-Count > Baseline-Count'] = df[['Codereco_Concepts_Count','Baseline_Concepts_Count']]\
            .apply(lambda x : x['Codereco_Concepts_Count'] >= x['Baseline_Concepts_Count'], axis=1)
    df.loc[:,'Codereco-int > Baseline-int'] = df[['Codereco-Sample-int','Baseline-Sample-int']]\
            .apply(lambda x : x['Codereco-Sample-int'] >= x['Baseline-Sample-int'], axis=1)
    
    df.loc[:,'Codereco - Sample'] = df.apply(lambda x : np.abs(x['Codereco_Concepts_Count'] - x['Sample_Concepts_Count']), axis=1)
    df.loc[:,'Baseline - Sample'] = df.apply(lambda x : np.abs(x['Baseline_Concepts_Count'] - x['Sample_Concepts_Count']), axis=1)
    
    return df

if __name__ == "__main__":
    depths = getDepthDict(ontology_file)
    out_df = evaluate(sys.argv[1])
    out_df.to_csv('evaluation_data.csv')
    out_df.describe().to_csv('evaluation_summary.csv')
    import pdb; pdb.set_trace()
