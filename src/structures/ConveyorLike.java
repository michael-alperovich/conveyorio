package structures;

import conveyorio.Point;

public abstract class ConveyorLike extends Structure {

    public ConveyorLike previous, next;

    public ConveyorLike(Point loc, int dimX, int dimY) {
        super(loc, dimX, dimY);
    }

    public abstract DIRECTIONS getDirection(boolean asNext);
}
