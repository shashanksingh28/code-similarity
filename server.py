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
from flask_cors import CORS, cross_origin

app = Flask(__name__)
# CORS(app)
# app.config['SERVER_NAME'] = 'codesimilarity.com'
# with app.app_context():
#    url_for('static', filename='style.css')
#    url_for('static', filename='code.js')

#### Config section ####
solutions_input_file = "solutionSet.txt"
tfIdf_fit_model_file = "models/tokenTf-Idf.model"
tfIdf_tranformed_data_file = "models/tokenTf-Idf.dat"
solutions_data_file = "models/solutionVectors.pck"
kNearest = 4
####

#### Global Variables to be used throughout ####
# solution vectors which will be recommended
solution_vectors = None
# tfIDf model to be used to transform new data
tfIdf_model = None
# this will be used to calculate similarity
tfIdf_data = None
#####

@app.before_first_request
def loadData():
    """ Function to load model solutions in memory.
        Also stores necessary data in local system. """    
    
    global solution_vectors
    global tfIdf_model
    global tfIdf_data
    
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
        print("Done")
   
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

@app.route('/equalJaccard', methods=['POST'])
def jaccard_kNearest():
    try:
        print(request.data)
        method_vector = util.vector_from_text(request.data.decode("utf-8"))
        kNearest = ml.jaccard_kNearest(method_vector, solution_vectors, k=4)
        # nearest contains score, solutions_index and intersections
        print(kNearest)
        nearest_vectors = []
        for element in kNearest:
            nearest_vectors.append(solution_vectors[element[1]])
        return jsonify(nearest_vectors)
    except Exception as ex:
        raise ex

@app.route('/cosine', methods=['POST'])
def cosine_kNearest():
    try:
        method_vector = util.vector_from_text(request.data.decode("utf-8"))
        if method_vector is None:
            raise Exception("Couldn't get the method")
        # first transform the given method_vector with our model
        method_vector_transformed = tfIdf_model.transform([' '.join(method_vector.tokens)])
        kNearest = ml.cosine_kNearest(method_vector_transformed, tfIdf_data, tfIdf_model.get_feature_names(), k=4)
        # nearest contains score, solutions_index and intersections
        print(kNearest)
        nearest_vectors = []
        for element in kNearest:
            nearest_vectors.append(solution_vectors[element[1]])
        return jsonify(nearest_vectors)
    except Exception as ex:
        raise ex
    
if __name__ == "__main__":
    app.run(debug=True)
