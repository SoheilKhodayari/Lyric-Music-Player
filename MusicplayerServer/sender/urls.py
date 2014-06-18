from django.conf.urls import patterns,url
from sender import views

urlpatterns = patterns('',
    url(r'^lyric/$',views.sendLyric, name='lyricdl'),
    url(r'^music/$',views.sendSong, name='musicdl'),
    )
