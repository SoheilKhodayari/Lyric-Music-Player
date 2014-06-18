from django.db import models


# Create your models here.
class Songs(models.Model):
    name=models.CharField(max_length=100,blank=True)
    artist=models.CharField(max_length=100,blank=True)
#     cover=models.ImageField(null=True,upload_to='photos',default='photos/defaulpcture')
    file=models.FileField(null=True,upload_to='songs')
    lyric=models.FileField(null=True,upload_to='lyrics')
    