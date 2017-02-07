from .util import *
from .similarity import *
import os
import numpy as np
from . import lang

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from gensim import corpora, models, similarities

def jaccard_kNearest(method, solutions, k=3):
    """ Return K Nearest methods based on jaccard similarity.
        Returns a list of tuples containing (score, solutions_index, similarityDictionary)
    """
    results = [] 
    similarities = [jaccardSimilarity(method, solution, {'concepts':2}) for solution in solutions]
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

def create_tfIdf_model(documents):
    """ Given solution vectors, return trained gensim language models for similarity """
    dictionary = corpora.Dictionary(documents)
    corpus = [dictionary.doc2bow(text) for text in documents]

    tfidf_model = models.TfidfModel(corpus)
    # corpus_tfidf = tfidf_model[corpus]
    # lsi_model = models.LsiModel(corpus_tfidf, id2word=dictionary, num_topics=100)
    return dictionary, tfidf_model

def proposed_kNearest(method, solutions, nl_dict, nl_model, k, weights):
    """ Our proposed similarity kernel. """
    results = []
    similarities = [proposed_similarity(method, solution, nl_dict, nl_model, weights) for solution in solutions]
    sim_scores = [tup[0] for tup in similarities]
    sorted_idx = np.argsort(sim_scores)
    kNearest_idx = sorted_idx[-k:]

    for i in kNearest_idx[::-1]:
        results.append((float(np.round(similarities[i][0], decimals = 3)), i, similarities[i][1]))
    return results
