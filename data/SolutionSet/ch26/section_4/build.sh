cd timezone
javac -d WEB-INF/classes WEB-INF/classes/bigjava/*.java
jar -cvf . ../timezone.war
cd ..
