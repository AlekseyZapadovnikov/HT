package itemsInArrea;

import utility.Validate;

import java.time.LocalDate;

/**
 * The Route class represents a route with various attributes such as id, name, coordinates,
 * creation date, starting and ending locations, and distance.
 * <p>
 * This class implements the {@link Validate} interface to ensure that a Route instance is valid,
 * and the {@link Comparable} interface to compare two Route objects based on their id.
 * </p>
 */
public class Route implements Validate, Comparable<Route> {
    // The id of the route. Must be greater than 0, unique, and generated automatically.
    private long id;

    // The name of the route. Cannot be null or empty.
    private String name;

    // The coordinates of the route. Cannot be null.
    private Coordinates coordinates;

    // The date of creation. Cannot be null and is generated automatically.
    private LocalDate creationDate;

    // The starting location of the route. Cannot be null.
    private Location from;

    // The destination location of the route. Can be null.
    private Location to;

    // The distance of the route. Must be greater than 1.
    private long distance;

    /**
     * Constructs a new Route with the specified attributes including a destination location.
     *
     * @param id          the unique identifier of the route (must be > 0)
     * @param name        the name of the route (cannot be null or empty)
     * @param coordinates the coordinates of the route (cannot be null)
     * @param from        the starting location (cannot be null)
     * @param distance    the distance of the route (must be > 1)
     * @param to          the destination location (can be null)
     */
    public Route(long id, String name, Coordinates coordinates, Location from, long distance, Location to) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    /**
     * Constructs a new Route with the specified attributes without a destination location.
     *
     * @param id          the unique identifier of the route (must be > 0)
     * @param name        the name of the route (cannot be null or empty)
     * @param coordinates the coordinates of the route (cannot be null)
     * @param from        the starting location (cannot be null)
     * @param distance    the distance of the route (must be > 1)
     */
    public Route(long id, String name, Coordinates coordinates, Location from, long distance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.from = from;
        this.distance = distance;
    }

    /**
     * Default constructor.
     */
    public Route() {
    }

    /**
     * Validates the properties of the Route object.
     * <p>
     * A valid Route must have:
     * - an id greater than 0,
     * - a non-null, non-empty name,
     * - a non-null creation date,
     * - non-null and valid coordinates,
     * - a non-null starting location,
     * - a distance greater than 1.
     * </p>
     *
     * @return true if the Route is valid; false otherwise.
     */
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (creationDate == null) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (from == null || distance < 1) return false;
        return true;
    }

    /**
     * Gets the unique identifier of the route.
     *
     * @return the id of the route.
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the distance of the route.
     *
     * @return the distance.
     */
    public long getDistance() {
        return distance;
    }

    /**
     * Gets the destination location.
     *
     * @return the destination location, or null if not set.
     */
    public Location getTo() {
        return to;
    }

    /**
     * Gets the starting location.
     *
     * @return the starting location.
     */
    public Location getFrom() {
        return from;
    }

    /**
     * Gets the creation date.
     *
     * @return the creation date.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the coordinates of the route.
     *
     * @return the coordinates.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Gets the name of the route.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the route.
     *
     * @param name the new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Compares this Route with another Route based on their ids.
     *
     * @param other the other Route to compare to.
     * @return a negative integer, zero, or a positive integer as this id is less than, equal to, or greater than the other id.
     */
    @Override
    public int compareTo(Route other) {
        return Long.compare(this.id, other.id);
    }

    /**
     * Returns a string representation of the Route.
     *
     * @return a string representation containing all attributes of the route.
     */
    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                '}';
    }
}