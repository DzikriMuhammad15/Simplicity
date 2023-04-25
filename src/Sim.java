import java.util.*;
import java.lang.Math;

public class Sim implements AksiAktif, AksiDitinggal, AksiPasif{
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    private int uang;
    private Rumah rumah;
    private Map<String, Integer> inventory = new HashMap<>();
    private Barang[] onDelivery;
    private Kesejahteraan kesejahteraan;
    private String status;
    private Posisi posisi;
    private int waktuMakanAwal;


/* -------------------KONSTRUKTOR------------------- */

    public Sim(String namaLengkap){
        // Menginisiasi kesejahteraan dan uang
        Kesejahteraan kesejahteraan = new Kesejahteraan(false,80,80,80);
        this.kesejahteraan = kesejahteraan;
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
        this.pekerjaan = daftarPekerjaan[randomIndex];

        // Menginisiasi rumah
        Rumah rumah = new Rumah(new Point(0,0));
        this.rumah=rumah;
        this.posisi= new Posisi(rumah, rumah.getArrayOfRuangan().get(0), null);

        //menginisiasi nama
        this.namaLengkap = namaLengkap;
        posisi.setCurrRumah(rumah);
    }


/* ----------------------GETTER-------------------------- */

    public String getName(){
        return namaLengkap;
    }

    public Pekerjaan getPekerjaan(){
        return pekerjaan;
    }

    public int getUang(){
        return uang;
    }

    public Rumah getRumah(){
        return rumah;
    }

    public HashMap<String, Integer> getInventory(){
        return inventory;
    }

    public Barang[] getOnDelivery(){
        return onDelivery;
    }

    public Kesejahteraan getKesejahteraan(){
        return kesejahteraan;
    }

    public String getStatus(){
        return status;
    }

    public Posisi getPosisi(){
        return posisi;
    }

/* --------------------------SETTER------------------------------ */

    public void setName(String namaLengkap){
        this.namaLengkap = namaLengkap;
    }

    public void setPekerjaan(Pekerjaan pekerjaan){
        this.pekerjaan = pekerjaan;
    }

    public void setUang(int uang){
        this.uang = uang;
    }

    public void setRumah(Rumah rumah){
        this.rumah = rumah;
    }

    public void setInventory(HashMap<String, Integer> inventory){
        this.inventory = inventory;
    }

    public void setOnDelivery(Barang[] onDelivery){
        this.onDelivery = onDelivery;
    }

    public void setKesejahteraan(Kesejahteraan kesejahteraan){
        this.kesejahteraan = kesejahteraan;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setPosisi(Posisi posisi){
        this.posisi = posisi;
    }

    public void changePekerjaan(Pekerjaan kerjaBaru){
        this.pekerjaan = kerjaBaru;
    }
    
/* ----------------------GO TO OBJECT---------------------------- */
   
    public void gotToBarang(Barang barang){
        posisi.setCurrBarang(barang);
    }

/* -------------------------------- AKSI DITINGGAL ----------------------------------- */

    @Override
    public void bunuhDiri(){
        kesejahteraan.setMood(0);
        kesejahteraan.setKesehatan(0);
        kesejahteraan.setKekenyangan(0);
        kesejahteraan.setDead(true);
    }

    public void upgradeRumah(String ruanganBaru, String posisi){

    }
    public void beliBarang(String namaBarang){

    }


/* ------------------------------- AKSI PASIF ----------------------------------- */
    public void moveToRoom(String ruangTujuan){
        posisi.setCurrRuangan();
    }

    public void lihatInventory(){
        System.out.printf("| %-10s | %-8s |%n", "Barang", "Jumlah");
        System.out.println("|------------|----------|");

        // Loop over entries in the HashMap and print them in table format
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String barang = entry.getKey();
            int jumlah = entry.getValue();
            System.out.printf("| %-10s | %-8d |%n", barang, jumlah);
        }
    }

    public void pasangBarang(String namaBarang, int x, int y){
        NonMakanan barang = new NonMakanan(namaBarang);
        barang.setPosisi(new Point(x, y));
        // ngurangin barang dari inventory, kalo 0 dihapus dr invnt
    }

    public void lihatWaktu(){
        if (posisi.getCurrBarang().getNama()=="Jam"){
            World world = World.getInstance();
            int waktu = world.getWaktu();
            System.out.println("Waktu saat ini adalah : ", +waktu);
        }
    }

    public void pukulSim(Sim otherSim){
        int kesehatanAwal = otherSim.getKesejahteraan().getKesehatan();
        otherSim.getKesejahteraan().setKesehatan(kesehatanAwal-10);
    }

