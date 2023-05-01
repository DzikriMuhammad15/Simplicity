package simplicity;
import java.util.Random;

public class BahanMakanan extends Barang {
    private int kekenyangan;
    private int hargaBahan;
    private int shippingTime;
    private int waktuSelesai;

    public BahanMakanan(String namaBahan) {
        super(namaBahan);
        if (namaBahan.equals("Nasi")) {
            kekenyangan = 5;
            hargaBahan = 5;
        } else if (namaBahan.equals("Kentang")) {
            kekenyangan = 4;
            hargaBahan = 3;
        } else if (namaBahan.equals("Ayam")) {
            kekenyangan = 8;
            hargaBahan = 10;
        } else if (namaBahan.equals("Sapi")) {
            kekenyangan = 15;
            hargaBahan = 12;
        } else if (namaBahan.equals("Wortel")) {
            kekenyangan = 3;
            hargaBahan = 2;
        } else if (namaBahan.equals("Bayam")) {
            kekenyangan = 3;
            hargaBahan = 2;
        } else if (namaBahan.equals("Kacang")) {
            kekenyangan = 2;
            hargaBahan = 2;
        } else if (namaBahan.equals("Susu")) {
            kekenyangan = 1;
            hargaBahan = 2;
        }
        Random rand = new Random();
        shippingTime = rand.nextInt(10) + 1;
        this.waktuSelesai = World.getInstance().getHari() * 720 + World.getInstance().getWaktu() + this.shippingTime;
    }

    public int getWaktuSelesai() {
        return this.waktuSelesai;
    }

    public void setWaktuSelesai(int waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public int getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(int shippingTime) {
        this.shippingTime = shippingTime;
    }

    public int getHargaBahan() {
        return hargaBahan;
    }

    public void setHargaBahan(int hargaBahan) {
        this.hargaBahan = hargaBahan;
    }

    public int getKekenyangan() {
        return kekenyangan;
    }

    public void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
    }
}
