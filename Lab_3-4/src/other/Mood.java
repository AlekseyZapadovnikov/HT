package other;

public enum Mood {
    NORMAL(100),
    NERVOUS(60),
    SCARED(30),
    MINDLESS(0);

    final int lvl;

    public int getLvl(){
        return lvl;
    }

    Mood(int lvl) {
        this.lvl = lvl;
    }

}
