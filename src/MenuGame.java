import java.util.*;

public class MenuGame {
    private Sim currSim;
    private World world = World.getInstance();

    Scanner scan = new Scanner(System.in);
    Random random = new Random();
    //belum fix
    public void startGame(){
        //daftar makanan dan bahan makanan
        addSim();
        currSim = world.getArrSim().get(0);
        currSim.getRumah().setLokasi(new Point(random.nextInt()%64, random.nextInt()%64));
        boolean gameover = false;
        while (!gameover){
            System.out.println("Apa yang ingin anda lakukan?");
            String aksi = scan.nextLine();
            if (aksi.equals("View Sim status")){
                viewSimInfo();
            }else if(aksi.equals("View Current Location")){
                viewCurrentLocation();
            }else if(aksi.equals("View Inventory")){
                currSim.lihatInventory();
            }else if(aksi.equals("Upgrade House")){
                System.out.println("Masukkan nama ruangan");
                String nama = scan.nextLine();
                System.out.println("Di mana posisi dari ruangan saat ini");
                String orientasi = scan.nextLine();
                currSim.upgradeRumah(nama,orientasi);
                //belum menghandle kasus ruangan gak bisa ditambah
                //di sim.java harus dihandle
            }else if(aksi.equals("Edit Room")){
                editRoom();
                //belum
            }else if(aksi.equals("Add Sim")){
                addSim();
                //belum handle satu sim perhari
            }else if(aksi.equals("Change Sim")){
                System.out.println("Berikut nama sim yang ada");
                for (int i=0;i<world.getArrSim().size();i++){
                    System.out.println(i+1+". "+world.getArrSim().get(i).getName());
                }
                String namaSim = scan.nextLine();
                changeSim(namaSim);
            }else if(aksi.equals("List Object")){
                listObject();
            }else if(aksi.equals("Go To Object")){
                listObject();
                System.out.println("mau ke objek mana");
                String obj = scan.nextLine();
                boolean check=false;
                for (Barang i : currSim.getPosisi().getCurrRuangan().getBarangInRuangan()){
                    if (i.getNama().contains(obj)){
                        currSim.gotToBarang(i);
                        check=true;
                    }
                }
                if (check){
                    System.out.println("Berhasil pindah ke "+obj);
                }else{
                    System.out.println("Gagal berpindah");
                }
            }else if(aksi.equals("Action")){
                    if (currSim.getPosisi().getCurrBarang().getNama().equals("Kasur Single")){
                        tidur();
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Kasur Queen Size")){
                        tidur();
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Kasur King Size")){
                        tidur();
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Toilet")){
                        buangAir();
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Kompor Gas")){
                        memasak();
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Kompor Listrik")){
                        memasak();
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Meja dan Kursi")){
                        makan();
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Jam")){
                        lihatWaktu();
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("TV")){
                        nontonTV();
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Laptop")){
                        System.out.println("Pilih aksi yang ingin dilakukan Ngoding/Dengar Musik/Main Game");
                        String aksiOnLaptop = scan.nextLine();
                        if (aksiOnLaptop.equals("Ngoding")){
                            ngoding();
                        }else if(aksiOnLaptop.equals("Dengar Musik")){
                            dengarMusik();
                        }else if(aksiOnLaptop.equals("Main Game")){
                            mainGame();
                        }
                    }
            }else if(aksi.equals("Kerja")){
                System.out.println("Berapa lama?");
                int kerja = Integer.parseInt(scan.nextLine());
                currSim.kerja(kerja);
            }else if(aksi.equals("Olahraga")){
                System.out.println("Berapa lama?");
                int olahraga = Integer.parseInt(scan.nextLine());
                currSim.olahraga(olahraga);
            }else if(aksi.equals("Berkunjung")){
                System.out.println("Ke mana?");
                //menampilkan daftar tetangga
                //input user menggunakan angka
                String tetangga = scan.nextLine();
                boolean check = false;
                for (Sim i: world.getArrSim()){
                    if (i.getName().equals(tetangga)){
                        currSim.berkunjung(i.getRumah());
                        check=true;
                    }
                }
                if (check){
                    System.out.println("Berhasil berkunjung ke "+tetangga);
                }else{
                    System.out.println(tetangga+" tidak ditemukan");
                }
            }else if(aksi.equals("Beli barang")){
                System.out.println("Barang apa?");
                String barang = scan.nextLine();
                currSim.beliBarang(barang);
                //perlu handle di sim apakah pembelian berhasil atau gagal
            }else if(aksi.equals("Move room")){
                System.out.println("Silakan masukkan angka untuk ruangan yang dipilih");
                for (int  i=0;i<currSim.getPosisi().getCurrRumah().getArrayOfRuangan().size();i++){
                    System.out.println(i+1+". "+currSim.getPosisi().getCurrRumah().getArrayOfRuangan().get(i));
                }
                int noruangan = Integer.parseInt(scan.nextLine());
                currSim.moveToRoom(currSim.getPosisi().getCurrRumah().getArrayOfRuangan().get(noruangan-1));
            }else if(aksi.equals("Memasang barang")){
                System.out.println("Barang apa?");
                String barang = (scan.nextLine());
                boolean check = false;
                for (HashMap.Entry<String,Integer> i : currSim.getInventory().entrySet()){
                    if (i.getKey().equals(barang)){
                        System.out.println("Masukkan titik pemasangan");
                        String titik=scan.nextLine();
                        int x = Integer.parseInt(titik.substring(0, 1));
                        int y = Integer.parseInt(titik.substring(2, 1));
                        currSim.pasangBarang(barang, x, y);
                        check = true;
                    }
                }
                if (!check){
                    System.out.println("Barang tidak tersedia");
                }
            }else if(aksi.equals("Help")){
                help();
            }else if(aksi.equals("Pukul")){
                pukul();
            }else if(aksi.equals("Bercanda")){
                bercanda();
            }else if(aksi.equals("Bunuh Diri")){
                bunuhDiri();
            }else{
                System.out.println("Masukkan aksi yang sesuai");
            }
        }
    }
    
