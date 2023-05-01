package simplicity;
public class Pekerjaan {
    private String namaPekerjaan;
    private int GajiHarian;

    //constructor Pekerjaan
    public Pekerjaan(String namaPekerjaan, int gaji){
        this.namaPekerjaan = namaPekerjaan;
        this.GajiHarian = gaji;
    }

    //getter namaPekerjaan
    public String getNamaPekerjaan(){
        return this.namaPekerjaan;
    }

    //setter namaPekerjaan
    public void setNamaPekerjaan(String namaPekerjaan){
        this.namaPekerjaan = namaPekerjaan;
    }

    //getter GajiHarian
    public int getGajiHarian(){
        return this.GajiHarian;
    }

    //setter gajiHarian
    public void setGajiHarian(int gaji){
        this.GajiHarian=gaji;
    }
}
