import json
import re
from collections import Counter
from FunRep.lang import meaningful_tokens, meaningful_tokens_camelCase

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
        
        # extracted features 
        self.features = {}

        # String or numeric features
        self.features['returnType'] = str(jsonObj['returnType'])
        self.features['modifier'] = int(jsonObj['modifier'])
        self.features['line_count'] = int(jsonObj['lineCount']) 
        if 'javaDoc' in jsonObj:
            self.features['java_doc'] = str(jsonObj['javaDoc']).lower()
        if 'comments' in jsonObj:
            self.features['comments'] = str(jsonObj['comments']).lower()
        
        # List features
        self.features['paramTypes'] = list(jsonObj['paramTypes'])
        
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
        
        camelCaseSplitText = re.sub(r'((?<=[a-z])[A-Z]|(?<!\A)[A-Z](?=[a-z]))', r' \1', self.raw_text)
        self.tokens = meaningful_tokens(camelCaseSplitText)

    def lang_tokens(self):
        """ Get only those tokens which one would consider to be natural language tokens
            E.g.: Comments, variable names, tokens, etc """
        lang_keys = ['variables', 'constants', 'comments', 'java_doc']
        tokens = []
        for key in lang_keys & self.features.keys():
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
        return list(set(tokens))
