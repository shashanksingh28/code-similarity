"""study URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from django.contrib import admin, auth
from . import views

urlpatterns = [
    # url(r'^', include('django.contrib.auth.urls')),
    url(r'^login', views.user_login, name='login'),
    url(r'^register', views.user_register, name='register'),
    url(r'^logout', views.user_logout, name='logout'),
    url(r'^$', views.tutorial, name='index'),
    url(r'^study/q1', views.q1, name='question1'),
    url(r'^study/q2', views.q2, name='question2'),
    url(r'^study/faq', views.faq, name='faq'),
    url(r'^study', views.index, name='study'),
    url(r'^tutorial', views.tutorial),
    url(r'^vote', views.user_vote, name="vote"),
    url(r'^submit', views.user_submit, name='submit'),
    url(r'^weightupdate', views.user_weight_update, name='weight_update'),
    url(r'^cutcopy', views.user_log, name='log'),
    url(r'^feedback', views.feedback, name='feedback')
]
