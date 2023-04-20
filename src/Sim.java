public class Sim {
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    private int uang;
    private Rumah rumah;
    private HashMap<String, Integer> inventory;
    private Barang[] onDelivery;
    private Kesejahteraan kesejahteraan;
    private String status;
    private Posisi posisi;
}

public Sim(String namaLengkap){
    Kesejahteraan kesejahteraan = new Kesejahteraan();
    kesejahteraan.setKesehatan(80);
    kesejahteraan.setKekenyangan(80);
    kesejahteraan.setMood(80);
    this.uang = 100;
    Pekerjaan[] daftarPekerjaan = {
        new Pekerjaan("Badut Sulap", 15),
        new Pekerjaan("Koki", 30),
        new Pekerjaan("Polisi", 35),
        new Pekerjaan("Programmer", 45),
        new Pekerjaan("Dokter", 50),
    };
    Random random = new Random();
    int randomIndex = random.nextInt(daftarPekerjaan.length);
    changePekerjaan(daftarPekerjaan[randomIndex]);
}