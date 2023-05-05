package simplicity;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import netscape.javascript.JSObject;

public class JSONWriter {

    public void writeWorld(World world, String namafile){
        JSONObject world1 = new JSONObject();
        world1.put("panjang",world.getPanjang());
        world1.put("lebar",world.getLebar());
        world1.put("Hari",world.getHari());
        world1.put("waktu",world.getWaktu());
        JSONArray arrsim = new JSONArray();
        for (int i=0;i<world.getArrSim().size();i++){
            arrsim.add(writeSim(world.getArrSim().get(i)));
        }

        world1.put("ArrSim",arrsim);
        try (
            FileWriter file = new FileWriter(String.format(namafile))) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(world1.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }

    public JSONObject writeSim(Sim sim){
        JSONObject sim1 = new JSONObject();
        sim1.put("nama lengkap",sim.getName());
        sim1.put("pekerjaan",writePekerjaan(sim.getPekerjaan()));
        sim1.put("rumah",writeRumah(sim.getRumah()));
        sim1.put("inventory",writeInventory(sim.getInventory()));
        JSONArray arrondev = new JSONArray();
        if (sim.getOnDelivery()!=null){
            for (int i=0;i<sim.getOnDelivery().size();i++){
                arrondev.add(writeBarang(sim.getOnDelivery().get(i)));
            }
        }
        sim1.put("on delivery",arrondev);
        sim1.put("kesejahteraan",writeKesejahteraan(sim.getKesejahteraan()));
        sim1.put("status",(sim.getStatus()));
        sim1.put("posisi",writePosisi(sim.getPosisi()));
        sim1.put("uang",sim.getUang());
        sim1.put("waktu makan awal",sim.getWaktuMakanAwal());
        sim1.put("waktu tidur awal",sim.getWaktuTidurAwal());
        sim1.put("waktu kerja",sim.getWaktuKerja());
        sim1.put("hari resign",sim.getHariResign());
        sim1.put("sudah buang air",sim.getSudahBuangAir());
        sim1.put("makan pertama",sim.getMakanPertama());
        return sim1;
    }


    public JSONObject writePekerjaan(Pekerjaan pekerjaan){
        JSONObject pekerjaan1 = new JSONObject();
        pekerjaan1.put("nama",pekerjaan.getNamaPekerjaan());
        pekerjaan1.put("gaji harian",pekerjaan.getGajiHarian());
        return pekerjaan1;
    }
    
    public JSONObject writeRumah(Rumah rumah){
        JSONObject rumah1 = new JSONObject();
        rumah1.put("lokasi",writePoint(rumah.getLokasi()));
        JSONArray arrOfRuangan = new JSONArray();
        for (int i=0;i<rumah.getArrayOfRuangan().size();i++){
            arrOfRuangan.add(writeRuangan(rumah.getArrayOfRuangan().get(i)));
        }
        rumah1.put("array of ruangan",arrOfRuangan);
        return rumah1;
    }

    public JSONObject writePoint(Point point){
        JSONObject point1 = new JSONObject();
        point1.put("x",point.getX());
        point1.put("y",point.getY());
        return point1;
    }

    public JSONObject writeInventory(HashMap<String,Integer> inventory){
        ArrayList<JSONObject> inventoryHashMap = new ArrayList<>();
        JSONObject obj = new JSONObject();
        JSONObject inventory2 = new JSONObject();
        for (Map.Entry i :inventory.entrySet()){
            obj.put(i.getKey(),i.getValue());
            inventoryHashMap.add(obj);
        }
        inventory2.put("inventory",inventoryHashMap);
        return inventory2;
    }

    public JSONObject writeOnDelivery(ArrayList<Barang> ondelivery){
        JSONObject ondelivery1 = new JSONObject();
        JSONArray arrOfBarang = new JSONArray();
        for (int i=0;i<ondelivery.size();i++){
            arrOfBarang.add(writeBarang(ondelivery.get(i)));
        }
        ondelivery1.put("on delivery",arrOfBarang);
        return ondelivery1;
    }

    public JSONObject writeBarang(Barang barang){
        JSONObject barang1 = new JSONObject();
        barang1.put("nama",barang.getNama());
        return barang1;
    }


    public JSONObject writeKesejahteraan(Kesejahteraan kesejahteraan){
        JSONObject kesejahteraan1 = new JSONObject();
        kesejahteraan1.put("dead",kesejahteraan.isDead());
        kesejahteraan1.put("kesehatan",kesejahteraan.getKesehatan());
        kesejahteraan1.put("kekenyangan",kesejahteraan.getKekenyangan());
        kesejahteraan1.put("mood",kesejahteraan.getMood());
        return kesejahteraan1;
    }

    public JSONObject writePosisi(Posisi posisi){
        JSONObject posisi1 = new JSONObject();
        World world = World.getInstance();
        posisi1.put("currRumah",world.getSimOwnRumah(posisi.getCurrRumah()).getName());
        posisi1.put("currRuangan",(posisi.getCurrRuangan().getNamaRuangan()));
        return posisi1;
    }

    public JSONObject writeRuangan(Ruangan ruangan){
        JSONObject ruangan1 = new JSONObject();
        if (ruangan!=null){
            ruangan1.put("nama",(ruangan.getNamaRuangan()));
            ruangan1.put("ruang atas",writeRuangan(ruangan.getRuangAtas()));
            ruangan1.put("ruang bawah",writeRuangan(ruangan.getRuangBawah()));
            ruangan1.put("ruang kanan",writeRuangan(ruangan.getRuangKanan()));
            ruangan1.put("ruang kiri",writeRuangan(ruangan.getRuangKiri()));
            JSONArray arrayOfBarang = new JSONArray();
            JSONArray barangFix = new JSONArray();
            for (Barang i : ruangan.getBarangInRuangan()){
                arrayOfBarang.add(writeNonMakanan((NonMakanan) i));
            }
            for (int i=0;i<ruangan.getDaftarBarangFix().length-1;i++){
                barangFix.add(ruangan.getDaftarBarangFix()[i]);
            }
            ruangan1.put("array of barang",arrayOfBarang);
            ruangan1.put("daftar barang fix",barangFix);
            ruangan1.put("waktu selesai",ruangan.getWaktuSelesai());
        }else{
            ruangan1.put("nama","null");
        }
        return ruangan1;
    }
    
    public JSONObject writeNonMakanan(NonMakanan nonMakanan){
        JSONObject nonMakanan1 = new JSONObject();
        if (nonMakanan!=null){
            nonMakanan1.put("nama",nonMakanan.getNama());
            nonMakanan1.put("harga",nonMakanan.getHarga());
            nonMakanan1.put("panjang",nonMakanan.getPanjang());
            nonMakanan1.put("lebar",nonMakanan.getLebar());
            nonMakanan1.put("orientasi",nonMakanan.getOrientasi());
            nonMakanan1.put("shipping time",nonMakanan.getShippingTime());
            nonMakanan1.put("posisi",writePoint(nonMakanan.getPosisi()));
            nonMakanan1.put("shipping time",nonMakanan.getWaktuSelesai()-World.getInstance().getHari()*720+World.getInstance().getWaktu());
            nonMakanan1.put("waktu selesai",nonMakanan.getWaktuSelesai());
        }else{
            nonMakanan1.put("nama","null");
        }     
        return nonMakanan1;
    }

    public JSONObject writeBarang(NonMakanan nonMakanan){
        JSONObject nonMakanan1 = new JSONObject();
        if (nonMakanan!=null){
            nonMakanan1.put("nama",nonMakanan.getNama());
            nonMakanan1.put("harga",nonMakanan.getHarga());
            nonMakanan1.put("panjang",nonMakanan.getPanjang());
            nonMakanan1.put("lebar",nonMakanan.getLebar());
            nonMakanan1.put("orientasi",nonMakanan.getOrientasi());
            nonMakanan1.put("shipping time",nonMakanan.getShippingTime());
            nonMakanan1.put("posisi",writePoint(nonMakanan.getPosisi()));
            nonMakanan1.put("orientasi",nonMakanan.getOrientasi());
            nonMakanan1.put("shipping time",nonMakanan.getWaktuSelesai()-World.getInstance().getHari()*720+World.getInstance().getWaktu());
            nonMakanan1.put("waktu selesai",nonMakanan.getWaktuSelesai());
        }else{
            nonMakanan1.put("nama","null");
        }     
        return nonMakanan1;
    }

    public JSONObject writeBarang(BahanMakanan bahanMakanan){
        JSONObject bahanMakanan1 = new JSONObject();
        if (bahanMakanan!=null){
            bahanMakanan1.put("nama",bahanMakanan.getNama());
            bahanMakanan1.put("harga",bahanMakanan.getHargaBahan());
            bahanMakanan1.put("kekenyangan",bahanMakanan.getKekenyangan());
            bahanMakanan1.put("shipping time",bahanMakanan.getWaktuSelesai()-World.getInstance().getHari()*720+World.getInstance().getWaktu());
            bahanMakanan1.put("waktu selesai",bahanMakanan.getWaktuSelesai());
        }else{
            bahanMakanan1.put("nama","null");
        }     
        return bahanMakanan1;
    }
}

