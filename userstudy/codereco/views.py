from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect
from django.contrib.auth import login, logout, authenticate
from django.contrib.auth.decorators import login_required

# Create your views here.

@login_required
def index(request):
    return render(request, 'codereco/index.html')

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
                return render(request, 'codereco/index.html')
            else:
                return render(request, 'registration/login.html', {'msg': 'Username or Password incorrect'})
        except Exception as ex:
            print(ex)
        return render(request, 'registration/login.html', {'msg':'Username and '})
        
