package simplicity;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;


public class JSONreader {

    public void readWorld(World world, String namafile) {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        try (
            FileReader reader = new FileReader(namafile)) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonobj = (JSONObject) obj;
            ArrayList<Object> daftarPosisi = new ArrayList<>();
            String hari = jsonobj.get("Hari").toString();
            world.setHari(Integer.parseInt(hari));
            String waktu = jsonobj.get("waktu").toString();
            world.setWaktu(Integer.parseInt(waktu));
            JSONArray arrsim = (JSONArray) jsonobj.get("ArrSim");
            for (Object i : arrsim) {
                world.getArrSim().add(readSim((JSONObject) i,daftarPosisi));
            }
            for (Sim i: world.getArrSim()){
                String rumah="";
                String ruangan="";
                for (Object m: daftarPosisi){
                    JSONObject n = (JSONObject)m;
                    if (i.getName().equals(n.get("nama").toString())){
                        JSONObject posisi = (JSONObject)n.get("posisi");
                        rumah = posisi.get("currRumah").toString();
                        ruangan = posisi.get("currRuangan").toString();
                    }
                }
                
                
                for (Sim j:world.getArrSim()){
                    if (j.getName().equals(rumah)){
                        i.getPosisi().setCurrRumah(j.getRumah());
                        for (Ruangan k: i.getPosisi().getCurrRumah().getArrayOfRuangan()){
                            if (k.getNamaRuangan().equals(ruangan)){
                                i.getPosisi().setCurrRuangan(k);
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Sim readSim(JSONObject jsonobj,ArrayList<Object> daftarPosisi) {
        Sim sim1 = new Sim(jsonobj.get("nama lengkap").toString());
        sim1.setInventory(readInventory((JSONObject)jsonobj.get("inventory")));
        sim1.setKesejahteraan(readKesejahteraan((JSONObject)jsonobj.get("kesejahteraan")));
        sim1.setOnDelivery(readOnDelivery((JSONArray)jsonobj.get("on delivery")));
        sim1.setPekerjaan(readPekerjaan((JSONObject)jsonobj.get("pekerjaan")));
        JSONObject posisi = new JSONObject();
        posisi.put("nama",jsonobj.get("nama lengkap").toString());
        posisi.put("posisi",jsonobj.get("posisi"));
        daftarPosisi.add((Object) posisi);
        // sim1.setPosisi(new Posisi(jsonobj.get("posisi"),null,null));
        sim1.setRumah(readRumah((JSONObject) jsonobj.get("rumah")));
        sim1.setStatus((String)jsonobj.get("status"));
        sim1.setUang(Integer.parseInt(jsonobj.get("uang").toString()));
        sim1.setWaktuMakanAwal(Integer.parseInt(jsonobj.get("waktu makan awal").toString()));
        sim1.setWaktuTidurAwal(Integer.parseInt(jsonobj.get("waktu tidur awal").toString()));
        sim1.setWaktuKerja(Integer.parseInt(jsonobj.get("waktu kerja").toString()));
        sim1.setHariResign(Integer.parseInt(jsonobj.get("hari resign").toString()));
        sim1.setSudahBuangAir((boolean)jsonobj.get("sudah buang air"));
        sim1.setMakanPertama((boolean)jsonobj.get("makan pertama"));
        return sim1;
    }

    public Pekerjaan readPekerjaan(JSONObject jsonobj){
         Pekerjaan pekerjaan1 = new Pekerjaan(jsonobj.get("nama").toString(),Integer.parseInt(jsonobj.get("gaji harian").toString()));
         return pekerjaan1;
    }

    public Rumah readRumah(JSONObject jsonobj) {
        Rumah rumah = new Rumah(readPoint((JSONObject) jsonobj.get("lokasi")));
        JSONArray array = (JSONArray) jsonobj.get("array of ruangan");
        for (Object i : array){
            rumah.getArrayOfRuangan().add(readRuangan((JSONObject)i));
        }
        return rumah;
    }

    // public Ruangan readRuangan(JSONObject jsonobj){
    //     System.out.println(1);
    //     if (jsonobj==null){
    //         System.out.println(2);
    //         return null;
    //     }else{
    //         System.out.println(3);
    //         Ruangan ruangan = new Ruangan(jsonobj.get("nama").toString());
    //         if (jsonobj.get("ruang atas")==null){
    //             ruangan.setRuangAtas(null);
    //         }else{
    //             ruangan.setRuangAtas(readRuangan((JSONObject) jsonobj.get("ruang atas")));
    //         }
    //         if (jsonobj.get("ruang bawah")==null){
    //             ruangan.setRuangBawah(null);
    //         }else{
    //             ruangan.setRuangBawah(readRuangan((JSONObject) jsonobj.get("ruang bawah")));
    //         }
    //         if (jsonobj.get("ruang kanan")==null){
    //             ruangan.setRuangKanan(null);
    //         }else{
    //             ruangan.setRuangKanan(readRuangan((JSONObject) jsonobj.get("ruang kanan")));
    //         }
    //         if (jsonobj.get("ruang kiri")==null){
    //             ruangan.setRuangKiri(null);
    //         }else{
    //             ruangan.setRuangKiri(readRuangan((JSONObject) jsonobj.get("ruang kiri")));
    //         }
    //         ruangan.getBarangInRuangan().clear();
    //         JSONArray baranginruangan = (JSONArray) jsonobj.get("array of barang");
    //         for (Object i : baranginruangan){
    //             ruangan.getBarangInRuangan().add((Barang) i);
    //         }
    //         return ruangan;
    //     }   
    // }
    public Point readPoint(JSONObject jsonPoint) {
        int x = Integer.parseInt(jsonPoint.get("x").toString());
        int y = Integer.parseInt(jsonPoint.get("y").toString());
        return new Point(x, y);
    }    

    public HashMap<String,Integer> readInventory(JSONObject object){
        // JSONArray inven = (JSONArray) object.get("inventory");
        HashMap<String,Integer> inventory = new HashMap<>();
        // inventory.clear();
        // Set<String> kset = inven.keySet();
        String inventor = object.get("inventory").toString().substring(2,object.get("inventory").toString().length()-2); 
        String[] inven = inventor.split(",");
        String[] spliter;
        for (String i: inven){
            spliter = i.split(":");
            inventory.put(spliter[0].substring(1, spliter[0].length()-1),Integer.parseInt(spliter[1]));
        }
        return inventory;
    }

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
            String nama = ((JSONObject)i).get("nama").toString();
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

    public Object readPosisi(Sim sim,JSONObject jsonobj) {
        JSONObject posisi = new JSONObject();
        posisi.put("nama",sim.getName());
        posisi.put("posisi",jsonobj);
        // posisi.put("")
        return (Object) posisi;
    }
    
    public Ruangan readRuangan(JSONObject obj) {
        String namaRuangan = obj.get("nama").toString();
        if (namaRuangan.equals("null")) {
            return null;
        }
    
        Ruangan ruangan = new Ruangan(namaRuangan);
    
        JSONObject ruangAtasObj = (JSONObject) obj.get("ruang atas");
        if (!ruangAtasObj.equals(null)) {
            Ruangan ruangAtas = readRuangan(ruangAtasObj);
            ruangan.setRuangAtas(ruangAtas);
        }
    
        JSONObject ruangBawahObj = (JSONObject) obj.get("ruang bawah");
        if (!ruangBawahObj.equals("null")) {
            Ruangan ruangBawah = readRuangan(ruangBawahObj);
            ruangan.setRuangBawah(ruangBawah);
        }
    
        JSONObject ruangKananObj = (JSONObject) obj.get("ruang kanan");
        if (!ruangKananObj.equals("null")) {
            Ruangan ruangKanan = readRuangan(ruangKananObj);
            ruangan.setRuangKanan(ruangKanan);
        }
    
        JSONObject ruangKiriObj = (JSONObject) obj.get("ruang kiri");
        if (!ruangKiriObj.equals("null")) {
            Ruangan ruangKiri = readRuangan(ruangKiriObj);
            ruangan.setRuangKiri(ruangKiri);
        }
    
        JSONArray arrayOfBarang = (JSONArray) obj.get("array of barang");
        ruangan.getBarangInRuangan().clear();
        if (arrayOfBarang != null) {
            for (Object i : arrayOfBarang) {
                JSONObject barangObj = (JSONObject) i;
                if (barangObj != null) {
                    Barang barang = readBarang(barangObj);
                    if (barang != null) {
                        ruangan.addBarangInRuangan(barang);
                }
            }
            }
            
        }
        ruangan.setWaktuSelesai(Integer.parseInt(obj.get("waktu selesai").toString()));
    
        return ruangan;
    }

    public NonMakanan readNonMakanan(JSONObject obj) {
        String nama = obj.get("nama").toString();
        if (!nama.equals("null")){
            NonMakanan nonMakanan = new NonMakanan(nama);
            nonMakanan.setOrientasi(Integer.parseInt(obj.get("orientasi").toString()));
            nonMakanan.setShippingTime(Integer.parseInt(obj.get("shipping time").toString()));
            nonMakanan.setPosisi(readPoint((JSONObject) obj.get("posisi")));
            nonMakanan.setWaktuSelesai(Integer.parseInt(obj.get("waktu selesai").toString()));
            return nonMakanan;
        }else{
            return null;
        }
    }
    
    public NonMakanan readBarang(JSONObject obj) {
        String nama = obj.get("nama").toString();
        if (!nama.equals("null")){
            NonMakanan nonMakanan = new NonMakanan(nama);
            nonMakanan.setOrientasi(Integer.parseInt(obj.get("orientasi").toString()));
            nonMakanan.setShippingTime(Integer.parseInt(obj.get("shipping time").toString()));
            nonMakanan.setPosisi(readPoint((JSONObject) obj.get("posisi")));
            nonMakanan.setWaktuSelesai(Integer.parseInt(obj.get("waktu selesai").toString()));
            return nonMakanan;
        }else{
            return null;
        }
    }
}
