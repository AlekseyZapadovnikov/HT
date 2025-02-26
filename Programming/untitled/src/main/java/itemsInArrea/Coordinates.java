package itemsInArrea;

import utility.Validate;

import java.util.Objects;

/**
 * The Coordinates class represents a pair of (x, y) coordinates.
 * <p>
 * The x-coordinate cannot be null, while the y-coordinate is represented as a primitive float.
 * This class implements the {@link Validate} interface to ensure that the coordinates are valid.
 * </p>
 */
public class Coordinates implements Validate {
    /**
     * The x-coordinate. This field cannot be null.
     */
    private Float x;

    /**
     * The y-coordinate.
     */
    private float y;

    /**
     * Constructs a new Coordinates instance with the specified x and y values.
     *
     * @param x the x-coordinate (must not be null).
     * @param y the y-coordinate.
     */
    public Coordinates(Float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Default constructor.
     */
    public Coordinates() {
    }

    /**
     * Sets the x-coordinate.
     *
     * @param x the new x-coordinate (must not be null).
     */
    public void setX(Float x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate.
     *
     * @param y the new y-coordinate.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Validates the Coordinates instance.
     * <p>
     * A Coordinates instance is valid if its x-coordinate is not null.
     * </p>
     *
     * @return true if the x-coordinate is not null; false otherwise.
     */
    @Override
    public boolean validate() {
        return (x != null);
    }

    /**
     * Checks the equality of this Coordinates instance with another object.
     *
     * @param o the object to compare with.
     * @return true if both objects are Coordinates with equal x and y values; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(y, that.y) == 0 && Objects.equals(x, that.x);
    }

    /**
     * Returns the hash code for this Coordinates instance.
     *
     * @return the hash code based on the x and y values.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a string representation of the Coordinates.
     *
     * @return a string in the format "Coordinates{x=value, y=value}".
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
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
     * Returns the y-coordinate.
     *
     * @return the y-coordinate.
     */
    public float getY() {
        return y;
    }
}