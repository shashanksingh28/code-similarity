import json
import re
from collections import Counter
from nltk.corpus import stopwords
import pdb

DEBUG = True

class MethodFeatureVector:
    """ A method object represents Java Methods parsed via the featureExtractor.jar """
    textTokenizationPattern = re.compile(r"(\r\n|\n|\.(?!\d)|[,;#?!$]|\s\s+)")
    
    def __init__(self, jsonString):
        """ Always initialize from JSON strings like {key1 : value1, key2: value2..}"""
        jsonObj = json.loads(jsonString.strip())
        
        self.name = jsonObj['name']
        if 'className' in jsonObj:
            self.className = jsonObj['className']
        self.isEmpty = jsonObj['isEmpty']

        # pure text form of the method
        self.rawText = jsonObj['text']
        
        # extracted features 
        self.features = {}

        # String or numeric features
        self.features['returnType'] = str(jsonObj['returnType'])
        self.features['modifier'] = int(jsonObj['modifier'])
        self.features['lineCount'] = int(jsonObj['lineCount']) 
        if 'javaDoc' in jsonObj:
            self.features['javaDoc'] = str(jsonObj['javaDoc']).lower()
        if 'comments' in jsonObj:
            self.features['comments'] = str(jsonObj['comments']).lower()
        
        # List or set features
        self.features['paramTypes'] = list(jsonObj['paramTypes'])
        self.features['exceptions'] = set(jsonObj['exceptions'])
        self.features['annotations'] = set(jsonObj['annotations'])
        self.features['concepts'] = set(jsonObj['concepts'])

        # Counter Dictionaries
        self.features['methodCalls'] = dict(jsonObj['methodCalls'])
        self.features['variables'] = dict(jsonObj['variables'])
        self.features['constants'] = dict(jsonObj['constants'])
        self.features['types'] = dict(jsonObj['types'])
        self.textTokens = MethodFeatureVector.__getTextTokens(self.rawText)
        self.langTokens = self.__getLanguageTokens()

    
    def getJaccardSimilarity(self, otherMethod):
        # pdb.set_trace()
        intersections = []
        if DEBUG:
            print("Jaccard:",self.rawText,"------- with -------", otherMethod.rawText, sep="\n")
        totalSim = 0
        for feature in self.features:
            # Skip line counts because they are not one-hot encoded set features
            if feature == 'lineCount':
                continue
            if DEBUG:
                print(feature)
            if feature not in otherMethod.features:
                continue
            typeOf = type(self.features[feature])
            if typeOf is dict:
                # this would be the case when it is a dict of counters
                sim, commonKeys = MethodFeatureVector.__getDictJaccardSim(self.features[feature], otherMethod.features[feature])
                totalSim += sim
                intersections += list(commonKeys)
            elif typeOf is set:
                if len(self.features[feature]) == 0 or len(otherMethod.features[feature]) == 0:
                    continue
                # textbook definition of Jaccard similarity - intersection / union of sets
                totalCount = len(self.features[feature]) + len(otherMethod.features[feature])
                if DEBUG:
                    print(feature,":",self.features[feature] & otherMethod.features[feature])
                intCount = len(self.features[feature] & otherMethod.features[feature])
                intersections += self.features[feature] & otherMethod.features[feature]
                totalSim += (intCount / totalCount)
            elif typeOf is list:
                # this is an intersection of lists
                sim, commonKeys = MethodFeatureVector.__getListJaccardSim(self.features[feature], otherMethod.features[feature])
                totalSim += sim
                intersections += list(commonKeys)
            else:
                # this should be just element comparison
                if self.features[feature] == otherMethod.features[feature]:
                    if DEBUG:
                        print("Match:",self.features[feature])
                    totalSim += 1
                    intersections += [self.features[feature]]
        if DEBUG:
            print(totalSim, intersections)
        return totalSim, intersections

    @staticmethod
    def __getDictJaccardSim(dict1, dict2):
        """ Given two Counters, find the Jaccard similarities """
        commonKeys = dict1.keys() & dict2.keys()
        if len(commonKeys) == 0: return 0, commonKeys
        if DEBUG:
            print(commonKeys)
        totalCount = sum(dict1.values()) + sum(dict2.values())
        if totalCount == 0:
            return 0

        intCount = 0
        for key in commonKeys:
            intCount += min(dict1[key], dict2[key])
        
        return ((intCount / totalCount) , commonKeys)

    @staticmethod
    def __getListJaccardSim(list1, list2):
        """ Given two lists, give the Jaccard similarities """
        countList1 = Counter(list1)
        countList2 = Counter(list2)
        return MethodFeatureVector.__getDictJaccardSim(countList1, countList2)
        
    @staticmethod
    def __getTextTokens(text):
        """ If we consider this to be simply a text file, what would we get"""
        if DEBUG:
            print("Text before:", text)
        tokens = re.sub(MethodFeatureVector.textTokenizationPattern, " ", text).split()
        # had we treated this as pure text, we would filter it the same way as language
        filtered_words = [word.strip() for word in tokens if word.strip() not in stopwords.words('english') and len(word.strip()) > 1]
        if DEBUG:
            print("Text after:", filtered_words)

        return filtered_words
    
    @staticmethod
    def __getListFromCounts(dict1):
        """ Given a dictionary with key as elements and their counts, return a flat list with duplicates """
        elements = []
        for key in dict1:
            elements += [key] * dict1[key]
        return elements

    @staticmethod
    def __languageParse(token):
        """ Given a word, parse it(camelCase) and return a list of language tokens.
            Skip stopwords and if possible add lemma and synonym features them"""
        # ToDo: can add multiple features here
        # print("Lang before:",token)
        # literals are quoted and there may be . operators, remove them first
        token = re.sub('[".]',' ', token)

        # Camel Case Split
        token = re.sub(r'((?<=[a-z])[A-Z]|(?<!\A)[A-Z](?=[a-z]))', r' \1', token).lower()
        tokens = token.split()
        
        # filter stop words and single chars assuming they don't have any information
        # note that this will also remove single digit numbers
        filtered_words = [word.strip() for word in tokens if word.strip() not in stopwords.words('english')]
        # print("Lang after:",filtered_words)

        return filtered_words

    def __getLanguageTokens(self):
        """ Language tokens are all possible natural language components in the function.
            Primarily variable and class names, method names, comments and documentation, etc"""
        
        nonLanguageFeatures = ['modifier', 'lineCount', 'concepts', 'operators']
        
        languageTokens = []
        words = []
        for key in self.features:
            if key in nonLanguageFeatures:
                continue
            if type(self.features[key]) is dict:
                words += MethodFeatureVector.__getListFromCounts(self.features[key])
            elif type(self.features[key]) is list:
                words += self.features[key]
            elif type(self.features[key]) is set:
                words += list(self.features[key])
            else:
                words += [self.features[key]]
        
        for word in words:
            languageTokens += MethodFeatureVector.__languageParse(str(word))
        
        return languageTokens    
    
