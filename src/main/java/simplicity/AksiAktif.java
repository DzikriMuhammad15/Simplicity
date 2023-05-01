package simplicity;
public interface AksiAktif {
    
    public void kerja(int waktu);
        
    public void olahraga(int waktu);
        
    public void makan(Makanan makanan);
        
    public void makan(BahanMakanan bahanMakanan);
        
    public void tidur(int waktu);
        
    public void masak(Makanan makanan);
       
    public void berkunjung(Rumah rumahSim);

    public void buangAir();
       
    public void nontonTV(int waktu);
       
    public void ngoding(int waktu, String bahasaProgram);

    public void dengerMusik(int waktu, String genre);
        
    public void mainGame(int waktu);
       
}