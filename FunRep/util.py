import re
import time
import mysql.connector
import tempfile
import os
from collections import Counter
import MySQLdb

# used to call jar files
from subprocess import Popen, PIPE, STDOUT
from .method import MethodFeatureVector

### Cofig section ###
java_method_extractor = "JavaMethodFeatureExtractor.jar"
java_file_extractor = "JavaFileFeatureExtractor.jar"
java_concept_tagger = "javaparser_batch_mode/javaparser_batch.jar"
db_user='root'
db_pass='root'
db_port='3306'
db_name='parser'
query = "SELECT concept FROM ent_content_concept WHERE content_id="
# cnx = mysql.connector.connect(user=db_user,password=db_pass,database=db_name)
###

def get_concept_tags(text):
    cnx = mysql.connector.connect(user=db_user,password=db_pass,database=db_name)
    uniq_id = "#C" + str(int(time.time()))
    ctr = Counter()
    raw_text = uniq_id + "\n" + text + "\nEOF"
    fp = create_temp_file(raw_text)
    try:
        proc = Popen(['java', '-jar', java_concept_tagger, fp.name, db_user, db_pass, db_port])
        out, err = proc.communicate()
        if proc.returncode != 0:
            if err:
                raise Exception(out.decode() + err.decode())
            else:
                print("Error code for:",text,sep="\n")
        curs = cnx.cursor()
        this_query = query + '"' + uniq_id + '"'
        # print(this_query)
        curs.execute(this_query)
        concepts = [concept[0] for concept in curs.fetchall()]
        ctr = Counter(concepts)
        curs.close()
        cnx.close()
    except:
        print("Exception for:", text,sep="\n")
    finally:
        del_temp_file(fp)
    return ctr, uniq_id

def unique_vectors_from_file(input_file):
    doc_vectors = {}
    with open(input_file, "r") as f:
        ctr = 0
        for line in f:
            if re.search(r'^#*$', line) or len(line) == 0:
                continue
            vector = MethodFeatureVector(line)
            if not vector.is_empty:
                vector.concepts, vector.c_id = get_concept_tags(vector.raw_text)
                doc_vectors[vector.raw_text.strip()] = vector
                print(ctr, len(vector.concepts))
            ctr += 1    
    return list(doc_vectors.values())

def vector_from_text(code_text):
    # create temporary file
    fp = create_temp_file(code_text)
    proc = Popen(['java', '-jar', java_method_extractor], stdout=PIPE, stdin=PIPE, stderr=PIPE)
    out, err = proc.communicate(input=code_text.encode())
    if proc.returncode != 0:
        raise Exception(out.decode() + err.decode()) 
    method_text = out.decode().strip()
    method_vector=None
    for line in method_text.split("\n"):
        if re.search(r'^[^#].+$', line):
            method_vector = MethodFeatureVector(line)
            method_vector.concepts, method_vector.c_id = get_concept_tags(method_vector.raw_text)
            break
    return method_vector


def vectors_from_file(solutionsFile):
    vectors = unique_vectors_from_file(solutionsFile)
    return vectors
    
def create_temp_file(text):
     # create temporary file
    fp = tempfile.NamedTemporaryFile(delete=False)
    fp.write(str.encode(text))
    fp.close()
    return fp

def del_temp_file(fp):
    try:
        os.remove(fp.name)
    except Exception as ex:
        print(ex)
