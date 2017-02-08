from .util import *
from .similarity import *
import os
import numpy as np
from . import lang

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from gensim import corpora, models, similarities

def cosine_kNearest(method, solutions, dictionary, model, k):
    """ Provided a single vector and a list of vectors with the same dimensions, finds closest
        based on cosine similarity. Features is just a list of features, so it also provides
        a list of which features contributed to the cosine 
        Returns a list of tuples containing (score, solutions_index, similarityDictionary)
    """
    similarities = [ gensim_lang_cossim(method, solution, dictionary, model) for solution in solutions]
    # since we need indexes
    sortedIndices = np.argsort(similarities)
    kNearestIndices = sortedIndices[-k:]
    results = []
    method_bow = dictionary.doc2bow(method.tokens)
    method_bow_keys = set([tup[0] for tup in method_bow])
    for i in kNearestIndices[::-1]:
        intersectingTokens = []
        solution_bow = dictionary.doc2bow(solutions[i].tokens)
        solution_bow_keys = set([tup[0] for tup in solution_bow])
        intersect_keys = method_bow_keys & solution_bow_keys
        intersect_tokens = [dictionary[key] for key in intersect_keys]
        results.append((float(np.round(similarities[i], decimals = 4)), i, intersect_tokens))    
    return results

def create_tfIdf_model(documents):
    """ Given solution vectors, return trained gensim language models for similarity """
    dictionary = corpora.Dictionary(documents)
    # remove the most infrequent words
    dictionary.filter_extremes(no_below=1)
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
