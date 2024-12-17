package creatures;

public class Dog extends Animal {

    public Dog(int lvlOfHorror, int stels){
        super(lvlOfHorror, stels);
    }

    @Override
    public int hashCode() {
        return 31 * (lvlOfHorror + stels);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Crown) {
            Dog k = (Dog) obj;
            if (k.hashCode() == this.hashCode()) {
                if (k.getStels() == this.stels && k.getLvlOfHorror() == this.lvlOfHorror) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void makeNoise() {
        System.out.println("ГАВ-ГАВ");
    }
}
