public class TimerRumah extends Thread {
    private Sim sim;
    private String namaRuangan;
    private Ruangan ruanganSekarang;
    private String lokasi;

    public TimerRumah(Sim sim, String namaRuangan, Ruangan ruanganSekarang, String lokasi) {
        this.sim = sim;
        this.namaRuangan = namaRuangan;
        this.ruanganSekarang = ruanganSekarang;
        this.lokasi = lokasi;
    }

    public void run() {
        int waktuSelesai = World.getHari() * 12 + World.getWaktu() + 6;
        boolean muter = true;
        while (muter) {
            if (World.getHari() * 12 + World.getWaktu() >= waktuSelesai) {
                muter = false;
            }
        }

        // baru tambahin ruangan ke dalam rumah
        Ruangan ruangBaru = new Ruangan(namaRuangan);
        if (lokasi.equals("kanan")) {
            if (ruanganSekarang.getRuangKanan() == null) {
                ruanganSekarang.setRuangKanan(ruangBaru);
                ruangBaru.setRuangKiri(ruanganSekarang);
            } else {
                System.out.println("Di sebelah kanan sudah ada ruangan");
            }

        } else if (lokasi.equals("kiri")) {
            if (ruanganSekarang.getRuangKiri() == null) {
                ruanganSekarang.setRuangKiri(ruangBaru);
                ruangBaru.setRuangKanan(ruanganSekarang);
            } else {
                System.out.println("Di sebelah kiri sudah ada ruangan");
            }
        } else if (lokasi.equals("atas")) {
            if (ruanganSekarang.getRuangAtas() == null) {
                ruanganSekarang.setRuangAtas(ruangBaru);
                ruangBaru.setRuangBawah(ruanganSekarang);
            } else {
                System.out.println("Di atas sudah ada ruangan");
            }
        } else if (lokasi.equals("bawah")) {
            if (ruanganSekarang.getRuangBawah() == null) {
                ruanganSekarang.setRuangBawah(ruangBaru);
                ruangBaru.setRuangAtas(ruanganSekarang);
            } else {
                System.out.println("Di bawah sudah ada ruangan");
            }
        }
        this.sim.getRumah().addRuangan(ruangBaru);
    }

}
