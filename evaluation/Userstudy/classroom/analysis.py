#!/usr/bin/env python3
import sys
import os
import pandas as pd
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

asu_folder='ASU101'
cpi_folder='CPI101'
asu_filter_users = [1,20,21]
cpi_filter_users = [1,2,3,4,5,6,67]
name = "Classroom"

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
    
    cpi_submissions = submissions_cpi[~submissions_cpi.user_id.isin(cpi_filter_users)]\
                    .merge(users_cpi, left_on='user_id',right_on='id')[['question','code','username']]
    asu_submissions = submissions_asu[~submissions_asu.user_id.isin(asu_filter_users)]\
                    .merge(users_asu, left_on='user_id',right_on='id')[['question','code','username']]
    
    cpi_copy = cutcopy_cpi[~cutcopy_cpi.user_id.isin(cpi_filter_users)]\
                    .merge(users_cpi, left_on='user_id',right_on='id')[['question','source','rank','username']]
    asu_copy = cutcopy_asu[~cutcopy_asu.user_id.isin(asu_filter_users)]\
                    .merge(users_asu, left_on='user_id',right_on='id')[['question','source','rank','username']]
    
    votes = pd.concat([cpi_votes, asu_votes], ignore_index=True)
    weights = pd.concat([cpi_weights, asu_weights], ignore_index=True)
    submissions = pd.concat([cpi_submissions, asu_submissions], ignore_index=True)
    copies = pd.concat([cpi_copy, asu_copy], ignore_index=True)
    
    # Cleaning
    weights.loc[:,'code'] = weights.loc[:,'code'].apply(cleanWeightsCode)    
    votes.loc[:,'word_count'] = votes['code'].apply(wordcount)
    weights.loc[:,'word_count'] = weights['code'].apply(wordcount)
    votes = votes[votes.rating != 0]

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
    rateplot = plt.boxplot([baseline_votes['rating'], codereco_votes['rating']], labels=['Baseline', 'CodeReco'], showmeans=True)
    plt.title(name + ' Ratings')
    plt.show()
    print(name + " Baseline Distribution different than CodeReco:")
    print(ranksums(baseline_votes['rating'], codereco_votes['rating']))
    # print(name + " Discounted Baseline different than discounted CodeReco:")
    # print(ranksums(baseline_votes['discounted_rating'], codereco_votes['discounted_rating']))

    weightplot = weights[['signature','structure','concepts','language']].plot.box(showmeans=True)
    plt.title(name + ' Weight Customizations (Exploration)')
    plt.show()
    
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
    # study_weights.describe().to_csv(name + '_study_weights.csv')

    # print(study_weights.describe())
    # vote_weights.describe().to_csv(name +'_vote_weights.csv')
    
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