    public void pukul(){
        System.out.println("Siapa yang ingin anda pukul?");
        String otherSim = scan.nextLine();
        boolean check = false;
        for (Sim i: world.getArrSim()){
            if (i.getName().equals(otherSim)){
                currSim.pukulSim(i);
                check=true;
            }
        }
        if (check){
            System.out.println(otherSim+" terpukul hingga babak belur");
        }else{
            System.out.println("Gagal memukul");
        }
    }

    public void nontonTV(){
        System.out.println("Berapa lama?");
        int waktu = Integer.parseInt(scan.nextLine());
        currSim.nontonTV(waktu);
        System.out.println("Berhasil menonton TV");
    }

    public void ngoding(){
        System.out.println("Pilih dari beberapa bahasa pemrograman yang dikuasai berikut");
        System.out.println("Python,C,C++,Java");    
        String bahasa = scan.nextLine();
        System.out.println("Berapa lama?");
        int waktu = Integer.parseInt(scan.nextLine());
        currSim.ngoding(waktu, bahasa);
    }

    public void bercanda(){
        //untuk semua Sim di ruangan yang sama mendapat effect bercanda
        boolean check=false;
        for (Sim i: world.getArrSim()){
            if (i.getPosisi().getCurrRuangan().equals(currSim.getPosisi().getCurrRuangan())){
                currSim.bercanda(i);
                check=true;
            }
        }
        if (check){
            System.out.println("Semua orang di dalam ruangan tertawa terpingkal-pingkal");
        }else{
            System.out.println("Tidak ada orang yang diajak bercanda");
        }
    }

