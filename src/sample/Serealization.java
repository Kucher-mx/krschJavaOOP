package sample;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serealization {

    public static void serializeNow(File file) {
        if(file != null) {

            try {
                FileWriter fileWriter = null;
                fileWriter = new FileWriter(file);

                fileWriter.write(Integer.toString(Main.microObjectsCT.size()));
                fileWriter.write("\n");
                for( MicroObject r : Main.microObjectsCT ) {
                    r.Save(fileWriter);
                }

                fileWriter.write(Integer.toString(Main.microObjectsT.size()));
                fileWriter.write("\n");
                for( MicroObject r : Main.microObjectsT ) {
                    r.Save(fileWriter);
                }

                fileWriter.write(Integer.toString(Main.sites.length));
                fileWriter.write("\n");
                for( MacroObjSite r : Main.sites ) {
                    r.Save(fileWriter);
                }

                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void deserializeNow(File file) {
        if(file != null) {
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                String text = bufferedReader.readLine();
                System.out.println(text);
                int hmany = Integer.parseInt(text);
                System.out.println(hmany);
//                for (Pilz r : Main.pl) {Main.group.getChildren().remove(r.g); }
//                Pilz.counter =0;
//                Main.pl = new Pilz[hmany];
//                for (int i = 0; i < hmany; i++) {
//                    Main.pl[i] = new Pilz();
//                    Main.pl[i].Open(bufferedReader);
//                    Main.group.getChildren().add(Main.pl[i].g);
//                }
//                //--------------------------------------
//                text = bufferedReader.readLine();
//                hmany = Integer.parseInt(text);
//                for (Mario r : Main.mr) {
//                    Main.groupMar.getChildren().remove(r.getGroup());
//                }
//                Main.mr = new Mario[hmany];
//                int type;
//                for (int i = 0; i < hmany; i++) {
//                    text = bufferedReader.readLine();
//                    type = Integer.parseInt(text);
//
//                    if (type == 1) {
//                        Main.mr[i] = new Mario();
//                        Main.mr[i].Open(bufferedReader);
//                    } else if (type == 2) {
//                        Main.mr[i] = new Drago();
//                        Main.mr[i].Open(bufferedReader);
//                    } else if (type == 3) {
//                        Main.mr[i] = new Princess();
//                        Main.mr[i].Open(bufferedReader);
//                    }
//                    Main.groupMar.getChildren().add(Main.mr[i].getGroup());
//                }
                //--------------------------------------
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void serializeNowOLD(File file) {
        XMLEncoder encoder;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));

            HashMap<String, Object> hashMap = new HashMap<>();
//            hashMap.put("t", Main.microObjectsT);
//            hashMap.put("ct", Main.microObjectsCT);
            hashMap.put("sites",Main.sites);
            encoder.writeObject(hashMap);
            encoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Помилка відкриття файлу");
        }

//        try{
//            FileOutputStream fos = new FileOutputStream(file);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            HashMap<String, Object> hashMap = new HashMap<>();
//            hashMap.put("t", Main.microObjectsT);
//            hashMap.put("ct", Main.microObjectsCT);
//            hashMap.put("sites",Main.sites);
//            oos.writeObject(hashMap);
//            oos.close();
//            fos.close();
//            System.out.printf("Serialized HashMap data is saved in hashmap.ser");
//        }catch(IOException ioe){
//            ioe.printStackTrace();
//        }
    }
    public static void deserializeNowOLD(File file){
        XMLDecoder decoder;
        try {
            decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));

//            for (int i = 0; i<Main.microObjectsCT.size(); i++){
//                MicroObject ship = Main.microObjectsCT.get(i--);
//                Main.microObjectsCT.remove(ship);
//            }
//
//            for (int i = 0; i<Main.microObjectsT.size(); i++){
//                MicroObject ship = Main.microObjectsCT.get(i--);
//                Main.microObjectsCT.remove(ship);
//            }
//
//            for (int i = 0; i<Main.getWorld().getPlanets().size(); i++){
//                Planet planet = Main.getWorld().getPlanets().get(i--);
//                Main.getWorld().deletePlanet(planet);
//            }
            HashMap<String, Object> hashMap = (HashMap<String, Object>)decoder.readObject();
            System.out.println(hashMap.get("sites"));

//            for (MacroObjSite site: (ArrayList<MacroObjSite>)hashMap.get("sites")){
//                System.out.println(site);
////                Main.getWorld().addNewPlanet(planet);
//            }
//            for (MicroObject unit:(ArrayList<MicroObject>)hashMap.get("ct")){
//                System.out.println(unit);
////                if (ship.getSide().equals("Green")){
////                    System.out.println(ship.getSide());
////                    Main.getWorld().addNewShip(ship, false);
////                }
////                if (ship.getSide().equals("Red")){
////                    System.out.println("Ship blue");
////                    switch (ship.getType()){
////                        case "Scout":
////                            Main.getWorld().addNewShip(new Scout(ship.getName().toString(),ship.getIsActive(),5,500,5,200,ship.getSide(),(int)ship.getChordX(),(int)ship.getChordY()),false);
////                            break;
////                        case "Heavy":
////                            Main.getWorld().addNewShip(new Heavy(ship.getName().toString(),ship.getIsActive(),8,1000,8,200,ship.getSide(),(int)ship.getChordX(),(int)ship.getChordY()),false);
////                            break;
////                        case "StarShip":
////                            Main.getWorld().addNewShip(new StarShip(ship.getName().toString(),ship.getIsActive(),10,2000,11,200,ship.getSide(),(int) ship.getChordX(),(int)ship.getChordY()),false);
////                            break;
////                    }
////                }
//
//            }
//            for (MicroObject unit:(ArrayList<MicroObject>)hashMap.get("t")) {
//                System.out.println(unit);
//            }
            decoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Помилка відкриття файлу");
        }
    }
}
