import json
import threading
from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.contrib.auth import login, logout, authenticate
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.views.decorators.csrf import csrf_exempt
from .models import Vote, Submission, CustomWeight, UserQuestion, CutCopy

# Create your views here.
question1 = {'questionId' : 1, 'question' : """Write a method that takes an integer array and returns the largest difference between its elements.
For example, if array contains [7, 4, 1], the method should return 6 as the largest difference (7 - 1)"""}
question2 = {'questionId' : 2, 'question' : """
    class Person{
        String name;
        int age;
    }
    class Employee extends Person{
        double getSalary();
    }
    class BusinessOwner extends Person {
        double getProfit();
    }
    class Veteran extends Person{
        String veteran_id;
    }

Write a java method that takes a person and returns their tax as below:
1. For employees, if salary < $10,000, 2% of salary, else 5%
2. For business owners, 10% of their profit
3. 0 for veterans

What would you do if a person is none of these?"""}

# Test Question
test_question = {'questionId': 3, 'question' : """
Write a java method that takes in a binary string (e.g.: "101") and returns its decimal integer value (5).
"""}

questions = {1 : question1, 2 : question2}
counter = 0
lock = threading.Lock()

@login_required
def index(request):
    return faq(request)

@login_required
def faq(request):
    return render(request, 'codereco/faq.html')

@login_required
def q1(request):
    subm = Submission.objects.filter(user=request.user, question=1)
    curr = UserQuestion.objects.filter(user=request.user).get()
    if subm.exists():
        if curr.count == 2:
            return feedback(request)
        else:
            return render(request, 'codereco/done.html', question1)
    elif curr.question == 2:
        return render(request, 'codereco/other.html', question1)
    return render(request, 'codereco/index.html', question1)

@login_required
def q2(request):
    subm = Submission.objects.filter(user=request.user, question=2)
    curr = UserQuestion.objects.filter(user=request.user).get()
    if subm.exists():
        if curr.count == 2:
            return feedback(request)
        else:
            return render(request, 'codereco/done.html', question2)
    elif curr.question == 1:
        return render(request, 'codereco/other.html', question2)
    return render(request, 'codereco/index.html', question2)

@login_required
def feedback(request):
    return render(request, 'feedback.html')

@login_required
def intro(request):
    return render(request, 'codereco/intro.html', {'questionId':3})

@login_required
def test(request):
    return render(request, 'codereco/test.html', test_question)

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
                return intro(request)
            else:
                return render(request, 'registration/login.html', {'msg': 'Username or Password incorrect'})
        except Exception as ex:
            print(ex)
        return render(request, 'registration/login.html', {'msg':'Username and '})
        
def user_register(request):
    global counter
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
                lock.acquire()
                if counter % 2 == 0:
                    userquestion = UserQuestion(user=user,question=1)
                else:
                    userquestion = UserQuestion(user=user,question=2)
                counter += 1
                lock.release()
                userquestion.save()
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
    custom_weight.code = request.body.decode()
    return custom_weight

@login_required
@csrf_exempt
def user_vote(request):
    try:
        cw = None
        if request.method == "POST":
            data = json.loads(request.body.decode())
            source = data['reco']['source']
            code = data['code']
            rating = data['reco']['rating']
            if source == 2:
                # we need to create two vote objects here
                vote1, vote2 = Vote(), Vote()
                vote1.question, vote2.question = data['qId'], data['qId']
                vote1.user, vote2.user = request.user, request.user             
                vote1.rank, vote2.rank = data['reco']['rank_1'], data['reco']['rank_0']
                vote1.source, vote2.source = 1, 0
                vote1.rating, vote2.rating = rating, rating
                vote1.code, vote2.code = code
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
                vote.rating = rating
                vote.code = code
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
            curr = UserQuestion.objects.filter(user=request.user).get()
            if curr.question == 2:
                curr.question = 1
            else:
                curr.question = 2
            curr.count += 1
            curr.save()
            return JsonResponse({})            
    except Exception as ex:
        print(ex)
        return HttpResponse(str(ex))

@login_required
@csrf_exempt
def user_log(request):
    try:
        if request.method == "POST":
            data = json.loads(request.body.decode())
            source = data['reco']['source']
            if source == 2:
                # we need to create two vote objects here
                log1, log2 = CutCopy(), CutCopy()
                log1.question, log2.question = data['qId'], data['qId']
                log1.user, log2.user = request.user, request.user             
                log1.rank, log2.rank = data['reco']['rank_1'], data['reco']['rank_0']
                log1.rating, log2.rating = data['reco']['rating'], data['reco']['rating']
                log1.source, log2.source = 1, 0
                log1.save()
                log2.save()
            else:
                log = CutCopy()
                log.question = data['qId']
                log.user = request.user
                log.rank = data['reco']['rank']
                log.rating = data['reco']['rating']
                log.source = source
                log.save()
    except Exception as ex:
        print(ex)
    return HttpResponse()

@login_required
@csrf_exempt
def user_weight_update(request):
    cw = get_weights(request)
    cw.save()
    return JsonResponse({})
