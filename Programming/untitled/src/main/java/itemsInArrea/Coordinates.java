package itemsInArrea;
import utility.Validate;

public class Coordinates implements Validate {
    private Float x; //Поле не может быть null
    private float y;

    public Coordinates(Float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean validate() {
        return (x != null);
    }
}
