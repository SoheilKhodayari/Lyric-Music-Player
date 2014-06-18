from django.conf.urls import patterns,url
from receiver import views
urlpatterns = patterns('',
    url(r'^lyric/$',views.receiveLyric, name='lyricup'),
    url(r'^music/$',views.receiveSong, name='musicup'),
    )
    