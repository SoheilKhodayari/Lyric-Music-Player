from django.shortcuts import render

from receiver.models import *

from django import forms
class LyricUploadForm(forms.Form):
    lyric = forms.FileField()


class MusicUploadForm(forms.Form):
    music = forms.FileField()

def receiveLyric(request):
    name=request.POST['name']
    artist=request.POST['artist']
    form = LyricUploadForm(request.POST, request.FILES)
    try:
        exSong=Songs.objects.get(name=name,artist=artist)
    except:
        exSong=0
    if form.is_valid():
        if exSong:
            exSong.lyric=form.cleaned_data['lyric']
        else:
            lyric=form.cleaned_data['lyric']
            newSong=Song(name=name,artist=artist,lyric=lyric)
        

def receiveSong(request):
    name=request.POST['name']
    artist=request.POST['artist']
    form = MusicUploadForm(request.POST, request.FILES)
    try:
        exSong=Songs.objects.get(name=name,artist=artist)
    except:
        exSong=0
    if form.is_valid():
        if exSong:
            exSong.music=form.cleaned_data['music']
        else:
            muisc==form.cleaned_data['music']
            newSong=Song(name=name,artist=artist,music=music)