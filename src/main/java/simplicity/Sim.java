package simplicity;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
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
    private int waktuKerja;
    private int hariResign = -9999;
    private boolean sudahBuangAir = false;
    private boolean makanPertama = false;
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

    public ArrayList<Barang> getOnDelivery(){
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

    public int getWaktuMakanAwal(){
        return waktuMakanAwal;
    }

    public int getWaktuTidurAwal(){
        return waktuTidurAwal;
    }

    public int getWaktuKerja(){
        return waktuKerja;
    }

    public int getHariResign(){
        return hariResign;
    }

    public boolean getSudahBuangAir(){
        return sudahBuangAir;
    }

    public boolean getMakanPertama(){
        return makanPertama;
    }

    public void cekKesejahteraan(){    // Cek agar kesejahteraan maksimal bernilai 100 dan jika salah satu mencapai 0 maka sim akan mati
        if (kesejahteraan.getMood() > 100){
            kesejahteraan.setMood(100);
        }
        if (kesejahteraan.getMood() <= 0){
            kesejahteraan.setDead(true);
            System.out.println("Sim telah mati karena depresi");
        }
        if (kesejahteraan.getKekenyangan() > 100){
            kesejahteraan.setKekenyangan(100);
        }
        if (kesejahteraan.getKekenyangan() <= 0){
            kesejahteraan.setDead(true);
            System.out.println("Sim telah mati karena kelaparan");
        }
        if (kesejahteraan.getKesehatan() > 100){
            kesejahteraan.setKesehatan(100);
        }
        if (kesejahteraan.getKesehatan() <= 0){
            kesejahteraan.setDead(true);
            System.out.println("Sim telah mati karena sakit");
        }
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
        if (waktuKerja >= 720){   // jika sim bekerja kurang dari 12 menit maka belum bisa berganti pekerjaan
            this.uang = uang - ((kerjaBaru.getGajiHarian())/2);
            this.pekerjaan = kerjaBaru;
            setWaktuKerja(0); // reset waktu kerja
            World world = World.getInstance();
            this.hariResign = world.getHari();
        }
        else{
            System.out.println("Waktu kerja belum mencukupi");
        }
    }
    public void setWaktuTidurAwal(int waktuTidurAwal){
        this.waktuTidurAwal = waktuTidurAwal;
    }
    public void setWaktuMakanAwal(int waktuMakanAwal){
        this.waktuMakanAwal = waktuMakanAwal;
    }

    public void setWaktuKerja(int waktuKerja){
        this.waktuKerja = waktuKerja;
    }

    public void setHariResign(int hariResign){
        this.hariResign=hariResign;
    }

    public void setSudahBuangAir(boolean sudahBuangAir){
        this.sudahBuangAir=sudahBuangAir;
    }

    public void setMakanPertama(boolean sudahMakanPertama){
        this.makanPertama = sudahMakanPertama;
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
        if (namaBarang.equals("Kasur Single")) {
            if (this.uang < 50) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new NonMakanan("Kasur Single"), this);
                timerBarang.start();
                this.uang = uang-50;
            }
        } else if (namaBarang.equals("Kasur Queen Size")) {
            if (this.uang < 100) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new NonMakanan("Kasur Queen Size"), this);
                timerBarang.start();
                this.uang = uang-100;
            }
        } else if (namaBarang.equals("Kasur King Size")) {
            if (this.uang < 150) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new NonMakanan("Kasur King Size"), this);
                timerBarang.start();
                this.uang = uang-150;
            }
        } else if (namaBarang.equals("Toilet")) {
            if (this.uang < 50) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new NonMakanan("Toilet"), this);
                timerBarang.start();
                this.uang = uang-50;
            }
        } else if (namaBarang.equals("Kompor Gas")) {
            if (this.uang < 100) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new NonMakanan("Kompor Gas"), this);
                timerBarang.start();
                this.uang = uang-100;
            }
        } else if (namaBarang.equals("Kompor Listrik")) {
            if (this.uang < 200) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new NonMakanan("Kompor Listrik"), this);
                timerBarang.start();
                this.uang = uang-200;
            }
        } else if (namaBarang.equals("Meja dan Kursi")) {
            if (this.uang < 50) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new NonMakanan("Meja dan Kursi"), this);
                timerBarang.start();
                this.uang = uang-50;
            }
        } else if (namaBarang.equals("Jam")) {
            if (this.uang < 10) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new NonMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang - 10;
            }
        } else if (namaBarang.equals("TV")) {
            if (this.uang < 100) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new NonMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang - 100;
            }
        } else if (namaBarang.equals("Laptop")) {
            if (this.uang < 200) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new NonMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang-200;
            }
        }else if (namaBarang.equals("Nasi")) {
            if (this.uang < 5) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new BahanMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang - 5;
            }
        } else if (namaBarang.equals("Kentang")) {
            if (this.uang < 3) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new BahanMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang - 3;
            }
        } else if (namaBarang.equals("Ayam")) {
            if (this.uang < 10) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new BahanMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang - 10;
            }
        } else if (namaBarang.equals("Sapi")) {
            if (this.uang < 12) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new BahanMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang - 12;
            }
        } else if (namaBarang.equals("Wortel")) {
            if (this.uang < 2) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new BahanMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang - 2;
            }
        } else if (namaBarang.equals("Bayam")) {
            if (this.uang < 2) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new BahanMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang - 2;
            }
        } else if (namaBarang.equals("Kacang")) {
            if (this.uang < 2) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new BahanMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang - 2;
            }
        } else if (namaBarang.equals("Susu")) {
            if (this.uang < 2) {
                System.out.println("Uang tidak mencukupi untuk membeli " +namaBarang);
            } else {
                TimerBarang timerBarang = new TimerBarang(new BahanMakanan(namaBarang), this);
                timerBarang.start();
                this.uang = uang - 2;
            }
        }
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
        // ngurangin barang dari inventory, kalo 0 dihapus dr inventory
        }
    }

    public void lihatWaktu(){  // sesuai deskripsi yeah
        if (posisi.getCurrBarang().getNama().equals("Jam")){
            World world = World.getInstance();
            int waktu = world.getWaktu();
            System.out.println("Ini adalah hari ke-"+world.getHari());
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
        world.addWaktu(waktu);
        if (currentTime-waktuMakanAwal >= 240 && sudahBuangAir==false && makanPertama==true){
            kesejahteraan.setKesehatan(kesejahteraan.getKesehatan()-5);
            kesejahteraan.setMood(kesejahteraan.getMood()-5);
            setWaktuMakanAwal(waktuMakanAwal+240);   // supaya berkurang tiap 4 menit sekali jika tetap belum buang air
            System.out.println("Kesejahteraan telah berkurang karena belum buang air.");
        }
        if (currentTime-waktuTidurAwal >= 600){
            kesejahteraan.setKesehatan(kesejahteraan.getKesehatan()-5);
            kesejahteraan.setMood(kesejahteraan.getMood()-5);
            setWaktuTidurAwal(waktuTidurAwal+600);   // supaya berkurang tiap 10 menit sekali jika tetap belum tidur
            System.out.println("Kesejahteraan telah berkurang karena belum tidur.");
        }
    }

/* --------------------------------AKSI AKTIF ------------------------------------ */

    public void kerja(int waktu){
        // thread nya
        World world = World.getInstance();
        int currentDay = world.getHari();
        if (waktu%120 == 0){
            if ((currentDay-hariResign) >= 1){   // jika habis ganti pekerjaan, maka baru bisa kerja di hari selanjutnya
                lock.lock();
                for (int i = 0; i < waktu; i+=30){   // iterasi tiap 30 detik untuk mengurangi kekenyangan dan mood
                    int kekenyanganAwal = kesejahteraan.getKekenyangan();
                    int moodAwal = kesejahteraan.getMood();
                    try {
                        Thread.sleep(30000); // Tunggu selama 30 detik
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    kesejahteraan.setKekenyangan(kekenyanganAwal-10);
                    kesejahteraan.setMood(moodAwal-10);
                    if (i%240 == 0){   // apabila i mencapai kelipatan 240 maka uang bertambah
                        this.uang = uang + pekerjaan.getGajiHarian();
                    }
                }
                this.waktuKerja = waktuKerja + waktu;
                lock.unlock();
                cekTidurdanBuangAir(waktu);
                cekKesejahteraan();
                synchronized(l){
                    l.notifyAll();
                }
            }
            else{
                System.out.println("Anda baru saja mengganti pekerjaan. Kerja bisa mulai dilakukan esok hari.");
            } 
        }    
        else{
            System.out.println("Masukkan waktu dengan kelipatan 120!");
        }
    }


    public void olahraga(int waktu){
        if (waktu%20 == 0){
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
            cekKesejahteraan();
            synchronized(l){
                l.notifyAll();
            }
        }
        else{
            System.out.println("Masukkan waktu dengan kelipatan 20!");
        }
    } 

    public void makan(Makanan makanan){
        lock.lock();
        String namaMakanan = makanan.getNama();
        int kekenyanganAwal = kesejahteraan.getKekenyangan();
        int currentQuantity = inventory.getOrDefault(namaMakanan, 0);
        int kekenyanganMakanan = makanan.getKekenyangan();

        if (inventory.containsKey(namaMakanan) || currentQuantity>0){   // apabila di dalam inventory ada nama makanan dan jumlahnya tidak 0 (just in case)
            try {
                Thread.sleep(30000); // Tunggu selama 30 detik
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kesejahteraan.setKekenyangan(kekenyanganAwal+makanan.getKekenyangan());
            inventory.put(namaMakanan, currentQuantity - 1);
            if (currentQuantity - 1 == 0) {
                inventory.remove(namaMakanan);
            }
            cekTidurdanBuangAir(30);
            cekKesejahteraan();
            sudahBuangAir = false;
            makanPertama = true;
        }
        else{
            System.out.println("Makanan habis atau tidak tersedia.");
            lock.unlock();
            return;
        }
        lock.unlock();
        synchronized(l){
            l.notifyAll();
        World world = World.getInstance();
        int currentTime = world.getHari()*720 + world.getWaktu();
        setWaktuMakanAwal(currentTime);  // waktu makan awal diset setelah selesai makan
        }
    }

    public void makan(BahanMakanan bahanMakanan){
        lock.lock();
        int kekenyanganAwal = kesejahteraan.getKekenyangan();
        String namaBahanMakanan = bahanMakanan.getNama();
        int currentQuantity = inventory.getOrDefault(namaBahanMakanan, 0);
        int kekenyanganBahanMakanan = bahanMakanan.getKekenyangan();
        if (inventory.containsKey(namaBahanMakanan) || currentQuantity>0){
            try {
                Thread.sleep(30000); // Tunggu selama 30 detik
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kesejahteraan.setKekenyangan(kekenyanganAwal+kekenyanganBahanMakanan);
            inventory.put(namaBahanMakanan, currentQuantity - 1);
            if (currentQuantity - 1 == 0) {
                inventory.remove(namaBahanMakanan);
            }
        }
        else{
            System.out.println("Bahan makanan habis atau tidak tersedia.");
            lock.unlock();
            return;
        }
        lock.unlock();
        cekTidurdanBuangAir(30);
        cekKesejahteraan();
        synchronized(l){
            l.notifyAll();
        }
        makanPertama = true;   // maka pertama dijalankan game, sim sudah makan dan cekTidurdanBuang air dapat mengurangi kesejahteraan apabila tidak buang air
        sudahBuangAir = false;
        World world = World.getInstance();
        int currentTime = world.getHari()*720 + world.getWaktu();
        setWaktuMakanAwal(currentTime);
    }
    

    public void tidur(int waktu){
        lock.lock();
        if (waktu>180 && (waktu%240==0)){
            int moodAwal = kesejahteraan.getMood();
            int kesehatanAwal = kesejahteraan.getKesehatan();
            int count = waktu/240;
            for (int i=0; i<count; i++){
                try {
                    Thread.sleep(waktu*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    kesejahteraan.setMood(moodAwal+30);
                    kesejahteraan.setKesehatan(kesehatanAwal+20);
                }
            }
            World world = World.getInstance();
            int currentTime = world.getHari()*720 + world.getWaktu() + waktu;   
            setWaktuTidurAwal(currentTime);
            cekTidurdanBuangAir(waktu);
            cekKesejahteraan();
            synchronized(l){
                l.notifyAll();
            }
        }
        else{
            System.out.println("Masukkan waktu minimal 3 menit dan kelipatan 4 menit");
        }
        lock.unlock();
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
            Thread.sleep(waktuMemasak*1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String bahan : arrayOfBahanMakanan) {   // mengurangi bahan makanan dalam inventory
            int jumlah = inventory.get(bahan);
            inventory.put(bahan, jumlah-1);
            if (jumlah - 1 == 0){
                inventory.remove(bahan);
            }
        }
        int jumlahMakananAwal = inventory.getOrDefault(makanan.getNama(), 0);
        inventory.put(makanan.getNama(), jumlahMakananAwal+1);    // Menambahkan makanan ke dalam inventory
        kesejahteraan.setMood(moodAwal+10);
        cekTidurdanBuangAir(waktuMemasak);
        cekKesejahteraan();
        synchronized(l){
            l.notifyAll();
        }
        lock.unlock();
    }

    public void berkunjung(Rumah rumahSim){
        lock.lock();
        int x1 = posisi.getCurrRumah().getLokasi().getX();
        int y1 = posisi.getCurrRumah().getLokasi().getY();
        int x2 = rumahSim.getLokasi().getX();
        int y2 = rumahSim.getLokasi().getY();
        int moodAwal = kesejahteraan.getMood();
        int kekenyanganAwal = kesejahteraan.getKekenyangan();
        int waktu = (int)Math.sqrt(Math.pow((x2-x1),2)-Math.pow((y2-y1), 2));
        int count = waktu/30;
        try {
            Thread.sleep(waktu*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kesejahteraan.setMood(moodAwal + (10*count));
        kesejahteraan.setKekenyangan(kekenyanganAwal - (10*count));
        posisi.setCurrRumah(rumahSim);
        posisi.setCurrRuangan(rumahSim.getRuangan("ruangUtama"));
        cekTidurdanBuangAir(waktu);
        cekKesejahteraan();
        synchronized(l){
            l.notifyAll();
        }
        lock.unlock();
    }

    public void buangAir(){
        lock.lock();
        int kekenyanganAwal = kesejahteraan.getKekenyangan();
        int moodAwal = kesejahteraan.getMood();
        try {
            Thread.sleep(10000);   // siklus 10 detik
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kesejahteraan.setKekenyangan(kekenyanganAwal-20);
        kesejahteraan.setMood(moodAwal+10);
        World world = World.getInstance();
        int currentTime = (int) world.getHari()*720 + world.getWaktu() + 10;
        world.addWaktu(10);
        sudahBuangAir = true;   // maka setelah makan SIM sudah buang air -> tidak perlu dikurangi kesejahteraannya
        cekKesejahteraan();
        synchronized(l){
            l.notifyAll();
        }
        lock.unlock();
    }

    public void nontonTV(int waktu){
        lock.lock();
        int moodAwal = kesejahteraan.getMood();
        try {
            Thread.sleep(waktu*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kesejahteraan.setMood(moodAwal+5);
        cekTidurdanBuangAir(waktu);
        cekKesejahteraan();
        synchronized(l){
            l.notifyAll();
        }
        lock.unlock();
    }

    public void ngoding(int waktu, String bahasaProgram){
        //thread
        lock.lock();
        if ((bahasaProgram.equals("Java")) || (bahasaProgram.equals("C")) || (bahasaProgram.equals("C++")) || (bahasaProgram.equals("Python"))){
            int kekenyanganAwal = kesejahteraan.getKekenyangan();
            int moodAwal = kesejahteraan.getMood();
            try {
                Thread.sleep(waktu*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (bahasaProgram.equals("Java")){
                kesejahteraan.setKekenyangan(kekenyanganAwal-10);
                kesejahteraan.setMood(moodAwal+5);
            }
            else if(bahasaProgram.equals("C")){
                kesejahteraan.setKekenyangan(kekenyanganAwal-10);
                kesejahteraan.setMood(moodAwal-5);
            }
            else if (bahasaProgram.equals("Python")){
                kesejahteraan.setKekenyangan(kekenyanganAwal-10);
                kesejahteraan.setMood(moodAwal+5);
            }
            else if (bahasaProgram.equals("C++")){
                kesejahteraan.setKekenyangan(kekenyanganAwal-10);
                kesejahteraan.setMood(moodAwal-5);
            }
            cekTidurdanBuangAir(waktu);
            cekKesejahteraan();
        }
        else{
            System.out.println("Bahasa pemrograman tersebut tidak tersedia");
            lock.unlock();
            return;
        }
        synchronized(l){
            l.notifyAll();
        }
        lock.unlock();
    }

    public void dengerMusik(int waktu, String genre){
        lock.lock();
        int moodAwal = kesejahteraan.getMood();
        try {
            Thread.sleep(1000*waktu);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        synchronized(l){
            l.notifyAll();
        }
        cekTidurdanBuangAir(waktu);
        cekKesejahteraan();
        lock.unlock();
    }

    public void mainGame(int waktu){
        lock.lock();
        int moodAwal = kesejahteraan.getMood();
        Random rand = new Random();
        int min = 0;
        int max = 1;
        int result = rand.nextInt((max - min) + 1) + min; // menghasilkan angka 0 atau 1 secara acak
        try {
            Thread.sleep(1000*waktu);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (result == 0) {
            System.out.println("Anda kalah. Mood telah berkurang 5.");
            kesejahteraan.setMood(moodAwal-5);
        } else {
            System.out.println("Anda menang! Mood telah bertambah 10.");
            kesejahteraan.setMood(moodAwal+10);
        }
        synchronized(l){
            l.notifyAll();
        }
        cekTidurdanBuangAir(waktu);
        cekKesejahteraan();
        lock.unlock();
    }
}
