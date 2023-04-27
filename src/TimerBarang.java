import java.lang.*;

public class TimerBarang extends Thread {
    private int sisaWaktu;
    private NonMakanan nonMakanan; // ini adalah object yang dibelinya
    private Sim sim; // sim merupakan sim yang melakukan pembeliannya
    private Object lock = World.getInstance().getLock();

    // kosntruktor
    public TimerBarang(NonMakanan nonMakanan, Sim sim) {
        this.nonMakanan = nonMakanan;
        this.sim = sim;
        sisaWaktu = this.nonMakanan.getShippingTime();
    }

    // run
    public void run() {
        World instance = World.getInstance();
        int waktuSelesai = instance.getHari() * 720 + instance.getWaktu() + sisaWaktu;
        boolean muter = true;
        // masukin dulu ke dalem on delivery
        Barang b = nonMakanan;
        sim.getOnDelivery().add(b);

        while (muter) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
            // mengecek jam
            if (instance.getHari() * 12 + instance.getWaktu() >= waktuSelesai) {
                muter = false;
            }
        }
        // baru pindahin dari on delivery ke barang

        // hapus dari onDelivery

        sim.getOnDelivery().remove(b);
        // ...

        // masukkin ke inventory
        int jumlahSebelumnya;
        String namaBarang = b.getNama();
        if (sim.getInventory().containsKey(namaBarang)) {
            jumlahSebelumnya = sim.getInventory().get(namaBarang);
        } else {
            jumlahSebelumnya = 0;
        }
        sim.getInventory().put(namaBarang, jumlahSebelumnya + 1);
        // ...

    }
}
