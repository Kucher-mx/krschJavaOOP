package sample;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MicroObjectOne extends MicroObject{
    private double staminaRegenKoef;
    private int staminaWasteKoef;
    private double speedKoef;
    Scanner in = new Scanner(System.in);

    public double getSpeedKoef(){
        return this.speedKoef;
    }

    public int getStaminaWasteKoef(){
        return this.staminaWasteKoef;
    }

    public double getStaminaRegenKoef(){
        return this.staminaRegenKoef;
    }

    public MicroObjectOne(String side) throws FileNotFoundException {
        super(side);
        this.setLvl(2);
        this.setDamage(25);
        this.setKevlar(75);

        this.staminaRegenKoef = 0.7;
        this.speedKoef = 1.5;
        this.staminaWasteKoef = 1;

        this.microLabel = new Label("Lvl: 2, hp: "  + this.getHp());
        if(side.equals("t")){
            microImage = new Image(new FileInputStream("src/source/t_2.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.getX());
            this.microWrapper.setTranslateY(this.getY());

//            this.microImageView.setX(MicroObject.coordsTX);
//            this.microImageView.setY(MicroObject.coordsTY);
//            this.microLabel.setTranslateX(MicroObject.coordsTX);
//            this.microLabel.setTranslateY(MicroObject.coordsTY - 25.0);
        }else{
            microImage = new Image(new FileInputStream("src/source/ct_2.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.getX());
            this.microWrapper.setTranslateY(this.getY());

//            this.microImageView.setX(MicroObject.coordsCTX);
//            this.microImageView.setY(MicroObject.coordsCTY);
//            this.microLabel.setTranslateX(MicroObject.coordsCTX);
//            this.microLabel.setTranslateY(MicroObject.coordsCTY - 25.0);
        }
        this.microImageView.setPreserveRatio(true);
        this.microImageView.setFitHeight(120.0);
        this.microImageView.setFitWidth(90.0);

        this.microWrapper.setOnMouseClicked((event) -> {
            this.changeActive();
            if(this.getActive()) {
                String styleWrapper = "-fx-border-color: red;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-style: dotted;";
                this.microWrapper.setStyle(styleWrapper);
            }else {
                this.microWrapper.setStyle(" ");
            }
        });

        this.microWrapper.getChildren().clear();
        this.microWrapper.getChildren().addAll(this.microImageView, this.microLabel);
        this.microGroup = new Group(this.microWrapper);
//        this.microGroup = new Group(this.microImageView, this.microLabel);
    }

    public void callBaseMethod(){
        super.sayToChild();
    }
    public void callBaseMethodWithMsg(){
        System.out.println("Enter your message");
        String msg = in.nextLine();
        super.sayToChild(msg);
    }
    @Override
    public String toString() {
        return "Unit#2: { " +
                "id: " + id +
                "site: " + super.getSide() +
                ", hp: " + super.getHp() +
                ", lvl: " + characterLevel +
                ", kevlar" + super.getKevlar() +
                ", damage" + super.getDamage() +
                ", x: " + super.getX() +
                ", y: " + super.getY() +
                " }";
    }

}
