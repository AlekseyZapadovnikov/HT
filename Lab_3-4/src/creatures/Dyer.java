package creatures;

public class Dyer extends Searcher {

    public Dyer(int mind, int intelligence, int passivAttention) {
        super(mind, intelligence, passivAttention);
        super.setName("Дэер ");
    }

    public Dyer(int[] lst) {
        super(lst);
    }

    @Override
    public int hashCode() {
        return 31 * (getIntelligence() + getName().hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dyer) {
            Dyer k = (Dyer) obj;
            if (this.hashCode() == k.hashCode()) {
                if (k.getPassivAttention() == this.getPassivAttention() && this.getName().equals(k.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return ("Денфорд" + getMind());
    }
}
