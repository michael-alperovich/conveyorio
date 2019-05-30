from PIL import Image
def quarterImage(ifile,TL,TR,BL,BR):
    im = Image.open(ifile)
    topLeft = im.crop((0,0,50,50))
    topRight = im.crop((50,0,100,50))
    bottomLeft = im.crop((0,50,50,100))
    bottomRight = im.crop((50,50,100,100))
    topLeft.save(TL,"PNG")
    topRight.save(TR,"PNG")
    bottomLeft.save(BL,"PNG")
    bottomRight.save(BR,"PNG")

def curvedConveyor(namingconvention):
    if namingconvention.count("%s") != 2:
        return False
    for angle in range(0,360):
        quarterImage("curvedconveyor_rotateframe(%s).png"%(angle),namingconvention%("NW",angle),namingconvention%("NE",angle),namingconvention%("SW",angle), namingconvention%("SE",angle))
    