    public void bercanda(Sim otherSim){
        int moodAwal = otherSim.getKesejahteraan().getMood();
        otherSim.getKesejahteraan().setMood(moodAwal+10);
    }

/* --------------------------------AKSI AKTIF ------------------------------------ */

    public void kerja(int waktu){
        // thread nya
        uang += pekerjaan.getGajiHarian();
        }


    public void olahraga(int waktu){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock(); // Kunci sumber daya
                try {
                    int count = waktu / 20; // Hitung jumlah iterasi yang diperlukan
                    for (int i = 0; i < count; i++) {
                        int kesehatanAwal = kesejahteraan.getKesehatan();
                        int kekenyanganAwal = kesejahteraan.getKekenyangan();
                        int moodAwal = kesejahteraan.getMood();
                        kesejahteraan.setKesehatan(kesehatanAwal+5);
                        kesejahteraan.setKekenyangan(kekenyanganAwal-5);
                        kesejahteraan.setMood(moodAwal+10);
                        try {
                            Thread.sleep(20000); // Tunggu selama 20 detik
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    lock.unlock(); // Lepaskan kunci sumber daya
                }
            }
        });
        t.start();
    }

    public void makan(Makanan makanan){
        String namaMakanan = makanan.getNama();
        int kekenyanganAwal = kesejahteraan.getKekenyangan();
        int currentQuantity = inventory.getOrDefault(namaMakanan, 0);

        if (inventory.containsKey(namaMakanan) || currentQuantity>0){
            kesejahteraan.setKekenyangan(kekenyanganAwal+makanan.kekenyangan())
            inventory.put(namaMakanan, currentQuantity - 1);
            if (currentQuantity - 1 == 0) {
                inventory.remove(namaMakanan);
            }
        }
        else{
            System.out.println("Makanan habis atau tidak tersedia.")
        }
    }

    public void makan(BahanMakanan bahanMakanan){
        int kekenyanganAwal = kesejahteraan.getKekenyangan();
        String namaBahanMakanan = bahanMakanan.getNama();
        int currentQuantity = inventory.getOrDefault(namaBahanMakanan, 0);

        if (inventory.containsKey(namaBahanMakanan) || currentQuantity>0){
            kesejahteraan.setKekenyangan(kekenyanganAwal+bahanMakanan.kekenyangan())
            inventory.put(namaBahanMakanan, currentQuantity - 1);
            if (currentQuantity - 1 == 0) {
                inventory.remove(namaBahanMakanan);
            }
        }
        else{
            System.out.println("Bahan makanan habis atau tidak tersedia.")
        }
    }
    

    public void tidur(int waktu){

    }

    public void masak(Makanan makanan){
        ArrayList<String> arrayOfBahanMakanan = makanan.getArrayOfBahanMakanan();
        for (String bahan : arrayOfBahanMakanan) {
            int jumlah = inventory.getOrDefault(bahan, 0);
            if (jumlah <= 0) {
                System.out.println("Bahan makanan " + bahan + " tidak tersedia dalam inventory. Pilih menu yang lain!");
                return; // keluar dari method masak jika bahan tidak tersedia
            }
        }
        for (String bahan : bahanMakanan) {
            int jumlah = inventory.get(bahan);
            inventory.put(bahan, jumlah - 1);
        }
        int jumlahMakananAwal = inventory.get(makanan.getNama());
        inventory.put(makanan.getNama(), jumlahMakananAwal+1);
    }

    public void berkunjung(Rumah rumahSim){
        int x1 = posisi.getX();
        int y1 = posisi.getY();
        int x2 = rumahSim.getLokasi().getX();
        int y2 = rumahSim.getLokasi().getY();
        int waktu = Math.sqrt(Math.pow((x2-x1),2)-Math.pow((y2-y1), 2));
        posisi.setCurrRumah(rumahSim);
        // posisi.setCurrRuangan();
    }

    public void buangAir(){
        World world = World.getInstance();
        int waktuBuangAir = world.getWaktu();
        int kekenyanganAwal = kesejahteraan.getKekenyangan();
        int moodAwal = kesejahteraan.getMood();
        int kesehatanAwal = kesehatanAwal.getKesehatan();
        if (waktuBuangAir-waktuMakanAwal <= 240000){
            kesejahteraan.setKekenyangan(kekenyanganAwal-20);
            kesejahteraan.setMood(moodAwal+10);
        }
        else{
            kesejahteraan.setKesehatan(kesehatanAwal-5);
            kesejahteraan.setMood(moodAwal-5);
        }
    }

    public void nontonTV(int waktu){

    }

    public void ngoding(int waktu, String bahasaProgram){

    }

    public void dengerMusik(int waktu, String genre){

    }

    public void mainGame(int waktu){}

}