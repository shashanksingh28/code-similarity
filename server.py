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
from flask import Flask, jsonify, request
from flask.json import JSONEncoder
app = Flask(__name__)

#### Config section ####
solutions_input_file = "solutionSet.txt"
tfIdf_fit_model_file = "models/tokenTf-Idf.model"
tfIdf_tranformed_data_file = "models/tokenTf-Idf.dat"
solutions_data_file = "models/solutionVectors.pck"
kNearest = 4
####

#### Server Variables ####
# solution vectors which will be recommended
vectors = None
# tfIDf model to be used to transform new data
tfIdf_model = None
# this will be used to calculate similarity
ifIdf_data = None
#####

@app.before_first_request
def loadData():
    """ Function to load model solutions in memory.
        Also stores necessary data in local system. """    
    if not os.path.exists("models"):
        os.makedirs("models")

    if not os.path.isfile(solutions_data_file):
        print("Extracting solution vectors...")
        vectors = util.vectors_from_file(solutions_input_file)
        pickle.dump(vectors, open(solutions_data_file, "wb"))
    else:
        print("Loading solution vectors...")
        vectors = pickle.load(open(solutions_data_file, "rb"))
        
    if not os.path.isfile(tfIdf_tranformed_data_file):
        print("Extracting Tf-IDF models...")
        tfIdf_model, tfIdf_data = ml.tfIdf_models(vectors)
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
def hello_world():
    return "Hello World!"

@app.route('/equalJaccard', methods=['POST'])
def equalJaccardNearest():
    try:
        method_vector = util.vector_from_text(request.data.decode("utf-8"))
        return jsonify(method_vector)
    except Exception as ex:
        raise ex

if __name__ == "__main__":
    app.run(debug=True)
