import pandas as pd
import sys
import os
from FunRep import util, method, ml
import pickle
import threading

solutions_data_file = "models/solutionVectors.pck"
solutions_input_file = "solutionSet.txt"
test_data_file = "models/testVectors.pck"
lang_dict_file = "models/dictionary.pck"
lang_model_file = "models/sim_model.model"

feature_weights = dict()
feature_weights['language'] = {'features' : method.lang_features, 'weight':1.0}
feature_weights['signature'] = {'features' : ('params','return','modifier'), 'weight': 1.0}
feature_weights['structure'] = {'features' : ('expressions', 'statements', 'methods_called', 'types', 'exceptions','annotations'), 'weight': 1.0}
feature_weights['concepts'] = {'features' : ('concepts'), 'weight' : 1.0}

class AnalyzeDiff:

    def __init__(self, solution_vectors, lang_dict, lang_model, k, feature_weights):
        self._solution_vectors = solution_vectors
        self._lang_dict = lang_dict
        self._lang_model = lang_model
        self.k = k
        self._cNearest = []
        self._kNearest = []
        self.feature_weights = feature_weights

    def getNearestData(self, vector):
        proposed_kNearest = ml.proposed_kNearest(vector, self._solution_vectors, self._lang_dict, self._lang_model, self.k, self.feature_weights)
        baseline_kNearest = ml.cosine_kNearest(vector, self._solution_vectors, self._lang_dict, self._lang_model, self.k)
        """ Now that we have fetched kNearest, get row of differences """
        data = []
        for i in range(self.k):
            proposed_vector = self._solution_vectors[proposed_kNearest[i][1]]
            baseline_vector = self._solution_vectors[baseline_kNearest[i][1]]
            
            if proposed_vector == baseline_vector:
                continue
            
            data.append([ i+1, vector.raw_text, vector.concepts, proposed_vector.raw_text,\
                    proposed_vector.concepts, baseline_vector.raw_text, baseline_vector.concepts ])
        
        return data
    

def main(testFile, k=5):
    if not os.path.isfile(solutions_data_file):
        print("Extracting solution vectors...")
        solution_vectors = util.vectors_from_file(solutions_input_file)
        pickle.dump(solution_vectors, open(solutions_data_file, "wb"))
    else:
        print("Loading solution vectors...")
        solution_vectors = pickle.load(open(solutions_data_file, "rb"))
    
    print(len(solution_vectors), " methods loaded...")

    if not os.path.isfile(lang_model_file):
        print("Extracting natural language models")
        lang_documents = []
        for vector in solution_vectors: 
            lang_documents.append(vector.nl_tokens)
        lang_dict, lang_model = ml.create_tfIdf_model(lang_documents)
        pickle.dump(lang_dict, open(lang_dict_file, "wb"))
        pickle.dump(lang_model, open(lang_model_file, "wb"))
    else:
        print("Loading natural language models")
        lang_dict = pickle.load(open(lang_dict_file, "rb"))
        lang_model = pickle.load(open(lang_model_file, "rb"))
    
    if not os.path.isfile(test_data_file):
        print("Extracting test vectors")
        test_vectors = util.vectors_from_file(testFile)
        pickle.dump(test_vectors, open(test_data_file, "wb"))
    else:
        print("Loading test vectors")
        test_vectors = pickle.load(open(test_data_file, "rb"))


    data = []
    analyzer = AnalyzeDiff(solution_vectors, lang_dict, lang_model, k, feature_weights)
    for i, vector in enumerate(test_vectors):
        records = analyzer.getNearestData(vector)
        for record in records:
            data.append(record)
        print(i)

    df = pd.DataFrame(data=data, columns=['Rank', 'Sample','Sample_Concepts','IR_Reco',
                'IR_Reco_concepts', 'Tags_Reco', 'Tags_Reco_concepts'])
    df.index.name='#'
    df.to_csv('comparison.csv')    
    

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Enter test-file containing method vectors..")
        sys.exit(0)

    main(sys.argv[1])

