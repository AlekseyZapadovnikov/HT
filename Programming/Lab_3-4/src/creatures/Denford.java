package creatures;

public class Denford extends Searcher {

    public Denford(int mind, int intelligence, int passivAttention) {
        super(mind, intelligence, passivAttention);
        super.setName("Денфорд ");
    }

    public Denford(int[] lst) {
        super(lst);
    }

    @Override
    public String toString() {
        return ("Denford" + super.getMind());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Denford) {
            Denford k = (Denford) obj;
            if (this.hashCode() == k.hashCode()) {
                if (k.getPassivAttention() == this.getPassivAttention() && this.getName().equals(k.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * (getIntelligence() + getName().hashCode());
    }

//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
}

