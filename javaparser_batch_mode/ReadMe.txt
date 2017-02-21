============
Input Format
============
The code snippets should be stored in a single txt file (e.g. input.txt).  See input.txt that shows an input file for three code snippets with ids: #C1, #C2, #C3

Note that the each code snippet starts with a unique identifier that must start with "#C".
"EOF" should be added to the end of the file.

============
MySQL
============
The output of the parser is stored in a mysql table: parser.ent_content_concept
You need to import the "parser.sql" in your local MySQL server.
Below are the description of the fields in parser table:

id: is a unique incremental identifier  (you can ignore it)
content_id: is the identifier for each code (e.g., #C1, #C2, ...)
concept: is the name of the concept
sline: is the line that the concept appeared in the code 
eline: is the same as sline when the concept is not referring to a block. Otherwise, an eline refers to the end of the block.

========================
How to Run the Parser
========================
Type the following command in the terminal after you replaced A1, A2, A3, A4 with your own arguments:
A1: pathToInputFile;    A2: mysqlUser;      A3: mysqlPass;       A4: mysqlPort
java -jar javaparser_batch.jar A1 A2 A3 A4
e.g.: java -jar javaparser_batch.jar "/Users/roya/Desktop/input.txt" "user" "root" "3306"

========================
How to Cite The Parser
========================
Hosseini, R., & Brusilovsky, P. (2013). Javaparser: A fine-grain concept indexing tool for java problems. In The First Workshop on AI-supported Education for Computer Science (AIEDCS 2013) (pp. 60-63). University of Pittsburgh.