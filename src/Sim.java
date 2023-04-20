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

}