    public void dengarMusik(){
        System.out.println("Beberapa genre lagu yang dapat diputar");
        ArrayList<String> daftarGenre = new ArrayList<>();
        daftarGenre.add("Indie");
        daftarGenre.add("Dangdut");
        daftarGenre.add("Pop");
        daftarGenre.add("K-Pop");
        daftarGenre.add("Reggae");
        daftarGenre.add("WOTA");
        for (int i=0;i<daftarGenre.size();i++){
            System.out.println(i+1+". "+daftarGenre.get(i));
        }
        System.out.println("Masukkan nomor genre");
        int genre = Integer.parseInt(scan.nextLine());
        System.out.println("Berapa lama");
        int waktu = Integer.parseInt(scan.nextLine());
        currSim.dengerMusik(waktu, daftarGenre.get(genre-1));
    }

    public void mainGame(){
        System.out.println("Berapa lama?");
        int waktu = Integer.parseInt(scan.nextLine());
        currSim.mainGame(waktu);
    }

    public void bunuhDiri(){
        currSim.bunuhDiri();
        checkSim();
        if (!world.getArrSim().isEmpty()){
            System.out.println("Pilih Sim lain untuk tetap bermain");
            for (Sim i : world.getArrSim()){
                System.out.println(i.getName());
            }
            String simBaru = scan.nextLine();
            changeSim(simBaru);
        }
    }

    public void help(){
        System.out.println("Berikut adalah command-command yang dapat digunakan");
    }
    
    // Menunggu class Sim
    public void upgradeHouse(){

    }
    
    public void editRoom(){
        System.out.println("Anda dapat memindahkan barang, merotasi, meletakkan barang atau memindahkan ke dalam inventory");
        System.out.println("Gunakan command [memindahkan {barang}, meletakkan {barang}, merotasi {barang}, memindahkan {barang} ke inventory]");
        String edit = scan.nextLine();
        if (edit.length()>=11 && edit.substring(0,11).equals("memindahkan")){

        }else if(edit.length()>=10 && edit.substring(0, 10).equals("meletakkan")){

        }else if(edit.length()>=8 && edit.substring(0, 8).equals("merotasi")){

        }else if(edit.length()>=24 && edit.substring(0,24).equals("memindahkan ke inventory")){

        }else{
            System.out.println("Masukkan perintah yang sesuai");
        }
        //belum
    }
    
    
    //mungkin fix
    public void exit(){
        //break;
    }

    public void viewSimInfo(){
        System.out.println("Sim Info");
        System.out.println("1. Nama: "+currSim.getName());
        System.out.println("2. Pekerjaan: "+currSim.getPekerjaan().getNamaPekerjaan());
        System.out.println("3. Kesehatan: "+currSim.getKesejahteraan().getKesehatan());
        System.out.println("4. Kekenyangan: "+currSim.getKesejahteraan().getKekenyangan());
        System.out.println("5. Mood: "+currSim.getKesejahteraan().getMood());
        System.out.println("6. Uang: "+currSim.getUang());
    }

    public void viewCurrentLocation(){
        System.out.println("Lokasi sim saat ini : ");
        System.out.println("Sim berada pada rumah "+world.getSimOwnRumah(currSim.getPosisi().getCurrRumah()).getName()+" pada ruangan "+currSim.getPosisi().getCurrRuangan().getNamaRuangan());
        try{
            System.out.println("Sim berada pada "+currSim.getPosisi().getCurrBarang().getNama());
        }catch(Exception e){
            System.out.println("Sim tidak menghadapi barang apapun");
        }
    }

    public void addSim(){
        System.out.println("Silakan masukkan nama lengkap Sim mu");
        String nama = scan.nextLine();
        Sim sim1 = new Sim(nama);
        world.getArrSim().add(sim1);
        //belum handle satu hari satu sim
    }
    
    public void changeSim(String simBaru){
        for (Sim i:world.getArrSim()){
            if (i.getName().equals(simBaru)){
                currSim=i;
            }
        }
    }

    public void checkSim(){
        for (Sim i:world.getArrSim()){
            if (i.getKesejahteraan().isDead()||i.getKesejahteraan().getKekenyangan()==0||i.getKesejahteraan().getKesehatan()==0||i.getKesejahteraan().getMood()==0){
                world.getArrSim().remove(i);
            }
        }
    }

