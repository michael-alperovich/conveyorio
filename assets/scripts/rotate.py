from PIL import Image
def createRotator(prefix):
    # take in the image
    im = Image.open(prefix+".png")
    center = map(lambda x: x/2, im.size)
    for ang in range(0,360):
        new_image = im.rotate(ang,expand = False,center = center)
        new_image.save(prefix+"_rotateframe(%s).png"%(ang))
        
        
