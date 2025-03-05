package itemsInArrea;

import utility.Validate;
import java.util.Objects;

/**
 * The Location class represents a geographical location with x and y coordinates and a name.
 * <p>
 * The x-coordinate must not be null. The name field must not be empty if provided.
 * </p>
 */
public class Location implements Validate {
    /**
     * The x-coordinate. This field cannot be null.
     */
    private Float x;

    /**
     * The y-coordinate.
     */
    private long y;

    /**
     * The name of the location. May be null, but if not it must not be empty.
     */
    private String name;

    /**
     * Constructs a new Location instance with the specified x-coordinate, y-coordinate, and name.
     *
     * @param x    the x-coordinate (must not be null).
     * @param y    the y-coordinate.
     * @param name the name of the location (must not be empty if provided).
     */
    public Location(Float x, long y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    /**
     * Checks if this Location is equal to another object.
     *
     * @param o the object to compare with.
     * @return true if the object is a Location with the same x, y values and name; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return y == location.y && Objects.equals(x, location.x) && Objects.equals(name, location.name);
    }

    /**
     * Returns the hash code for this Location.
     *
     * @return the hash code based on x, y, and name.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }

    /**
     * Validates the Location object.
     * <p>
     * A valid Location has a non-null x-coordinate and a non-empty name.
     * </p>
     *
     * @return true if the Location is valid; false otherwise.
     */
    @Override
    public boolean validate() {
        return this.x != null && (this.name != null && !this.name.isEmpty());
    }

    /**
     * Returns a string representation of the Location.
     *
     * @return a string in the format "Location{x=value, y=value, name='value'}".
     */
    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Returns the x-coordinate.
     *
     * @return the x-coordinate.
     */
    public Float getX() {
        return x;
    }

    /**
     * Returns the name of the location.
     *
     * @return the name of the location.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the y-coordinate.
     *
     * @return the y-coordinate.
     */
    public long getY() {
        return y;
    }
}