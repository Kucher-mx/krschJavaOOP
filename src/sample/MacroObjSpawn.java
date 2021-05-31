package sample;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class MacroObjSpawn {
    private String name;
    Queue ct = new LinkedList();
    Queue t = new LinkedList();

    protected Group spawnGroup;
    protected GridPane spawnWrapper = new GridPane();
    protected Label spawnLabel = new Label();
    protected Image spawnImage;
    protected ImageView spawnImageView;

    public String getName(){
        System.out.println("return " + this.name);
        return this.name;
    }

    public MacroObjSpawn(String name) throws FileNotFoundException {
        this.name = name;
        double x, y;
        if(name.equals("ct")){
            x = 2080.0;
            y = 445.0;
            this.spawnWrapper.getRowConstraints().add(new RowConstraints(30));
            this.spawnWrapper.getRowConstraints().add(new RowConstraints(285));
            this.spawnWrapper.getColumnConstraints().add(new ColumnConstraints(360));

            spawnLabel.setTextFill(Color.WHITE);
            spawnLabel.setText("CT spawn");
            spawnImage = new Image(new FileInputStream("src/source/ct_got_spot.png"));
        }else{
            x = 1060.0;
            y = 2570.0;
            this.spawnWrapper.getRowConstraints().add(new RowConstraints(35));
            this.spawnWrapper.getRowConstraints().add(new RowConstraints(200));
            this.spawnWrapper.getColumnConstraints().add(new ColumnConstraints(720));

            spawnLabel.setTextFill(Color.WHITE);
            spawnLabel.setText("T spawn");
            spawnImage = new Image(new FileInputStream("src/source/t_got_spot.png"));
        }
        spawnImageView = new ImageView(spawnImage);
        spawnWrapper.setTranslateX(x);
        spawnWrapper.setTranslateY(y);
        spawnImageView.setPreserveRatio(true);
        spawnImageView.setFitWidth(90);
        spawnImageView.setFitHeight(90);
        spawnWrapper.setHalignment(spawnLabel, HPos.CENTER);
        spawnWrapper.setValignment(spawnLabel, VPos.CENTER);
        spawnWrapper.setHalignment(spawnImageView, HPos.CENTER);
        spawnWrapper.setValignment(spawnImageView, VPos.CENTER);
        spawnWrapper.setGridLinesVisible(true);
        spawnWrapper.add(spawnLabel, 0, 0);
        spawnWrapper.add(spawnImageView, 0, 1);
        spawnWrapper.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        spawnWrapper.setOpacity(0.8);

        spawnWrapper.setStyle("-fx-border-style: solid outside;" +
                "-fx-border-width: 3;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");
        spawnLabel.setStyle("-fx-border-style: solid outside;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        spawnLabel.setTextFill(Color.BLACK);
        this.spawnGroup = new Group(spawnWrapper);
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

