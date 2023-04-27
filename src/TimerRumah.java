public class TimerRumah extends Thread {
    private Sim sim;
    private String namaRuangan;
    private Ruangan ruanganSekarang;
    private String lokasi;
    private Object lock = World.getInstance().getLock();

    public TimerRumah(Sim sim, String namaRuangan, Ruangan ruanganSekarang, String lokasi) {
        this.sim = sim;
        this.namaRuangan = namaRuangan;
        this.ruanganSekarang = ruanganSekarang;
        this.lokasi = lokasi;
    }

    public void run() {
        World instance = World.getInstance();
        int waktuSelesai = instance.getHari() * 720 + instance.getWaktu() + 1080;
        boolean muter = true;
        Ruangan ruangBaru = new Ruangan(namaRuangan);
        // masukin dulu ke denah sehingga saat proses
        if (lokasi.equals("kanan")) {
            if (ruanganSekarang.getRuangKanan() == null) {
                ruanganSekarang.setRuangKanan(ruangBaru);
                ruangBaru.setRuangKiri(ruanganSekarang);
            } else {
                System.out.println("Di sebelah kanan sudah ada ruangan");
                return;
            }

        } else if (lokasi.equals("kiri")) {
            if (ruanganSekarang.getRuangKiri() == null) {
                ruanganSekarang.setRuangKiri(ruangBaru);
                ruangBaru.setRuangKanan(ruanganSekarang);
            } else {
                System.out.println("Di sebelah kiri sudah ada ruangan");
                return;
            }
        } else if (lokasi.equals("atas")) {
            if (ruanganSekarang.getRuangAtas() == null) {
                ruanganSekarang.setRuangAtas(ruangBaru);
                ruangBaru.setRuangBawah(ruanganSekarang);
            } else {
                System.out.println("Di atas sudah ada ruangan");
                return;
            }
        } else if (lokasi.equals("bawah")) {
            if (ruanganSekarang.getRuangBawah() == null) {
                ruanganSekarang.setRuangBawah(ruangBaru);
                ruangBaru.setRuangAtas(ruanganSekarang);
            } else {
                System.out.println("Di bawah sudah ada ruangan");
                return;
            }
        }
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

        // baru tambahin ruangan ke dalam rumah
        this.sim.getRumah().addRuangan(ruangBaru);
    }

}
