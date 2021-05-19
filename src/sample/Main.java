package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main extends Application {

    public static MacroObjSite[] sites = new MacroObjSite[2];
    public static MacroObjSpawn[] spawns = new MacroObjSpawn[2];
    public static MicroObject[] microObjectsT;
    public static MicroObject[] microObjectsCT;
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

    public static void setMainStage(double width, double height) throws FileNotFoundException {
        SpawnWallpaper();
        SpawnMacros();
        SpawnMicros(5);

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

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Main.MoveMicro();
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
//        SpawnWallpaper();
//        SpawnMacros();
//        SpawnMicros(5);
//
//        Group root = new Group(group);
//        scrollPane = new ScrollPane(root);
//
//        scrollPane.setMaxWidth(Wallpaper.border.getWidth());
//        scrollPane.setMaxHeight(Wallpaper.border.getHeight());
//
//        scrollPane.setFitToHeight(true);
//        scrollPane.setFitToWidth(true);
//
//        layout = new BorderPane();
//        layout.setCenter(scrollPane);
//
//        scene = new Scene(layout, this.width,this.height);
//
//        timer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
////                Main.MoveMicro();
//            }
//        };
//
//        primaryStage.setTitle("CS clone");
//        primaryStage.setScene(scene);
        timer.start();
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

    static void SpawnMicros(int size) throws FileNotFoundException {
        Main.microObjectsCT = new MicroObject[size];
        Main.microObjectsT = new MicroObject[size];

        for(int i = 0; i < Main.microObjectsCT.length; i++){
            int rnd = random.nextInt((int)3);
            switch (rnd){
                case 0:
                    Main.microObjectsCT[i] = new MicroObject("ct");
                    break;
                case 1:
                    Main.microObjectsCT[i] = new MicroObjectOne("ct");
                    break;
                case 2:
                    Main.microObjectsCT[i] = new MicroObjectTwo("ct");
                    break;
            }
        }

        for(int i = 0; i < Main.microObjectsT.length; i++){
            int rnd = random.nextInt((int)3);
            switch (rnd){
                case 0:
                    Main.microObjectsT[i] = new MicroObject("t");
                    break;
                case 1:
                    Main.microObjectsT[i] = new MicroObjectOne("t");
                    break;
                case 2:
                    Main.microObjectsT[i] = new MicroObjectTwo("t");
                    break;
            }
        }

        for (MicroObject micro : Main.microObjectsT){
            group.getChildren().add(micro.microGroup);
        }

        for (MicroObject micro : Main.microObjectsCT){
            group.getChildren().add(micro.microGroup);
        }

//        for (MicroObject m : Main.microObjectsCT){
//            System.out.println(m + "\n");
//        }
//
//        for (MicroObject m : Main.microObjectsT){
//            System.out.println(m + "\n");
//        }
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
