package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        universal mainObj = new universal();
        Scanner in = new Scanner(System.in);
        int choice = 1;
        while(choice != 0) {
            System.out.println();
            System.out.println("""
                    Enter action you wanna do:\s
                    1: Add an microObject\s
                    2: Print all teams\s
                    3: Interaction of the micro you choose\s
                    4: Interaction of the two sites A&B\s
                    5: Count the micro based on characteristics you choose\s
                    6: Delete a micro from the team you choose\s
                    7: Call base method:)\s
                    0: Exit""");
            choice = in.nextInt();
            System.out.println();
            switch (choice){
                case 1:
                    mainObj.addMicroObj();
                    break;
                case 2:
                    mainObj.printAll();
                    break;
                case 3:
                    mainObj.interactWithMicro();
                    break;
                case 4:
                    mainObj.InteractInMacro();
                    break;
                case 5:
                    mainObj.countMicro();
                    break;
                case 6:
                    mainObj.deleteMicro();
                    break;
                case 7:
                    MicroObject first = new MicroObject("t");
                    MicroObjectOne second = new MicroObjectOne("t");

                    if(mainObj.callBase(first)){
                        mainObj.callBase(second);
                    }
                    break;
                default:
                    System.out.println("You have chosen the wrong one");
                    break;
            }
        }
    }
}
