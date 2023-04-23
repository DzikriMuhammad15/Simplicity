import java.util.Random;

public class NonMakanan extends Barang {
    private int panjang;
    private int lebar;
    private int harga;
    private int shippingTime;
    private Point posisi;
    private int orientasi;

    public NonMakanan(String namaNonMakanan) {
        super(namaNonMakanan);
        posisi = null;
        orientasi = 0;
        if (namaNonMakanan.equals("Kasur Single")) {
            panjang = 4;
            lebar = 1;
            harga = 50;
        } else if (namaNonMakanan.equals("Kasur Queen Size")) {
            panjang = 4;
            lebar = 2;
            harga = 100;
        } else if (namaNonMakanan.equals("Kasur King Size")) {
            panjang = 5;
            lebar = 2;
            harga = 150;
        } else if (namaNonMakanan.equals("Toilet")) {
            panjang = 1;
            lebar = 1;
            harga = 50;
        } else if (namaNonMakanan.equals("Kompor Gas")) {
            panjang = 2;
            lebar = 1;
            harga = 100;
        } else if (namaNonMakanan.equals("Kompor Listrik")) {
            panjang = 1;
            lebar = 1;
            harga = 200;
        } else if (namaNonMakanan.equals("Meja dan Kursi")) {
            panjang = 3;
            lebar = 3;
            harga = 50;
        } else if (namaNonMakanan.equals("Jam")) {
            panjang = 1;
            lebar = 1;
            harga = 10;
        }
        Random rand = new Random();
        shippingTime = rand.nextInt(10) + 1;
    }

    public void setOrientasi(int orientasi) {
        this.orientasi = orientasi;
    }

    public int getOrientasi() {
        return this.orientasi;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NonMakanan)) {
            return false;
        }
        NonMakanan nm = (NonMakanan) o;
        return nm.getNama().equals(getNama());
    }

}
