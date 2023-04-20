import java.util.*;

public class Rumah {
    // atribut
    private Point lokasi;
    private ArrayList<Ruangan> arrOfRuangan = new ArrayList<Ruangan>();

    // konstruktor
    public Rumah(Point lokasi) {
        this.lokasi = lokasi;
        // membuat ruangan utama
        Ruangan r = new Ruangan("ruangUtama");
        arrOfRuangan.add(r);
    }

    // method-method

    // getter
    public Point getLokasi() {
        return this.lokasi;
    }

    public ArrayList<Ruangan> getArrayOfRuangan() {
        return this.arrOfRuangan;
    }

    // setter
    public void setLokasi(Point newLoc) {
        this.lokasi = newLoc;
    }

    public void addRuangan(Ruangan r) {
        arrOfRuangan.add(r);
        // nanti threadnya ditambah di upgradeRumah trus di akhir threadnya panggil
        // addRuangan ini
    }

    public Ruangan getRuangan(String nama) {
        for (Ruangan r : arrOfRuangan) {
            String namaRuangan = r.getNamaRuangan();
            if (namaRuangan.equals(nama)) {
                return r;
            }
        }
        return null;
    }

}