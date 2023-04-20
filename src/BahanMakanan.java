public class BahanMakanan extends Barang {
    private int kekenyangan;
    private int hargaBahan;

    public BahanMakanan(String namaBahan) {
        super(namaBahan);
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
