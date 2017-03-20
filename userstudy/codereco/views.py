import json
from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.contrib.auth import login, logout, authenticate
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.views.decorators.csrf import csrf_exempt
from .models import Vote, Submission, CustomWeight

# Create your views here.
question1 = {'questionId' : 1, 'question' : 'Write a method that takes an integer n and prints the Fibonacci series till nth term.'}
question2 = {'questionId' : 2, 'question' : 'Write a method that returns a new sorted array of integers'}

@login_required
def index(request):
    return q1(request)

@login_required
def q1(request):
    subm = Submission.objects.filter(user=request.user, question=1)
    if subm.exists():
        return render(request, 'codereco/done.html', question1)
    return render(request, 'codereco/index.html', question1)

@login_required
def q2(request):
    subm = Submission.objects.filter(user=request.user, question=2)
    if subm.exists():
        return render(request, 'codereco/done.html', question2)
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
                return q1(request)
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

def get_weights(request, vote=None):
    weights = json.loads(request.META['HTTP_WEIGHTS'])
    custom_weight = CustomWeight(vote=vote)
    custom_weight.user = request.user
    custom_weight.signature = weights['signature']
    custom_weight.structure = weights['structure']
    custom_weight.concepts = weights['concepts']
    custom_weight.language = weights['language']
    return custom_weight

@login_required
@csrf_exempt
def user_vote(request):
    try:
        cw = None
        if request.method == "POST":
            data = json.loads(request.body.decode())
            source = data['reco']['source']
            if source == 2:
                # we need to create two vote objects here
                vote1, vote2 = Vote(), Vote()
                vote1.question, vote2.question = data['qId'], data['qId']
                vote1.user, vote2.user = request.user, request.user             
                vote1.rank, vote2.rank = data['reco']['rank_1'], data['reco']['rank_0']
                vote1.source, vote2.source = 1, 0
                vote1.rating, vote2.rating = data['reco']['rating'], data['reco']['rating']
                vote1.save()
                vote2.save()
                cw1 = get_weights(request, vote1)
                cw1.save()
                cw2 = get_weights(request, vote2)
                cw2.save()
            else:
                vote = Vote()
                vote.question = data['qId']
                vote.user = request.user
                vote.rank = data['reco']['rank']
                vote.source = source
                vote.rating = data['reco']['rating']
                vote.save()
                cw = get_weights(request, vote)
                cw.save()        
        return JsonResponse({})
    except Exception as ex:
        print(ex)
        return HttpResponse(str(ex))

@login_required
@csrf_exempt
def user_submit(request):
    try:
        if request.method == "POST":
            req_json = json.loads(str(request.body.decode()))
            subm = Submission(user=request.user, question=req_json['questionId'],\
                    code=req_json['text'])
            subm.save()
            return JsonResponse({})            
    except Exception as ex:
        print(ex)
        return HttpResponse(str(ex))

@login_required
@csrf_exempt
def user_weight_update(request):
    cw = get_weights(request)
    cw.save()
    return JsonResponse({})
