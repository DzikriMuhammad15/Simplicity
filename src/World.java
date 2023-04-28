import java.util.ArrayList;

public class World {
    private int panjang = 64;
    private int lebar = 64;
    private int hari = 0;
    private int waktu = 0;
    private ArrayList<Sim> ArrSim = new ArrayList<>(0);
    private static World instance = new World();
    private Object lock = new Object();

    // konstruktor world
    private World() {
    }

    // getter world
    static World getInstance() {
        return instance;
    }

    public Object getLock() {
        return this.lock;
    }

    // getter panjang
    public int getPanjang() {
        return panjang;
    }

    // getter lebar
    public int getLebar() {
        return lebar;
    }

    // getter hari
    public int getHari() {
        return hari;
    }

    // getter waktu
    public int getWaktu() {
        return waktu;
    }

    // setter panjang
    public void setPanjang(int panjang) {
        this.panjang = panjang;
    }

    // setter lebar
    public void setLebar(int lebar) {
        this.lebar = lebar;
    }

    // setter hari
    public void setHari(int hari) {
        this.hari = hari;
    }

    // setter waktu
    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }

    public void addWaktu(int waktu){
        if (this.waktu+waktu>720){
            setHari((this.waktu+waktu)/720);
            setWaktu((this.waktu+waktu)%720);
        }else{
            setWaktu(waktu);
        }
    }
    // getter arraylist
    public ArrayList<Sim> getArrSim() {
        return ArrSim;
    }

    // getter untuk mendapatkan sim yang memiliki rumah yang sedang dikunjungi sim
    // saat ini
    public Sim getSimOwnRumah(Rumah rumah) {
        Sim sim2 = null;
        for (Sim i : ArrSim) {
            if (i.getRumah().equals(rumah)) {
                sim2 = i;
            }
        }
        return sim2;
    }
}