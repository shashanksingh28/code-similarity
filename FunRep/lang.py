import re
from nltk.corpus import stopwords

# This decides that when we tokenize text, what do we use as token-delimiters
punctuations = re.compile(r'[!"#$%&\'\(\)\*\+,-\./:;<=>?@\[\\\]\^_`\{|\}~]')

def tokenize(text):
    """ Extract text tokens from a string"""
    # replace all token delimiters with a costmmon delimiter - space and split it
    tokens = re.sub(punctuations, ' ', text.lower()).split()
    return tokens

def meaningful_tokens(text):
    """ Tokenize, remove stop-words and single character literals"""
    tokens = tokenize(text)
    filtered_tokens = [word.strip() for word in tokens if word.strip() not in stopwords.words('english')]
    return filtered_tokens

def meaningful_tokens_camelCase(text):
    """ Same as meaninful tokens, but with camel-case splitting"""
    text = re.sub(r'((?<=[a-z])[A-Z]|(?<!\A)[A-Z](?=[a-z]))', r' \1', text).lower()
    return meaningful_tokens(text)

