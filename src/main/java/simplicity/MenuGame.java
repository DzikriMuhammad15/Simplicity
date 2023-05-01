package simplicity;
import java.util.*;

public class MenuGame {
    private Sim currSim;
    private World world = World.getInstance();
    private JSONWriter writer = new JSONWriter();
    private JSONreader reader = new JSONreader();
    private int simSpawn = -1;
    Display display = new Display();

    Scanner scan = new Scanner(System.in);
    Random random = new Random();
    public void startGame(){
        boolean gameover = false;
        System.out.println("Silakan memilih menu");
        System.out.println("1. New Game");
        System.out.println("2. Load");
        int menu = Integer.parseInt(scan.nextLine());
        if (menu==1){
            addSim();
            currSim = world.getArrSim().get(0);
            currSim.getRumah().setLokasi(new Point(random.nextInt()%64, random.nextInt()%64));
        }else if(menu==2){
            System.out.println("Masukkan namafile!");
            String namafile = scan.nextLine();
            String file = "src/main/resources"+namafile+".json";
            reader.readWorld(world,file);
            currSim = world.getArrSim().get(0);
            for (Sim i : world.getArrSim()){
                    for (Ruangan k : i.getRumah().getArrayOfRuangan()){
                        for (Barang l: k.getBarangInRuangan()){
                            NonMakanan nonMakanan = (NonMakanan) l;
                            k.locateBarang(nonMakanan,nonMakanan.getPosisi().getX(),nonMakanan.getPosisi().getY());
                        }
                    }
            
            }
        }
        //pilihan load game atau new game
        while (!gameover){
            currSim.getPosisi().getCurrRuangan().display();
            System.out.println("Apa yang ingin anda lakukan?");
            String aksi = scan.nextLine();
            if (aksi.equals("View Sim Info")){
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
                
            }else if(aksi.equals("Change Sim")){
                System.out.println("Masukkan no sim dari sim-sim yang ada di bawah");
                for (int i=0;i<world.getArrSim().size();i++){
                    System.out.println(i+1+". "+world.getArrSim().get(i).getName());
                }
                Integer noSim = Integer.parseInt(scan.nextLine());
                changeSim(noSim);
            }else if(aksi.equals("List Object")){
                listObject();
            }else if(aksi.equals("Go To Object")){
                listObject();
                System.out.println("masukkan no object yang dituju");
                Integer obj = Integer.parseInt(scan.nextLine());
                boolean check=false;
                for (int i=0;i<currSim.getPosisi().getCurrRuangan().getBarangInRuangan().size();i++){
                    if (i==obj-1){
                        currSim.getPosisi().setCurrBarang(currSim.getPosisi().getCurrRuangan().getBarangInRuangan().get(i));
                    }
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
                System.out.println("Masukkan waktu dalam satuan detik (kelipatam 120)");
                int kerja = Integer.parseInt(scan.nextLine());
                if (kerja%120==0){
                    currSim.kerja(kerja);
                }else{
                    System.out.println("Masukan waktu tidak valid");
                }
            }else if(aksi.equals("Olahraga")){
                System.out.println("Masukkan waktu dalam satuan detik (kelipatan 20)");
                int olahraga = Integer.parseInt(scan.nextLine());
                if (olahraga%20==0){
                    currSim.olahraga(olahraga);
                }else{
                    System.out.println("Masukan waktu tidak valid");
                }
            }else if(aksi.equals("Berkunjung")){
                System.out.println("Berikut tetangga di sekitar sim");
                int n=0;
                int j=0;
                while (n<world.getArrSim().size()){
                    if (!world.getArrSim().get(n).equals(currSim)){
                        System.out.println(j+1+world.getArrSim().get(n).getName());    
                    }else{
                        j=n-1;
                    }
                    n++;
                    j++;
                }
                System.out.println("Masukan no tetangga");
                Integer tetangga = Integer.parseInt(scan.nextLine());
                boolean check = false;
                int i=0;
                while (i<world.getArrSim().size() && i<tetangga+1){
                    i++;
                }
                //whilenya masih perlu diitung
                currSim.berkunjung(world.getArrSim().get(i).getRumah());
            }else if(aksi.equals("Beli Barang")){
                System.out.println("Barang apa?");
                //perlu ngeprint nomor barang biar input lebih valid
                String barang = scan.nextLine();
                currSim.beliBarang(barang);
                //perlu handle di sim apakah pembelian berhasil atau gagal
                //uang belum berkurang
            }else if(aksi.equals("Move Room")){
                System.out.println("Silakan masukkan angka untuk ruangan yang dipilih");
                for (int  i=0;i<currSim.getPosisi().getCurrRumah().getArrayOfRuangan().size();i++){
                    System.out.println(i+1+". "+currSim.getPosisi().getCurrRumah().getArrayOfRuangan().get(i).getNamaRuangan());
                }
                int noruangan = Integer.parseInt(scan.nextLine());
                currSim.moveToRoom(currSim.getPosisi().getCurrRumah().getArrayOfRuangan().get(noruangan-1));
            }else if(aksi.equals("Memasang Barang")){
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
            }else if(aksi.equals("Exit")){
                System.out.println("Silakan pilih!");
                System.out.println("1. Save");
                System.out.println("2. Exit");
                int no = Integer.parseInt(scan.nextLine());
                if (no==1){
                    System.out.println("Masukkan nama file!");
                    String namafile = scan.nextLine();
                    String file  = "src/main/resources"+namafile+".json";
                    writer.writeWorld(world, file);
                }
                gameover=true;
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
            for (int i=0;i<world.getArrSim().size();i++){
                System.out.println(i+1+world.getArrSim().get(i).getName());
            }
            Integer simBaru = Integer.parseInt(scan.nextLine());
            changeSim(simBaru-1);
        }
    }

    public void help(){
        System.out.println("Berikut adalah command-command di dalam game yang dapat digunakan");
        System.out.println("1. View Sim Info");
        System.out.println("2. View Current Location");
        System.out.println("3. Upgrade House");
        System.out.println("4. Move Room");
        System.out.println("5. Edit Room");
        System.out.println("6. Add Sim");
        System.out.println("7. List Object");
        System.out.println("8. Go To Object");
        System.out.println("9. Action");
        System.out.println("10. Kerja");
        System.out.println("11. Pukul");
        System.out.println("12. Beli Barang");
        System.out.println("13. Olahraga");
        System.out.println("14. Berkunjung");
        System.out.println("15. Memasang Barang");
        System.out.println("16. Bercanda");
        System.out.println("17. Bunuh Diri");
        
    }
    
    public void editRoom(){
        System.out.println("Anda dapat melakukan hal terhadap barang di ruangan");
        System.out.println("1. memindahkan barang\n2. meletakkan barang\n3. merotasi barang");
        int noCommand = Integer.parseInt(scan.nextLine());
        listObject();
        int noBarang = Integer.parseInt(scan.nextLine());
        if (noBarang>currSim.getPosisi().getCurrRuangan().getBarangInRuangan().size()||noCommand>3){
            System.out.println("Masukan tidak valid");
        }else{
            Barang barang = currSim.getPosisi().getCurrRuangan().getBarangInRuangan().get(noBarang-1);
            if (noCommand==1){
                System.out.println("Masukkan titik pemindahan barang!");
                System.out.print("X : ");
                int x=Integer.parseInt(scan.nextLine());
                System.out.print("Y : ");
                int y=Integer.parseInt(scan.nextLine());
                currSim.getPosisi().getCurrRuangan().moveBarang(barang, x, y);   
            }else if(noCommand==2){
                System.out.println("Masukkan titik pemindahan barang!");
                System.out.print("X : ");
                int x=Integer.parseInt(scan.nextLine());
                System.out.print("Y : ");
                int y=Integer.parseInt(scan.nextLine());
                currSim.getPosisi().getCurrRuangan().locateBarang(barang, x,y);
            }else if (noCommand==3){
                System.out.print("Rotasi : ");
                int y=Integer.parseInt(scan.nextLine());
                currSim.getPosisi().getCurrRuangan().rotate(barang, y);
            }    
        }
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
        if (simSpawn<world.getHari()){
            System.out.print("Silakan masukkan nama lengkap Sim mu : ");
            String nama = scan.nextLine();
            Sim sim1 = new Sim(nama);
            world.getArrSim().add(sim1);
            simSpawn=world.getHari();
        }else{
            System.out.println("Belum dapat menspawn sim baru");
        }
    }
    
    public void changeSim(Integer simBaru){
        for (int i=0;i<world.getArrSim().size();i++){
            if (i==simBaru-1){
                currSim=world.getArrSim().get(i);
            }    
        }
        System.out.println("Sim saat ini "+currSim.getName());
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
            ArrayList<String> daftarmakanan = new ArrayList<>();
            daftarmakanan.add("Nasi Ayam");
            daftarmakanan.add("Nasi Kari");
            daftarmakanan.add("Susu Kacang");
            daftarmakanan.add("Tumis sayur");
            daftarmakanan.add("Bistik");
            for (int i=0;i<daftarmakanan.size();i++){
                System.out.println(i+1+". "+daftarmakanan.get(i));
            }
            System.out.println("masukkan no makanan?");
            int noMakanan = Integer.parseInt(scan.nextLine());
            String makanan = "Nasi Ayam";
            for (int i=0;i<daftarmakanan.size();i++){
                if (i==noMakanan-1){
                    makanan = daftarmakanan.get(i);
                }
            }
            if (daftarmakanan.contains(makanan)){
                currSim.masak(new Makanan(makanan));
            }else{
                System.out.println("Barang tidak termasuk makanan yang dapat dimasak");
            } 
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
            System.out.printf("|%-1s | %-10s | %-8s |%n","no", "makanan", "Jumlah");
            System.out.println("|---|------------|----------|");
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
            int i=1;
            for (Map.Entry<String, Integer> entry : currSim.getInventory().entrySet()) {
                if (daftarMakanan.contains(entry.getKey())){
                    System.out.printf("| %-1d | %-10s | %-8d |%n", i,entry.getKey(),entry.getValue());
                    i++;
                }    
            }
            System.out.println("Masukkan nomor makanan");
            Integer noMakanan = Integer.parseInt(scan.nextLine());
            int j=1;
            String namaMakanan = "Ayam";
            int k=0;
            for (Map.Entry<String, Integer> entry : currSim.getInventory().entrySet()) {
                if (j==noMakanan){
                    for (k=0;k<daftarMakanan.size();k++){
                        if (daftarMakanan.get(k).equals(entry.getKey())){
                            namaMakanan = entry.getKey();
                            break;
                        }
                    } 
                    break;
                }else if (daftarMakanan.contains(entry.getKey())){
                    j++;
                }    
            }
            if (k>=5){
                currSim.makan(new Makanan(namaMakanan));
            }else{
                currSim.makan(new BahanMakanan(namaMakanan));
            }
            
    }

    

    public static void main(String[] args){
        
        MenuGame menu = new MenuGame();
        Display display = new Display();
        System.out.println("\t\t     Welcome To");
        display.home();
        while (true){
            System.out.println("silahkan memilih menu permainan");
            System.out.println("1. Start Game");
            System.out.println("2. Exit");
            System.out.println("3. Help");
            String command = menu.scan.nextLine();
            if (command.equals("Start Game")){
                menu.startGame();
            }else if (command.equals("Exit")){
                System.exit(0);
            }else if(command.equals("Help")){
                menu.help();
            }else{
                System.out.println("Masukkan perintah command yang sesuai");
            }     
        }
    }
}
