from collections import Counter, defaultdict
from . import lang
import numpy as np
import gensim
import math
from . import method

def counter_cossim(c1, c2):
    """ Given two counters, treat them as feature vectors and return cosine similarity"""
    common_keys = c1.keys() & c2.keys()
    d1 = math.sqrt(sum([value ** 2 for value in c1.values()]))
    d2 = math.sqrt(sum([value ** 2 for value in c2.values()]))
    denom = d1 * d2
    if denom == 0:
        return 0.0, c1 & c2
    
    num = 0
    for key in common_keys:
        num += c1[key] * c2[key]

    return float(num) / denom, c1 & c2 
    
def listJaccardSimilarity(l1, l2):
    """ Given 2 lists, return jaccard similarity and a set of intersections.
        Note that this method does double count if element present twice in both lists. """
    c1 = Counter(l1)
    c2 = Counter(l2)
    return counter_cossim(c1, c2)

def setJaccardSimilarity(s1, s2):
    intersect = s1 & s2
    lengthUnion = len(s1 | s2)
    if lengthUnion == 0:
        return 0.0, set()
    return (len(intersect) / lengthUnion) , intersect

def stringJaccardSimilarity(string1, string2):
    wordSet1 = set(string1.split())
    wordSet2 = set(string2.split())
    return setJaccardSimilarity(wordSet1, wordSet2)

def jaccardSimilarity(method1, method2, weights, nl_sim):
    """ Given a two methods (class MethodFeatureVector), 
        return the Jaccard Similarity between them and a dictionary of intersections."""
    info_dict = defaultdict(Counter)
    
    # f1 is a conscise way of representing features dictionary for method1
    f1 = method1.features
    f2 = method2.features
    commonFeatures = (f1.keys() & f2.keys()) - set(method.lang_features)
    sim_total = 0.0

    info = defaultdict(Counter)
    for feature_category in weights:
        category_count = 0
        category_sim = 0
        for feature in weights[feature_category]['features']:
            if not feature in f1 or not feature in f2:
                continue
            if isinstance(f1[feature], dict):
                # In case of method vectors, dictionaries are usually counters
                # sim, count, intersections = counterJaccardSimilarity(f1[key], f2[key])
                sim, intersections = counter_cossim(f1[feature], f2[feature])
            elif isinstance(f1[feature], list):
                sim, intersections = listJaccardSimilarity(f1[feature], f2[feature])
            elif isinstance(f1[feature], set):
                # textbook definition of jaccard similarity
                sim, intersections = setJaccardSimilarity(f1[feature], f2[feature])
            elif isinstance(f1[feature], str):
                sim, intersections = stringJaccardSimilarity(f1[feature], f2[feature])
            elif isinstance(f1[feature], int) or isinstance(f1[feature], float) or isinstance(f1[feature], bool):
                sim = 1 if f1[feature] == f2[feature] else 0
                intersections = set([f1[feature]])
            
            category_count += 1
            category_sim += sim
            if sim > 0:
                info[feature_category][feature] = intersections
        # overall score for featur category
        if not category_count == 0:
            info[feature_category]['score'] =  category_sim / category_count
        else:
            info[feature_category]['score'] = 0
     

    # lang sim was calculated from a different method
    lang_sim = weights['language']['weight'] * nl_sim
    info['language']['score'] = nl_sim
    total_sim = 0.0
    for feature_category in weights:
        total_sim += weights[feature_category]['weight'] * info[feature_category]['score']
    normalized_sim = total_sim / len(weights)
    info['simlarity'] = normalized_sim
    return normalized_sim, info

def gensim_lang_cossim(method1, method2, dictionary, model):
    vec1_bow = dictionary.doc2bow(method1.nl_tokens)
    vec1 = model[vec1_bow]
    
    vec2_bow = dictionary.doc2bow(method2.nl_tokens)
    vec2 = model[vec2_bow]
    
    return gensim.matutils.cossim(vec1, vec2)

def proposed_similarity(method1, method2, nl_dict, nl_model, weights):
    """ Our proposed similarity metric """
    nl_sim = gensim_lang_cossim(method1, method2, nl_dict, nl_model)
    jaccard_sim, info_dict = jaccardSimilarity(method1, method2, weights, nl_sim=nl_sim)
    # jaccard_sim, info_dict = jaccardSimilarity(method1, method2, weights, None)
    return jaccard_sim, info_dict

# temp method used for ontology based comparison
def concept_tags_similarity(method1, method2, nl_dict, nl_model):
    """ UPitt concept tags similarity """
    # nl_sim = gensim_lang_cossim(method1, method2, nl_dict, nl_model)
    jaccard_sim, info_dict = counter_cossim(method1.concepts, method2.concepts)
    # avg_sim = (jaccard_sim + nl_sim) / 2
    # if len(info_dict) > 1:
    #    print(method2)
    return jaccard_sim, info_dict
