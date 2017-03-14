import sys
import pandas as pd

def diff(df):
    """ Assuming col1 and col2 are lists, return set difference between list1 & list2 """
    return list(set(df[0]) - set(df[1]))

def coverage(df):
    """ How much of column1 does column2 cover """
    s = set(df[0])
    
    num = len(s.intersection(set(df[1])))
    den = len(s)
    if den == 0:
        return 0 
    return num / den

def main(filepath):
    df = pd.read_csv(filepath, index_col='#')
    df['IR_Reco_concepts'] = df['IR_Reco_concepts'].apply(eval)
    df['Tags_Reco_concepts'] = df['Tags_Reco_concepts'].apply(eval)
    df['Sample_Concepts'] = df['Sample_Concepts'].apply(eval)
   
    df['IR-Tags'] = df[['IR_Reco_concepts','Tags_Reco_concepts']].apply(diff, axis=1)
    df['Tags-IR'] = df[['Tags_Reco_concepts','IR_Reco_concepts']].apply(diff, axis=1)

    df['IR_Reco_count'] = df['IR_Reco_concepts'].apply(len)
    df['Tags_Reco_count'] = df['Tags_Reco_concepts'].apply(len)
    df['IR-Tags-Count'] = df['IR-Tags'].apply(len)
    df['Tags-IR-Count'] = df['Tags-IR'].apply(len)
    df['IR-Coverage'] = df[['Sample_Concepts','IR_Reco_concepts']].apply(coverage, axis=1)
    df['Tags-Coverage'] = df[['Sample_Concepts','Tags_Reco_concepts']].apply(coverage, axis=1)
    
    df.to_csv('analysis_details.csv')
    df[['Rank', 'Sample_Concepts','IR_Reco_concepts', 'Tags_Reco_concepts',\
            'IR-Tags', 'Tags-IR', 'IR_Reco_count', 'Tags_Reco_count',\
            'IR-Tags-Count', 'Tags-IR-Count', 'IR-Coverage',\
            'Tags-Coverage']].to_csv('analysis_short.csv')
    df.describe().to_csv('desc.csv')

if __name__ == "__main__":
    main(sys.argv[1])
