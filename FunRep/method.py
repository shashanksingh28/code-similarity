import json
import re
from collections import Counter
from .lang import *

nl_features = set(['variables', 'constants', 'comments', 'java_doc', 'types'])

class MethodFeatureVector:
    """ A method object represents Java Methods parsed via the featureExtractor.jar 
        This class should implement the bare bones needed for a baseline.
    """
    
    def __init__(self, jsonString):
        """ Always initialize from JSON strings like {key1 : value1, key2: value2..}"""
        jsonObj = json.loads(jsonString.strip(), strict=False)
        
        self.name = jsonObj['name']
        if 'className' in jsonObj:
            self.class_name = jsonObj['className']
        self.is_empty = jsonObj['isEmpty']

        # pure text form of the method
        self.raw_text = jsonObj['text']
        self.line_count = int(jsonObj['lineCount'])

        # extracted features 
        self.features = {}

        # String or numeric features
        self.features['returnType'] = str(jsonObj['returnType'])
        self.features['modifier'] = int(jsonObj['modifier'])
        if 'javaDoc' in jsonObj:
            self.features['java_doc'] = str(jsonObj['javaDoc']).lower()
        if 'comments' in jsonObj:
            self.features['comments'] = str(jsonObj['comments']).lower()
        
        # List features
        self.features['paramTypes'] = MethodFeatureVector.parse_generics(list(jsonObj['paramTypes']))
        
        # set features, where we consider only if present or absent
        self.features['exceptions'] = set(jsonObj['exceptions'])
        self.features['annotations'] = set(jsonObj['annotations'])
        self.features['concepts'] = set(jsonObj['concepts'])
        self.features['methodCalls'] = set(jsonObj['methodCalls'])
        self.features['variables'] = set(jsonObj['variables'])
        self.features['constants'] = set(jsonObj['constants'])
        
        # Where counts matter, we use counter objects
        self.features['expressions'] = Counter(jsonObj['expressions'])
        self.features['statements'] = Counter(jsonObj['statements'])
        self.features['types'] = Counter(jsonObj['types'])
        
        self.tokens = meaningful_tokens_camelCase(self.raw_text)
        self.nl_tokens = text_pre_process(self.extract_lang_tokens())

    @staticmethod
    def parse_generics(string_iterable):
        """This is to deal with generics. If we have 'List<String>', split as List and String"""
        parsed_set = list()
        for element in string_iterable:
            found = re.search(r'(\w+)<(\w+)>',element)
            if found is not None:
                # we found use of generics, so let's split
                parsed_set.append(found.group(1))
                parsed_set.append(found.group(2))
            else:
                parsed_set.append(element)
        return parsed_set
    
    def extract_lang_tokens(self):
        """ Get only those tokens which one would consider to be natural language tokens
            E.g.: Comments, variable names, tokens, etc """
        # while we are using Tf-IDF
        # lang_keys = ['variables', 'constants', 'comments', 'java_doc', 'types']
        tokens = []

        # Name and parameters
        tokens.extend(meaningful_tokens_camelCase(self.name))

        for key in nl_features & self.features.keys():
            if isinstance(self.features[key], str):
                if key == 'java_doc':
                    # javadocs have syntax keywords like param and return
                    # these are not natural language terms
                    javadoc = re.sub(r"\b(return | param | see)\b","",self.features[key])
                    tokens.extend(meaningful_tokens_camelCase(javadoc))
                elif key == 'variables':
                    # args is an extremely common variable name that we remove
                    self.features[key].remove('args')
                    tokens.extend(meaningful_tokens_camelCase(self.features[key]))
                else:
                    tokens.extend(meaningful_tokens_camelCase(self.features[key]))
            elif isinstance(self.features[key], set):
                for value in self.features[key]:
                    tokens.extend(meaningful_tokens_camelCase(value))
            elif isinstance(self.features[key], Counter):
                # case of counters
                tokens.extend(meaningful_tokens_camelCase(' '.join(self.features[key].elements())))
            elif isinstance(self.features[key], list):
                tokens.extend(meaningful_tokens_camelCase(' '.join(self.features[key])))
        
        return list(set(tokens))
