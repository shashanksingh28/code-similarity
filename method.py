import json
import re
from collections import Counter
from nltk.corpus import stopwords
import pdb

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
            self.features['javaDoc'] = str(jsonObj['javaDoc'])
        if 'comments' in jsonObj:
            self.features['comments'] = str(jsonObj['comments'])
        
        # List or set features
        self.features['paramTypes'] = list(jsonObj['paramTypes'])
        self.features['methodCalls'] = set(jsonObj['methodCalls'])
        self.features['exceptions'] = set(jsonObj['exceptions'])
        self.features['annotations'] = set(jsonObj['annotations'])
        self.features['concepts'] = set(jsonObj['concepts'])

        # Counter Dictionaries
        self.features['variables'] = dict(jsonObj['variables'])
        self.features['constants'] = dict(jsonObj['constants'])
        self.features['types'] = dict(jsonObj['types'])
        self.textTokens = MethodFeatureVector.__getTextTokens(self.rawText)
        self.langTokens = self.__getLanguageTokens()

    
    @staticmethod
    def __getTextTokens(text):
        """ If we consider this to be simply a text file, what would we get"""
        return re.sub(MethodFeatureVector.textTokenizationPattern, " ", text).split()
    
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
        # print("Input:",token)
        # literals are quoted, so remove quotes first
        token = re.sub('"',' ', token)

        # Camel Case Split
        token = re.sub(r'((?<=[a-z])[A-Z]|(?<!\A)[A-Z](?=[a-z]))', r' \1', token).lower()
        tokens = token.split()
        
        # filter stop words and single chars assuming they don't have any information
        # note that this will also remove single digit numbers
        filtered_words = [word.strip() for word in tokens if word.strip() not in stopwords.words('english')]
        # print(filtered_words)

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
    
    
    ### Not sure if I would need functions below later ###
    
    

    def __getParsedTokens(self):
        """ If we consider this as using the parser for tokenization, what would we get?"""
        tokens = [self.features['modifier'] , self.features['returnType'], self.name]
        
        if 'javaDoc' in self.features:
            tokens += self.__getTextTokens(self.features['javaDoc'])
        if 'comments' in self.features:
            tokens += self.__getTextTokens(self.features['comments'])
        tokens += self.features['paramTypes'] + list(self.features['exceptions']) + list(self.features['annotations'])
        
        tokens += self.__getListFromCounts(self.features['operands'])
        tokens += self.__getListFromCounts(self.features['operators'])
        tokens += self.__getListFromCounts(self.features['types'])
        
        return tokens

    def getTextMatch(self, otherMethod):
        """ Return a list of matches based on pure text tokenization """
        myWords = self.textTokensCounter
        hisWords = otherMethod.textTokensCounter
        
        commonWords = myWords.keys() & hisWords.keys()   
        
        matches = []
        for word in commonWords:
            matches += [word] * min(myWords[word],hisWords[word])
        
        return matches
    
    def getTokenMatch(self, otherMethod):
        """ Return a list of matches based on parser tokenization """
        myWords = self.parsedTokensCounter
        hisWords = otherMethod.parsedTokensCounter
        
        commonWords = myWords.keys() & hisWords.keys()   
        
        matches = []
        for word in commonWords:
            matches += [word] * min(myWords[word],hisWords[word])
        
        return matches

