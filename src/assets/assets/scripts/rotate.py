from PIL import Image
def createRotator(prefix):
    # take in the image
    im = Image.open(prefix+".png")
    center = map(lambda x: x/2, im.size)
    for ang in range(0,360):
        new_image = im.rotate(ang,expand = False,center = center)
        new_image.save(prefix+"_rotateframe(%s).png"%(ang))
        fixBackground(prefix+"_rotateframe(%s).png"%(ang))
def fixBackground(im):
    img = Image.open(im)
    img = img.convert("RGBA")

    pixdata = img.load()

    width, height = img.size
    for y in xrange(height):
        for x in xrange(width):
            if pixdata[x, y] == (255, 255, 255, 255):
                pixdata[x, y] = (255, 255, 255, 0)

    img.save(im, "PNG")

def fillImage(im,originalrgb,newrgba):
    img = Image.open(im)
    img = img.convert('RGBA')
    pixdata = img.load()
    width,height = img.size
    width, height = img.size
    for y in xrange(height):
        for x in xrange(width):
            if list(pixdata[x, y][:3]) == list(originalrgb[:3]):
                pixdata[x, y] = tuple(newrgba)
    img.save(im,"PNG")
    
