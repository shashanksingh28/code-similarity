from django.db import models
from django.contrib.auth.models import User

# Create your models here.

class Vote(models.Model):
    voteid = models.AutoField(primary_key=True)
    user = models.ForeignKey(User)
    question = models.IntegerField()
    source = models.IntegerField()
    rank = models.IntegerField()
    rating = models.IntegerField()
    time = models.DateTimeField(auto_now_add=True)

class Submission(models.Model):
    submissionid = models.AutoField(primary_key=True)
    user = models.ForeignKey(User)
    question = models.IntegerField()
    code = models.TextField()
    time = models.DateTimeField(auto_now_add=True)

class CustomWeight(models.Model):
    weightid = models.AutoField(primary_key=True)
    user = models.ForeignKey(User)
    vote = models.ForeignKey(Vote, null=True, default=None)
    signature = models.FloatField()
    structure = models.FloatField()
    concepts = models.FloatField()
    language = models.FloatField()
    time = models.DateTimeField(auto_now_add=True)
