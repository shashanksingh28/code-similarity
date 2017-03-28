#!/usr/bin/env python3
import sys
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
    df.loc[:,'Condereco_Concepts_Count'] = df['Codereco_Concepts'].apply(len)
    df.loc[:,'Baseline_Concepts'] = df['Baseline_Concepts'].apply(eval)
    df.loc[:,'Baseline_Concepts_count'] = df['Baseline_Concepts'].apply(len)
   
    df.loc[:,'Sample_Concepts_Avg_Depth'] = df['Sample_Concepts'].apply(avg_depth)
    df.loc[:,'Codereco_Concepts_Avg_Depth'] = df['Codereco_Concepts'].apply(avg_depth)
    df.loc[:,'Baseline_Concepts_Avg_Depth'] = df['Baseline_Concepts'].apply(avg_depth)
    
    return df.describe()

if __name__ == "__main__":
    depths = getDepthDict(ontology_file)
    out_df = evaluate(sys.argv[1])
    out_df.to_csv('evaluation_results.csv')
