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
                viewInventory();
            }else if(aksi.equals("Upgrade House")){
                System.out.println("Masukkan nama ruangan");
                String nama = scan.nextLine();
                System.out.println("Di mana posisi dari ruangan saat ini");
                String orientasi = scan.nextLine();
                currSim.upgradeRumah(nama,orientasi);
                //belum menghandle kasus ruangan gak bisa ditambah
                //di sim.java harus dihandle
            }else if(aksi.equals("Move Room")){
                System.out.println("Pergi ke ruangan mana?");
                String namaRuangan = scan.nextLine();
                currSim.moveToRoom(namaRuangan);
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
            }else if(aksi.equals("GO TO Object")){
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
                if (currSim.getPosisi().getCurrBarang()!=null){
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
                    }
                }else{
                    System.out.println("Sim tidak menghadapi barang apapun, tidak ada aksi yang dapat dilakukan");
                }
            }else if(aksi.equals("Kerja")){
                System.out.println("Berapa lama?");
                int kerja = Integer.parseInt(scan.nextLine());
                currSim.kerja(kerja);
                //belum dipastikan bagaimana sistem penggajiannya
            }else if(aksi.equals("Olahraga")){
                System.out.println("Berapa lama?");
                int olahraga = Integer.parseInt(scan.nextLine());
                currSim.olahraga(olahraga);
            }else if(aksi.equals("Berkunjung")){
                System.out.println("Ke mana?");
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
                //belum dicek ke Sim.java
            }else if(aksi.equals("Move room")){
                System.out.println("Ruang mana?");
                String ruangan = (scan.nextLine());
                currSim.moveToRoom(ruangan);
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
            }else{
                System.out.println("Masukkan aksi yang sesuai");
            }
        }
    }
    
    public void moveRoom(String namaruangan){
        //nunggu dzikri
        currSim.getPosisi().setCurrRuangan(currSim.getRumah().getRuangan(namaruangan));
    }
    
    public void help(){
        System.out.println("Berikut adalah command-command yang dapat digunakan");
    }

    public void viewInventory(){
        System.out.println("Berikut adalah daftar barang di dalam inventory");
        for (HashMap.Entry<String,Integer> i : currSim.getInventory().entrySet()){
            System.out.println("1. "+i.getKey()+" ada "+i.getValue()+" buah");
        }
    }
    
    // Menunggu class Sim
    public void upgradeHouse(){}
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

    public void listObject(){
        System.out.println("Objek yang berada di dalam ruangan");
        for (int i=0;i<currSim.getPosisi().getCurrRuangan().getBarangInRuangan().size();i++){
            System.out.println((i+1)+". "+currSim.getPosisi().getCurrRuangan().getBarangInRuangan().get(i).getNama());
        }
        // belum handle 2 objek dalam satu ruangan
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
    if (currSim.getPosisi().getCurrBarang().getNama().substring(0, 6).equals("Jam")){
        currSim.lihatWaktu();
    }else{
        System.out.println("Sim tidak berada di Jam");
    }
}

public void makan(){
    //belum ada ide
}

    // public void goToObject(Barang barang){
    //     boolean found=false;
    //     for (Barang i:currSim.getPosisi().getCurrRuangan().getBarangInRuangan()){
    //         if (i.equals(barang)){
    //             found=true;
    //         }
    //     }
    //     if (found){
    //         System.out.println("Berhasil bergerak menuju "+barang.getNama());
    //     }else{
    //         System.out.println(barang.getNama()+" tidak ada di ruangan");
    //     }
    // }

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
