from django.db import models
from django.shortcuts import render, get_object_or_404
from django.http import HttpResponseRedirect, HttpResponse
from django.core.urlresolvers import reverse
from receiver.models import *
from django.views.decorators.csrf import csrf_exempt
@csrf_exempt
def show(request):
    return render(request,'sender/a.html',{})

@csrf_exempt
def sendLyric(request):
    name=request.POST['name']
    artists=request.POST['artist']
    object=get_object_or_404(Songs,name=name,artist=artist)
    lyric=object.lyric
    response = HttpResponse(lyric, mimetype='text/plain')
    response['Content-Disposition'] = "attachment; filename=%s - %s.lrc" % \
                                     (artist, name)
    return response


@csrf_exempt
def sendSong(request):
    name=request.POST['name']
    artist=request.POST['artist']
    object=get_object_or_404(Songs,name=name,artist=artist)
    music=object.file
    response = HttpResponse(music, mimetype='audio/mpeg')
    response['Content-Disposition'] = "attachment; filename=%s - %s.mp3" % \
                                     (artist, name)
    return response