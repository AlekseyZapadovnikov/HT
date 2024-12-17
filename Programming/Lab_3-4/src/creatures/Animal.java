package creatures;

abstract public class Animal {
    protected final int lvlOfHorror;
    protected final int stels;

    public Animal(int lvlOfHorror, int stels){
        this.lvlOfHorror = lvlOfHorror;
        this.stels = stels;
    }

    abstract void makeNoise();

    public int getLvlOfHorror(){
        return lvlOfHorror;
    }

    public int getStels(){
        return stels;
    }
}
