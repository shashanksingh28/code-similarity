import pandas as pd
import sys
import os
from FunRep import util, method, ml
import pickle

solutions_data_file = "models/solutionVectors.pck"
test_data_file = "models/testVectors.pck"
lang_dict_file = "models/dictionary.pck"
lang_model_file = "models/sim_model.model"

feature_weights = dict()
feature_weights['language'] = {'features' : method.lang_features, 'weight':1.0}
feature_weights['signature'] = {'features' : ('params','return','exceptions','annotations'), 'weight': 1.0}
feature_weights['structure'] = {'features' : ('expressions','statements'), 'weight': 1.0}
feature_weights['concepts'] = {'features' : ('concepts'), 'weight' : 1.0}

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
    for i, vector in enumerate(test_vectors):
        nearest_concept_tags = ml.concept_tag_kNearest(vector,solution_vectors,lang_dict, lang_model, k)
        nearest_proposed = ml.proposed_kNearest(vector, solution_vectors, lang_dict, lang_model, k, feature_weights)
        for j in range(k):
            if nearest_proposed[j][1] == nearest_concept_tags[j][1]:
                continue
            #data.append([ i+1, j+1, vector.raw_text + "\n\n" + str(dict(vector.concepts)), 
            #            solution_vectors[nearest_proposed[j][1]].raw_text + "\n\n" + str(dict(nearest_proposed[j][2])) + "\n" + str(nearest_proposed[j][0]),
            #            solution_vectors[nearest_concept_tags[j][1]].raw_text + "\n\n" + str(dict(nearest_concept_tags[j][2])) + "\n" + str(nearest_proposed[j][0]) ])
            proposed_vector = solution_vectors[nearest_proposed[j][1]]
            tag_vector = solution_vectors[nearest_concept_tags[j][1]]
            data.append([i+1, j+1, vector.raw_text, str(vector.concepts),
                        proposed_vector.raw_text, nearest_proposed[j][0], str(proposed_vector.concepts),
                        tag_vector.raw_text, nearest_concept_tags[j][0], str(tag_vector.concepts)
                        ]) 

            
        print(i)
        
    pd.DataFrame(data=data, columns=['#', 'Rank', 'Sample','Sample_Concepts','IR_Reco', 'IR_Score',
                'IR_Reco_concepts', 'Tags_Reco', 'Tags_Score','Tags_Reco_concepts']).to_csv('out.csv')

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Enter test-file containing method vectors..")
        sys.exit(0)

    main(sys.argv[1])

