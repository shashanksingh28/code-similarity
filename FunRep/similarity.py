import pdb
from collections import Counter
import FunRep.lang as lang
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

def counterJaccardSimilarity(c1, c2):
    """ Given 2 counter dictionaries, return jaccard similarity and a set of intersections """
    common_counter = c1 & c2
    if len(common_counter) == 0:
        return 0.0, set()

    total_count = sum(c1.values()) + sum(c2.values())
    intersect_count = sum(common_counter.values())
    return (intersect_count / total_count) , set(common_counter.keys())

def listJaccardSimilarity(l1, l2):
    """ Given 2 lists, return jaccard similarity and a set of intersections.
        Note that this method does double count if element present twice in both lists. """
    c1 = Counter(l1)
    c2 = Counter(l2)
    return counterJaccardSimilarity(c1, c2)

def setJaccardSimilarity(s1, s2):
    intersect = s1 & s2
    lengthUnion = len(s1) + len(s2)
    if lengthUnion == 0:
        return 0.0, set()
    return (len(intersect) / lengthUnion) , intersect 

def stringJaccardSimilarity(string1, string2):
    wordSet1 = set(string1.split())
    wordSet2 = set(string2.split())
    return setJaccardSimilarity(wordSet1, wordSet2)

def jaccardSimilarity(method1, method2, featureWeights = dict()):
    """ Given a two methods (class MethodFeatureVector), 
        return the Jaccard Similarity between them and a dictionary of intersections."""
    featureIntersections = {}
    
    # Certain features to be skipped, like lineCount
    skipFeatures = set(['line_count','class_name'])

    # f1 is a conscise way of representing features dictionary for method1
    f1 = method1.features
    f2 = method2.features
    
    commonFeatures = (f1.keys() & f2.keys()) - skipFeatures
    jaccardSim = 0.0
    
    for key in commonFeatures:
        if isinstance(f1[key], dict):
            # In case of method vectors, dictionaries are usually counters
            sim, intersections = counterJaccardSimilarity(f1[key], f2[key])
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
        
        # additional functionality of boosting certain features
        if key in featureWeights:
            weight = int(featureWeights[key])
        else:
            weight = 1

        jaccardSim += weight * sim
        if sim > 0:
            featureIntersections[key] = intersections

    return jaccardSim, featureIntersections

def stream_to_arr(gensim_stream):
    """ gensim stream consists of a list of tuples """
    # HACK! FIND an elegant way
    arr = np.zeros((1, 200))
    for tup in gensim_stream:
        arr[0,tup[0]] = tup[1]
    return arr

def proposed_similarity(method1, method2, nl_dict, nl_model, nl_ratio=0.5):
    featureIntersections = {}
    
    # Certain features to be skipped, like lineCount
    skip_features = set(['line_count','class_name'])
    nl_features = set(['java_doc','comments','variables','constants']) 

    # f1 is a conscise way of representing features dictionary for method1
    f1 = method1.features
    f2 = method2.features
    
    jaccard_features = (f1.keys() & f2.keys()) - skip_features - nl_features
    jaccard_sim = 0.0
    
    for key in jaccard_features:
        if isinstance(f1[key], dict):
            # In case of method vectors, dictionaries are usually counters
            sim, intersections = counterJaccardSimilarity(f1[key], f2[key])
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
        
    vec1_bow = nl_dict.doc2bow(method1.nl_tokens)
    vec1 = nl_model[vec1_bow]
    
    vec2_bow = nl_dict.doc2bow(method2.nl_tokens)
    vec2 = nl_model[vec2_bow]

    vec1_arr = stream_to_arr(vec1)
    vec2_arr = stream_to_arr(vec2)
    nl_sim = cosine_similarity(vec1_arr, vec2_arr).ravel()[0]
    proposed_sim = (1 - nl_ratio) * jaccard_sim + nl_ratio * nl_sim
    # print(proposed_sim)    
    return proposed_sim, dict()
