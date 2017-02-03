import re
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer

### Global sets that we use ###
number = set(["zero", "one", "two", "three", "four", "five", "six", "seven", "eight",
        "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
        "sixteen", "seventeen", "eighteen", "nineteen", "twenty", "thirty", "forty",
        "fifty", "sixty", "seventy", "eighty", "ninety"])

rank = set(["first", "second", "third","fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth"])

scales = set(["hundred", "thousand", "million", "billion", "trillion"])

datetime = set(["day", "days", "sunday", "monday", "tuesday", "wednesday","thursday","friday", "saturday","am",
    "pm", "year", "month", "months", "january", "february", "march", "april", "may", "june", "july", "august",
    "september","october","november","december", "today", "tomorrow", "years"])

lemma = WordNetLemmatizer()

def tokenize(text):
    """ Extract text tokens from a string"""
    # replace all token delimiters with a costmmon delimiter - space and split it
    # make all lowerscore and replace underscores with space first
    text = re.sub('_', ' ', text.lower())
    tokens = re.findall(r'[\w]+', text)
    return list(tokens)

def meaningful_tokens(text):
    """ Tokenize, remove stop-words and single character literals """
    tokens = tokenize(text)
    filtered_tokens = [word.strip() for word in tokens if word.strip() not in stopwords.words('english')]
    return filtered_tokens

def meaningful_tokens_camelCase(text):
    """ Same as meaninful tokens, but with camel-case splitting """
    text = re.sub(r'((?<=[a-z])[A-Z]|(?<!\A)[A-Z](?=[a-z]))', r' \1', text)
    return meaningful_tokens(text)

def text_pre_process(word_list):
    """ Some basic natural language text processing to improve similarity scores """
    out = []
    for word in word_list:
        word = word.lower()
        if word.isdigit() or word in number:
            out.append("DIGIT")
        elif word in rank:
            out.append("RANK")
        elif word in scales:
            out.append("SCALE")
        elif word in datetime:
            out.append("TIME")
        elif len(word) > 1:
            out.append(lemma.lemmatize(word))
    return out
