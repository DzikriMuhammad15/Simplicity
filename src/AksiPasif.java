public interface AksiPasif{
    public void moveToRoom(String ruangTujuan);

    public void lihatInventory();

    public void pasangBarang(String namaBarang, int x, int y);

    public void lihatWaktu();

    public void pukulSim(Sim otherSim);

    public void bercanda(Sim otherSim);
}