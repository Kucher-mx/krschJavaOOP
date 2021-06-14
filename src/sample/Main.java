package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

    static {
        System.out.println("static block init");
    }

    {
        System.out.println("non-static block init");
    }

    public static MacroObjSite[] sites = new MacroObjSite[2];
    public static MacroObjSpawn[] spawns = new MacroObjSpawn[2];
    public static ArrayList<MicroObject> microObjectsT = new ArrayList<MicroObject>();
    public static ArrayList<MicroObject> microObjectsCT = new ArrayList<MicroObject>();
    public static int teamSize;
    static boolean toMacro = false;
    static int timeToCapture = 10000;
    static long berserkTimeStart = 0;
    static long secondLvlAbilityTime = 0;
    static long secondLvlAbilityTimeEnd = new Date().getTime() - 30000;
    static long berserkTimeEnd = new Date().getTime() - 30000;
    static boolean berserkPressed = false;
    static boolean secondLvlAbility = false;
    public static boolean endOfTheGame = false;
    public static boolean getA = false;
    public static boolean getB = false;

    static AnimationTimer timer;

    static Group group = new Group();
    static Scene scene;
    static StackPane layout2;
    static Wallpaper wallpaper;
    static DialogWindow dialogWindow;
    static ScrollPane scrollPane;
    static Stage primaryStage;
    final static Random random = new Random();
    static ImageView miniMapView;
    static Group miniMapGroup = new Group();
    static Scale miniMapScale = new Scale();
    static Rectangle miniMapBoxView = new Rectangle();

    static HBox topMenu = new HBox();
    static VBox topMenuWrapper = new VBox();
    static Label microCt = new Label();
    static Label microT = new Label();
    static Label macroAInfo = new Label();
    static Label macroBInfo = new Label();

    static MiniMap minimap;
    static Group showInfoActiveGroup = new Group();
    static VBox showInfoActiveWrapper = new VBox();

    public static void setMainStage(double width, double height, ArrayList<String> ctLvls, ArrayList<String> tLvls, boolean load) throws FileNotFoundException {
        SpawnWallpaper();

        if(load){

            for (MacroObjSite site : Main.sites){
                site.printTeamCT();
                site.printTeamT();
            }

            ArrayList<MicroObject> temp = new ArrayList<MicroObject>();

            for(MicroObject unit : Main.microObjectsCT){
                switch (unit.getLvl()){
                    case 1:
                        temp.add(new MicroObject(unit.getSide(), unit.getXDest(), unit.getYDest(), unit.getX(), unit.getY(), unit.getHp()));
                        break;
                    case 2:
                        temp.add(new MicroObjectOne(unit.getSide(), unit.getXDest(), unit.getYDest(), unit.getX(), unit.getY(), unit.getHp()));
                        break;
                    case 3:
                        temp.add(new MicroObjectTwo(unit.getSide(), unit.getXDest(), unit.getYDest(), unit.getX(), unit.getY(), unit.getHp()));
                        break;
                }
            }

            Main.microObjectsCT.clear();
            for(MicroObject ct : temp){
                Main.microObjectsCT.add(ct);
            }
            temp.clear();

            for(MicroObject unit : Main.microObjectsT){
                switch (unit.getLvl()){
                    case 1:
                        temp.add(new MicroObject(unit.getSide(), unit.getXDest(), unit.getYDest(), unit.getX(), unit.getY(), unit.getHp()));
                        break;
                    case 2:
                        temp.add(new MicroObjectOne(unit.getSide(), unit.getXDest(), unit.getYDest(), unit.getX(), unit.getY(), unit.getHp()));
                        break;
                    case 3:
                        temp.add(new MicroObjectTwo(unit.getSide(), unit.getXDest(), unit.getYDest(), unit.getX(), unit.getY(), unit.getHp()));
                        break;
                }
            }

            Main.microObjectsT.clear();
            for(MicroObject t : temp){
                Main.microObjectsT.add(t);
            }
            temp.clear();


            ArrayList<MacroObjSite> sitesArrayList = new ArrayList<MacroObjSite>();
            for(MacroObjSite site : Main.sites){
                sitesArrayList.add(new MacroObjSite(site.getName(), site.getBelong(), site.timeStartedCT, site.timeStartedT));
            }

            int sitesIterator = 0;
            Arrays.fill(Main.sites, null);
            for(MacroObjSite site : sitesArrayList){
                Main.sites[sitesIterator] = site;
                sitesIterator++;
            }

            SpawnMacros(false);

            for (MicroObject micro : Main.microObjectsCT){
                group.getChildren().add(micro.microGroup);
            }

            for (MicroObject micro : Main.microObjectsT){
                group.getChildren().add(micro.microGroup);
            }

        }else{
            SpawnMacros(true);
            SpawnMicros(tLvls, "t");
            SpawnMicros(ctLvls, "ct");
        }

//        SpawnMacrosTest(Main.sites[0]);
//        SpawnMacrosTest(Main.spawns[1]);
        Main.endOfTheGame = false;

        scrollPane = new ScrollPane(group);
        minimap = new MiniMap();

        for (MacroObjSite site : Main.sites){
            if(!site.getBelong().equals("none")){
                Main.minimap.updateSite(site, site.getBelong());
            }
        }

        scrollPane.setMaxWidth(Wallpaper.border.getWidth());
        scrollPane.setMaxHeight(Wallpaper.border.getHeight());

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        layout2 = new StackPane();

        miniMapScale.setX(0.1);
        miniMapScale.setY(0.1);

        layout2.getChildren().add(scrollPane);
        layout2.getChildren().add(minimap.getPane());
        layout2.setAlignment(minimap.getPane(), Pos.TOP_RIGHT);

        Button save = new Button("Save");
        Button secondBtn = new Button("Keys info");

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if(Main.sites[0].t.size() > 0 || Main.sites[0].ct.size() > 0 || Main.sites[1].t.size() > 0 || Main.sites[1].ct.size() > 0){
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Save error");
                        a.setContentText("Can not save while interaction in macro");

                        a.show();
                        return;
                    }

                    Main.sites[0].t.clear();
                    Main.sites[0].ct.clear();
                    Main.sites[1].t.clear();
                    Main.sites[1].ct.clear();
                    Main.serealize();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        secondBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Dialog<Boolean> dialog = new Dialog<>();
                dialog.setTitle("Key handling info");
                Button button = new Button("OK");
                Label V = new Label("V - use ability of 2&3 lvl units");
                Label B = new Label("B - use berserk mode (All units)");
                Label R = new Label("B - remove selected units");
                Label C = new Label("C - sent all units to macro + enlarge speed");
                Label ESCAPE = new Label("ESCAPE - cancel selection");
                Label I = new Label("I - insert an unit");
                Label WASD = new Label("WASD - move active unit/units");

                GridPane dialogPane = new GridPane();

                dialogPane.getRowConstraints().add(new RowConstraints(30));
                dialogPane.getRowConstraints().add(new RowConstraints(30));
                dialogPane.getRowConstraints().add(new RowConstraints(30));
                dialogPane.getRowConstraints().add(new RowConstraints(30));
                dialogPane.getRowConstraints().add(new RowConstraints(30));
                dialogPane.getRowConstraints().add(new RowConstraints(30));
                dialogPane.getRowConstraints().add(new RowConstraints(30));
                dialogPane.getRowConstraints().add(new RowConstraints(30));

                dialogPane.getColumnConstraints().add(new ColumnConstraints(250));

                dialogPane.add(WASD, 0, 0);
                dialogPane.add(ESCAPE, 0, 1);
                dialogPane.add(R, 0, 2);
                dialogPane.add(I, 0, 3);
                dialogPane.add(C, 0, 4);
                dialogPane.add(V, 0, 7);
                dialogPane.add(B, 0, 8);
                dialogPane.add(button, 0, 9);

                dialogPane.setHalignment(WASD, HPos.CENTER);
                dialogPane.setValignment(WASD, VPos.CENTER);
                dialogPane.setHalignment(ESCAPE, HPos.CENTER);
                dialogPane.setValignment(ESCAPE, VPos.CENTER);
                dialogPane.setHalignment(R, HPos.CENTER);
                dialogPane.setValignment(R, VPos.CENTER);
                dialogPane.setHalignment(I, HPos.CENTER);
                dialogPane.setValignment(I, VPos.CENTER);
                dialogPane.setHalignment(C, HPos.CENTER);
                dialogPane.setValignment(C, VPos.CENTER);
                dialogPane.setHalignment(V, HPos.CENTER);
                dialogPane.setValignment(V, VPos.CENTER);
                dialogPane.setHalignment(B, HPos.CENTER);
                dialogPane.setValignment(B, VPos.CENTER);
                dialogPane.setHalignment(button, HPos.RIGHT);
                dialogPane.setValignment(button, VPos.CENTER);

                dialogPane.setStyle("-fx-padding: 15px 0 0 75px;");

                dialog.getDialogPane().setPrefSize(400, 330);
                dialog.getDialogPane().getChildren().add(dialogPane);

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        dialog.setResult(Boolean.TRUE);
                    }
                });

                dialog.showAndWait();
            }
        });

        topMenu.getChildren().addAll(save, secondBtn);

        topMenuWrapper.getChildren().add(topMenu);
        topMenuWrapper.getChildren().add(microCt);
        topMenuWrapper.getChildren().add(microT);
        topMenuWrapper.getChildren().add(macroAInfo);
        topMenuWrapper.getChildren().add(macroBInfo);

        microCt.setFont(new Font(14));
        microT.setFont(new Font(14));
        macroAInfo.setFont(new Font(14));
        macroBInfo.setFont(new Font(14));
        microCt.setTextFill(Color.WHITE);
        microT.setTextFill(Color.WHITE);
        macroAInfo.setTextFill(Color.WHITE);
        macroBInfo.setTextFill(Color.WHITE);

        topMenuWrapper.setMaxHeight(150);
        topMenuWrapper.setMaxWidth(130);
        topMenuWrapper.setStyle("-fx-border-color: yellow;");

        topMenuWrapper.setAlignment(Pos.TOP_CENTER);
        topMenuWrapper.setSpacing(10);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.setSpacing(10);

        layout2.getChildren().add(topMenuWrapper);
        layout2.setAlignment(topMenuWrapper, Pos.TOP_LEFT);

        showInfoActiveGroup.getChildren().add(showInfoActiveWrapper);
        layout2.getChildren().add(showInfoActiveGroup);
        layout2.setAlignment(showInfoActiveGroup, Pos.BOTTOM_CENTER);

        scene = new Scene(layout2, width, height);
        scene.setOnKeyPressed(new KeyPressHandler());

        primaryStage.setTitle("CS Clone");
        primaryStage.setScene(scene);

        miniMapGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();

                miniMapBoxView.setX(x);
                miniMapBoxView.setY(y);

                double posX = ( (x) / (3700*0.1) * 3700);
                double posY = ( (y) / (3000*0.1) * 3000);
                scrollPane.setVvalue(posY / 2700);
                scrollPane.setHvalue(posX / 3200);
            }
        });


        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                miniMapBoxView.setY(scrollPane.getVvalue() * 300 - 5);
            }
        });

        scrollPane.hvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                miniMapBoxView.setX(scrollPane.getHvalue() * 370 - 5);
            }
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if(!endOfTheGame){

                    minimap.updateMap();
                    Main.updateGlobalLabels();
                    Main.MoveMicro();
                    try {
                        Main.checkIntersectsMacro();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        Main.checkIntersectsMicro();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(berserkTimeEnd + 30000 <= new Date().getTime()){
                        if(berserkPressed){
                            berserkHandle();
                        }else {
                            interval = 250;
                            for(MicroObject micro : microObjectsCT){
                                micro.setDamage(micro.defaultDamage);
                                micro.microLabel.setTextFill(Color.WHITE);
                            }

                            for(MicroObject micro : microObjectsT){
                                micro.setDamage(micro.defaultDamage);
                                micro.microLabel.setTextFill(Color.WHITE);
                            }
                        }
                    }

                    if(secondLvlAbilityTimeEnd + 30000 <= new Date().getTime()){
                        if(secondLvlAbility){
                            secondLvlAbilityHandle();
                        }else {
                            for(MicroObject micro : microObjectsCT){
                                micro.setSpeed(micro.defaultSpeed);
                                micro.microLabel.setStyle("-fx-border-color: white; -fx-padding: 3px");
                            }

                            for(MicroObject micro : microObjectsT){
                                micro.microLabel.setStyle("-fx-border-color: white; -fx-padding: 3px");
                                micro.setSpeed(micro.defaultSpeed);
                            }
                        }
                    }



                    try {
                        if(sites[1].ct.size() > 0){
                            checkGetMacro(sites[1], "ct");
                        }else if(sites[1].t.size() > 0){
                            checkGetMacro(sites[1], "t");
                        }

                        if(sites[0].ct.size() > 0){
                            checkGetMacro(sites[0], "ct");
                        }else if(sites[0].t.size() > 0){
                            checkGetMacro(sites[0], "t");
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    checkWin();
                }
            }
        };

        timer.start();
    }

    public static void setUpMainStage(double width, double height) throws FileNotFoundException {
        scrollPane = new ScrollPane(group);
        minimap = new MiniMap();

        scrollPane.setMaxWidth(Wallpaper.border.getWidth());
        scrollPane.setMaxHeight(Wallpaper.border.getHeight());

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        layout2 = new StackPane();

        miniMapScale.setX(0.1);
        miniMapScale.setY(0.1);

        layout2.getChildren().add(scrollPane);
        layout2.getChildren().add(minimap.getPane());
        layout2.setAlignment(minimap.getPane(), Pos.TOP_RIGHT);

        Button save = new Button("Save");
        Button edit = new Button("Edit");

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.serealize();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        topMenu.getChildren().addAll(save, edit);

        topMenuWrapper.getChildren().add(topMenu);
        topMenuWrapper.getChildren().add(microCt);
        topMenuWrapper.getChildren().add(microT);
        topMenuWrapper.getChildren().add(macroAInfo);
        topMenuWrapper.getChildren().add(macroBInfo);

        microCt.setFont(new Font(14));
        microT.setFont(new Font(14));
        macroAInfo.setFont(new Font(14));
        macroBInfo.setFont(new Font(14));
        microCt.setTextFill(Color.WHITE);
        microT.setTextFill(Color.WHITE);
        macroAInfo.setTextFill(Color.WHITE);
        macroBInfo.setTextFill(Color.WHITE);

        topMenuWrapper.setMaxHeight(150);
        topMenuWrapper.setMaxWidth(130);
        topMenuWrapper.setStyle("-fx-border-color: yellow;");

        topMenuWrapper.setAlignment(Pos.TOP_CENTER);
        topMenuWrapper.setSpacing(10);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.setSpacing(10);

        layout2.getChildren().add(topMenuWrapper);
        layout2.setAlignment(topMenuWrapper, Pos.TOP_LEFT);

        showInfoActiveGroup.getChildren().add(showInfoActiveWrapper);
        layout2.getChildren().add(showInfoActiveGroup);
        layout2.setAlignment(showInfoActiveGroup, Pos.BOTTOM_CENTER);

        scene = new Scene(layout2, width, height);
        scene.setOnKeyPressed(new KeyPressHandler());

        primaryStage.setTitle("CS Clone");
        primaryStage.setScene(scene);

        miniMapGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();

                miniMapBoxView.setX(x);
                miniMapBoxView.setY(y);

                double posX = ( (x) / (3700*0.1) * 3700);
                double posY = ( (y) / (3000*0.1) * 3000);
                scrollPane.setVvalue(posY / 2700);
                scrollPane.setHvalue(posX / 3200);
            }
        });


        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                miniMapBoxView.setY(scrollPane.getVvalue() * 300 - 5);
            }
        });

        scrollPane.hvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                miniMapBoxView.setX(scrollPane.getHvalue() * 370 - 5);
            }
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if(!endOfTheGame){

                    minimap.updateMap();
                    Main.updateGlobalLabels();
                    Main.MoveMicro();
                    try {
                        Main.checkIntersectsMacro();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        Main.checkIntersectsMicro();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(berserkTimeEnd + 30000 <= new Date().getTime()){
                        if(berserkPressed){
                            berserkHandle();
                        }else {
                            interval = 250;
                            for(MicroObject micro : microObjectsCT){
                                micro.setDamage(micro.defaultDamage);
                                micro.microLabel.setTextFill(Color.WHITE);
                            }

                            for(MicroObject micro : microObjectsT){
                                micro.setDamage(micro.defaultDamage);
                                micro.microLabel.setTextFill(Color.WHITE);
                            }
                        }
                    }

                    if(secondLvlAbilityTimeEnd + 30000 <= new Date().getTime()){
                        if(secondLvlAbility){
                            secondLvlAbilityHandle();
                        }else {
                            for(MicroObject micro : microObjectsCT){
                                micro.setSpeed(micro.defaultSpeed);
                                micro.microLabel.setStyle("-fx-border-color: white; -fx-padding: 3px");
                            }

                            for(MicroObject micro : microObjectsT){
                                micro.microLabel.setStyle("-fx-border-color: white; -fx-padding: 3px");
                                micro.setSpeed(micro.defaultSpeed);
                            }
                        }
                    }


                    try {
                        if(sites[1].ct.size() > 0){
                            checkGetMacro(sites[1], "ct");
                        }else if(sites[1].t.size() > 0){
                            checkGetMacro(sites[1], "t");
                        }

                        if(sites[0].ct.size() > 0){
                            checkGetMacro(sites[0], "ct");
                        }else if(sites[0].t.size() > 0){
                            checkGetMacro(sites[0], "t");
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    checkWin();
                }
            }
        };

        timer.start();
    }

    public static void setSetupStage(){
        dialogWindow = new DialogWindow();
        Main.primaryStage.setTitle("Cs Clone Dialog");
        Main.primaryStage.setScene(dialogWindow.returnDialogScene());
    }

    public static void updateGlobalLabels(){
        microCt.setText("ct alive: " + (Main.microObjectsCT.size() + Main.sites[0].ct.size() + Main.sites[1].ct.size()));
        microT.setText("t alive: " + (Main.microObjectsT.size() + Main.sites[0].t.size() + Main.sites[1].t.size()));
        macroAInfo.setText("A belongs to: " + Main.sites[0].getBelong());
        macroBInfo.setText("B belongs to: " + Main.sites[1].getBelong());
    }

    public static void showInfoActive(MicroObject unit, boolean add){
        if(add){
            Label unitInfo = new Label(unit.toString());
            unitInfo.setId(String.valueOf(unit.id));
            showInfoActiveWrapper.getChildren().add(unitInfo);
            showInfoActiveWrapper.setPrefWidth(800);
            showInfoActiveWrapper.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            showInfoActiveWrapper.setAlignment(Pos.CENTER);
        }else{
            for(Node label : showInfoActiveWrapper.getChildren()){
                if(label.getId().equals(String.valueOf(unit.id))){
                    showInfoActiveWrapper.getChildren().remove(label);
                    break;
                }
            }
        }
    }

    public static void secondLvlAbilityHandle(){
        if(secondLvlAbilityTime == 0) {
            secondLvlAbilityTime = new Date().getTime();
        }
        if(secondLvlAbilityTime + 15000 >= new Date().getTime()){
            for(MicroObject micro : microObjectsCT){
                if(micro.getLvl() == 2 || micro.getLvl() == 3){
                    micro.microLabel.setStyle("-fx-border-color: blue; -fx-padding: 3px");
                    micro.setSpeed(micro.defaultSpeed * 2);
                }
            }

            for(MicroObject micro : microObjectsT){
                if(micro.getLvl() == 2 || micro.getLvl() == 3){
                    micro.microLabel.setStyle("-fx-border-color: blue; -fx-padding: 3px");
                    micro.setSpeed(micro.defaultSpeed * 2);
                }
            }
        }else{
            secondLvlAbilityTimeEnd = new Date().getTime();
            secondLvlAbilityTime = 0;
            secondLvlAbility = false;
        }
    }

    public static void berserkHandle(){
        if(berserkTimeStart == 0) {
            berserkTimeStart = new Date().getTime();
        }
        if(berserkTimeStart + 15000 >= new Date().getTime()){
            interval = 100;
            for(MicroObject micro : microObjectsCT){
                micro.setDamage(micro.defaultDamage * 3);
                micro.microLabel.setTextFill(Color.RED);
            }

            for(MicroObject micro : microObjectsT){
                micro.setDamage(micro.defaultDamage * 3);
                micro.microLabel.setTextFill(Color.RED);
            }
        }else{
            berserkTimeEnd = new Date().getTime();
            berserkTimeStart = 0;
            berserkPressed = false;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.setProperty("quantum.multithreaded", "true");
        Main.primaryStage = primaryStage;
        Main.setSetupStage();
        primaryStage.show();
    }

    public static void SpawnWallpaper() throws FileNotFoundException {
        wallpaper = new Wallpaper();
        group.getChildren().add(wallpaper.getWallGroup());
    };

    public static void main(String[] args) {
        launch(args);
    }

//    public static void SpawnMacrosTest(Object obj) throws FileNotFoundException {
//        if(obj instanceof MacroObjSite){
//            System.out.println("site: " + ((MacroObjSite) obj).getName());
//        }
//
//        if(obj instanceof MacroObjSpawn){
//            System.out.println("spawn: " + ((MacroObjSpawn) obj).getName());
//        }
//    }

    public static void SpawnMacros(Boolean siteBool) throws FileNotFoundException {
        Main.spawns[0] = new MacroObjSpawn("ct");
        Main.spawns[1] = new MacroObjSpawn("t");

        if(siteBool){
            Main.sites[0] = new MacroObjSite("a");
            Main.sites[1] = new MacroObjSite("b");

            for (MacroObjSpawn spawn : spawns){
                group.getChildren().add(spawn.spawnGroup);
            }

            for (MacroObjSite site : sites){
                group.getChildren().add(site.siteGroup);
            }
        }else{

            for (MacroObjSpawn spawn : spawns){
                group.getChildren().add(spawn.spawnGroup);
            }

            for (MacroObjSite site : Main.sites){
                group.getChildren().add(site.siteGroup);
            }
        }


    }

    static void MoveMicro(){

        for (MicroObject micro : Main.microObjectsT){
            micro.run(Main.toMacro);
        }

        for (MicroObject micro : Main.microObjectsCT){
            micro.run(Main.toMacro);
        }

    }

    static void SpawnMicros(ArrayList<String> lvls, String side) throws FileNotFoundException {
        if(side.equals("ct")){
            for(String lvl : lvls){
                if(lvl.equals("First level")){
                    Main.microObjectsCT.add(new MicroObject("ct"));
                }else if(lvl.equals("Second level")){
                    Main.microObjectsCT.add(new MicroObjectOne("ct"));
                }else if(lvl.equals("Third level")){
                    Main.microObjectsCT.add(new MicroObjectTwo("ct"));
                }
            }
            for (MicroObject micro : Main.microObjectsCT){
                group.getChildren().add(micro.microGroup);
            }
        }else if(side.equals("t")){
            for(String lvl : lvls){
                if(lvl.equals("First level")){
                    Main.microObjectsT.add(new MicroObject("t"));
                }else if(lvl.equals("Second level")){
                    Main.microObjectsT.add(new MicroObjectOne("t"));
                }else if(lvl.equals("Third level")){
                    Main.microObjectsT.add(new MicroObjectTwo("t"));
                }
            }
            for (MicroObject micro : Main.microObjectsT){
                group.getChildren().add(micro.microGroup);
            }
        }
    }

    static long lastInvocationT = new Date().getTime();
    static long interval = 250;

    public static void checkIntersectsMicro() throws InterruptedException {
        ArrayList<MicroObject> microsToDelete = new ArrayList<MicroObject>();
        for(MicroObject microCT : Main.microObjectsCT){
            for(MicroObject microT : Main.microObjectsT){
                if(microCT.microGroup.intersects(microT.microGroup.getLayoutBounds())){
                    long current = new Date().getTime();
                    if(((current - lastInvocationT) > interval)){
                        microT.interactWith(microCT);
                        microCT.interactWith(microT);
                        AudioClip shoot = new AudioClip(new File("src/audio/ak_shoot.mp3").toURI().toString());
                        shoot.setVolume(10);
                        shoot.play();
                        microT.microLabel.setText("Lvl: " + microT.getLvl() + ", hp: "  + microT.getHp());
                        microCT.microLabel.setText("Lvl: " + microCT.getLvl() + ", hp: "  + microCT.getHp());
                    }
                    lastInvocationT = current;
                }

                if(!microT.getAlive() && (!Main.getA || !Main.getB)){
                    Main.group.getChildren().remove(microT.microGroup);
                    microsToDelete.add(microT);
                }else if(!microCT.getAlive() && (!Main.getA || !Main.getB)){
                    Main.group.getChildren().remove(microCT.microGroup);
                    microsToDelete.add(microCT);
                }
            }
        }
        for(MicroObject delete : microsToDelete){
            if(delete.getSide().equals("t")){
                Main.microObjectsT.remove(delete);
            }else{
                Main.microObjectsCT.remove(delete);
            }
        }
    }

    public static void checkIntersectsMacro() throws FileNotFoundException {
        ArrayList<MicroObject> microsToRemove = new ArrayList<MicroObject>();
        for(MacroObjSite site : Main.sites){
            for(MicroObject micro : Main.microObjectsCT){
                if(site.siteGroup.intersects(micro.microGroup.getLayoutBounds()) && !site.getBelong().equals("ct")){
                    showInfoActive(micro, false);
                    site.addCt(micro);
                    Main.group.getChildren().remove(micro.microGroup);
                    checkGetMacro(site, "ct");
                    microsToRemove.add(micro);
                    micro.changeInMacro(true);
                    minimap.deleteUnit(micro);
                    if(site.getName().equals('a')){
                        Main.getA = true;
                    }else{
                        Main.getB = true;
                    }
                }else{
                    micro.changeInMacro(false);
                }
            }

            for(MicroObject micro : Main.microObjectsT){
                if(site.siteGroup.intersects(micro.microGroup.getLayoutBounds()) && !site.getBelong().equals("t")){
                    showInfoActive(micro, false);
                    site.addT(micro);
                    Main.group.getChildren().remove(micro.microGroup);
                    checkGetMacro(site, "t");
                    microsToRemove.add(micro);
                    micro.changeInMacro(true);
                    minimap.deleteUnit(micro);
                    if(site.getName().equals('a')){
                        Main.getA = true;
                    }else{
                        Main.getB = true;
                    }
                }else{
                    micro.changeInMacro(false);
                }
            }
        }

        for(MicroObject delete : microsToRemove){
            if(delete.getSide().equals("t")){
                Main.microObjectsT.remove(delete);
            }else{
                Main.microObjectsCT.remove(delete);
            }
        }
    }

    public static void serealize() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Main.primaryStage);

        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();

        Serealization.serializeNow(file);
    }

    public static void deserealize() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Main.primaryStage);

        Serealization.deserializeNow(file);
    }

    public static void checkWin(){
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Game Over");
        Button restart = new Button("Restart");
        Button quit = new Button("Quit");
        Label gameOverLabel = new Label();
        GridPane endGrid = new GridPane();
        Boolean won = false;

        endGrid.getRowConstraints().add(new RowConstraints(50));
        endGrid.getRowConstraints().add(new RowConstraints(50));

        endGrid.getColumnConstraints().add(new ColumnConstraints(200));
        endGrid.getColumnConstraints().add(new ColumnConstraints(200));


        if(sites[0].getBelong().equals("ct") && sites[1].getBelong().equals("ct")){
            gameOverLabel.setText("ct won by sites");
            won = true;
        }

        if(sites[0].getBelong().equals("t") && sites[1].getBelong().equals("t")){
            gameOverLabel.setText("t won by sites");
            won = true;
        }

        if(microObjectsCT.size() == 0 && microObjectsT.size() > 0 && (sites[0].ct.size() == 0 && sites[1].ct.size() == 0)){
            gameOverLabel.setText("t won by quantity");
            won = true;
        }

        if(microObjectsT.size() == 0 && microObjectsCT.size() > 0 && (sites[0].t.size() == 0 && sites[1].t.size() == 0)){
            gameOverLabel.setText("ct won by quantity");
            won = true;
        }

        endGrid.setHalignment(gameOverLabel, HPos.CENTER);
        endGrid.setValignment(gameOverLabel, VPos.CENTER);
        endGrid.setHalignment(quit, HPos.CENTER);
        endGrid.setValignment(quit, VPos.CENTER);

        dialog.getDialogPane().setPrefSize(520, 200);
        endGrid.setStyle("-fx-padding: 50px 0 0 60px;");
        endGrid.setGridLinesVisible(true);

        if(won){
            endGrid.add(gameOverLabel, 0, 0, 2 , 1);
            endGrid.add(restart, 0, 1);
            endGrid.add(quit, 0, 1);
            won = false;

            quit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    dialog.setResult(Boolean.TRUE);
                    System.exit(0);
                }
            });

            restart.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    dialog.setResult(Boolean.TRUE);
                    Main.microObjectsCT = new ArrayList<MicroObject>();
                    Main.microObjectsT = new ArrayList<MicroObject>();
                    Main.sites = new MacroObjSite[2];
                    Main.spawns = new MacroObjSpawn[2];

                    Main.minimap.getPane().getChildren().clear();
                    Main.group.getChildren().clear();
                    Main.topMenu.getChildren().clear();
                    Main.topMenuWrapper.getChildren().clear();
                    Main.showInfoActiveGroup.getChildren().clear();
                    Main.showInfoActiveWrapper.getChildren().clear();
                    Main.timer.stop();
                    Main.setSetupStage();
                }
            });

            dialog.getDialogPane().getChildren().add(endGrid);
            endOfTheGame = true;
            minimap.updateMap();
            Main.updateGlobalLabels();
            dialog.show();
        }
    }

    public static void checkGetMacro(MacroObjSite site, String side) throws FileNotFoundException {
        if(side.equals("ct")){
            if(!(site.getBelong().equals("ct"))){
                if(site.t.size() > 0){
                    String battleRes = site.fightToGet();
                    if(battleRes.equals("ct")){
                        if(site.timeStartedCT == 0){
                            site.timeStartedCT = new Date().getTime();
                        }
                        site.getMacro("ct");
                    }
                }else{
                    if(site.timeStartedCT == 0){
                        site.timeStartedCT = new Date().getTime();
                    }
                    site.getMacro("ct");
                }
            }else if(site.getBelong().equals("none")){
                if(site.timeStartedCT == 0){
                    site.timeStartedCT = new Date().getTime();
                }
                site.getMacro("ct");
            }
        }

        if(side.equals("t")){
            if(!(site.getBelong().equals("t"))){
                if(site.ct.size() > 0){
                    String battleRes = site.fightToGet();
                    if(battleRes.equals("t")){
                        if(site.timeStartedT == 0){
                            site.timeStartedT = new Date().getTime();
                        }
                        site.getMacro("t");
                    }
                }else{
                    if(site.timeStartedT == 0){
                        site.timeStartedT = new Date().getTime();
                    }
                    site.getMacro("t");
                }
            }else if(site.getBelong().equals("none")){
                if(site.timeStartedT == 0){
                    site.timeStartedT = new Date().getTime();
                }
                site.getMacro("t");
            }
        }
    }

    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}
