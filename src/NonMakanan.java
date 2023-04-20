import java.awt.Point;

public class NonMakanan extends Barang {
    private int panjang;
    private int lebar;
    private int harga;
    private int shippingTime;
    private Point posisi;

    public NonMakanan(String namaNonMakanan) {
        super(namaNonMakanan);
    }

    public int getPanjang() {
        return panjang;
    }

    public void setPanjang(int panjang) {
        this.panjang = panjang;
    }

    public int getLebar() {
        return lebar;
    }

    public void setLebar(int lebar) {
        this.lebar = lebar;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(int shippingTime) {
        this.shippingTime = shippingTime;
    }

    public Point getPosisi() {
        return posisi;
    }

    public void setPosisi(Point posisi) {
        this.posisi = posisi;
    }

}
