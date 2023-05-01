package simplicity;
public class Posisi {
    private Rumah currRumah;
    private Ruangan currRuangan;
    private Barang currBarang;

    //constructor Posisi
    public Posisi(Rumah currRumah, Ruangan currRuangan, Barang currBarang){
        this.currRumah = currRumah;
        this.currRuangan = currRuangan;
        this.currBarang = currBarang;
    }

    //getter currRumah
    public Rumah getCurrRumah(){
        return this.currRumah;
    }

    //getter currBarang
    public Barang getCurrBarang(){
        return this.currBarang;
    }

    //getter currRuangan
    public Ruangan getCurrRuangan(){
        return this.currRuangan;
    }

    //setter currRuangan
    public void setCurrRuangan(Ruangan ruangan){
        this.currRuangan = ruangan;
    }

    //setter currRumah
    public void setCurrRumah(Rumah rumah){
        this.currRumah = rumah;
    }

    //setter currBarang
    public void setCurrBarang(Barang barang){
        this.currBarang = barang;
    }
}
