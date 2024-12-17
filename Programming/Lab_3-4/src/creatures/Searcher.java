package creatures;
import other.*;
import java.util.ArrayList;

public class Searcher implements StoneSearcher {
    private int mind;
    private int health;
    private final int passivAttention;
    private final int intelligence;
    private int averegMood = Mood.NORMAL.getLvl();
    private int anderstanding;
    private String name;
    protected ArrayList<Stone> bag = new ArrayList<>();

    public Searcher(int mind, int intelligence, int passivAttention) {
        this.mind = mind;
        this.averegMood = averegMood;
        this.intelligence = intelligence;
        this.passivAttention = passivAttention;
    }

    public Searcher(int[] args){
        this.mind = args[0];
        this.averegMood = args[1];
        this.intelligence = args[2];
        this.passivAttention = args[3];
    }

    public void takeStone(Stone stone){
        System.out.println(name + "Пытается отломать камень");
        if (Math.random() > 0.1){
            bag.add(stone);
            analiseStone(stone);
        }else{
            System.out.println("Этот камень не изучить");
        }
    }

    public void analiseStone(Stone stone){
        int k = (intelligence * stone.analiseValue() / stone.quality()) * averegMood/100;
        anderstanding += k;
        if (anderstanding > 400){
            System.out.println("Я все понял!! Я знаю сколько этому городу лет, возвращаемся домой");
        }else{
            System.out.println("Пока не полностью понятно сколько лет этосу городу, нужны ещё данные");
        }
    }

    public void lookAround(Animal[] animals){
        double k = Math.random();
        if (k > 0.9 && passivAttention > animals[1].getStels()){
            System.out.println(name + ":'Что это такое?!!!'");
            animals[1].makeNoise();
            mind -= (animals[1].getLvlOfHorror() / 4);
            averegMood -= 20;
        }else if (k < 0.33){
            System.out.println("Я осмотрелся, вроде ничего не видно");
        }else {
            System.out.println(name + ":'Что это такое?!!!' ...");
            System.out.println("Это просто ворона");
            animals[0].makeNoise();
            mind -= (animals[0].lvlOfHorror / 4);
            averegMood -= 5;
        }
    }
    public int getAveregMoodlvl(){
        return averegMood;
    }

    public Mood getMood(){
        if (averegMood > 60){
            return Mood.NORMAL;
        }else if(averegMood > 30){
            return Mood.NERVOUS;
        }else if (averegMood > 0){
            return Mood.SCARED;
        }else{
            System.out.println(name + "Сошёл с ума");
            return Mood.MINDLESS;
        }
    }

    public boolean condition(){
        if (mind < 0 || averegMood < 0){
            return true;
        }else{
            return false;
        }
    }

    public int getMind() {
        return mind;
    }

    public int getAveregMood() {
        return averegMood;
    }

    public int getAnderstanding() {
        return anderstanding;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getPassivAttention() {
        return passivAttention;
    }

}
