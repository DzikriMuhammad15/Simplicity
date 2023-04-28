import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import netscape.javascript.JSObject;

public class JSONreader {
    
    public void readWorld(World world,String namafile){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("saveit.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonobj = (JSONObject) obj;
            String hari = jsonobj.get("Hari").toString();
            world.setHari(Integer.parseInt(hari));
            String waktu = jsonobj.get("waktu").toString();
            world.setWaktu(Integer.parseInt(waktu));
            
            // world.getLock() = (Object) jsonobj.get("lock");
            JSONArray arrsim = (JSONArray) jsonobj.get("ArrSim");
            for (Object i:arrsim){
                world.getArrSim().add((Sim) readSim((JSONObject)i));
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Sim readSim(JSONObject jsonobj){
        Sim sim1 = new Sim((String) jsonobj.get("nama"));
        sim1.setInventory(readInventory(jsonobj.get("inventory")));
        sim1.setKesejahteraan(readKesejahteraan(jsonobj.get("kesejahteraan")));
        sim1.setOnDelivery(readOnDelivery(jsonobj.get("on delivery")));
        sim1.setPekerjaan(readPekerjaan(jsonobj.get("pekerjaan")));
        sim1.setPosisi(readPosisi(jsonobj.get("posisi")));
        sim1.setRumah(readRumah(jsonobj.get("rumah")));
        sim1.setStatus(readStatus(jsonobj.get("status")));
        sim1.setUang((int)jsonobj.get("uang"));
        return sim1;
    }

    public Posisi readPosisi(JSONObject jsonobj){
        Posisi posisi1 = new Posisi(readRumah(jsonobj.get("rumah")), readRuangan(jsonobj.get("ruangan")), readBarang(jsonobj.get("barang")));
    }
    public static void main(String[] args) {
        JSONreader reader = new JSONreader();
        reader.readWorld(World.getInstance(),"asu");
    }
}

