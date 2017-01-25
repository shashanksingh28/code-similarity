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
tfIdf_fit_model_file = "models/tokenTf-Idf.model"
tfIdf_tranformed_data_file = "models/tokenTf-Idf.dat"
solutions_data_file = "models/solutionVectors.pck"
nlp_sim_dict_file = "models/dictionary.pck"
nlp_sim_model_file = "models/sim_model.pck"
kNearest = 7
####

#### Global Variables to be used throughout ####
# solution vectors which will be recommended
solution_vectors = None
# tfIDf model to be used to transform new data
tfIdf_model = None
# this will be used to calculate similarity
tfIdf_data = None
nl_sim_dict = None
nl_sim_model = None
#####

with app.test_request_context():
    url_for('static', filename='code.js')
    url_for('static', filename='codemirror-5.23.0')

@app.before_first_request
def loadData():
    """ Function to load model solutions in memory.
        Also stores necessary data in local system. """    
    
    global solution_vectors
    global tfIdf_model
    global tfIdf_data
    global nl_sim_dict
    global nl_sim_model

    if not os.path.exists("models"):
        os.makedirs("models")

    if not os.path.isfile(solutions_data_file):
        print("Extracting solution vectors...")
        solution_vectors = util.vectors_from_file(solutions_input_file)
        pickle.dump(solution_vectors, open(solutions_data_file, "wb"))
    else:
        print("Loading solution vectors...")
        solution_vectors = pickle.load(open(solutions_data_file, "rb"))
        
    if not os.path.isfile(tfIdf_tranformed_data_file):
        print("Extracting Tf-IDF models...")
        tfIdf_model, tfIdf_data = ml.tfIdf_models(solution_vectors)
        pickle.dump(tfIdf_model, open(tfIdf_fit_model_file, "wb"))
        pickle.dump(tfIdf_data, open(tfIdf_tranformed_data_file, "wb"))
    else:
        print("Loading Tf-Idf models...")
        tfIdf_model = pickle.load(open(tfIdf_fit_model_file, "rb"))
        tfIdf_data = pickle.load(open(tfIdf_tranformed_data_file, "rb"))
  
    if not os.path.isfile(nlp_sim_model_file):
        print("Extracting natural language models")
        nl_sim_dict, nl_sim_model = ml.create_language_model(solution_vectors)
        pickle.dump(nl_sim_dict, open(nlp_sim_dict_file, "wb"))
        pickle.dump(nl_sim_model, open(nlp_sim_model_file, "wb"))
    else:
        print("Loading natural language models")
        nl_sim_dict = pickle.load(open(nlp_sim_dict_file, "rb"))
        nl_sim_model = pickle.load(open(nlp_sim_model_file, "rb"))

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
        body_string = request.data.decode("utf-8")
        if len(body_string) == 0:
            return jsonify("Empty String")
        if 'nl_ratio' in request.headers:
            nl_weight = float(request.headers['nl_ratio'])
        else:
            nl_weight = 0.5
        print("NL_WEIGHT:",nl_weight)
        method_vector = util.vector_from_text(body_string)
        nearest = ml.proposed_kNearest(method_vector, solution_vectors, nl_sim_dict, 
                nl_sim_model, k=kNearest, nl_weight=nl_weight)
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

@app.route('/equalJaccard', methods=['POST'])
def jaccard_kNearest():
    try:
        body_string = request.data.decode("utf-8")
        if len(body_string) == 0:
            return jsonify("Empty String")
        method_vector = util.vector_from_text(body_string)
        nearest = ml.jaccard_kNearest(method_vector, solution_vectors, k=kNearest)
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

@app.route('/cosine', methods=['POST'])
def cosine_kNearest():
    try:
        body_string = request.data.decode("utf-8")
        if len(body_string) == 0:
            return jsonify("Empty String")
        method_vector = util.vector_from_text(body_string)
        # first transform the given method_vector with our model
        method_vector_transformed = tfIdf_model.transform([' '.join(method_vector.tokens)])
        nearest = ml.cosine_kNearest(method_vector_transformed, tfIdf_data, 
                tfIdf_model.get_feature_names(), k=kNearest)
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
