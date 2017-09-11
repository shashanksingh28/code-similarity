#!/bin/bash
# Platform specific setup for Ubuntu

echoerr() { echo "$@" 1>&2; }

version=1.0
venv_root="codereco-${version}-venv"
cwd=$(pwd)

echo "Checking for python3..."
if ! command -v python3 > /dev/null 2>&1; then
  echoerr "Missing dependency: 'python3'" > 2
	exit 1
fi

echo "Checking for virtualenv..."
if ! command -v virtualenv > /dev/null 2>&1 ; then
  echoerr "Missing dependency: virtualenv" > 2
  exit 1
fi

if [ ! -d $venv_root ]; then
  mkdir $venv_root
  virtualenv -p python3 $venv_root
  cd $venv_root
  ln -s $cwd/service .
  source bin/activate
  pip3 install flask flask-cors nltk mysql-connector==2.1.4 numpy gensim sklearn
  python3 -c "import nltk; nltk.download()"
else
  echo "$venv_root already exists"
fi
