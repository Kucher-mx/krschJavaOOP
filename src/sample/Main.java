package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

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

    static AnimationTimer timer;
    static Group group = new Group();
    static Scene scene;
    static StackPane layout2;
    public static Wallpaper wallpaper;
    public static DialogWindow dialogWindow;
    public static ScrollPane scrollPane;
    public static Stage primaryStage;
    final static Random random = new Random();
    static ImageView miniMapView;
    static Group miniMapGroup = new Group();
    static Scale miniMapScale = new Scale();
    static Rectangle miniMapBoxView = new Rectangle();

    static Group root;

    static Group showInfoActiveGroup = new Group();
    static VBox showInfoActiveWrapper = new VBox();

    //check framerate
    public static final long[] frameTimes = new long[100];
    public static int frameTimeIndex = 0 ;
    public static boolean arrayFilled = false ;

    public static void setMainStage(double width, double height, ArrayList<String> ctLvls, ArrayList<String> tLvls) throws FileNotFoundException {
        SpawnWallpaper();
        SpawnMacros();
        SpawnMicros(tLvls, "t");
        SpawnMicros(ctLvls, "ct");

        root = new Group(group);
        scrollPane = new ScrollPane(group);

        scrollPane.setMaxWidth(Wallpaper.border.getWidth());
        scrollPane.setMaxHeight(Wallpaper.border.getHeight());

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        layout2 = new StackPane();

        miniMapScale.setX(0.1);
        miniMapScale.setY(0.1);

        layout2.getChildren().add(scrollPane);
        layout2.getChildren().add(miniMapGroup);
        layout2.setAlignment(miniMapGroup, Pos.TOP_RIGHT);


        showInfoActiveGroup.getChildren().add(showInfoActiveWrapper);
        layout2.getChildren().add(showInfoActiveGroup);
        layout2.setAlignment(showInfoActiveGroup, Pos.BOTTOM_CENTER);
        miniMapGroup.setStyle("-fx-padding: 0 20px 0 0");

        scene = new Scene(layout2, width, height);
        actualizeMiniMap();
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

//                long oldFrameTime = frameTimes[frameTimeIndex] ;
//                frameTimes[frameTimeIndex] = now ;
//                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
//                if (frameTimeIndex == 0) {
//                    arrayFilled = true ;
//                }
//                if (arrayFilled) {
//                    long elapsedNanos = now - oldFrameTime ;
//                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
//                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
//                    System.out.println("Current frame rate: " + frameRate);
//                }

                if(!endOfTheGame){
//                    updateMiniMap();

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

    public static void updateMiniMap(){
        miniMapGroup.getChildren().clear();
        miniMapBoxView.setX(miniMapBoxView.getX());
        miniMapBoxView.setY(miniMapBoxView.getY());
        miniMapView.setFitWidth(370);
        miniMapView.setFitHeight(300);
        miniMapView = new ImageView(group.snapshot( null, null));
        miniMapGroup.getChildren().add(miniMapView);
        miniMapGroup.getChildren().add(miniMapBoxView);
        miniMapBoxView.toFront();
        miniMapView.getTransforms().add(miniMapScale);
    }

    public static void actualizeMiniMap() {
        miniMapBoxView.setX(0);
        miniMapBoxView.setY(0);
        miniMapBoxView.setHeight(5);
        miniMapBoxView.setWidth(5);
        miniMapBoxView.setFill(Color.WHITE);
        miniMapBoxView.setStroke(Color.NAVAJOWHITE);
        miniMapView = new ImageView(group.snapshot( null, null));
        miniMapView.setFitWidth(370);
        miniMapView.setFitHeight(300);

        miniMapGroup.getChildren().add(miniMapView);
        miniMapGroup.getChildren().add(miniMapBoxView);
        miniMapBoxView.toFront();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
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

    public static void SpawnMacros() throws FileNotFoundException {
        Main.spawns[0] = new MacroObjSpawn("ct");
        Main.spawns[1] = new MacroObjSpawn("t");

        Main.sites[0] = new MacroObjSite("a");
        Main.sites[1] = new MacroObjSite("b");

        for (MacroObjSpawn spawn : spawns){
            group.getChildren().add(spawn.spawnGroup);
        }

        for (MacroObjSite site : sites){
            group.getChildren().add(site.siteGroup);
        }
    }

    static void MoveMicro(){
        for (MicroObject micro : Main.microObjectsT){
            micro.run(Main.toMacro);
            System.out.println(micro);
        }

        for (MicroObject micro : Main.microObjectsCT){
            micro.run(Main.toMacro);
            System.out.println(micro);
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

                if(!microT.getAlive()){
                    Main.group.getChildren().remove(microT.microGroup);
                    microsToDelete.add(microT);
                }else if(!microCT.getAlive()){
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

    public static void checkWin(){
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Game Over");
        Button restart = new Button("Quit");
        Label gameOverLabel = new Label();
        GridPane endGrid = new GridPane();
        Boolean won = false;

        endGrid.getRowConstraints().add(new RowConstraints(50));
        endGrid.getRowConstraints().add(new RowConstraints(50));

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
        endGrid.setHalignment(restart, HPos.CENTER);
        endGrid.setValignment(restart, VPos.CENTER);

        dialog.getDialogPane().setPrefSize(320, 150);
        endGrid.setStyle("-fx-padding: 25px 0 0 60px;");
        endGrid.setGridLinesVisible(true);

        if(won){
            endGrid.add(gameOverLabel, 0, 0);
            endGrid.add(restart, 0, 1);

            restart.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    dialog.setResult(Boolean.TRUE);
                    System.exit(0);
                }
            });

            dialog.getDialogPane().getChildren().add(endGrid);
            endOfTheGame = true;
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
}
