package itemsInArrea;

import itemsInArrea.Validate;

import java.time.LocalDate;


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

    public Route(String name, Coordinates coordinates, Location from, long distance, Location to) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }


    public Route(long id, String name, Coordinates coordinates, Location from, long distance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.from = from;
        this.distance = distance;
    }


    public Route() {
    }


    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (creationDate == null) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (from == null || distance < 1) return false;
        return true;
    }


    public long getId() {
        return id;
    }


    public long getDistance() {
        return distance;
    }


    public Location getTo() {
        return to;
    }


    public Location getFrom() {
        return from;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id){
        this.id = id;
    }


    @Override
    public int compareTo(Route other) {
        return Long.compare(this.id, other.id);
    }


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