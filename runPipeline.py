import pdb
import re
import json

def getVectors(inputFile):
    # pdb.set_trace()
    docVectors = []
    with open(inputFile,"r") as f:
        for line in f:
            if re.search(r'^[^#].+$', line):
                docVectors.append(json.loads(line.strip()))

    return docVectors

def runPipe():
    """ Run whole pipeline """
    vectors = getVectors("input.txt")
    print("Pure Text matches")
    for i, vector in enumerate(vectors):
        for j in range(i + 1, len(vectors)):
            iStrings = set(vectors[i]['text'].split())
            jStrings = set(vectors[j]['text'].split())
            intersect = iStrings.intersection(jStrings)
            print(vectors[i]['Name'],"vs",vectors[j]['Name'])
            print("Naive text :",len(intersect))
            print(intersect)
            iTokens = set(vectors[i]['Operands'] + vectors[i]['Operators'] + list(vectors[j]['Types'].keys()) + vectors[i]['MethodNames'] + vectors[i]['Signature'])
            jTokens = set(vectors[j]['Operands'] + vectors[j]['Operators'] + list(vectors[j]['Types'].keys()) + vectors[j]['MethodNames'] + vectors[j]['Signature'])
            intersect = iTokens.intersection(jTokens)
            print("Parsed tokens :",len(intersect))
            print(intersect)

if __name__ == "__main__":
    runPipe()

