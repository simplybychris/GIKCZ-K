package CGlab;

public class Vec2i {
    public float x;
    public float y;

    public Vec2i(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
