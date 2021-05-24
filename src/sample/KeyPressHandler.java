package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class KeyPressHandler implements EventHandler<KeyEvent>{

    @Override
    public void handle(KeyEvent event) {

        for(int i = 0; i < Main.microObjectsCT.size(); i++){
            if(Main.microObjectsCT.size() > 0){
                if(Main.microObjectsCT.get(i).getActive()){
                    double speed = Main.microObjectsCT.get(i).getSpeed();
                    if (event.getCode().equals(KeyCode.W)){
                        Main.microObjectsCT.get(i).run(0,-(speed));
                    }

                    if (event.getCode().equals(KeyCode.D)){
                        Main.microObjectsCT.get(i).run((speed),0);
                    }

                    if (event.getCode().equals(KeyCode.S)){
                        Main.microObjectsCT.get(i).run(0,(speed));
                    }

                    if (event.getCode().equals(KeyCode.A)){
                        Main.microObjectsCT.get(i).run(-(speed),0);
                    }
                }
            }
        }

        for(int i = 0; i < Main.microObjectsT.size(); i++){
            if(Main.microObjectsT.size() > 0){
                if(Main.microObjectsT.get(i).getActive()){
                    double speed = Main.microObjectsT.get(i).getSpeed();
                    if (event.getCode().equals(KeyCode.W)){
                        Main.microObjectsT.get(i).run(0,-(speed));
                    }

                    if (event.getCode().equals(KeyCode.D)){
                        Main.microObjectsT.get(i).run((speed),0);
                    }

                    if (event.getCode().equals(KeyCode.S)){
                        Main.microObjectsT.get(i).run(0,(speed));
                    }

                    if (event.getCode().equals(KeyCode.A)){
                        Main.microObjectsT.get(i).run(-(speed),0);
                    }
                }
            }
        }

        if (event.getCode().equals(KeyCode.I)){
            Dialog<Boolean> dialog = new Dialog<>();
            dialog.setTitle("Spawn Micro");
            Button button = new Button("Spawn");
            Label spawnLabel = new Label();
            spawnLabel.setText("Choose characteristics of the unit:");

            RadioButton first = new RadioButton("First level");
            RadioButton second = new RadioButton("Second level");
            RadioButton third = new RadioButton("Third level");

            ToggleGroup radioGroupLvl = new ToggleGroup();
            first.setToggleGroup(radioGroupLvl);
            second.setToggleGroup(radioGroupLvl);
            third.setToggleGroup(radioGroupLvl);

            RadioButton tSide = new RadioButton("t side");
            RadioButton ctSide = new RadioButton("ct side");

            ToggleGroup radioGroupSide = new ToggleGroup();
            tSide.setToggleGroup(radioGroupSide);
            ctSide.setToggleGroup(radioGroupSide);

            GridPane dialogPane = new GridPane();

            dialogPane.getRowConstraints().add(new RowConstraints(50));
            dialogPane.getRowConstraints().add(new RowConstraints(50));
            dialogPane.getRowConstraints().add(new RowConstraints(50));

            dialogPane.getColumnConstraints().add(new ColumnConstraints(100));
            dialogPane.getColumnConstraints().add(new ColumnConstraints(100));
            dialogPane.getColumnConstraints().add(new ColumnConstraints(100));

            dialogPane.add(spawnLabel, 0, 0, 3, 1);
            dialogPane.add(tSide, 0, 1);
            dialogPane.add(ctSide, 1, 1);
            dialogPane.add(button, 2, 1);
            dialogPane.add(first, 0, 2);
            dialogPane.add(second, 1, 2);
            dialogPane.add(third, 2, 2);

            dialogPane.setHalignment(spawnLabel, HPos.CENTER);
            dialogPane.setValignment(spawnLabel, VPos.CENTER);
            dialogPane.setHalignment(button, HPos.CENTER);
            dialogPane.setValignment(button, VPos.CENTER);

            dialogPane.setStyle("-fx-padding: 35px 0 0 40px;");

            dialogPane.setHgap(10);
            dialogPane.setVgap(12);

            dialog.getDialogPane().setPrefSize(400, 250);
            dialog.getDialogPane().getChildren().add(dialogPane);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    RadioButton lvlRadioValue = (RadioButton)radioGroupLvl.getSelectedToggle();
                    RadioButton sideRadioValue = (RadioButton)radioGroupSide.getSelectedToggle();
                    MicroObject createdMicro = null;
                    try {
                        if(sideRadioValue.getText().equals("ct side")){
                            if(lvlRadioValue.getText().equals("First level")){
                                createdMicro = new MicroObject("ct");
                            }else if(lvlRadioValue.getText().equals("Second level")){
                                createdMicro = new MicroObjectOne("ct");
                            }else if(lvlRadioValue.getText().equals("Third level")){
                                createdMicro = new MicroObjectTwo("ct");
                            }
                            Main.microObjectsCT.add(createdMicro);
                        }else if(sideRadioValue.getText().equals("t side")){
                            if(lvlRadioValue.getText().equals("First level")){
                                createdMicro = new MicroObject("t");
                            }else if(lvlRadioValue.getText().equals("Second level")){
                                createdMicro = new MicroObjectOne("t");
                            }else if(lvlRadioValue.getText().equals("Third level")){
                                createdMicro = new MicroObjectTwo("t");
                            }
                            Main.microObjectsT.add(createdMicro);
                        }else {
                            spawnLabel.setText("Choose char.");
                            dialog.show();
                        }
                        Main.group.getChildren().add(createdMicro.microGroup);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    dialog.setResult(Boolean.TRUE);
                }
            });

            dialog.show();
        }

        if (event.getCode().equals(KeyCode.R)){
            ArrayList<MicroObject> microsToDelete = new ArrayList<MicroObject>();
            for(MicroObject unit : Main.microObjectsCT){
                if(unit.getActive()){
                    microsToDelete.add(unit);
                }
            }

            for(MicroObject unit : Main.microObjectsT){
                if(unit.getActive()){
                    microsToDelete.add(unit);
                }
            }

            for(MicroObject unitToDelete : microsToDelete){
                Main.group.getChildren().remove(unitToDelete.microGroup);
                if(unitToDelete.getSide().equals("ct")){
                    Main.microObjectsCT.remove(unitToDelete);
                }else{
                    Main.microObjectsT.remove(unitToDelete);
                }
            }
        }

        if (event.getCode().equals(KeyCode.ESCAPE)){
            for(MicroObject unit : Main.microObjectsCT){
                if(unit.getActive()){
                    unit.changeActive();
                    unit.microWrapper.setStyle(" ");
                }
            }

            for(MicroObject unit : Main.microObjectsT){
                if(unit.getActive()){
                    unit.changeActive();
                    unit.microWrapper.setStyle(" ");
                }
            }
        }

        if (event.getCode().equals(KeyCode.C)){
            for(MicroObject unit : Main.microObjectsCT){
                if(unit.getActive()){
                    unit.changeActive();
                    unit.setSpeed(unit.getSpeed() * 2);
                    unit.microWrapper.setStyle("");
                    int rndSite = Main.random.nextInt(1);
                    if(rndSite == 0){
                        unit.setXCoordDest(Main.random.nextInt(50) + 2820);
                        unit.setYCoordDest(Main.random.nextInt(30) + 420);
                    }else{
                        unit.setXCoordDest(Main.random.nextInt(50) + 600);
                        unit.setYCoordDest(Main.random.nextInt(30) + 230);
                    }
                    Main.toMacro = true;
                    Main.timeToCapture = 5000;
                }
            }

            for(MicroObject unit : Main.microObjectsT){
                if(unit.getActive()){
                    int rndSite = Main.random.nextInt(1);
                    unit.setSpeed(unit.getSpeed() * 1.5);
                    unit.changeActive();
                    unit.microWrapper.setStyle("");
                    if(rndSite == 0){
                        unit.setXCoordDest(Main.random.nextInt(50) + 2820);
                        unit.setYCoordDest(Main.random.nextInt(30) + 420);
                    }else{
                        unit.setXCoordDest(Main.random.nextInt(50) + 600);
                        unit.setYCoordDest(Main.random.nextInt(30) + 230);
                    }
                    Main.toMacro = true;
                    Main.timeToCapture = 5000;
                }
            }
        }

        if (event.getCode().equals(KeyCode.V)){

        }
    }
}
