import java.util.ArrayList;

public class World{
    private int panjang = 64;
    private int lebar = 64;
    private int hari = 0;
    private int waktu=0;
    private ArrayList<Sim>  ArrSim = new ArrayList<>(0);
    private static World instance = new World();
    
    private World(){}
    static World getInstance(){
        return instance;
    }

    public int getPanjang(){
        return panjang;
    }    
    public int getLebar(){
        return lebar;
    }
    public int getHari(){
        return hari;
    }

    public int getWaktu(){
        return waktu;
    }

    public void setPanjang(int panjang){
        this.panjang=panjang;
    }    
    public void setLebar(int lebar){
        this.lebar=lebar;
    }
    public void setHari(int hari){
        this.hari=hari;
    }

    public void setWaktu(int waktu){
        this.waktu=waktu;
    }

    public ArrayList<Sim> getArrSim(){
        return ArrSim;
    }

}