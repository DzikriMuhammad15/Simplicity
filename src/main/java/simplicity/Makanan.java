import java.util.ArrayList;

public class Makanan extends Barang {
    private int kekenyangan;
    private ArrayList<String> arrayOfBahanMakanan;

    public Makanan(String namaMakanan) {
        super(namaMakanan);
        if (namaMakanan.equals("Nasi Ayam")) {
            kekenyangan = 16;
            arrayOfBahanMakanan = new ArrayList<String>();
            arrayOfBahanMakanan.add("Nasi");
            arrayOfBahanMakanan.add("Ayam");
        } else if (namaMakanan.equals("Nasi Kari")) {
            kekenyangan = 30;
            arrayOfBahanMakanan = new ArrayList<String>();
            arrayOfBahanMakanan.add("Nasi");
            arrayOfBahanMakanan.add("Kentang");
            arrayOfBahanMakanan.add("Wortel");
            arrayOfBahanMakanan.add("Sapi");
        } else if (namaMakanan.equals("Susu Kacang")) {
            kekenyangan = 5;
            arrayOfBahanMakanan = new ArrayList<String>();
            arrayOfBahanMakanan.add("Susu");
            arrayOfBahanMakanan.add("Kacang");
        } else if (namaMakanan.equals("Tumis Sayur")) {
            kekenyangan = 5;
            arrayOfBahanMakanan = new ArrayList<String>();
            arrayOfBahanMakanan.add("Wortel");
            arrayOfBahanMakanan.add("Bayam");
        } else if (namaMakanan.equals("Bistik")) {
            kekenyangan = 22;
            arrayOfBahanMakanan = new ArrayList<String>();
            arrayOfBahanMakanan.add("Kentang");
            arrayOfBahanMakanan.add("Sapi");
        }
    }

    public int getKekenyangan() {
        return kekenyangan;
    }

    public void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
    }

    public ArrayList<String> getArrayOfBahanMakanan() {
        return arrayOfBahanMakanan;
    }

    public void setArrayOfBahanMakanan(ArrayList<String> arrayOfBahanMakanan) {
        this.arrayOfBahanMakanan = arrayOfBahanMakanan;
    }
}
