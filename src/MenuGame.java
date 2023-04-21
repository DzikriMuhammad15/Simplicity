import java.text.BreakIterator;
import java.util.*;

import javax.sound.midi.Soundbank;

public class MenuGame {
    private Sim currSim;
    private World world = World.getInstance();

    Scanner scan = new Scanner(System.in);
    Random random = new Random();

    
    //belum fix
    public void startGame(){
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
                upgradeHouse();
            }else if(aksi.equals("Move Room")){
                System.out.println("Pergi ke ruangan mana?");
                String namaRuangan = scan.nextLine();
                currSim.moveToRoom(namaRuangan);
            }else if(aksi.equals("Edit Room")){
                editRoom();
            }else if(aksi.equals("Add Sim")){
                addSim();
            }else if(aksi.equals("Change Sim")){
                changeSim(currSim);
            }else if(aksi.equals("List Object")){
                listObject();
            }else if(aksi.equals("GO TO Object")){
                // belum ada parameter objec mau ke mana
                //currSim.gotToBarang("");
            }else if(aksi.equals("Action")){
                if (currSim.getPosisi().getCurrBarang()!=null){
                    if (currSim.getPosisi().getCurrBarang().getNama().equals("Kasur Single")){
                        System.out.println("Aksi yang dapat dilakukan");
                        System.out.println("Tidur");
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Kasur Queen Size")){
                        System.out.println("Aksi yang dapat dilakukan");
                        System.out.println("Tidur");
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Kasur King Size")){
                        System.out.println("Aksi yang dapat dilakukan");
                        System.out.println("Tidur");
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Toilet")){
                        System.out.println("Aksi yang dapat dilakukan");
                        System.out.println("Buang air");
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Kompor Gas")){
                        System.out.println("Aksi yang dapat dilakukan");
                        System.out.println("Memasak");
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Kompor Listrik")){
                        System.out.println("Aksi yang dapat dilakukan");
                        System.out.println("Memasak");
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Meja dan Kursi")){
                        System.out.println("Aksi yang dapat dilakukan");
                        System.out.println("Makan");
                    }else if (currSim.getPosisi().getCurrBarang().getNama().equals("Jam")){
                        System.out.println("Aksi yang dapat dilakukan");
                        System.out.println("Melihat waktu");
                    }
                    action();
                }else{
                    System.out.println("Sim tidak menghadapi barang apapun, tidak ada aksi yang dilakukan");
                }
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
    }
    
    public void changeSim(Sim otherSim){
        currSim=otherSim;
    }

    public void listObject(){
        System.out.println("Objek yang berada di dalam ruangan");
        for (int i=0;i<currSim.getPosisi().getCurrRuangan().getBarangInRuangan().size();i++){
            System.out.println((i+1)+". "+currSim.getPosisi().getCurrRuangan().getBarangInRuangan().get(i));
        }
    }

    public void action(){
        System.out.println("Masukkan aksu yang akan dilakukan");
        String aksiObjek = scan.nextLine();
        if (aksiObjek.equals("Tidur")){
            System.out.println("Berapa lama? (masukkan dalam satuan menit)");
            int waktu = Integer.parseInt(scan.nextLine());
            currSim.tidur(waktu);
            world.setWaktu(waktu);
        }else if(aksiObjek.equals("Makan")){
            System.out.println("Makan apa?");
            //perlu nampilin inventory atau nggak?
        }else if(aksiObjek.equals("Memasak")){

        }else if(aksiObjek.equals("Buang air")){

        }else if(aksiObjek.equals("Melihat waktu")){

        }else{
            System.out.println("Masukkan aksi yang sesuai");
        }
    }

    public void goToObject(Barang barang){
        boolean found=false;
        for (Barang i:currSim.getPosisi().getCurrRuangan().getBarangInRuangan()){
            if (i.equals(barang)){
                found=true;
            }
        }
        if (found){
            System.out.println("Berhasil bergerak menuju "+barang.getNama());
        }else{
            System.out.println(barang.getNama()+" tidak ada di ruangan");
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
