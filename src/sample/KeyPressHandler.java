package sample;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    }
}
