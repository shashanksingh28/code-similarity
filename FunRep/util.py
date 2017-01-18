import re
# used to call jar files
from subprocess import Popen, PIPE, STDOUT

from FunRep.method import MethodFeatureVector

java_method_extractor = "JavaMethodFeatureExtractor.jar"
java_file_extractor = "JavaFileFeatureExtractor.jar"

def unique_vectors_from_file(input_file):
    doc_vectors = {}
    with open(input_file, "r") as f:
        for line in f:
            if re.search(r'^#*$', line) or len(line) == 0:
                continue
            vector = MethodFeatureVector(line)
            if not vector.is_empty:
                doc_vectors[vector.raw_text.strip()] = vector
    return list(doc_vectors.values())

def vector_from_text(code_text):
    proc = Popen(['java', '-jar', java_method_extractor], stdout=PIPE, stdin=PIPE, stderr=PIPE)
    out, err = proc.communicate(input=code_text.encode())
    if proc.returncode != 0:
        raise Exception(err.decode("utf-8")) 
    method_text = out.decode("utf-8").strip()
    for line in method_text.split("\n"):
        if re.search(r'^[^#].+$', line):
            method_vector = MethodFeatureVector(line)
            break 
    return method_vector

def vectors_from_file(solutionsFile):
    print("Loading vectors from", solutionsFile)
    vectors = unique_vectors_from_file(solutionsFile)
    print("Total Samples:",len(vectors))
    return vectors
    
