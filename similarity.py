import pdb
from collections import Counter

def counterJaccardSimilarity(c1, c2):
    """ Given 2 counter dictionaries, return jaccard similarity and a set of intersections """
    commonKeys = c1.keys() & c2.keys()
    totalCounts = sum(c1.values()) + sum(c2.values())
    commonCount = 0
    for key in commonKeys:
        commonCount += min(c1[key], c2[key])
    if totalCounts == 0:
        return 0.0, set()
    return (commonCount / totalCounts) , commonKeys

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

def jaccardSimilarity(method1, method2, featureWeights = dict()):
    """ Given a two methods (class MethodFeatureVector), 
        return the Jaccard Similarity between them and a dictionary of intersections."""
    featureIntersections = {}
    
    # Certain features to be skipped, like lineCount
    skipFeatures = set(['lineCount'])

    # f1 is a conscise way of representing features dictionary for method1
    f1 = method1.features
    f2 = method2.features
    
    commonFeatures = (f1.keys() & f2.keys()) - skipFeatures

    jaccardSim = 0.0
    
    for key in commonFeatures:
        fType = type(f1[key])
        if fType is dict:
            # In case of method vectors, dictionaries are usually counters
            sim, intersections = counterJaccardSimilarity(f1[key], f2[key])
        elif fType is list:
            sim, intersections = listJaccardSimilarity(f1[key], f2[key])
        elif fType is set:
            # textbook definition of jaccard similarity
            sim, intersections = setJaccardSimilarity(f1[key], f2[key])
        elif fType is int or fType is float or fType is str or fType is bool:
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
