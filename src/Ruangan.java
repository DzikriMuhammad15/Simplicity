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

                    // tambah titik ke dalam atribut NonMakanan

                    nonMakanan.setPosisi(new Point(x, y));

                } else {
                    System.out.println("Sudah ada barang lain di posisi tersebut");
                }
            } else {
                System.out.println("Melebihi ukuran ruangan");
            }
        }

    }

    public void moveBarang(Barang barang, int x, int y) {
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

                    // HAPUS DULU DI TEMPAT YANG DULUNYA
                    int xLama;
                    int yLama;
                    int panjang;
                    int lebar;
                    int orientasi;
                    int m;
                    int n;

                    NonMakanan non = (NonMakanan) barang;
                    Point p = non.getPosisi();

                    // cek setiap xLama dan yLama

                    xLama = p.getX();
                    yLama = p.getY();

                    panjang = non.getPanjang();
                    lebar = non.getLebar();

                    orientasi = non.getOrientasi();

                    // untuk orientasi yang berbeda
                    if (orientasi == 90) {
                        int temp;
                        temp = panjang;
                        panjang = lebar;
                        lebar = temp;
                    } else if (orientasi == 180) {
                        panjang = -1 * panjang;
                    } else if (orientasi == 270) {
                        int temp;
                        temp = panjang;
                        panjang = lebar;
                        lebar = -1 * temp;
                    }

                    // kujungi setiap titiknya lalu hapus
                    if (panjang > 0 && lebar > 0) {
                        for (m = yLama; m < yLama + lebar; m++) {
                            for (n = xLama; n < xLama + panjang; n++) {
                                this.denahRuangan[m][n] = -1;
                            }
                        }
                    } else if (panjang < 0) {
                        for (m = yLama; m < yLama + lebar; m++) {
                            for (n = xLama; n > xLama + panjang; n--) {
                                this.denahRuangan[m][n] = -1;
                            }
                        }
                    } else if (lebar < 0) {
                        for (m = yLama; m > lebar; m--) {
                            for (n = xLama; n < xLama + panjang; n++) {
                                this.denahRuangan[m][n] = -1;
                            }
                        }
                    }

                    // baru masukin ke denah

                    int a;
                    int b;
                    for (a = y; a < y + ukuranVertikalBarang; a++) {
                        for (b = x; b < x + ukuranHorizontalBarang; b++) {
                            denahRuangan[a][b] = angka;
                        }
                    }

                    // tambah titik ke dalam atribut NonMakanan

                    nonMakanan.setPosisi(new Point(x, y));

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

    public void rotate(Barang barang, int derajat) {
        int degree;
        degree = derajat % 360;
        // konstanta
        int sin90;
        int sin180;
        int sin270;
        int cos90;
        int cos180;
        int cos270;

        sin90 = 1;
        sin180 = 0;
        sin270 = -1;
        cos90 = 0;
        cos180 = -1;
        cos270 = 0;

        int a;
        int b;
        int panjang;
        int lebar;

        NonMakanan non = (NonMakanan) barang;
        Point p = non.getPosisi();

        // a dan b adalah porosnya
        a = p.getX();
        b = p.getY();

        // tentukan panjang dan lebar barang yang akan dirotate
        panjang = non.getPanjang();
        lebar = non.getLebar();
        int orientasi;
        orientasi = non.getOrientasi();

        // untuk orientasi yang berbeda
        if (orientasi == 90) {
            int temp;
            temp = panjang;
            panjang = lebar;
            lebar = temp;
        } else if (orientasi == 180) {
            panjang = -1 * panjang;
        } else if (orientasi == 270) {
            int temp;
            temp = panjang;
            panjang = lebar;
            lebar = -1 * temp;
        }

        // rotate tiap titiknya
        int xCek;
        int yCek;
        int xCermin;
        int yCermin;
        int i;
        int j;
        boolean valid = true;
        int sin;
        int cos;
        int angka = denahRuangan[b][a];

        if (degree == 90) {
            sin = sin90;
            cos = cos90;
        } else if (degree == 180) {
            sin = sin180;
            cos = cos180;
        } else {
            sin = sin270;
            cos = cos270;
        }
        if (panjang > 0 && lebar > 0) {

            for (i = b; i < b + lebar; i++) {
                for (j = a; j < a + panjang; j++) {
                    // mengunjungi setiap titik dan memutarnya
                    xCek = ((j - a) * cos) - ((i - b) * sin) + a;
                    yCek = ((j - a) * sin) + ((i - b) * cos) + b;

                    if (xCek < 0 || xCek > 5 || yCek < 0 || yCek > 5) {
                        valid = false;
                    } else {
                        if (denahRuangan[yCek][xCek] != -1 && !(j == a && i == b)) {
                            valid = false;
                        }
                    }
                }
            }

            if (valid) {
                // hapus item yang lama di denah
                for (i = b; i < b + lebar; i++) {
                    for (j = a; j < a + panjang; j++) {
                        // mengunjungi tiap titik
                        xCermin = ((j - a) * cos) - ((i - b) * sin) + a;
                        yCermin = ((j - a) * sin) + ((i - b) * cos) + b;
                        denahRuangan[i][j] = -1;
                        denahRuangan[yCermin][xCermin] = angka;
                    }
                }
                // mengubah si orientasinya
                int orientasiBaru = (non.getOrientasi() + degree) % 360;
                non.setOrientasi(orientasiBaru);
            } else {
                System.out.println("rotasi melanggar syarat");
            }
        }

        // UNTUK PANJANG NEGATIF
        else if (panjang < 0) {

            for (i = b; i < b + lebar; i++) {
                for (j = a; j > a + panjang; j--) {
                    // mengunjungi setiap titik dan memutarnya
                    xCek = ((j - a) * cos) - ((i - b) * sin) + a;
                    yCek = ((j - a) * sin) + ((i - b) * cos) + b;

                    if (xCek < 0 || xCek > 5 || yCek < 0 || yCek > 5) {
                        valid = false;
                    } else {
                        if (denahRuangan[yCek][xCek] != -1 && !(j == a && i == b)) {
                            valid = false;
                        }
                    }
                }
            }

            if (valid) {
                // hapus item yang lama di denah
                for (i = b; i < b + lebar; i++) {
                    for (j = a; j > a + panjang; j--) {
                        // mengunjungi tiap titik
                        xCermin = ((j - a) * cos) - ((i - b) * sin) + a;
                        yCermin = ((j - a) * sin) + ((i - b) * cos) + b;
                        denahRuangan[i][j] = -1;
                        denahRuangan[yCermin][xCermin] = angka;
                    }
                }
                // mengubah si orientasinya
                int orientasiBaru = (non.getOrientasi() + degree) % 360;
                non.setOrientasi(orientasiBaru);
            } else {
                System.out.println("rotasi melanggar syarat");
            }
        }

        // UNTUK LEBAR NEGATIF
        else if (lebar < 0) {

            for (i = b; i > b + lebar; i--) {
                for (j = a; j < a + panjang; j++) {
                    // mengunjungi setiap titik dan memutarnya
                    xCek = ((j - a) * cos) - ((i - b) * sin) + a;
                    yCek = ((j - a) * sin) + ((i - b) * cos) + b;

                    if (xCek < 0 || xCek > 5 || yCek < 0 || yCek > 5) {
                        valid = false;
                    } else {
                        if (denahRuangan[yCek][xCek] != -1 && !(j == a && i == b)) {
                            valid = false;
                        }
                    }
                }
            }

            if (valid) {
                // hapus item yang lama di denah
                for (i = b; i > b + lebar; i--) {
                    for (j = a; j < a + panjang; j++) {
                        // mengunjungi tiap titik
                        xCermin = ((j - a) * cos) - ((i - b) * sin) + a;
                        yCermin = ((j - a) * sin) + ((i - b) * cos) + b;
                        denahRuangan[i][j] = -1;
                        denahRuangan[yCermin][xCermin] = angka;
                    }
                }
                // mengubah si orientasinya
                int orientasiBaru = (non.getOrientasi() + degree) % 360;
                non.setOrientasi(orientasiBaru);
            } else {
                System.out.println("rotasi melanggar syarat");
            }
        }

    }
}
