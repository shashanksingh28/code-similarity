# Code Recommender

Code Recommender is a utility that indexes java methods not just by keywords but structural and conceptual tags like "polymorphism", "casting" and "booleanOperator". These tags are used to calculate a similarity score, which eventually aids in finding more *conceptually* similar java methods, methods that might have a similar structure or hopefullly doing something very similar.

Feature categories are language, concepts, structure and signature. The weights of these categories can be customized by the user for fine-tuning similarity scores towards their own needs. This however, needs some understanding of the features. Check the further reading section for details.

## Dependencies ##

System level requirements
* python3
* virtualenv
* java

Installed by virtualenv
* flask, flask cors
* nltk, sklearn and gensim
* nunmpy

## Installation ##

* run setup.bash
* for nltk downloader, download 'stopwords' and 'wordnet' corpus

For running the service, run
```
$ source codereco-1.0-env/bin/activate
$ cd service
$ ./runserver.bash
```

* Open http://localhost:8080/ in your browser to test
Enter java methods in the text-box and search for similar samples.

Note: The samples recommended are currently based on a few textbooks that are used as corpus. You can index your own corpus by adding .java files in the data directory.

## Further reading ##
[Thesis](https://repository.asu.edu/items/44982)
