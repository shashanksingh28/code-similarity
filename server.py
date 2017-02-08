#!/usr/bin/env python3
import re
import pdb
import sys
import os
import time
import pickle
import json

from FunRep import method
import FunRep.util as util
import FunRep.ml as ml

# Web server requirements
from flask import Flask, jsonify, request, render_template, url_for
from flask.json import JSONEncoder

app = Flask(__name__)

#### Config section ####
solutions_input_file = "solutionSet.txt"
solutions_data_file = "models/solutionVectors.pck"
baseline_model_file = "models/baseline.model"
baseline_dict_file = "models/baseline.dat"
lang_dict_file = "models/dictionary.pck"
lang_model_file = "models/sim_model.model"
kNearest = 10

feature_weights = dict()
feature_weights['language'] = {'features' : method.lang_features, 'weight':1.0}
feature_weights['signature'] = {'features' : ('params','return','exceptions','annotations'), 'weight': 1.0}
feature_weights['structure'] = {'features' : ('expressions','statements'), 'weight': 1.0}
feature_weights['concepts'] = {'features' : ('concepts'), 'weight' : 1.0}
####

#### Global Variables to be used throughout ####
solution_vectors = None

baseline_dict = None
baseline_model = None

lang_dict = None
lang_model = None
#####

with app.test_request_context():
    url_for('static', filename='code.js')
    url_for('static', filename='codemirror-5.23.0')

@app.before_first_request
def loadData():
    """ Function to load model solutions in memory.
        Also stores necessary data in local system. """    
    
    global solution_vectors
    global baseline_dict
    global baseline_model
    global lang_dict
    global lang_model

    if not os.path.exists("models"):
        os.makedirs("models")

    if not os.path.isfile(solutions_data_file):
        print("Extracting solution vectors...")
        solution_vectors = util.vectors_from_file(solutions_input_file)
        pickle.dump(solution_vectors, open(solutions_data_file, "wb"))
    else:
        print("Loading solution vectors...")
        solution_vectors = pickle.load(open(solutions_data_file, "rb"))
    print(len(solution_vectors), " methods loaded...")
        
    documents = []
    lang_documents = []
    for vector in solution_vectors: 
        documents.append(vector.tokens)
        lang_documents.append(vector.nl_tokens)
    
    if not os.path.isfile(baseline_model_file):
        print("Extracting Baseline models...")
        baseline_dict, baseline_model = ml.create_tfIdf_model(documents)
        pickle.dump(baseline_model, open(baseline_model_file, "wb"))
        pickle.dump(baseline_dict, open(baseline_dict_file, "wb"))
    else:
        print("Loading Baseline models...")
        baseline_dict = pickle.load(open(baseline_dict_file, "rb"))
        baseline_model = pickle.load(open(baseline_model_file, "rb"))
  
    if not os.path.isfile(lang_model_file):
        print("Extracting natural language models")
        lang_dict, lang_model = ml.create_tfIdf_model(lang_documents)
        pickle.dump(lang_dict, open(lang_dict_file, "wb"))
        pickle.dump(lang_model, open(lang_model_file, "wb"))
    else:
        print("Loading natural language models")
        lang_dict = pickle.load(open(lang_dict_file, "rb"))
        lang_model = pickle.load(open(lang_model_file, "rb"))

    print(len(lang_dict)," language tokens")
    print(len(baseline_dict)," baseline tokens")

# Web server components
class CustomJSONEncoder(JSONEncoder):
    def default(self, obj):
        try:
            if isinstance(obj, method.MethodFeatureVector):
                return obj.__dict__
            iterable = iter(obj)
        except TypeError as te:
            pass
        else:
            return list(iterable)
        return JSONEncoder.default(self, obj)

app.json_encoder = CustomJSONEncoder

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/simcode', methods=['POST'])
def proposed_similarity():
    try:
        request_weights = feature_weights.copy()
        body_string = request.data.decode("utf-8")
        if len(body_string) == 0:
            return jsonify("Empty String")
        
        if request.headers['weights']: 
            header = json.loads(request.headers['weights'])
            print(header)
            for weight_key in header:
                if weight_key in request_weights:
                    request_weights[weight_key]['weight'] = float(header[weight_key])

        method_vector = util.vector_from_text(body_string)
        nearest = ml.proposed_kNearest(method_vector, solution_vectors, lang_dict, 
                lang_model, k=kNearest, weights=request_weights)
        # nearest contains score, solutions_index and intersections
        # print(kNearest)
        nearest_vectors = []
        for element in nearest:
            result = {}
            result['score'] = element[0]
            result['code'] = solution_vectors[element[1]]
            result['match'] = element[2]
            nearest_vectors.append(result)
        return jsonify(nearest_vectors)
    except Exception as ex:
        print(ex)
        return jsonify(str(ex))

@app.route('/cosine', methods=['POST'])
def cosine_kNearest():
    try:
        body_string = request.data.decode("utf-8")
        if len(body_string) == 0:
            return jsonify("Empty String")
        method_vector = util.vector_from_text(body_string)
        # first transform the given method_vector with our model
        nearest = ml.cosine_kNearest(method_vector, solution_vectors, baseline_dict,
                baseline_model, k=kNearest)
        # nearest contains score, solutions_index and intersections
        # print(kNearest)
        nearest_vectors = []
        for element in nearest:
            result = {}
            result['score'] = element[0]
            result['code'] = solution_vectors[element[1]]
            result['match'] = element[2]
            nearest_vectors.append(result)
        return jsonify(nearest_vectors)
    except Exception as ex:
        return jsonify(str(ex))

if __name__ == "__main__":
    # app.run(debug=True)
    loadData()
    import pdb; pdb.set_trace()
