import sys
import pandas as pd

def diff(df):
    """ Assuming col1 and col2 are lists, return set difference between list1 & list2 """
    return list(set(df[0]) - set(df[1]))

def main(filepath):
    df = pd.read_csv(filepath, index_col='#')
    df['IR_Reco_concepts'] = df['IR_Reco_concepts'].apply(eval)
    df['Tags_Reco_concepts'] = df['Tags_Reco_concepts'].apply(eval)
       
    df['IR-Tags'] = df[['IR_Reco_concepts','Tags_Reco_concepts']].apply(diff, axis=1)
    df['Tags-IR'] = df[['Tags_Reco_concepts','IR_Reco_concepts']].apply(diff, axis=1)

    df['IR_Reco_count'] = df['IR_Reco_concepts'].apply(len)
    df['Tags_Reco_count'] = df['Tags_Reco_concepts'].apply(len)
    df['IR-Tags-Count'] = df['IR-Tags'].apply(len)
    df['Tags-IR-Count'] = df['Tags-IR'].apply(len)

    df.to_csv('analysis.csv')

    df.describe()

    import pdb; pdb.set_trace()

if __name__ == "__main__":
    main(sys.argv[1])
