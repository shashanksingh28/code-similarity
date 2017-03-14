from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect
from django.contrib.auth import login, logout, authenticate
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User

# Create your views here.
question1 = {'question' : 'Write a method that takes an integer n and prints the Fibonacci series till nth term.'}
question2 = {'question' : 'Write a method that returns a new sorted array of integers'}

@login_required
def index(request):
    return render(request, 'codereco/index.html', question1)

@login_required
def q1(request):
    return render(request, 'codereco/index.html', question1)

@login_required
def q2(request):
    return render(request, 'codereco/index.html', question2)


def user_login(request):
    if request.method == "GET":
        return render(request, 'registration/login.html')
    elif request.method == "POST":
        try:
            username = request.POST['username']
            password = request.POST['password']
            user = authenticate(username=username, password=password)
            if user is not None:
                login(request, user)
                return HttpResponseRedirect('/')
            else:
                return render(request, 'registration/login.html', {'msg': 'Username or Password incorrect'})
        except Exception as ex:
            print(ex)
        return render(request, 'registration/login.html', {'msg':'Username and '})
        
def user_register(request):
    if request.method == "GET":
        return render(request, 'registration/register.html')
    elif request.method == "POST":
        try:
            username = request.POST['username']
            password = request.POST['password']
            email = request.POST['email']
            user = User.objects.create_user(username, email, password)
            if user is not None:
                login(request, user)
                return HttpResponseRedirect('/')
        except Exception as ex:
            print(ex)
        return HttpResponseRedirect('/register')

@login_required
def user_logout(request):
    logout(request)
    return HttpResponseRedirect('/')
