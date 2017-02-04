import pdb
from collections import Counter
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
    # return counterJaccardSimilarity(c1, c2)
    return counter_cossim(c1, c2)

def setJaccardSimilarity(s1, s2):
    intersect = s1 & s2
    lengthUnion = len(s1 | s2)
    if lengthUnion == 0:
        return 0.0, set()
    # return (len(intersect) / lengthUnion) , lengthUnion, intersect
    return (len(intersect) / lengthUnion) , intersect

def stringJaccardSimilarity(string1, string2):
    wordSet1 = set(string1.split())
    wordSet2 = set(string2.split())
    return setJaccardSimilarity(wordSet1, wordSet2)

#def jaccardSimilarity(method1, method2, featureWeights = {'concepts': 3,'modifier': 0.1, 'returnType': 0.1, 'annotations': 3, 'exceptions' : 2}, nl_sim=0.0):
def jaccardSimilarity(method1, method2, featureWeights = {}, nl_sim=0.0):
    """ Given a two methods (class MethodFeatureVector), 
        return the Jaccard Similarity between them and a dictionary of intersections."""
    info_dict = {}
    
    # Certain features to be skipped, like lineCount
    # modifier is skipped because it is a choice based on project
    # nl_features = set(['java_doc','comments','variables','constants', 'types'])
    
    # f1 is a conscise way of representing features dictionary for method1
    f1 = method1.features
    f2 = method2.features

    commonFeatures = (f1.keys() & f2.keys()) - method.nl_features | set(['types']) 
    jaccard_sim_total = 0.0
    count = 0
    for key in commonFeatures:
        count += 1
        if isinstance(f1[key], dict):
            # In case of method vectors, dictionaries are usually counters
            # sim, count, intersections = counterJaccardSimilarity(f1[key], f2[key])
            sim, intersections = counter_cossim(f1[key], f2[key])
        elif isinstance(f1[key], list):
            sim, intersections = listJaccardSimilarity(f1[key], f2[key])
        elif isinstance(f1[key], set):
            # textbook definition of jaccard similarity
            sim, intersections = setJaccardSimilarity(f1[key], f2[key])
        elif isinstance(f1[key], str):
            sim, intersections = stringJaccardSimilarity(f1[key], f2[key])
        elif isinstance(f1[key], int) or isinstance(f1[key], float) or isinstance(f1[key], bool):
            sim = 1 if f1[key] == f2[key] else 0
            intersections = set([f1[key]])
        else:
            # some type we do not know how to deal with
            print("Unknown type for jaccard similarity :",key)
            continue
        
        """
        if sim > 0:
            print("Feature:",key)
            print(f1[key])
            print(f2[key])
            print("Score:",sim)
            print("Intersections:",intersections)
        """
        
        # additional functionality of bosting certain features
        if key in featureWeights:
            weight = int(featureWeights[key])
        else:
            weight = 1

        jaccard_sim_total += weight * sim 
        if sim > 0:
            info_dict[key] = intersections
    
    """
    if jaccard_sim_total > 0:
        print("Total:",jaccard_sim_total)
        print("Count:", count)
    """
    # add nl_sim as a feature
    jaccard_sim_total += nl_sim
    count += 1

    jaccard_sim = jaccard_sim_total / count
    size_diff = abs(sum(f1['statements'].values()) - sum(f2['statements'].values()))
    # jaccard_sim = (jaccard_sim_total / count) - (size_diff / 10000)
    info_dict['jaccard_sim'] = jaccard_sim
    info_dict['size_diff'] = size_diff

    return jaccard_sim, info_dict

def nl_similarity(method1, method2, nl_dict, nl_model, nl_weight):
    vec1_bow = nl_dict.doc2bow(method1.nl_tokens)
    vec1 = nl_model[vec1_bow]
    
    vec2_bow = nl_dict.doc2bow(method2.nl_tokens)
    vec2 = nl_model[vec2_bow]
    
    return gensim.matutils.cossim(vec1, vec2)

def proposed_similarity(method1, method2, nl_dict, nl_model, nl_weight=0.5):
    """ Our proposed similarity metric """
    # average of jaccard indexes of sets
    nl_sim = nl_similarity(method1, method2, nl_dict, nl_model, nl_weight)
    jaccard_sim, info_dict = jaccardSimilarity(method1, method2, nl_sim=nl_sim)

    info_dict['nl_sim'] = nl_sim
    info_dict['jaccard_sim'] = jaccard_sim
    # proposed_sim = (1 - nl_weight) * jaccard_sim + nl_weight * nl_sim
    # print(proposed_sim)    
    # return proposed_sim, info_dict
    return jaccard_sim, info_dict
