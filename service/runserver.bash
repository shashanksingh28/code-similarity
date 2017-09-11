export FLASK_APP=server.py
export FLASK_DEBUG=1
python server.py & 
flask run --host=0.0.0.0 --port=8080 &

