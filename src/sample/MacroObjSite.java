package sample;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class MacroObjSite {
    public long timeStartedCT;
    public long timeStartedT;
    private String name;
    private String belongs = "none";
    Queue<MicroObject> ct = new LinkedList<>();
    Queue<MicroObject> t = new LinkedList<>();

    protected GridPane siteWrapper = new GridPane();
    protected VBox imgWrap = new VBox();
    protected Image getImg;
    protected ImageView getImgView;
    protected Label siteLabel = new Label();
    protected Group siteGroup = new Group();
    protected Image siteImage;
    protected ImageView siteImageView;

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
//            System.out.println(timeStartedCT);
            if(timeStartedCT + 10000 <= new Date().getTime()){
                this.setBelong("ct");
                this.siteLabel.setText("Site" + this.getName() + ", belongs to: " + belongs);

                getImg = new Image(new FileInputStream("src/source/ct_got_spot.png"));
                getImgView = new ImageView(getImg);
                getImgView.setPreserveRatio(true);
                getImgView.setFitWidth(90);
                getImgView.setFitHeight(90);
                siteWrapper.add(getImgView, 1, 1);

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
                this.siteLabel.setText("Site" + this.getName() + ", belongs to: " + belongs);

                getImg = new Image(new FileInputStream("src/source/t_got_spot.png"));
                getImgView = new ImageView(getImg);
                getImgView.setPreserveRatio(true);
                getImgView.setFitWidth(90);
                getImgView.setFitHeight(90);
                siteWrapper.add(getImgView, 1, 1);

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
//            imgWrap.getChildren().add(siteImageView);
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
//            imgWrap.getChildren().add(siteImageView);
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
//        rectangle.setFill(Color.GRAY);
//        rectangle.setOpacity(0.5);
        siteLabel.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
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

