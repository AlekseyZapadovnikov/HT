package itemsInArrea;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Validate, Serializable {
    private static final long serialVersionUID = 1L; // Рекомендуется добавить

    private Float x;
    private long y;
    private String name;

    public Location(Float x, long y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
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
        return this.x != null && (this.name != null && !this.name.isEmpty());
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
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