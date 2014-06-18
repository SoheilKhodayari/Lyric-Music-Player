from django.conf.urls import patterns, include, url
from sender import views 

from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'Musicplayer.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),
    url(r'^$', views.show,name='first'),
    url(r'^admin/', include(admin.site.urls)),
    url(r'^upload/', include('receiver.urls',namespace='upload')),
    url(r'^download/', include('sender.urls',namespace='download')),
    
    
)

from django.conf import settings

if settings.DEBUG:
    urlpatterns += patterns('',
        url(r'^media/(?P<path>.*)$', 'django.views.static.serve', {
            'document_root': settings.MEDIA_ROOT,
        }),
   )
