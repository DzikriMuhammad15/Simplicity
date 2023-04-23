import java.util.*;

public class Ruangan {
    // atribut
    private String namaRuangan;
    private ArrayList<Barang> barangInRuangan = new ArrayList<Barang>();
    private String[] daftarBarangFix = { "Kasur Single", "Kasur Queen Size", "Kasur King Size", "Toilet", "Kompor Gas",
            "Kompor Listrik", "Meja dan Kursi", "Jam", "Televisi", "Laptop" };
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

    public void addBarangInRuangan(Barang barang) {
        this.barangInRuangan.add(barang);
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

            if (x + ukuranHorizontalBarang <= 6 && y + ukuranVertikalBarang <= 6) {
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
                } else {
                    System.out.println("Sudah ada barang lain di posisi tersebut");
                }
            } else {
                System.out.println("Melebihi ukuran ruangan");
            }
        }

    }

    public void display() {
        int i;
        int j;

        System.out.println("=============== DENAH RUANGAN ===============");
        System.out.println("");

        for (i = 0; i < 6; i++) {
            for (j = 0; j < 6; j++) {
                System.out.print(denahRuangan[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("Keterangan : ");
        System.out.println("0 : Kasur Single");
        System.out.println("1 : Kasur Queen Size");
        System.out.println("2 : Kasur King Size");
        System.out.println("3 : Toilet");
        System.out.println("4 : Kompor Gas");
        System.out.println("5 : Kompor Listrik");
        System.out.println("6 : Meja dan Kursi");
        System.out.println("7 : Jam");
        System.out.println("8 : Televisi");
        System.out.println("9 : Laptop");
    }

    public void rotate() {
        // belum
    }
}
