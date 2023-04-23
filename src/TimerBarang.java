import java.lang.*;

public class TimerBarang extends Thread {
    private int sisaWaktu;
    private NonMakanan nonMakanan; // ini adalah object yang dibelinya
    private Sim sim; // sim merupakan sim yang melakukan pembeliannya

    // kosntruktor
    public TimerBarang(NonMakanan nonMakanan, Sim sim) {
        this.nonMakanan = nonMakanan;
        this.sim = sim;
        sisaWaktu = this.nonMakanan.getShippingTime();
    }

    // run
    public void run() {
        int waktuSelesai = World.getHari() * 12 + World.getWaktu() + sisaWaktu;
        boolean muter = true;
        while (muter) {
            if (World.getHari() * 12 + World.getWaktu() >= waktuSelesai) {
                muter = false;
            }
        }
        // baru pindahin dari on delivery ke barang

        // hapus dari onDelivery

        // ...
        // ...

        // masukkin ke inventory
        // ...
        // ...

    }
}
