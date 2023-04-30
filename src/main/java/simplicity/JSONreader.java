import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import netscape.javascript.JSObject;

public class JSONreader {

    public void readWorld(World world, String namafile) {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(namafile)) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonobj = (JSONObject) obj;
            String hari = jsonobj.get("Hari").toString();
            world.setHari(Integer.parseInt(hari));
            String waktu = jsonobj.get("waktu").toString();
            world.setWaktu(Integer.parseInt(waktu));

            JSONArray arrsim = (JSONArray) jsonobj.get("ArrSim");
            for (Object i : arrsim) {
                world.getArrSim().add(readSim((JSONObject) i));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Sim readSim(JSONObject jsonobj) {
        Sim sim1 = new Sim((String) jsonobj.get("nama"));
        //sim1.setInventory(readInventory(jsonobj.get("inventory")));
        sim1.setKesejahteraan(readKesejahteraan((JSONObject)jsonobj.get("kesejahteraan")));
        sim1.setOnDelivery(readOnDelivery((JSONArray)jsonobj.get("on delivery")));
        sim1.setPekerjaan(readPekerjaan((JSONObject)jsonobj.get("pekerjaan")));
        //sim1.setPosisi(readPosisi((JSONObject) jsonobj.get("posisi")));
        sim1.setRumah(readRumah((JSONObject) jsonobj.get("rumah")));
        sim1.setStatus((String)jsonobj.get("status"));
        sim1.setUang(Integer.parseInt(jsonobj.get("uang").toString()));
        return sim1;
    }

    public Pekerjaan readPekerjaan(JSONObject jsonobj){
         Pekerjaan pekerjaan1 = new Pekerjaan(jsonobj.get("nama").toString(),Integer.parseInt(jsonobj.get("gaji harian").toString()));
         return pekerjaan1;
    }

    public Rumah readRumah(JSONObject jsonobj) {
        Rumah rumah = new Rumah(readPoint((JSONObject) jsonobj.get("lokasi")));
        ArrayList<Ruangan> arrayOfRuangan = new ArrayList<>();
        rumah.getArrayOfRuangan().clear();
        JSONArray array = (JSONArray) jsonobj.get("array of ruangan");
        for (Object i : array){
            rumah.getArrayOfRuangan().add(readRuangan((JSONObject)i));
        }
        return rumah;
    }

    public Ruangan readRuangan(JSONObject jsonobj){
        System.out.println(1);
        if (jsonobj==null){
            System.out.println(2);
            return null;
        }else{
            System.out.println(3);
            Ruangan ruangan = new Ruangan(jsonobj.get("nama").toString());
            if (jsonobj.get("ruang atas")==null){
                ruangan.setRuangAtas(null);
            }else{
                ruangan.setRuangAtas(readRuangan((JSONObject) jsonobj.get("ruang atas")));
            }
            if (jsonobj.get("ruang bawah")==null){
                ruangan.setRuangBawah(null);
            }else{
                ruangan.setRuangBawah(readRuangan((JSONObject) jsonobj.get("ruang bawah")));
            }
            if (jsonobj.get("ruang kanan")==null){
                ruangan.setRuangKanan(null);
            }else{
                ruangan.setRuangKanan(readRuangan((JSONObject) jsonobj.get("ruang kanan")));
            }
            if (jsonobj.get("ruang kiri")==null){
                ruangan.setRuangKiri(null);
            }else{
                ruangan.setRuangKiri(readRuangan((JSONObject) jsonobj.get("ruang kiri")));
            }
            ruangan.getBarangInRuangan().clear();
            JSONArray baranginruangan = (JSONArray) jsonobj.get("array of barang");
            for (Object i : baranginruangan){
                ruangan.getBarangInRuangan().add((Barang) i);
            }
            return ruangan;
        }   
    }
    public Point readPoint(JSONObject jsonPoint) {
        int x = Integer.parseInt(jsonPoint.get("x").toString());
        int y = Integer.parseInt(jsonPoint.get("y").toString());
        return new Point(x, y);
    }    

    // public Inventory readInventory(Object object){
    //     // gatau euy
    // }

    public ArrayList<Barang> readOnDelivery(JSONArray jsonobj) {
        ArrayList<Barang> onDelivery = new ArrayList<>();
        ArrayList<String> bahanmakanan = new ArrayList<>();
        bahanmakanan.add("Nasi");
        bahanmakanan.add("Ayam");
        bahanmakanan.add("Sapi");
        bahanmakanan.add("Wortel");
        bahanmakanan.add("Kentang");
        bahanmakanan.add("Bayam");
        bahanmakanan.add("Kacang");
        bahanmakanan.add("Susu");
        JSONArray jsonArray = jsonobj;
        for (Object i: jsonArray) {
            String nama = i.toString();
            if (bahanmakanan.contains(nama)){
                BahanMakanan barang = new BahanMakanan(nama);
                onDelivery.add(barang);
            }else{
                NonMakanan barang = new NonMakanan(nama);
                onDelivery.add(barang);
            }   
        }
        return onDelivery;
    }
    
    public Kesejahteraan readKesejahteraan(JSONObject jsonobj) {
        boolean dead = (boolean) jsonobj.get("dead");
        int kesehatan =  Integer.parseInt(jsonobj.get("kesehatan").toString());
        int kekenyangan =  Integer.parseInt(jsonobj.get("kekenyangan").toString());
        int mood =  Integer.parseInt(jsonobj.get("mood").toString());
        return new Kesejahteraan(dead, mood, kesehatan, kekenyangan);
    }
    
    // public Posisi readPosisi(JSONObject jsonobj) {
    //     JSONObject currRumahJSON = (JSONObject) jsonobj.get("currRumah");
    //     Rumah currRumah = readRumah(currRumahJSON);
    //     JSONObject currRuanganJSON = (JSONObject) jsonobj.get("currRuangan");
    //     Ruangan currRuangan = readRuangan(currRuanganJSON);
    //     JSONObject currBarangJSON = (JSONObject) jsonobj.get("currBarang");
    //     NonMakanan currBarang = readNonMakanan(currBarangJSON);
    //     return new Posisi(currRumah, currRuangan, currBarang);
    // }
    
    // public Ruangan readRuangan(JSONObject obj) {
    //     String namaRuangan = obj.optString("nama");
    //     if (namaRuangan == null) {
    //         return null;
    //     }
    
    //     Ruangan ruangan = new Ruangan(namaRuangan);
    
    //     JSONObject ruangAtasObj = obj.optJSONObject("ruang atas");
    //     if (ruangAtasObj != null) {
    //         Ruangan ruangAtas = readRuangan(ruangAtasObj);
    //         ruangan.setRuangAtas(ruangAtas);
    //     }
    
    //     JSONObject ruangBawahObj = obj.optJSONObject("ruang bawah");
    //     if (ruangBawahObj != null) {
    //         Ruangan ruangBawah = readRuangan(ruangBawahObj);
    //         ruangan.setRuangBawah(ruangBawah);
    //     }
    
    //     JSONObject ruangKananObj = obj.optJSONObject("ruang kanan");
    //     if (ruangKananObj != null) {
    //         Ruangan ruangKanan = readRuangan(ruangKananObj);
    //         ruangan.setRuangKanan(ruangKanan);
    //     }
    
    //     JSONObject ruangKiriObj = obj.optJSONObject("ruang kiri");
    //     if (ruangKiriObj != null) {
    //         Ruangan ruangKiri = readRuangan(ruangKiriObj);
    //         ruangan.setRuangKiri(ruangKiri);
    //     }
    
    //     JSONArray arrayOfBarang = obj.optJSONArray("array of barang");
    //     if (arrayOfBarang != null) {
    //         ArrayList<Barang> barangInRuangan = new ArrayList<>();
    //         for (int i = 0; i < arrayOfBarang.length(); i++) {
    //             JSONObject barangObj = arrayOfBarang.optJSONObject(i);
    //             if (barangObj != null) {
    //                 Barang barang = readBarang(barangObj);
    //                 if (barang != null) {
    //                     barangInRuangan.add(barang);
    //                 }
    //             }
    //         }
    //         ruangan.setBarangInRuangan(barangInRuangan);
    //     }
    
    //     JSONArray barangFix = obj.optJSONArray("daftar barang fix");
    //     if (barangFix != null) {
    //         String[] daftarBarangFix = new String[barangFix.length()];
    //         for (int i = 0; i < barangFix.length(); i++) {
    //             daftarBarangFix[i] = barangFix.optString(i);
    //         }
    //         ruangan.setDaftarBarangFix(daftarBarangFix);
    //     }
    
    //     return ruangan;
    // }

    // public NonMakanan readNonMakanan(JSONObject obj) {
    //     NonMakanan nonMakanan = new NonMakanan();
    //     nonMakanan.setNama(obj.getString("nama"));
    //     nonMakanan.setHarga(obj.getInt("harga"));
    //     nonMakanan.setPanjang(obj.getInt("panjang"));
    //     nonMakanan.setLebar(obj.getInt("lebar"));
    //     nonMakanan.setOrientasi(obj.getInt("orientasi"));
    //     nonMakanan.setShippingTime(obj.getInt("shipping time"));
    //     nonMakanan.setPosisi(readPoint(obj.getJSONObject("posisi")));
    //     return nonMakanan;
    // }
    
    // public NonMakanakanan readBarang(JSONObject jsonObject) {
    //     NonMakanan nonMakanan = new NonMakanan();
    //     nonMakanan.setNama(obj.getString("nama"));
    //     nonMakanan.setHarga(obj.getInt("harga"));
    //     nonMakanan.setPanjang(obj.getInt("panjang"));
    //     nonMakanan.setLebar(obj.getInt("lebar"));
    //     nonMakanan.setOrientasi(obj.getInt("orientasi"));
    //     nonMakanan.setShippingTime(obj.getInt("shipping time"));
    //     nonMakanan.setPosisi(readPoint(obj.getJSONObject("posisi")));
    //     return nonMakanan;
    // }

    public static void main(String[] args){
        JSONreader reader = new JSONreader();
        reader.readWorld(World.getInstance(),"saveit.json");
    }
    
}
