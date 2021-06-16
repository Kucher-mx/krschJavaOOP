package sample;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.*;
//import java.util.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

import static java.util.Arrays.sort;

public class MacroObjSite implements Serializable {
    public long timeStartedCT;
    public long timeStartedT;
    private String name;
    private String belongs = "none";
    Queue<MicroObject> ct = new LinkedList<>();
    Queue<MicroObject> t = new LinkedList<>();

    transient GridPane siteWrapper = new GridPane();
    transient VBox imgWrap = new VBox();
    transient Image getImg;
    transient ImageView getImgView;
    transient Label siteLabel = new Label();
    transient Group siteGroup;
    transient Image siteImage;
    transient ImageView siteImageView;

    public String getName(){
        return this.name;
    }

    public String getBelong(){
        return this.belongs;
    }

    public void setBelong(String newBelong){
        this.belongs = newBelong;
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

    public void getMacro(String side) throws FileNotFoundException {
        if(side.equals("ct")){
            if(timeStartedCT + Main.timeToCapture <= new Date().getTime()){
                this.setBelong("ct");
                this.siteLabel.setText("Site" + this.getName() + ", belongs to: " + belongs);
                siteWrapper.getChildren().remove(getImgView);
                getImg = new Image(new FileInputStream("src/source/ct_got_spot.png"));
                getImgView = new ImageView(getImg);
                getImgView.setPreserveRatio(true);
                getImgView.setFitWidth(90);
                getImgView.setFitHeight(90);
                siteWrapper.add(getImgView, 1, 1);

                Iterator ctIterator = ct.iterator();
                while (ctIterator.hasNext()) {
                    MicroObject unitCT = (MicroObject) ctIterator.next();
                    if(unitCT.getAlive()){
                        Main.minimap.addUnit(unitCT);
                        Main.microObjectsCT.add(unitCT);
                        unitCT.setXCoord(2100 + Main.random.nextInt(100));
                        unitCT.setYCoord(420 + Main.random.nextInt(75));
                        unitCT.microWrapper.setTranslateX(unitCT.getX());
                        unitCT.microWrapper.setTranslateY(unitCT.getY());
                        unitCT.setSpeed(unitCT.defaultSpeed);
                        unitCT.microWrapper.setStyle(" ");
                        unitCT.microLabel.setText("Lvl: " + unitCT.getLvl() + ", hp: "  + unitCT.getHp());
                    }
                    if(unitCT.getActive()){
                        unitCT.changeActive();
                    }
                    if(!Main.group.getChildren().contains(unitCT.microGroup)){
                        Main.group.getChildren().add(unitCT.microGroup);
                    }
                    unitCT.microWrapper.setTranslateX(unitCT.getX());
                    unitCT.microWrapper.setTranslateY(unitCT.getY());
                    unitCT.setSpeed(unitCT.defaultSpeed);
                }
                ct.clear();
                Main.toMacro = false;
                Main.getA = false;
                Main.getB = false;
                Main.minimap.updateSite(this, "ct");
                Main.minimap.updateMap();
                this.timeStartedCT = 0;
            }
        }else if(side.equals("t")){
            if(timeStartedT + Main.timeToCapture <= new Date().getTime()){
                this.setBelong("t");
                this.siteLabel.setText("Site" + this.getName() + ", belongs to: " + belongs);
                siteWrapper.getChildren().remove(getImgView);
                getImg = new Image(new FileInputStream("src/source/t_got_spot.png"));
                getImgView = new ImageView(getImg);
                getImgView.setPreserveRatio(true);
                getImgView.setFitWidth(90);
                getImgView.setFitHeight(90);
                siteWrapper.add(getImgView, 1, 1);

                Iterator tIterator = t.iterator();

                while (tIterator.hasNext()) {
                    MicroObject unitT = (MicroObject) tIterator.next();
                    if(unitT.getAlive()){
                        unitT.microWrapper.setStyle(" ");
                        Main.microObjectsT.add(unitT);
                        unitT.setXCoord(1060.0 + Main.random.nextInt(100));
                        unitT.setYCoord(2570.0 + Main.random.nextInt(50));
                        unitT.microWrapper.setTranslateX(unitT.getX());
                        unitT.microWrapper.setTranslateY(unitT.getY());
                        unitT.setSpeed(unitT.defaultSpeed);
                        Main.minimap.addUnit(unitT);
                        unitT.microLabel.setText("Lvl: " + unitT.getLvl() + ", hp: "  + unitT.getHp());
                    }
                    if(unitT.getActive()){
                        unitT.changeActive();
                    }
                    if(!Main.group.getChildren().contains(unitT.microGroup)){
                        Main.group.getChildren().add(unitT.microGroup);
                    }
                }
                t.clear();
                Main.toMacro = false;
                Main.getA = false;
                Main.getB = false;
                Main.minimap.updateSite(this, "t");
                Main.minimap.updateMap();
                this.timeStartedT = 0;
            }
        }
        Main.timeToCapture = 10000;
    }

    public MacroObjSite(String name) throws FileNotFoundException {
        this.name = name;

        int x, y;
        if(name.equals("a")){
            x = 2820;
            y = 410;
            this.siteWrapper.getRowConstraints().add(new RowConstraints(30));
            this.siteWrapper.getRowConstraints().add(new RowConstraints(200));

            this.siteWrapper.getColumnConstraints().add(new ColumnConstraints(117));
            this.siteWrapper.getColumnConstraints().add(new ColumnConstraints(117));


            siteLabel.setTextFill(Color.WHITE);
            siteLabel.setText("Site A, belongs to: " + belongs);
            siteImage = new Image(new FileInputStream("src/source/a_site.png"));
            siteImageView = new ImageView(siteImage);
            siteWrapper.setTranslateX(x);
            siteWrapper.setTranslateY(y);
            siteImageView.setPreserveRatio(true);
            siteImageView.setFitWidth(90);
            siteImageView.setFitHeight(90);
            siteWrapper.setHalignment(siteLabel, HPos.CENTER);
            siteWrapper.setValignment(siteLabel, VPos.CENTER);
            siteWrapper.setHalignment(imgWrap, HPos.CENTER);
            siteWrapper.setValignment(imgWrap, VPos.CENTER);
            siteWrapper.setGridLinesVisible(true);
            siteWrapper.add(siteLabel, 0, 0, 2, 1);
            siteWrapper.add(siteImageView, 0, 1);
            siteWrapper.setStyle("-fx-background-color: gray");
            siteWrapper.setOpacity(0.8);
        }else{
            x = 600;
            y = 230;
            this.siteWrapper.getRowConstraints().add(new RowConstraints(20));
            this.siteWrapper.getRowConstraints().add(new RowConstraints(280));

            this.siteWrapper.getColumnConstraints().add(new ColumnConstraints(160));
            this.siteWrapper.getColumnConstraints().add(new ColumnConstraints(160));

            siteLabel.setTextFill(Color.WHITE);
            siteLabel.setText("Site B, belongs to: " + belongs);
            siteImage = new Image(new FileInputStream("src/source/b_site.png"));
            siteImageView = new ImageView(siteImage);
            siteImageView.setPreserveRatio(true);
            siteImageView.setFitWidth(90);
            siteImageView.setFitHeight(90);
            siteWrapper.setTranslateX(x);
            siteWrapper.setTranslateY(y);
            siteWrapper.setHalignment(siteLabel, HPos.CENTER);
            siteWrapper.setValignment(siteLabel, VPos.CENTER);
            siteWrapper.setHalignment(imgWrap, HPos.CENTER);
            siteWrapper.setValignment(imgWrap, VPos.CENTER);
            siteWrapper.setGridLinesVisible(true);
            siteWrapper.add(siteLabel, 0, 0, 2, 1);
            siteWrapper.add(siteImageView, 0, 1);
            siteWrapper.setStyle("-fx-background-color: gray");
            siteWrapper.setOpacity(0.8);
        }
        siteLabel.setStyle("-fx-border-style: solid outside;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        this.siteGroup = new Group(siteWrapper);
    }

    public MacroObjSite(String name, String belongsTo, long ctTime, long tTime) throws FileNotFoundException {
        this.name = name;
        this.belongs = belongsTo;

        int x, y;
        if(name.equals("a")){
            x = 2820;
            y = 410;
            this.siteWrapper.getRowConstraints().add(new RowConstraints(30));
            this.siteWrapper.getRowConstraints().add(new RowConstraints(200));

            this.siteWrapper.getColumnConstraints().add(new ColumnConstraints(117));
            this.siteWrapper.getColumnConstraints().add(new ColumnConstraints(117));


            siteLabel.setTextFill(Color.WHITE);
            siteLabel.setText("Site A, belongs to: " + belongs);
            siteImage = new Image(new FileInputStream("src/source/a_site.png"));
            siteImageView = new ImageView(siteImage);
            siteWrapper.setTranslateX(x);
            siteWrapper.setTranslateY(y);
            siteImageView.setPreserveRatio(true);
            siteImageView.setFitWidth(90);
            siteImageView.setFitHeight(90);
            siteWrapper.setHalignment(siteLabel, HPos.CENTER);
            siteWrapper.setValignment(siteLabel, VPos.CENTER);
            siteWrapper.setHalignment(imgWrap, HPos.CENTER);
            siteWrapper.setValignment(imgWrap, VPos.CENTER);
            siteWrapper.setGridLinesVisible(true);
            siteWrapper.add(siteLabel, 0, 0, 2, 1);
            siteWrapper.add(siteImageView, 0, 1);
            siteWrapper.setStyle("-fx-background-color: gray");
            siteWrapper.setOpacity(0.8);
        }else{
            x = 600;
            y = 230;
            this.siteWrapper.getRowConstraints().add(new RowConstraints(20));
            this.siteWrapper.getRowConstraints().add(new RowConstraints(280));

            this.siteWrapper.getColumnConstraints().add(new ColumnConstraints(160));
            this.siteWrapper.getColumnConstraints().add(new ColumnConstraints(160));

            siteLabel.setTextFill(Color.WHITE);
            siteLabel.setText("Site B, belongs to: " + belongs);
            siteImage = new Image(new FileInputStream("src/source/b_site.png"));
            siteImageView = new ImageView(siteImage);
            siteImageView.setPreserveRatio(true);
            siteImageView.setFitWidth(90);
            siteImageView.setFitHeight(90);
            siteWrapper.setTranslateX(x);
            siteWrapper.setTranslateY(y);
            siteWrapper.setHalignment(siteLabel, HPos.CENTER);
            siteWrapper.setValignment(siteLabel, VPos.CENTER);
            siteWrapper.setHalignment(imgWrap, HPos.CENTER);
            siteWrapper.setValignment(imgWrap, VPos.CENTER);
            siteWrapper.setGridLinesVisible(true);
            siteWrapper.add(siteLabel, 0, 0, 2, 1);
            siteWrapper.add(siteImageView, 0, 1);
            siteWrapper.setStyle("-fx-background-color: gray");
            siteWrapper.setOpacity(0.8);
        }

        if(this.getBelong().equals("t")){
            this.siteLabel.setText("Site" + this.getName() + ", belongs to: " + belongs);
            siteWrapper.getChildren().remove(getImgView);
            getImg = new Image(new FileInputStream("src/source/t_got_spot.png"));
            getImgView = new ImageView(getImg);
            getImgView.setPreserveRatio(true);
            getImgView.setFitWidth(90);
            getImgView.setFitHeight(90);
            siteWrapper.add(getImgView, 1, 1);
        }else if(this.getBelong().equals("ct")){
            this.siteLabel.setText("Site" + this.getName() + ", belongs to: " + belongs);
            siteWrapper.getChildren().remove(getImgView);
            getImg = new Image(new FileInputStream("src/source/ct_got_spot.png"));
            getImgView = new ImageView(getImg);
            getImgView.setPreserveRatio(true);
            getImgView.setFitWidth(90);
            getImgView.setFitHeight(90);
            siteWrapper.add(getImgView, 1, 1);
        }

        siteLabel.setStyle("-fx-border-style: solid outside;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        this.siteGroup = new Group(siteWrapper);
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
        System.out.println("Team t:");

        int queueSize = this.t.size();
        int i = 0;
        MicroObject tArray[] = new MicroObject[queueSize];

        Iterator iterator = this.t.iterator();

        while (iterator.hasNext()) {
            tArray[i] = (MicroObject) iterator.next();
            System.out.println(tArray[i]);
            i++;
        }

        if(tArray.length == 0){
            System.out.println("It's nobody there");
            return;
        }

        sort(tArray, MicroObject.lvlComparator);

        Arrays.toString(tArray);
    }

    public void printTeamCT(){
        System.out.println("Team ct:");

        int queueSize = this.ct.size();
        int i = 0;
        MicroObject ctArray[] = new MicroObject[queueSize];

        Iterator iterator = this.ct.iterator();

        while (iterator.hasNext()) {
            ctArray[i] = (MicroObject) iterator.next();
            i++;
        }

        if(ctArray.length == 0){
            System.out.println("It's nobody there");
            return;
        }

        sort(ctArray, MicroObject.lvlComparator);

        for(Object ct : ctArray){
            if(ct instanceof MicroObject){
                System.out.println(ct);
            }
        }
    }
}

