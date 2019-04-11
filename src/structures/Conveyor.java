package structures;

import objects.GenericGameObject;

public class Conveyor extends Structure {

    private DIRECTIONS direction;
    public Conveyor parent, child;

    public Conveyor(int x, int y, DIRECTIONS d) {
        super(x, y, 1, 1);
        direction = d;

    }
    @Override
    void onUpdate() {

    }

    @Override
    void onPlace() {

    }

    @Override
    void onDelete() {

    }

    @Override
    void onGet(int localX, int localY, GenericGameObject g) {

    }

    @Override
    void onTake(int localX, int localY, GenericGameObject g) {

    }
}