    public void listObject(){
        System.out.println("Objek yang berada di dalam ruangan");
        for (int i=0;i<currSim.getPosisi().getCurrRuangan().getBarangInRuangan().size();i++){
            System.out.println((i+1)+". "+currSim.getPosisi().getCurrRuangan().getBarangInRuangan().get(i).getNama());
        }
    }

    

public void tidur(){
    if (currSim.getPosisi().getCurrBarang().getNama().substring(0, 5).equals("Kasur")){
        System.out.println("Berapa lama? (masukkan dalam satuan menit)");
        int waktu = Integer.parseInt(scan.nextLine());
        currSim.tidur(waktu);
        world.setWaktu(waktu);
    }else{
        System.out.println("Sim tidak berada di kasur");
    }
}

public void memasak(){
    if (currSim.getPosisi().getCurrBarang().getNama().substring(0, 6).equals("Kompor")){
        System.out.println("Memasak apa?");
        String makanan = scan.nextLine();
        ArrayList<String> daftarmakanan = new ArrayList<>();
        daftarmakanan.add("Nasi Ayam");
        daftarmakanan.add("Nasi Kari");
        daftarmakanan.add("Susu Kacang");
        daftarmakanan.add("Tumis sayur");
        daftarmakanan.add("Bistik");
        if (daftarmakanan.contains(makanan)){
            // currSim.masak(makanan);
            //di sim perlu menghandle ketersediaan bahan di inventory
        }else{
            System.out.println("Barang tidak termasuk makanan yang dapat dimasak");
        } 
        //belum dicocokin dengan sim
    }else{
        System.out.println("Sim tidak berada di kompor");
    }
}

public void buangAir(){
    if (currSim.getPosisi().getCurrBarang().getNama().substring(0, 6).equals("Toilet")){
        currSim.buangAir();
    }else{
        System.out.println("Sim tidak berada di toilet");
    }
}

public void lihatWaktu(){
    if (currSim.getPosisi().getCurrBarang().getNama().substring(0, 3).equals("Jam")){
        currSim.lihatWaktu();
    }else{
        System.out.println("Sim tidak berada di Jam");
    }
}

public void makan(){
        System.out.printf("| %-10s | %-8s |%n", "makanan", "Jumlah");
        System.out.println("|------------|----------|");
        ArrayList<String> daftarMakanan = new ArrayList<>();
        daftarMakanan.add("Nasi Ayam");
        daftarMakanan.add("Nasi Kari");
        daftarMakanan.add("Susu Kacang ");
        daftarMakanan.add("Tumis Sayur");
        daftarMakanan.add("Bistik");
        daftarMakanan.add("Nasi");
        daftarMakanan.add("Kentang");
        daftarMakanan.add("Ayam");
        daftarMakanan.add("Sapi");
        daftarMakanan.add("Wortel");
        daftarMakanan.add("Bayam");
        daftarMakanan.add("Kacang");
        daftarMakanan.add("Susu");
        // Loop over entries in the HashMap and print them in table format
        for (Map.Entry<String, Integer> entry : currSim.getInventory().entrySet()) {
            if (daftarMakanan.contains(entry.getKey())){
                System.out.printf("| %-10s | %-8d |%n", entry.getKey(),entry.getValue());
            }    
        }
        String makanan = scan.nextLine();
        for (int i=0;i<daftarMakanan.size();i++){
            if (daftarMakanan.contains(makanan) && i<5){
                currSim.makan(new Makanan(makanan));
            }else{
                currSim.makan(new BahanMakanan(makanan));
            }
        }
}

    

    public static void main(String[] args){
        
        MenuGame menu = new MenuGame();
        System.out.println("Welcome to Simplycity");
        while (true){
            System.out.println("silahkan memilih menu permainan");
            System.out.println("1. Start Game");
            System.out.println("2. Exit");
            System.out.println("3. Help");
            String command = menu.scan.nextLine();
            if (command.equals("Start Game")){
                menu.startGame();
            }else if (command.equals("Exit")){
                menu.exit();
            }else if(command.equals("Help")){
                menu.help();
            }else{
                System.out.println("Masukkan perintah command yang sesuai");
            }
            
        }
    }
}
