package simplicity;
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

        // instansiasi barang2 untuk dimasukkan ke dalam ruanganUtama
        NonMakanan meja = new NonMakanan("Meja dan Kursi");
        NonMakanan jam = new NonMakanan("Jam");
        NonMakanan kompor = new NonMakanan("Kompor Gas");
        NonMakanan toilet = new NonMakanan("Toilet");
        NonMakanan kasur = new NonMakanan("Kasur Single");
        HashMap<String,Integer> inventory = new HashMap<>();
        inventory.put("Meja dan Kursi",1);
        inventory.put("Jam",1);
        inventory.put("Kompor Gas",1);
        inventory.put("Toilet",1);
        inventory.put("Kasur Single",1);
        // masukkin ke dalam ruanganUtama

        // locate barang
        r.locateBarang(kasur, 0, 0,inventory);
        r.locateBarang(toilet, 5, 5,inventory);
        r.locateBarang(kompor, 4, 0,inventory);
        r.locateBarang(jam, 5, 3,inventory);
        r.locateBarang(meja, 0, 3,inventory);

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
