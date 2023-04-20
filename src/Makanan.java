public class Makanan extends Barang {
    private int kekenyangan;
    private String[] arrayOfBahanMakanan;

    public Makanan(String namaMakanan) {
        super(namaMakanan);
    }

    public int getKekenyangan() {
        return kekenyangan;
    }

    public void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
    }

    public String[] getArrayOfBahanMakanan() {
        return arrayOfBahanMakanan;
    }

    public void setArrayOfBahanMakanan(String[] arrayOfBahanMakanan) {
        this.arrayOfBahanMakanan = arrayOfBahanMakanan;
    }
}
