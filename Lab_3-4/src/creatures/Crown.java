package creatures;

public class Crown extends Animal {

    public Crown(int lvlOfHorror, int stels){
        super(lvlOfHorror, stels);
    }

    @Override
    public int hashCode() {
        return 31 * (lvlOfHorror + stels);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Crown) {
            Crown k = (Crown) obj;
            if (k.hashCode() == this.hashCode()) {
                if (k.getStels() == this.stels && k.getLvlOfHorror() == this.lvlOfHorror) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return ("Crown" + this.lvlOfHorror + this.stels);
    }

    @Override
    public void makeNoise() {
        System.out.println("КАР-КАР");
    }
}

