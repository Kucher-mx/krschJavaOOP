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
import java.util.*;

public class MacroObjSite implements Serializable {
    public long timeStartedCT;
    public long timeStartedT;
    private String name;
    private String belongs = "none";
    Queue<MicroObject> ct = new LinkedList<>();
    Queue<MicroObject> t = new LinkedList<>();

    protected transient GridPane siteWrapper = new GridPane();
    public transient VBox imgWrap = new VBox();
    protected transient Image getImg;
    public transient ImageView getImgView;
    protected transient Label siteLabel = new Label();
    protected transient Group siteGroup;
    protected transient Image siteImage;
    public transient ImageView siteImageView;

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
        System.out.println("fight");
        String winner = "";
        Iterator tIterator = t.iterator();
        Iterator ctIterator = ct.iterator();
        ArrayList<MicroObject> microsToDelete = new ArrayList<MicroObject>();

        while (tIterator.hasNext() && ctIterator.hasNext()) {
            MicroObject unitT = (MicroObject) tIterator.next();
            MicroObject unitCT = (MicroObject) ctIterator.next();
            System.out.println(unitT + " fights with: " + unitCT);
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

    public void Save( FileWriter fileWriter ) throws IOException
    {
        fileWriter.write( this.getName() );
        fileWriter.write("\n");
        fileWriter.write( this.getBelong() );
        fileWriter.write("\n");
//        fileWriter.write( Double.toString(this.getTranslateX()) );
//        fileWriter.write("\n");
//        fileWriter.write( Double.toString(this.getTranslateY()) );
//        fileWriter.write("\n");
//        fileWriter.write( this.getSide() );
//        fileWriter.write("\n");
//        fileWriter.write( Integer.toString(this.count) );
//        fileWriter.write("\n");
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

