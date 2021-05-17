package sample;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class MacroObjSpawn {
    private String name;
    Label spawnLabel;
    Rectangle rectangle;
    Queue ct = new LinkedList();
    Queue t = new LinkedList();

    protected Group spawnGroup;

    public MacroObjSpawn(String name){
        this.name = name;
        double x, y;
        if(name.equals("ct")){
            x = 2100.0;
            y = 420.0;
            rectangle = new Rectangle(x, y, 320, 350);
            spawnLabel = new Label("CT spawn");
            spawnLabel.setTranslateX(x);
            spawnLabel.setTranslateY(y);
        }else{
            x = 1030.0;
            y = 2600.0;
            rectangle = new Rectangle(x, y, 750, 200);
            spawnLabel = new Label("T spawn");
            spawnLabel.setTranslateX(x);
            spawnLabel.setTranslateY(y);
        }
        rectangle.setFill(Color.YELLOWGREEN);
        rectangle.setOpacity(0.5);
        spawnLabel.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        this.spawnGroup = new Group(rectangle, spawnLabel);
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
            System.out.println("It's nobody there");
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
            System.out.println("It's nobody there");
        }else{
            System.out.println("CT side members: ");
        }

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + "\n");
        }
    }
}

