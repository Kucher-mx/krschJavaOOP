package sample;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class MiniMap {
    final static private double SCALE = 0.1;
    private final HashMap<MicroObject, ImageView> micros;
    private final HashMap<MacroObjSite, Group> sites;
    private final HashMap<MacroObjSpawn, Group> spawns;

    public Group getPane() {
        return Main.miniMapGroup;
    }

    public MiniMap() throws FileNotFoundException {
        Main.miniMapView = new ImageView(new Image(new FileInputStream("src/source/wallpaper.png")));
        Main.miniMapBoxView.setX(0);
        Main.miniMapBoxView.setY(0);
        Main.miniMapBoxView.setHeight(5);
        Main.miniMapBoxView.setWidth(5);
        Main.miniMapBoxView.setFill(Color.WHITE);
        Main.miniMapBoxView.setStroke(Color.NAVAJOWHITE);
        Main.miniMapView.setFitWidth(370);
        Main.miniMapView.setFitHeight(300);

        Main.miniMapGroup.getChildren().add(Main.miniMapView);
        Main.miniMapGroup.getChildren().add(Main.miniMapBoxView);
        Main.miniMapBoxView.toFront();

        micros = new HashMap<>();
        sites = new HashMap<>();
        spawns = new HashMap<>();

        for (MicroObject unit : Main.microObjectsCT) {
            System.out.println(unit);
            addUnit(unit);
        }

        for (MicroObject unit : Main.microObjectsT) {
            addUnit(unit);
        }

        for (MacroObjSite site : Main.sites) {
            addSite(site);
        }

        for (MacroObjSpawn spawn : Main.spawns) {
            addSpawn(spawn);
        }

        Main.miniMapGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();

                Main.miniMapBoxView.setX(x);
                Main.miniMapBoxView.setY(y);

                double posX = ( (x) / (3700*0.1) * 3700);
                double posY = ( (y) / (3000*0.1) * 3000);
                Main.scrollPane.setVvalue(posY / 2700);
                Main.scrollPane.setHvalue(posX / 3200);
            }
        });
    }

    public void addUnit(MicroObject unit) throws FileNotFoundException {
        ImageView imageView;

        switch (unit.getLvl()) {
            case 1:
                if (unit.getSide().equals("t")){
                    imageView = new ImageView(new Image(new FileInputStream("src/source/t_1.png")));
                }else
                    imageView = new ImageView(new Image(new FileInputStream("src/source/ct_1.png")));
                break;
            case 2:
                if (unit.getSide().equals("t")){
                    imageView = new ImageView(new Image(new FileInputStream("src/source/t_2.png")));
                }else
                    imageView = new ImageView(new Image(new FileInputStream("src/source/ct_2.png")));
                break;
            default:
                if (unit.getSide().equals("t")){
                    imageView = new ImageView(new Image(new FileInputStream("src/source/t_3.png")));
                }else
                    imageView = new ImageView(new Image(new FileInputStream("src/source/ct_3.png")));
                break;
        }
        imageView.setLayoutX(unit.getX() * SCALE);
        imageView.setLayoutY(unit.getY() * SCALE);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(190 * SCALE);
        micros.put(unit, imageView);
        Main.miniMapGroup.getChildren().add(imageView);
    }

    public void deleteUnit(MicroObject unit){
        Main.miniMapGroup.getChildren().remove(micros.get(unit));
    }

    public void addSite(MacroObjSite site) throws FileNotFoundException {
        ImageView imageView;
        GridPane siteWrapper = new GridPane();
        VBox imgWrap = new VBox();
        Group group;
        switch (site.getName()){
            case "a":
                imageView = new ImageView(new Image(new FileInputStream("src/source/a_site.png")));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(150 * MiniMap.SCALE);

                siteWrapper.getRowConstraints().add(new RowConstraints(30 * MiniMap.SCALE));
                siteWrapper.getRowConstraints().add(new RowConstraints(200 * MiniMap.SCALE));

                siteWrapper.getColumnConstraints().add(new ColumnConstraints(117 * MiniMap.SCALE));
                siteWrapper.getColumnConstraints().add(new ColumnConstraints(117 * MiniMap.SCALE));
                siteWrapper.setLayoutX(2820 * MiniMap.SCALE);
                siteWrapper.setLayoutY(410 * MiniMap.SCALE);
                break;
            case "b":
                imageView = new ImageView(new Image(new FileInputStream("src/source/b_site.png")));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(150 * MiniMap.SCALE);

                siteWrapper.getRowConstraints().add(new RowConstraints(20 * MiniMap.SCALE));
                siteWrapper.getRowConstraints().add(new RowConstraints(280 * MiniMap.SCALE));

                siteWrapper.getColumnConstraints().add(new ColumnConstraints(160 * MiniMap.SCALE));
                siteWrapper.getColumnConstraints().add(new ColumnConstraints(160 * MiniMap.SCALE));
                siteWrapper.setLayoutX(600 * MiniMap.SCALE);
                siteWrapper.setLayoutY(230 * MiniMap.SCALE);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + site.getName());
        }

        siteWrapper.setHalignment(imgWrap, HPos.CENTER);
        siteWrapper.setValignment(imgWrap, VPos.CENTER);
        siteWrapper.setGridLinesVisible(true);
        siteWrapper.add(imageView, 0, 1);
        siteWrapper.setStyle("-fx-background-color: gray");
        siteWrapper.setOpacity(0.8);

        group = new Group(siteWrapper);
        group.setStyle("-fx-border-color: white");

        sites.put(site, group);
        Main.miniMapGroup.getChildren().addAll(group);
    }

    public void addSpawn(MacroObjSpawn spawn) throws FileNotFoundException {
        ImageView imageView;
        GridPane spawnWrapper = new GridPane();
        Group group;
        switch (spawn.getName()){
            case "t":
                imageView = new ImageView(new Image(new FileInputStream("src/source/t_got_spot.png")));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(150 * MiniMap.SCALE);

                spawnWrapper.getRowConstraints().add(new RowConstraints(35 * MiniMap.SCALE));
                spawnWrapper.getRowConstraints().add(new RowConstraints(200 * MiniMap.SCALE));
                spawnWrapper.getColumnConstraints().add(new ColumnConstraints(720 * MiniMap.SCALE));

                spawnWrapper.setLayoutX(1060.0 * MiniMap.SCALE);
                spawnWrapper.setLayoutY(2570.0 * MiniMap.SCALE);
                break;
            case "ct":
                imageView = new ImageView(new Image(new FileInputStream("src/source/ct_got_spot.png")));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(150 * MiniMap.SCALE);

                spawnWrapper.getRowConstraints().add(new RowConstraints(30 * MiniMap.SCALE));
                spawnWrapper.getRowConstraints().add(new RowConstraints(285 * MiniMap.SCALE));
                spawnWrapper.getColumnConstraints().add(new ColumnConstraints(360 * MiniMap.SCALE));

                spawnWrapper.setLayoutX(2080.0 * MiniMap.SCALE);
                spawnWrapper.setLayoutY(445.0 * MiniMap.SCALE);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + spawn.getName());
        }

        spawnWrapper.setGridLinesVisible(true);
        spawnWrapper.setStyle("-fx-border-style: solid outside;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");

        spawnWrapper.setHalignment(imageView, HPos.CENTER);
        spawnWrapper.setValignment(imageView, VPos.CENTER);
        spawnWrapper.setGridLinesVisible(true);
        spawnWrapper.add(imageView, 0, 1);


        group = new Group(spawnWrapper);
        spawns.put(spawn, group);
        Main.miniMapGroup.getChildren().addAll(spawnWrapper);
    }

    public void updateSite(MacroObjSite site, String side) throws FileNotFoundException {
        Image getImg;
        ImageView getImgView = null;
        ImageView imageView = null;
        GridPane siteWrapper = new GridPane();
        VBox imgWrap = new VBox();
        Group group;

        if(side.equals("t")){
            getImg = new Image(new FileInputStream("src/source/t_got_spot.png"));
        }else{
            getImg = new Image(new FileInputStream("src/source/ct_got_spot.png"));
        }

        if(site.getName().equals("a")){
            imageView = new ImageView(new Image(new FileInputStream("src/source/a_site.png")));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150 * MiniMap.SCALE);

            getImgView = new ImageView(getImg);
            getImgView.setPreserveRatio(true);
            getImgView.setFitWidth(120 * SCALE);

            siteWrapper.getRowConstraints().add(new RowConstraints(30 * MiniMap.SCALE));
            siteWrapper.getRowConstraints().add(new RowConstraints(200 * MiniMap.SCALE));

            siteWrapper.getColumnConstraints().add(new ColumnConstraints(117 * MiniMap.SCALE));
            siteWrapper.getColumnConstraints().add(new ColumnConstraints(117 * MiniMap.SCALE));
            siteWrapper.setLayoutX(2820 * MiniMap.SCALE);
            siteWrapper.setLayoutY(410 * MiniMap.SCALE);
        }else{
            imageView = new ImageView(new Image(new FileInputStream("src/source/a_site.png")));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150 * MiniMap.SCALE);

            getImgView = new ImageView(getImg);
            getImgView.setPreserveRatio(true);
            getImgView.setFitWidth(120 * SCALE);

            siteWrapper.getRowConstraints().add(new RowConstraints(30 * MiniMap.SCALE));
            siteWrapper.getRowConstraints().add(new RowConstraints(200 * MiniMap.SCALE));

            siteWrapper.getColumnConstraints().add(new ColumnConstraints(117 * MiniMap.SCALE));
            siteWrapper.getColumnConstraints().add(new ColumnConstraints(117 * MiniMap.SCALE));
            siteWrapper.setLayoutX(2820 * MiniMap.SCALE);
            siteWrapper.setLayoutY(410 * MiniMap.SCALE);
        }

        siteWrapper.setHalignment(imgWrap, HPos.CENTER);
        siteWrapper.setValignment(imgWrap, VPos.CENTER);
        siteWrapper.setGridLinesVisible(true);
        siteWrapper.add(imageView, 0, 1);
        siteWrapper.setStyle("-fx-background-color: gray");
        siteWrapper.setOpacity(0.8);
        siteWrapper.add(getImgView, 1, 1);

        group = new Group(siteWrapper);
        sites.replace(site, sites.get(site), group);
        Main.miniMapGroup.getChildren().remove(sites.get(site));
        Main.miniMapGroup.getChildren().addAll(group);
    }

    public void updateMap() {
        for (MicroObject unit : Main.microObjectsCT) {
            ImageView imageView = micros.get(unit);
            imageView.setLayoutX(unit.getX() * MiniMap.SCALE);
            imageView.setLayoutY(unit.getY() * MiniMap.SCALE);
        }

        for (MicroObject unit : Main.microObjectsT) {
            ImageView imageView = micros.get(unit);
            imageView.setLayoutX(unit.getX() * MiniMap.SCALE);
            imageView.setLayoutY(unit.getY() * MiniMap.SCALE);
        }
    }


}



