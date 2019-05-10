from PIL import Image
def splitImg(infile):
    x = Image.open(infile+".png")
    csize = x.size
    print csize
    for p in range(0,50):
        subimage  = x.crop((p,0,p+1,csize[1]))
        subimage.save(infile+"_slice%s.png"%(p))
def createCompiled(infile,nslices):
    originallist = [infile+"_slice%s.png"%(i) for i in range(0,nslices)]
    
    for phase in range(0,nslices):
        
        images = map(Image.open, (originallist[-phase:]+originallist[:-phase]))
        widths, heights = zip(*(i.size for i in images))
        total_width = sum(widths)
        max_height = max(heights)

        new_im = Image.new('RGB', (total_width, max_height))

        x_offset = 0
        for im in images:
          new_im.paste(im, (x_offset,0))
          x_offset += im.size[0]

        new_im.save(infile+'_phase%s.png'%(phase))
def rotateCopmiled(infile,nslices,tangle):
    for phase in range(0,nslices):
        orim = Image.open(infile+"_phase%s.png"%(phase))
        orim.rotate(tangle,expand = 1).save(infile+"_phase%s.png"%(phase))
