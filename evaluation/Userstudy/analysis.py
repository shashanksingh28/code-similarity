#!/usr/bin/env python3
import sys
import os
import pandas as pd
import json
import matplotlib.pyplot as plt
from scipy.stats import ttest_ind
import re

def wordcount(df_column):
    try:
        return len(df_column.split())
    except:
        return 0

def cleanWeightsCode(df_column):
    try:
        obj = json.loads(df_column)
    except:
        # normal case, return df_column
        return df_column
    else:
        return obj['code']

asu_folder='classroom/ASU101'
cpi_folder='classroom/CPI101'
asu_filter_users = [1,20,21]
cpi_filter_users = [1,2,3,4,5,6,67]
filter_users = [1,3]
mturk_folder='MTurk'
pattern = re.compile(r'\s+')


def main():
    vote_cpi_file = os.path.join(cpi_folder,'codereco_vote.csv')
    user_cpi_file = os.path.join(cpi_folder, 'auth_user.csv')
    weight_cpi_file = os.path.join(cpi_folder, 'codereco_customweight.csv')
    submission_cpi_file = os.path.join(cpi_folder, 'codereco_submission.csv')
    cutcopy_cpi_file = os.path.join(cpi_folder, 'codereco_cutcopy.csv')

    vote_asu_file = os.path.join(asu_folder,'codereco_vote.csv')
    user_asu_file = os.path.join(asu_folder, 'auth_user.csv')
    weight_asu_file = os.path.join(asu_folder, 'codereco_customweight.csv')
    submission_asu_file = os.path.join(asu_folder, 'codereco_submission.csv')
    cutcopy_asu_file = os.path.join(asu_folder, 'codereco_cutcopy.csv')
    
    votes_cpi = pd.read_csv(vote_cpi_file)
    users_cpi = pd.read_csv(user_cpi_file)
    weights_cpi = pd.read_csv(weight_cpi_file)
    submissions_cpi = pd.read_csv(submission_cpi_file)
    cutcopy_cpi = pd.read_csv(cutcopy_cpi_file)

    votes_asu = pd.read_csv(vote_asu_file)
    users_asu = pd.read_csv(user_asu_file)
    weights_asu = pd.read_csv(weight_asu_file)
    submissions_asu = pd.read_csv(submission_asu_file)
    cutcopy_asu = pd.read_csv(cutcopy_asu_file)

    # filter test users
    study_cpi_votes = votes_cpi[~votes_cpi.user_id.isin(cpi_filter_users)].copy()
    study_asu_votes = votes_asu[~votes_asu.user_id.isin(asu_filter_users)].copy()
    
    # Merging
    cpi_votes = study_cpi_votes.merge(users_cpi, left_on='user_id', right_on='id')[['question','source','rank','rating','code','username','voteid']]
    asu_votes = study_asu_votes.merge(users_asu, left_on='user_id', right_on='id')[['question','source','rank','rating','code','username','voteid']]     
     
    cpi_weights = weights_cpi[~weights_cpi.user_id.isin(cpi_filter_users)]\
                    .merge(users_cpi, left_on='user_id', right_on='id')[['signature', 'structure', 'concepts', 'language','username','code','vote_id']]
    asu_weights = weights_asu[~weights_asu.user_id.isin(asu_filter_users)]\
                    .merge(users_asu, left_on='user_id', right_on='id')[['signature', 'structure', 'concepts', 'language','username','code','vote_id']]
    
    votes_class_final = pd.concat([cpi_votes, asu_votes], ignore_index=True)
    weights_class_final = pd.concat([cpi_weights, asu_weights], ignore_index=True)

    vote_file = os.path.join(mturk_folder,'codereco_vote.csv')
    user_file = os.path.join(mturk_folder, 'auth_user.csv')
    weight_file = os.path.join(mturk_folder, 'codereco_customweight.csv')
    submission_file = os.path.join(mturk_folder, 'codereco_submission.csv')
    cutcopy_file = os.path.join(mturk_folder, 'codereco_cutcopy.csv')

    votes_mturk = pd.read_csv(vote_file)
    users_mturk = pd.read_csv(user_file)
    weights_mturk = pd.read_csv(weight_file)
    submissions_mturk = pd.read_csv(submission_file)
    cutcopy_mturk = pd.read_csv(cutcopy_file)

    # filter test users
    study_votes = votes_mturk[~votes_mturk.user_id.isin(filter_users)].copy()
    
    votes_mturk_final = study_votes.merge(users_mturk, left_on='user_id', right_on='id')[['question','source','rank','rating','code','username','voteid']]
     
    weights_mturk_final = weights_mturk[~weights_mturk.user_id.isin(filter_users)]\
                    .merge(users_mturk, left_on='user_id', right_on='id')[['signature', 'structure', 'concepts', 'language','username','code','vote_id']]
    

    # clean weights
    weights_mturk_final.loc[:,'code'] = weights_mturk_final.loc[:,'code'].apply(cleanWeightsCode) 
    weights_class_final.loc[:, 'code'] = weights_class_final.loc[:, 'code'].apply(cleanWeightsCode)

    # drop nas
    weights_mturk_final.dropna(subset=['code'], inplace=True)
    weights_class_final.dropna(subset=['code'], inplace=True)
    
    # transform
    weights_mturk_final.loc[:,'word_count'] = weights_mturk_final['code'].apply(wordcount) 
    weights_class_final.loc[:,'word_count'] = weights_class_final['code'].apply(wordcount)
    votes_mturk_final.loc[:,'word_count'] = votes_mturk_final['code'].apply(wordcount)
    votes_class_final.loc[:,'word_count'] = votes_class_final['code'].apply(wordcount)
    
    # trim all code so that only whitespace does not differentiate between code snippets
    votes_mturk_final['code'] = votes_mturk_final.code.apply(lambda x: re.sub(pattern, '', x))
    votes_class_final['code'] = votes_class_final.code.apply(lambda x: re.sub(pattern, '', x))
    weights_mturk_final['code'] = weights_mturk_final.code.apply(lambda x : re.sub(pattern,'', x))
    weights_class_final['code'] = weights_class_final.code.apply(lambda x : re.sub(pattern, '', x))
    
     
    #-------------- Votes compare
    transf1 = []
    for n, group in votes_class_final.groupby(['username','code']):
        transf1.append([n[0],n[1],len(group),group.word_count.unique()[0]])

    transf2 = []
    for n, group in votes_mturk_final.groupby(['username','code']):
        transf2.append([n[0],n[1],len(group),group.word_count.unique()[0]])

    grpd1 = pd.DataFrame(transf1, columns=['Username','Code','Ratings','Word Count'])   
    grpd2 = pd.DataFrame(transf2, columns=['Username','Code','Ratings','Word Count'])
    ax = grpd1.plot(kind='scatter', x='Word Count', y='Ratings', color='Red', label='Classroom')
    grpd2.plot(kind='scatter',x='Word Count', y='Ratings',color='Blue',label='MTurk',ax=ax)
    plt.show()
    
    print("#------ Ratings vs Word Count (per edit) for Classroom -----------#")
    print(grpd1.describe())
    print("#------ Ratings vs Word Count (per edit) for MTurk -----------#")
    print(grpd2.describe())
    
    #------------- Weights compare
    transf1 = []
    for n, group in weights_class_final.groupby(['username','code']):
        transf1.append([n[0],n[1],len(group),group.word_count.unique()[0]])

    transf2 = []
    for n, group in weights_mturk_final.groupby(['username','code']):
        transf2.append([n[0],n[1],len(group),group.word_count.unique()[0]])

    grpd1_new = pd.DataFrame(transf1, columns=['Username','Code','Queries','Word Count'])   
    grpd2_new = pd.DataFrame(transf2, columns=['Username','Code','Queries','Word Count'])
    
    ax_new = grpd1_new.plot(kind='scatter', x='Word Count', y='Queries', color='Red', label='Classroom')
    grpd2_new.plot(kind='scatter',x='Word Count', y='Queries',color='Blue',label='MTurk',ax=ax_new)
    plt.show()

    print("#------ Queries vs Word Count (per edit) for Classroom -----------#")
    print(grpd1_new.describe())
    print("#------ Ratings vs Word Count (per edit) for MTurk-----------#")
    print(grpd2_new.describe())

if __name__ == "__main__":
    main()
