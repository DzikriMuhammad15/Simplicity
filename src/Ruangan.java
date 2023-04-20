import java.util.*;

public class Ruangan {
    // atribut
    private String namaRuangan;
    private ArrayList<Barang> barangInRuangan = new ArrayList<>();
    private String[] daftarBarangFix = { "kasur single", "kasur queen size", "kasur king size", "toilet", "kompor gas",
            "kompor listrik", "meja dan kursi", "jam", "televisi", "laptop" };
    private int[][] denahRuangan = new int[6][6];
    private Ruangan ruangAtas = null;
    private Ruangan ruangBawah = null;
    private Ruangan ruangKiri = null;
    private Ruangan ruangKanan = null;

    // konstruktor
    public Ruangan(String namaRuangan) {
        this.namaRuangan = namaRuangan;
        // menginisiasi denah dengan null
        int i; // i vertikal
        int j; // j horizontal
        // koordinat denah[y][x] -> (vertikal, horizontal) -> (i, j)
        for (i = 0; i < 6; i++) {
            for (j = 0; j < 6; j++) {
                denahRuangan[i][j] = -1; // -1 artinya tidak ada barang disana
            }
        }
    }

    // method-method
    public ArrayList<Barang> getBarangInRuangan() {
        return this.barangInRuangan;
    }

    public String[] getDaftarBarangFix() {
        return this.daftarBarangFix;
    }

    public String getNamaRuangan() {
        return this.namaRuangan;
    }

    public void setNamaRuangan(String namaRuangan) {
        this.namaRuangan = namaRuangan;
    }

    public void setRuangAtas(Ruangan ruangAtas) {
        this.ruangAtas = ruangAtas;
    }

    public Ruangan getRuangAtas() {
        return this.ruangAtas;
    }

    public void setRuangKanan(Ruangan ruangKanan) {
        this.ruangKanan = ruangKanan;
    }

    public Ruangan getRuangKanan() {
        return this.ruangKanan;
    }

    public void setRuangKiri(Ruangan ruangKiri) {
        this.ruangKiri = ruangKiri;
    }

    public Ruangan getRuangKiri() {
        return this.ruangKiri;
    }

    public void setRuangBawah(Ruangan ruangBawah) {
        this.ruangBawah = ruangBawah;
    }

    public Ruangan getRuangBawah() {
        return this.ruangBawah;
    }

    public void locateBarang(Barang barang, int x, int y) {
        int ukuranHorizontalBarang;
        int ukuranVertikalBarang;

        // cast dulu barang menjadi nonMakanan
        if (barang instanceof NonMakanan) {
            NonMakanan nonMakanan = (NonMakanan) barang;
            ukuranHorizontalBarang = nonMakanan.getPanjang();
            ukuranVertikalBarang = nonMakanan.getLebar();

            // cek apakah tempat kosong

            if (x + ukuranHorizontalBarang < 6 && y + ukuranVertikalBarang < 6) {
                int i;
                int j;
                boolean valid = true;
                for (i = y; i < y + ukuranVertikalBarang; i++) {
                    for (j = x; j < x + ukuranHorizontalBarang; j++) {
                        if (denahRuangan[i][j] != -1) {
                            valid = false;
                        }
                    }
                }

                // setelah mengecek

                if (valid) {
                    // cek dulu barangnya apa
                    int angka = 0;
                    for (String s : daftarBarangFix) {
                        if (barang.getNama().equals(s)) {
                            break;
                        }
                        angka++;
                    }

                    // baru masukin ke denah

                    int m;
                    int n;
                    for (m = y; m < y + ukuranVertikalBarang; m++) {
                        for (n = x; n < x + ukuranHorizontalBarang; n++) {
                            denahRuangan[m][n] = angka;
                        }
                    }
                }
            }
        }

    }

    public void rotate() {
        // belum
    }
}
