package sample;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyPressHandler implements EventHandler<KeyEvent>
{

    @Override
    public void handle(KeyEvent event) {

        System.out.println(event.getCode());

//        if( wolf==null )return;
//        if( !wolf.isActive() )return;
//
//        if (event.getCode().equals(KeyCode.UP) )
//        {
//            wolf.move(0,-10);
//        }
//
//        if (event.getCode().equals(KeyCode.RIGHT) )
//        {
//            wolf.move(10,0);
//        }
//
//        if (event.getCode().equals(KeyCode.DOWN) )
//        {
//            wolf.move(0,10);
//        }
//
//        if (event.getCode().equals(KeyCode.LEFT) )
//        {
//            wolf.move(-10,0);
//        }
    }
}
