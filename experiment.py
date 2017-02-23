import pandas as pd
import sys
from Funrep import util, method, ml

solutions_data_file = "models/solutionVectors.pck"
lang_dict_file = "models/dictionary.pck"
lang_model_file = "models/sim_model.model"

def main(testFile, k=5):
    import pdb; pdb.set_trace()
    if not os.path.isfile(solutions_data_file):
        print("Extracting solution vectors...")
        solution_vectors = util.vectors_from_file(solutions_input_file)
        pickle.dump(solution_vectors, open(solutions_data_file, "wb"))
    else:
        print("Loading solution vectors...")
        solution_vectors = pickle.load(open(solutions_data_file, "rb"))

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

    print(len(solution_vectors), " methods loaded...")

    vectors = util.vectors_from_file(testFile)
    data = []
    for i, vector in enumerate(vectors):
        nearest_concept_tags = ml.concept_tag_kNearest(vector,solution_vectors,lang_dict, lang_model, k)
        nearest_proposed = ml.proposed_features_kNearest(vector, solution_vectors, lang_dict, lang_model, k)
        for j in range(k):
            data.append([i+1,j+1, vector.concepts, nearest_proposed[j].concepts,nearest_concept_tags[j].concepts])
        print(i)
        
    pd.DataFrame(data=data, columns=['#', 'Rank','Concepts','IR','Tags']).to_csv('out.csv')

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Enter test-file containing method vectors..")
        sys.exit(0)

    main(sys.argv[1])

