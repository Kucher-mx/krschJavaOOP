package sample;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.*;

public class MacroObjSite {
    public long timeStartedCT;
    public long timeStartedT;
    private String name;
    private String belongs = "none";
    Queue<MicroObject> ct = new LinkedList<>();
    Queue<MicroObject> t = new LinkedList<>();

    Label siteLabel;
    Rectangle rectangle;
    protected Group siteGroup;

    public String getName(){
        return this.name;
    }

    public String getBelong(){
        return this.belongs;
    }

    public void setBelong(String newBelong){
        this.belongs = newBelong;
    }

    public MicroObject get(Queue<MicroObject> obj, int idx){
        MicroObject element = null;
        int size = obj.size();
        for (int i = 0; i <= size; i++) {
            if (i == idx) {
                element = obj.remove();
            } else {
                obj.add(obj.remove());
            }
        }
        return element;
    }

    public String fightToGet(){
        String winner = "";
        Iterator tIterator = t.iterator();
        Iterator ctIterator = ct.iterator();
        ArrayList<MicroObject> microsToDelete = new ArrayList<MicroObject>();

        while (tIterator.hasNext() && ctIterator.hasNext()) {
            MicroObject unitT = (MicroObject) tIterator.next();
            MicroObject unitCT = (MicroObject) ctIterator.next();
            unitCT.interactInMacro(unitT);

            if(!unitT.getAlive()){
                microsToDelete.add(unitT);
            }else if(!unitCT.getAlive()){
                microsToDelete.add(unitCT);
            }
        }

        if(ct.size() > 0){
            winner = "ct";
        }else{
            winner = "t";
        }

        for(MicroObject micro : microsToDelete){
            if(micro.getSide().equals("t")){
                this.removeT(micro);
            }else{
                this.removeCt(micro);
            }
        }
        return winner;
    }

    public void getMacro(String side){
        if(side.equals("ct")){
//            System.out.println(timeStartedCT);
            if(timeStartedCT + 10000 <= new Date().getTime()){
                this.setBelong("ct");
                System.out.println("ct got site: " + getName());
//                rectangle.setFill(Color.BLUE);
//                rectangle.setOpacity(0.7);
                Iterator ctIterator = ct.iterator();
                while (ctIterator.hasNext()) {
                    MicroObject unitCT = (MicroObject) ctIterator.next();
                    unitCT.setXCoord(0);
                    unitCT.setYCoord(0);
                    unitCT.microWrapper.setStyle(" ");
                    unitCT.changeActive();
                    Main.microObjectsCT.add(unitCT);
                    Main.group.getChildren().add(unitCT.microGroup);
                }
                ct.remove(0);
                this.timeStartedCT = 0;
            }
        }else if(side.equals("t")){
//            System.out.println(timeStartedT);
            if(timeStartedT + 10000 <= new Date().getTime()){
                this.setBelong("t");
                System.out.println("t got site: " + getName());
//                rectangle.setFill(Color.RED);
//                rectangle.setOpacity(0.7);
                Iterator tIterator = t.iterator();

                while (tIterator.hasNext()) {
                    MicroObject unitT = (MicroObject) tIterator.next();
                    unitT.microWrapper.setStyle(" ");
                    unitT.changeActive();
                    unitT.setXCoord(0);
                    unitT.setYCoord(0);
                    System.out.println(unitT);
                    Main.microObjectsT.add(unitT);
                    Main.group.getChildren().add(unitT.microGroup);
                }
                t.remove(0);
                this.timeStartedT = 0;
            }
        }
    }

    public MacroObjSite(String name) {
        this.name = name;

        int x, y;
        if(name.equals("a")){
            x = 2820;
            y = 410;

            rectangle = new Rectangle(x, y, 240, 235);
            siteLabel = new Label("A site");
            siteLabel.setTranslateX(x);
            siteLabel.setTranslateY(y);
        }else{
            x = 600;
            y = 230;
            rectangle = new Rectangle(x, y, 320, 300);
            siteLabel = new Label("B site");
            siteLabel.setTranslateX(x);
            siteLabel.setTranslateY(y);
        }
        rectangle.setFill(Color.GRAY);
        rectangle.setOpacity(0.5);
        siteLabel.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        this.siteGroup = new Group(rectangle, siteLabel);
    }

    public void addCt(MicroObject obj) {
        ct.add(obj);
    }

    public void addT(MicroObject obj) {
        t.add(obj);
    }

    public void removeCt(MicroObject obj) {
        ct.remove(obj);
    }

    public void removeT(MicroObject obj) {
        t.remove(obj);
    }

    public void printTeamT(){

        Iterator iterator = this.t.iterator();
        if(t.peek() == null){
//            System.out.println("It's nobody there");
        }else{
            System.out.println("T side members: ");
        }

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + "\n");
        }
    }

    public void printTeamCT(){
        Iterator iterator = this.ct.iterator();
        if(ct.peek() == null){
//            System.out.println("It's nobody there");
        }else{
            System.out.println("CT side members: ");
        }

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + "\n");
        }
    }
}

