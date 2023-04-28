import java.util.*;
import java.lang.Math;

public class Sim implements AksiAktif, AksiDitinggal, AksiPasif{
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    private int uang;
    private Rumah rumah;
    private HashMap<String, Integer> inventory = new HashMap<>();
    private ArrayList<Barang> onDelivery = new ArrayList<Barang>();
    private Kesejahteraan kesejahteraan;
    private String status;
    private Posisi posisi;
    private int waktuMakanAwal;
    private int waktuTidurAwal;
    private Object l = World.getInstance().getLock();
    private ReentrantLock lock = new ReentrantLock();


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

    public void setOnDelivery(ArrayList<Barang> onDelivery){
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
    public void setWaktuTidurAwal(int waktuTidurAwal){
        this.waktuTidurAwal = waktuTidurAwal;
    }
    public void setWaktuMakanAwal(int waktuMakanAwal){
        this.waktuMakanAwal = waktuMakanAwal;
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
        if (this.uang < 1500) {
            System.out.println("Uang tidak mencukupi untuk melakukan upgrade rumah");
        } else {
            TimerRumah timerRumah = new TimerRumah(this, ruanganBaru,
                    this.posisi.getCurrRuangan(), posisi);
            timerRumah.start();
        }
    }

    public void beliBarang(String namaBarang){
        // if barang apa aja, kurangi uang, masuk ke onDelivery
        // waktu diinisiasi barangnya (dr nama barang) tar inisiasinya barang atau nonmakanan/bahanmakanan? kan beda tuch
    }


/* ------------------------------- AKSI PASIF ----------------------------------- */
    public void moveToRoom(Ruangan ruangTujuan){
        posisi.setCurrRuangan(ruangTujuan);
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
        int currentQuantity = inventory.getOrDefault(namaBarang, 0);
        if (inventory.containsKey(namaBarang)){
            NonMakanan barang = new NonMakanan(namaBarang);
            barang.setPosisi(new Point(x, y));
            inventory.put(namaBarang, currentQuantity - 1);
            if (currentQuantity - 1 == 0) {
                inventory.remove(namaBarang);
            }
        // ngurangin barang dari inventory, kalo 0 dihapus dr invnt
        }
    }

    public void lihatWaktu(){
        if (posisi.getCurrBarang().getNama()=="Jam"){
            World world = World.getInstance();
            int waktu = world.getWaktu();
            System.out.println("Waktu saat ini adalah : " +waktu);
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

    public void cekTidurdanBuangAir(int waktu){
        World world = World.getInstance();
        int currentTime = world.getHari()*720 + world.getWaktu() + waktu;
        world.setWaktu(currentTime);
        if (currentTime-waktuMakanAwal >= 240){
            kesejahteraan.setKesehatan(kesejahteraan.getKesehatan()-5);
            kesejahteraan.setMood(kesejahteraan.getMood()-5);
        }
        if (currentTime-waktuTidurAwal >= 600){
            kesejahteraan.setKesehatan(kesejahteraan.getKesehatan()-5);
            kesejahteraan.setMood(kesejahteraan.getMood()-5);
        }
    }

/* --------------------------------AKSI AKTIF ------------------------------------ */

    public void kerja(int waktu){
        // thread nya
        if (waktu%120 == 0){
            lock.lock();
            int kekenyanganAwal = kesejahteraan.getKekenyangan();
            int moodAwal = kesejahteraan.getMood();
            int count = waktu / 240; // Hitung jumlah iterasi yang diperlukan
            for (int i = 0; i < waktu; i+=30){
                try {
                    Thread.sleep(30000); // Tunggu selama 4 menit
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                kesejahteraan.setKekenyangan(kekenyanganAwal-10);
                kesejahteraan.setMood(moodAwal-10)
                if (i%240 == 0){
                    this.uang = uang + pekerjaan.getGajiHarian();
                }
            }
            cekTidurdanBuangAir(waktu);
            synchronized(l){
                l.notifyAll();
            } 
        }    
        else{
            System.out.println("Masukkan waktu dengan kelipatan 120!");
        }
    }


    public void olahraga(int waktu){
        lock.lock(); // Kunci sumber daya
        try {
            int count = waktu / 20; // Hitung jumlah iterasi yang diperlukan
            for (int i = 0; i < count; i++) {
                int kesehatanAwal = kesejahteraan.getKesehatan();
                int kekenyanganAwal = kesejahteraan.getKekenyangan();
                int moodAwal = kesejahteraan.getMood();
                try {
                    Thread.sleep(20000); // Tunggu selama 20 detik
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                kesejahteraan.setKesehatan(kesehatanAwal+5);
                kesejahteraan.setKekenyangan(kekenyanganAwal-5);
                kesejahteraan.setMood(moodAwal+10);
            }
        } finally {
            lock.unlock(); // Lepaskan kunci sumber daya
        }
        cekTidurdanBuangAir(waktu);
        synchronized(l){
            l.notifyAll();
        }
    } 

    public void makan(Makanan makanan){
        lock.lock();
        String namaMakanan = makanan.getNama();
        int kekenyanganAwal = kesejahteraan.getKekenyangan();
        int currentQuantity = inventory.getOrDefault(namaMakanan, 0);

        if (inventory.containsKey(namaMakanan) || currentQuantity>0){
            try {
                Thread.sleep(30000); // Tunggu selama 20 detik
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kesejahteraan.setKekenyangan(kekenyanganAwal+makanan.getKekenyangan());
            inventory.put(namaMakanan, currentQuantity - 1);
            if (currentQuantity - 1 == 0) {
                inventory.remove(namaMakanan);
            }
            cekTidurdanBuangAir(30);
        }
        else{
            System.out.println("Makanan habis atau tidak tersedia.");
        }
        lock.unlock();
    }

    public void makan(BahanMakanan bahanMakanan){
        World world = World.getInstance();
        int waktuMakanAwal = world.getWaktu();
        int kekenyanganAwal = kesejahteraan.getKekenyangan();
        String namaBahanMakanan = bahanMakanan.getNama();
        int currentQuantity = inventory.getOrDefault(namaBahanMakanan, 0);

        if (inventory.containsKey(namaBahanMakanan) || currentQuantity>0){
            kesejahteraan.setKekenyangan(kekenyanganAwal+bahanMakanan.getKekenyangan());
            inventory.put(namaBahanMakanan, currentQuantity - 1);
            if (currentQuantity - 1 == 0) {
                inventory.remove(namaBahanMakanan);
            }
        }
        else{
            System.out.println("Bahan makanan habis atau tidak tersedia.");
        }
    }
    

    public void tidur(int waktu){
        World world = World.getInstance();
        int currentTime = world.getHari()*720 + world.getWaktu();
        setWaktuTidurAwal(currentTime);

        cekTidurdanBuangAir(waktu);
    }

    public void masak(Makanan makanan){
        lock.lock();
        int moodAwal = kesejahteraan.getMood();
        int waktuMemasak = (int) (1.5*(makanan.getKekenyangan()));
        ArrayList<String> arrayOfBahanMakanan = makanan.getArrayOfBahanMakanan();
        for (String bahan : arrayOfBahanMakanan) {
            int jumlah = inventory.getOrDefault(bahan, 0);
            if (jumlah <= 0) {
                System.out.println("Bahan makanan " + bahan + " tidak tersedia dalam inventory. Pilih menu yang lain!");
                lock.unlock();
                return; // keluar dari method masak jika bahan tidak tersedia
            }
        }
        //thread
        try {
            Thread.sleep(waktuMemasak*1000); // Tunggu selama 20 detik
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String bahan : arrayOfBahanMakanan) {
            int jumlah = inventory.get(bahan);
            inventory.put(bahan, jumlah - 1);
            if (jumlah == 0){
                inventory.remove(bahan);
            }
        }
        int jumlahMakananAwal = inventory.getOrDefault(makanan.getNama(), 0);
        inventory.put(makanan.getNama(), jumlahMakananAwal+1);
        kesejahteraan.setMood(moodAwal+10);
        cekTidurdanBuangAir(waktuMemasak);
        synchronized(l){
            l.notifyAll();
        }
        lock.unlock();
    }

    public void berkunjung(Rumah rumahSim){
        int x1 = posisi.getCurrRumah().getLokasi().getX();
        int y1 = posisi.getCurrRumah().getLokasi().getY();
        int x2 = rumahSim.getLokasi().getX();
        int y2 = rumahSim.getLokasi().getY();
        int waktu = (int)Math.sqrt(Math.pow((x2-x1),2)-Math.pow((y2-y1), 2));
        try {
            Thread.sleep(waktu);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        posisi.setCurrRumah(rumahSim);
        posisi.setCurrRuangan(rumahSim.getRuangan("ruangUtama"));
        cekTidurdanBuangAir(waktu);
    }

    public void buangAir(){
        World world = World.getInstance();
        int waktuBuangAir = world.getWaktu();
        int kekenyanganAwal = kesejahteraan.getKekenyangan();
        int moodAwal = kesejahteraan.getMood();
        int kesehatanAwal = kesejahteraan.getKesehatan();
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
        try {
            Thread.sleep(waktu*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int moodAwal = kesejahteraan.getMood();
        kesejahteraan.setMood(moodAwal+5);
        cekTidurdanBuangAir(waktu);
    }

    public void ngoding(int waktu, String bahasaProgram){
        //thread
        if ((bahasaProgram == "Java") || (bahasaProgram == "C") || (bahasaProgram == "C++") || (bahasaProgram == "Python")){
            try {
                Thread.sleep(waktu*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (bahasaProgram.equals("Java")){

            }
            else if(bahasaProgram.equals("C")){

            }
            else if (bahasaProgram.equal("Python")){

            }
            else if (bahasaProgram.equal("C++")){

            }
        }
        else{
            System.out.println("Bahasa pemrograman tersebut tidak tersedia");
            return;
        }
        cekTidurdanBuangAir(waktu);
    }

    public void dengerMusik(int waktu, String genre){
        int moodAwal = kesejahteraan.getMood();
        if (genre.equals("Indie")){
            kesejahteraan.setMood(moodAwal+1);
        }
        else if (genre.equals("Pop")){
            kesejahteraan.setMood(moodAwal+5);
        }
        else if (genre.equals("Reggae")){
            kesejahteraan.setMood(moodAwal+5);
        }
        else if (genre.equals("Dangdut")){
            kesejahteraan.setMood(moodAwal+5);
        }
        else if (genre.equals("K-Pop")){
            kesejahteraan.setMood(moodAwal+5);
        }
        else if (genre.equals("WOTA")){
            kesejahteraan.setMood(moodAwal+10);
        }
        cekTidurdanBuangAir(waktu);
    }

    public void mainGame(int waktu){

        cekTidurdanBuangAir(waktu);
    }

}