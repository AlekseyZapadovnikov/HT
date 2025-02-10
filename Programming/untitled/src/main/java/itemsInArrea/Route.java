package itemsInArrea;

import utility.Validate;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Route implements Validate {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле не может быть null
    private Location to; //Поле может быть null
    private long distance; //Значение поля должно быть больше 1

    public Route(long id, String name, Coordinates coordinates, Location from, long distance, Location to) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Route(long id, String name, Coordinates coordinates, Location from, long distance) {
        this.id = id;
        this.distance = distance;
        this.from = from;
        this.creationDate = creationDate;
        this.coordinates = coordinates;
        this.name = name;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setName(String name) {
        this.name = name;
    }
}
