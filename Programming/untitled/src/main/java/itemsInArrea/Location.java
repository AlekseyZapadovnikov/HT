package itemsInArrea;
import utility.Validate;

import java.util.Objects;

public class Location implements Validate {
    private Float x; //Поле не может быть null
    private long y;
    private String name; //Строка не может быть пустой, Поле может быть null

    public Location(Float x, long y, String name) {
        this.x = x;
        this.name = name;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return y == location.y && Objects.equals(x, location.x) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }

    @Override
    public boolean validate() {
        return this.x != null && !this.name.isEmpty();
    }

    public Float getX() {
        return x;
    }

    public String getName() {
        return name;
    }

    public long getY() {
        return y;
    }
}
