import java.text.BreakIterator;
import java.util.*;

public class MenuGame {
    private Sim currSim;
    private World world = World.getInstance();

    Scanner scan = new Scanner(System.in);
    Random random = new Random();

    
    //belum fix
    public void startGame(){
        addSim();
        currSim = world.getArrSim().get(0);
        Point point1 = new Point(random.nextInt(),random.nextInt());
        Rumah rumah = new Rumah(point1);
        while (true){
            
            
            
        }
    }
    
    public void moveRoom(String namaruangan){
        //nunggu dzikri
        currSim.getPosisi().setCurrRuangan(currSim.getRumah().getRuangan(namaruangan));
    }
    
    public void help(){
        System.out.println("Berikut adalah command-command yang dapat digunakan");
    }

    // Menunggu class Sim
    // public void viewInventory()
    // public void upgradeHouse()
    // public void editRoom()
    
    
    //mungkin fix
    public void exit(){
        break;
    }
    public void viewSimInfo(){
        System.out.println("Sim Info");
        System.out.println("1. Nama: "+currSim.getname());
        System.out.println("2. Pekerjaan: "+currSim.getPekerjaan().getNamaPekerjaan());
        System.out.println("3. Kesehatan: "+currSim.getKesejahteraan().getKesehatan());
        System.out.println("4. Kekenyangan: "+currSim.getKesejahteraan().getKekenyangan());
        System.out.println("5. Mood: "+currSim.getKesejahteraan().getMood());
        System.out.println("6. Uang: "+currSim.getUang());
    }

    public void viewCurrentLocation(){
        System.out.println("Lokasi sim saat ini : ");
        System.out.println("Sim berada pada rumah "+world.getSimOwnRumah(currSim.getPosisi().getRumah()).getname()+" pada ruangan "+currSim.getPosisi().getCurrRuangan().getNamaRuangan());
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

    public void goToObject(Barang barang){
        boolean found=false;
        for (Barang i:currSim.getPosisi().getRuangan().getBarangInRuangan()){
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
