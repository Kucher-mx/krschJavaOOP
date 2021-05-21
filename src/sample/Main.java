package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main extends Application {

    public static  MacroObjSite[] sites = new MacroObjSite[2];
    public static MacroObjSpawn[] spawns = new MacroObjSpawn[2];
    public static ArrayList<MicroObject> microObjectsT = new ArrayList<MicroObject>();
//    public static MicroObject[] microObjectsCT;
    public static ArrayList<MicroObject> microObjectsCT = new ArrayList<MicroObject>();
    public static int teamSize;

    static AnimationTimer timer;
    static Group group = new Group();
    static Scene scene;
    static BorderPane layout;
    public static Wallpaper wallpaper;
    public static DialogWindow dialogWindow;
    public static ScrollPane scrollPane;
    public static Stage primaryStage;
    final static Random random = new Random();
    //get screen size to render appropriate window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double  width = screenSize.getWidth();
    double height = screenSize.getHeight();

    public static void setMainStage(double width, double height, ArrayList<String> ctLvls, ArrayList<String> tLvls) throws FileNotFoundException {
        SpawnWallpaper();
        SpawnMacros();
        SpawnMicros(tLvls, "t");
        SpawnMicros(ctLvls, "ct");




//        for (MicroObject microT : microObjectsT){
//            System.out.println(microT);
//        }
//
//        for (MicroObject microCt : microObjectsCT){
//            System.out.println(microCt);
//        }

//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//        for (int i = 0; i < Main.teamSize; i++){
//            System.out.println("t: " + Main.microObjectsT[i] + ", ct: " + Main.microObjectsCT[i]);
//        }
//        System.out.println();
//        System.out.println();
//        System.out.println();

//        System.out.println(width + " " + height);

        Group root = new Group(group);
        scrollPane = new ScrollPane(root);

        scrollPane.setMaxWidth(Wallpaper.border.getWidth());
        scrollPane.setMaxHeight(Wallpaper.border.getHeight());

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        layout = new BorderPane();
        layout.setCenter(scrollPane);

        scene = new Scene(layout, width, height);
        primaryStage.setTitle("CS clone");
        primaryStage.setScene(scene);

        scene.setOnKeyPressed(new KeyPressHandler());

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Main.MoveMicro();
                Main.checkIntersectsMacro();
                try {
                    Main.checkIntersectsMicro();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.start();
    }

    public static void setSetupStage(){
        dialogWindow = new DialogWindow();
        Main.primaryStage.setTitle("Test Dialog");
        Main.primaryStage.setScene(dialogWindow.returnDialogScene());
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
            micro.run();
        }

        for (MicroObject micro : Main.microObjectsCT){
            micro.run();
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
    static long lastInvocationCT = new Date().getTime();
    static long interval = 250;

    public static void checkIntersectsMicro() throws InterruptedException {
        ArrayList<MicroObject> microsToDelete = new ArrayList<MicroObject>();
        for(MicroObject microCT : Main.microObjectsCT){
            for(MicroObject microT : Main.microObjectsT){
                if(microCT.microGroup.intersects(microT.microGroup.getLayoutBounds())){
                    long current = new Date().getTime();
                    if(!((current - lastInvocationT) < interval)){
                        microT.interactWith(microCT);
                        microCT.interactWith(microT);
                        AudioClip shoot = new AudioClip(new File("src/audio/ak_shoot.mp3").toURI().toString());
                        shoot.play();
                        microT.microLabel.setText("Lvl: " + microT.getLvl() + ", hp: "  + microT.getHp());
                        microCT.microLabel.setText("Lvl: " + microCT.getLvl() + ", hp: "  + microCT.getHp());
                    }
                    lastInvocationT = current;
                    lastInvocationCT = current;
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

    public static void checkIntersectsMacro(){

        for(MacroObjSite site : Main.sites){
            for(MicroObject micro : Main.microObjectsCT){
                if(site.siteGroup.intersects(micro.microGroup.getLayoutBounds())){
                    //if bomb planted stop and defuse it
                    micro.changeInMacro(true);
                }else{
                    micro.changeInMacro(false);
                }
            }

            for(MicroObject micro : Main.microObjectsT){
                if(site.siteGroup.intersects(micro.microGroup.getLayoutBounds())){
                    //stop to set bomb
                    micro.changeInMacro(true);
                }else{
                    micro.changeInMacro(false);
                }
            }
        }

    }



//    public static void main(String[] args) {
//        launch(args);
//
//        universal mainObj = new universal();
//        Scanner in = new Scanner(System.in);
//        int choice = 1;
//        while(choice != 0) {
//            System.out.println();
//            System.out.println("""
//                    Enter action you wanna do:\s
//                    1: Add an microObject\s
//                    2: Print all teams\s
//                    3: Interaction of the micro you choose\s
//                    4: Interaction of the two sites A&B\s
//                    5: Count the micro based on characteristics you choose\s
//                    6: Delete a micro from the team you choose\s
//                    7: Call base method:)\s
//                    0: Exit""");
//            choice = in.nextInt();
//            System.out.println();
//            switch (choice){
//                case 1:
//                    mainObj.addMicroObj();
//                    break;
//                case 2:
//                    mainObj.printAll();
//                    break;
//                case 3:
//                    mainObj.interactWithMicro();
//                    break;
//                case 4:
//                    mainObj.InteractInMacro();
//                    break;
//                case 5:
//                    mainObj.countMicro();
//                    break;
//                case 6:
//                    mainObj.deleteMicro();
//                    break;
//                case 7:
//                    MicroObject first = new MicroObject("t");
//                    MicroObjectOne second = new MicroObjectOne("t");
//
//                    if(mainObj.callBase(first)){
//                        mainObj.callBase(second);
//                    }
//                    break;
//                default:
//                    System.out.println("You have chosen the wrong one");
//                    break;
//            }
//        }
//    }
}
