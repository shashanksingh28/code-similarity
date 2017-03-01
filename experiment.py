import pandas as pd
import sys
import os
from FunRep import util, method, ml
import pickle
import threading

solutions_data_file = "models/solutionVectors.pck"
test_data_file = "models/testVectors.pck"
lang_dict_file = "models/dictionary.pck"
lang_model_file = "models/sim_model.model"

feature_weights = dict()
feature_weights['language'] = {'features' : method.lang_features, 'weight':1.0}
feature_weights['signature'] = {'features' : ('params','return','exceptions','annotations'), 'weight': 1.0}
feature_weights['structure'] = {'features' : ('expressions','statements'), 'weight': 1.0}
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

    def _get_kNearest(self):
        self._kNearest = ml.proposed_kNearest(self.vector, self._solution_vectors, self._lang_dict, self._lang_model, self.k, self.feature_weights)
    
    def _get_cNearest(self):
        self._cNearest = ml.concept_tag_kNearest(self.vector, self._solution_vectors, self._lang_dict, self._lang_model, self.k)

    def getNearestData(self, vector):
        # import pdb; pdb.set_trace()
        self.vector = vector
        tk = threading.Thread(target=self._get_kNearest)
        tc = threading.Thread(target=self._get_cNearest)
        tk.start()
        tc.start()
        tk.join()
        tc.join()
        
        """ Now that we have fetched kNearest, get row of differences """
        data = []
        for i in range(self.k):
            proposed_vector = self._solution_vectors[self._kNearest[i][1]]
            tag_vector = self._solution_vectors[self._cNearest[i][1]]
            
            if proposed_vector == tag_vector:
                continue
            
            data.append([ i+1, vector.raw_text, list(vector.concepts.keys()), proposed_vector.raw_text,\
                    list(proposed_vector.concepts.keys()), tag_vector.raw_text, list(tag_vector.concepts.keys()) ])
        
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
        
    pd.DataFrame(data=data, columns=['Rank', 'Sample','Sample_Concepts','IR_Reco',
                'IR_Reco_concepts', 'Tags_Reco', 'Tags_Reco_concepts'],
                index_label='#').to_csv('comparison.csv')
    

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Enter test-file containing method vectors..")
        sys.exit(0)

    main(sys.argv[1])

