from FunRep.util import *
from FunRep.similarity import *
import os
import numpy as np

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

def bag_of_words(vectors):
    bag = []
    for vector in vectors:
        bag.append(' '.join(vector.tokens))
    return bag

def tfIdf_models(vectors):
    bow = bag_of_words(vectors)
    tfidf_fitted_model = TfidfVectorizer(min_df=1).fit(bow)
    tfidf_transformed_data = tfidf_fitted_model.transform(bow)
    return tfidf_fitted_model, tfidf_transformed_data
   
def jaccard_kNearest(method, solutions, k = 1, featureWeights=dict()):
    """ Return K Nearest methods based on jaccard similarity.
        Returns a list of tuples containing (score, solutions_index, similarityDictionary)
    """
    results = [] 
    similarities = [ jaccardSimilarity(method, solution, featureWeights) for solution in solutions ]
    similarityScores = [tup[0] for tup in similarities]
    sortedIndices = np.argsort(similarityScores)
    kNearestIndices = sortedIndices[-k:]

    for i in kNearestIndices[::-1]:
        results.append((float(np.round(similarities[i][0], decimals = 3)), i, similarities[i][1]))

    return results

def cosine_kNearest(ndim_num_vector, ndim_num_solutions, dim_names, k = 1):
    """ Provided a single vector and a list of vectors with the same dimensions, finds closest
        based on cosine similarity. Features is just a list of features, so it also provides
        a list of which features contributed to the cosine 
        Returns a list of tuples containing (score, solutions_index, similarityDictionary)
    """
    similarities = cosine_similarity(ndim_num_vector, ndim_num_solutions).ravel()
    
    # since we need indexes
    sortedIndices = np.argsort(similarities)
    kNearestIndices = sortedIndices[-k:]
    results = []
    
    for i in kNearestIndices[::-1]:
        intersectingTokens = []
        for j, feature in enumerate(dim_names):
            if ndim_num_vector[0,j] > 0 and ndim_num_solutions[i,j] > 0:
                intersectingTokens.append(feature)
        results.append((float(np.round(similarities[i], decimals = 3)), i, intersectingTokens))
    
    return results

