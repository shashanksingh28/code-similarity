import json
import re
from collections import Counter
from nltk.corpus import stopwords
import pdb

class MethodFeatureVector:
    """ A method object represents Java Methods parsed via the featureExtractor.jar 
        This class should implement the bare bones needed for a baseline.
    """
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
        
        # List features
        self.features['paramTypes'] = list(jsonObj['paramTypes'])
        
        # set features, where we consider only if present or absent
        self.features['exceptions'] = set(jsonObj['exceptions'])
        self.features['expressions'] = set(jsonObj['expressions'])
        self.features['statements'] = set(jsonObj['statements'])
        self.features['annotations'] = set(jsonObj['annotations'])
        self.features['concepts'] = set(jsonObj['concepts'])

        # Counter Dictionaries
        self.features['methodCalls'] = dict(jsonObj['methodCalls'])
        self.features['variables'] = dict(jsonObj['variables'])
        self.features['constants'] = dict(jsonObj['constants'])
        self.features['types'] = dict(jsonObj['types'])

        self.textTokens = MethodFeatureVector.__getTextTokens(self.rawText)
        self.langTokens = self.__getLanguageTokens()

        
    @staticmethod
    def __getTextTokens(text):
        """ If we consider this to be simply a text file, what would we get"""
        tokens = re.sub(MethodFeatureVector.textTokenizationPattern, " ", text).split()
        # had we treated this as pure text, we would filter it the same way as language
        filtered_words = [word.strip() for word in tokens if word.strip() not in stopwords.words('english') and len(word.strip()) > 1]

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
        
        nonLanguageFeatures = ['modifier', 'lineCount', 'concepts', 'expressions', 'statements']
        
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
    
