package sample;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
public class MacroObjSite {
    private String name;
    Queue<MicroObject> ct = new LinkedList<>();
    Queue<MicroObject> t = new LinkedList<>();

    Label siteLabel;
    Rectangle rectangle;
    protected Group siteGroup;

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

