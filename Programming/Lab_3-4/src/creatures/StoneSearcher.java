package creatures;
import other.Stone;

public interface StoneSearcher {
    public void analiseStone(Stone stone);
    abstract public void takeStone(Stone stone);
}
