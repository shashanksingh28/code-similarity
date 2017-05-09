#!/usr/bin/env python3
import sys
import os
import pandas as pd
import numpy as np
import json
import matplotlib.pyplot as plt
from scipy.stats import ranksums

def discount(df_row):
    return df_row['rating'] / 2 ** (df_row['rank'])

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

mturk_folder='.'
filter_users = [1,3]
name = "MTurk"
bins = np.linspace(-10,100,100) - 0.5
xticks = range(0, 100,5)


dont_care_rating = 0
alpha = 5

def R_value(r, j):
    """ Assuming the r passed is already max for a given j"""
    return r / ( 2 ** ((j - 1 )/ (alpha - 1)) )

def R_u(user_votes):
    """ Return R_a and R_a_max as per formula """
    # first group by index
    j_group = user_votes.groupby(['rank'])
    r_values = []
    # for each index j, calculate actual utility
    for name, group in j_group:
        max_rating = group.max()['rating']
        r_value = R_value(max_rating, name)
        # print(group)
        # print(r_value)
        r_values.append(r_value)
    # print(r_values)
    
    #--------- Max secion -----------#
    # Sort by ratings, and assign rank accordingly
    user_votes_sorted = user_votes.sort_values(by='rating',ascending=False)
    r_max_values = []
    for i, row in enumerate(user_votes_sorted.values):
        r_max_values.append(R_value(row[2], i + 1))

    # value for each user should be between 0 - 1
    assert sum(r_max_values) >= sum(r_values)

    return sum(r_values), sum(r_max_values)

def R(votes):
    r = 0
    for name, group in votes.groupby(['username']):
        # print(group)
        ru, rmax = R_u(group)
        r += ru / rmax
        # print(ru / rmax)
        # print(r)
    r = 100 * r
    return r


def main():
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
    
    votes = study_votes.merge(users_mturk, left_on='user_id', right_on='id')[['question','source','rank','rating','code','username','voteid']]
     
    weights = weights_mturk[~weights_mturk.user_id.isin(filter_users)]\
                    .merge(users_mturk, left_on='user_id', right_on='id')[['signature', 'structure', 'concepts', 'language','username','code','vote_id']]
    
    submissions = submissions_mturk[~submissions_mturk.user_id.isin(filter_users)]\
                    .merge(users_mturk, left_on='user_id',right_on='id')[['question','code','username']]
    
    copies = cutcopy_mturk[~cutcopy_mturk.user_id.isin(filter_users)]\
                    .merge(users_mturk, left_on='user_id',right_on='id')[['question','source','rank','username']]
    
    # Cleaning
    weights.loc[:,'code'] = weights.loc[:,'code'].apply(cleanWeightsCode)    
    votes.loc[:,'word_count'] = votes['code'].apply(wordcount)
    weights.loc[:,'word_count'] = weights['code'].apply(wordcount)
    votes = votes[votes.rating != 0]

    #plt.hist(weights['word_count'], bins, alpha=0.5, label='Custom Search')
    #plt.hist(votes['word_count'], bins, alpha=0.5, label='Votes')
    #plt.legend(loc='upper right')
    #plt.xticks(xticks)
    #plt.show()

    # filter users who did not submit answers
    # study_votes = study_votes[study_votes.user_id.isin(submissions.user_id.unique())]
    # hist = study_votes['user_id'].value_counts().plot(kind='bar', title=name + ' Vote Count Distribution')
    # hist.set_xlabel('Student-Id')
    # hist.set_ylabel('Votes')
    # plt.show()
    print(name + " Users:", len(votes.username.unique()))
    print(name + " Total Votes:",votes.shape[0])
    print(name + " Total Weight customizations:", weights.shape[0])

    print(weights.describe())
    # uniq_students = pd.merge(users, study_votes, how='inner', left_on='id', right_on='user_id')
    # uniq_students[['id', 'username']].drop_duplicates().to_csv(name + '_students.csv',index=None)
    
    # of students copy pasted question to seek information (containing keyword class)
    q_copy = votes[votes.apply(lambda x : x['code'].strip().startswith('class'), axis = 1)]
    q_copy_users = q_copy['username'].unique()
    print("Copied:",len(q_copy_users))

    baseline_votes = votes[votes['source'] == 0]
    print("### Baseline Votes ###")
    print(baseline_votes.describe())
    codereco_votes = votes[votes['source'] == 1]
    print("### CodeReco Votes ###")
    print(codereco_votes.describe())
    # ignoring this as we are following a similar, more established metric
    # codereco_votes.loc[:,'discounted_rating'] = codereco_votes.apply(discount, axis=1)
    # baseline_votes.loc[:,'discounted_rating'] = baseline_votes.apply(discount, axis=1)
    
    #rateplot = plt.boxplot([baseline_votes['rating'], baseline_votes['discounted_rating'], codereco_votes['rating'], codereco_votes['discounted_rating']], \
    #        labels=['Baseline','Baseline Rank-Discounted', 'CodeReco', 'CodeReco Rank-Discounted'], showmeans=True)
    rateplot = plt.boxplot([baseline_votes['rating'], codereco_votes['rating']],labels=['Baseline', 'CodeReco'], showmeans=True)
    plt.title(name + ' Ratings') 
    plt.show()
    print(name + " Baseline Distribution different than CodeReco:")
    print(ranksums(baseline_votes['rating'], codereco_votes['rating']))
    # print(name + " Discounted Baseline different than discounted CodeReco:")
    # print(ranksums(baseline_votes['discounted_rating'], codereco_votes['discounted_rating']))

    weightplot = weights[['signature','structure','concepts','language']].plot.box(showmeans=True)
    plt.title(name + ' Weight Customizations (Exploration)')
    plt.show()
    
    # For finding weight correlations, filter weights that were not associated with votes
    weights_filtered = weights.dropna()
    weight_rating = weights_filtered[['vote_id','signature','structure','concepts','language','username']].merge(codereco_votes, left_on=['username','vote_id'], right_on=['username','voteid'])
    weight_rating[['signature','structure','concepts','language']].plot.box(showmeans=True)
    plt.title(name + ' Weight Customizations (Ratings)')
    plt.show()
    
    # pd.scatter_matrix(weight_rating[['signature','structure','concepts','language','rating']])
    # plt.suptitle(name + ' Weight-Rating Correlations')
    # plt.show()
    print("Weight Coorrelations:")
    print(weight_rating[['signature','structure','concepts','language','rating']].corr())
    print("Difference between Language and Signature weights:")
    print(ranksums(weight_rating['language'], weight_rating['signature']))
    print("Difference between Language and Structure weights:")
    print(ranksums(weight_rating['language'], weight_rating['structure']))
    print("Difference between Language and Concept weights:")
    print(ranksums(weight_rating['language'], weight_rating['concepts']))
    
    #study_weights.describe().to_csv(name + '_study_weights.csv')

    #print(study_weights.describe())
    #vote_weights.describe().to_csv(name +'_vote_weights.csv')
    
    print("Copies from Baseline:",len(copies[copies['source'] == 0]))
    print("Copies from CodeReco:",len(copies[copies['source'] == 1]))

    submissions.loc[:,'word_count'] = submissions['code'].apply(wordcount)
    print("Q1 Submissions:",len(submissions[submissions['question'] == 1]))
    print("Q2 Submissions:",len(submissions[submissions['question'] == 2]))
    print("Average words per submission",submissions['word_count'].mean())

    print("R score for Baseline:",R(votes[['username','rank','rating']][votes.source == 0]))
    print("R score for CodeReco:",R(votes[['username','rank','rating']][votes.source == 1]))

if __name__ == "__main__":
    main()
