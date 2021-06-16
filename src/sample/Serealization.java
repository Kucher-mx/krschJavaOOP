package sample;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Serealization {
    public static void serializeNow(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            oos.writeObject(Main.microObjectsCT);
            oos.writeObject(Main.microObjectsT);
            oos.writeObject(Main.sites);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deserializeNow(File file) throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            ArrayList<MicroObject> ct = (ArrayList<MicroObject>) ois.readObject();
            ArrayList<MicroObject> t = (ArrayList<MicroObject>) ois.readObject();
            MacroObjSite[] sites = (MacroObjSite[]) ois.readObject();

            Main.microObjectsCT = ct;
            Main.microObjectsT = t;
            Main.sites = sites;

            System.out.println("in deser");
            for (MicroObject ctunit : Main.microObjectsCT){
                System.out.println(ctunit);
            }

            for (MicroObject tunit : Main.microObjectsT){
                System.out.println(tunit);
            }

            for (MacroObjSite site : Main.sites){
                System.out.println(site);
            }

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double  width = screenSize.getWidth();
            double height = screenSize.getHeight();
            try {
                Main.setMainStage(width, height, new ArrayList<String>(), new ArrayList<String>(), true);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
        catch(Exception ex){
            System.out.println("deser err: " + ex.getMessage());
        }
    }
}
