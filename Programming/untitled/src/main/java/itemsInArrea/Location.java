package itemsInArrea;
import utility.Validate;

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
    public boolean validate() {
        return this.x != null && !this.name.isEmpty();
    }
}
