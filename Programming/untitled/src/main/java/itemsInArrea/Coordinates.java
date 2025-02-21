package itemsInArrea;
import utility.Validate;

import java.util.Objects;

public class Coordinates implements Validate {
    private Float x; //Поле не может быть null
    private float y;

    public Coordinates(Float x, float y){
        this.x = x;
        this.y = y;
    }

    public Coordinates(){}

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public boolean validate() {
        return (x != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(y, that.y) == 0 && Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
