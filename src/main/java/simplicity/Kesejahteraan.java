package simplicity;
public class Kesejahteraan {
    private boolean Dead;
    private int Mood;
    private int Kesehatan;
    private int Kekenyangan;

    //constructor Kesejahteraan
    public Kesejahteraan(boolean dead, int mood, int kesehatan, int kekenyangan){
        this.Dead = dead;
        this.Mood = mood;
        this.Kesehatan = kesehatan;
        this.Kekenyangan = kekenyangan;
    }

    //getter Dead
    public boolean isDead(){
        return this.Dead;
    }

    //setter Dead
    public void setDead(boolean dead){
        this.Dead = dead;
    }

    //getter Mood
    public int getMood(){
        return this.Mood;
    }

    //setter Mood
    public void setMood(int mood){
        this.Mood = mood;
    }

    //getter Kesehatan
    public int getKesehatan(){
        return this.Kesehatan;
    }

    //setter Kesehatan
    public void setKesehatan(int kesehatan){
        this.Kesehatan = kesehatan;
    }

    //getter Kekenyangan
    public int getKekenyangan(){
        return this.Kekenyangan;
    }

    //setter Kekenyangan
    public void setKekenyangan(int kekenyangan){
        this.Kekenyangan = kekenyangan;
    }
}
