public class Sim implements AksiAktif, AksiDitinggal, AksiPasif{
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    private int uang;
    private Rumah rumah;
    private HashMap<String, Integer> inventory;
    private Barang[] onDelivery;
    private Kesejahteraan kesejahteraan;
    private String status;
    private Posisi posisi;

    public Sim(String namaLengkap){
    // Menginisiasi kesejahteraan dan uang
    Kesejahteraan kesejahteraan = new Kesejahteraan();
    kesejahteraan.setKesehatan(80);
    kesejahteraan.setKekenyangan(80);
    kesejahteraan.setMood(80);
    this.uang = 100;

    // Menginisiasi pekerjaan secara random
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

    // Menginisiasi rumah
    Rumah rumah = new Rumah(new Point(0,0));
    }


    public void changePekerjaan(Pekerjaan kerjaBaru){
        this.pekerjaan = kerjaBaru;
    }

    
    public void gotToBarang(Barang barang){

    }
}