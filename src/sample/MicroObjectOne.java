package sample;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    public MicroObjectOne(String side, double destX, double destY, double coordX, double coordY, int hp) throws FileNotFoundException {
        super(side, destX, destY, coordX, coordY, hp);
        this.setLvl(2);
        this.setDamage(7);
        this.defaultDamage = 7;
        this.setKevlar(75);

        this.staminaRegenKoef = 0.7;
        this.speedKoef = 1.5;
        this.staminaWasteKoef = 1;

        this.microLabel = new Label("Lvl: 2, hp: "  + this.getHp());
        this.microLabel.setStyle("-fx-text-inner-color: white;");
        if(side.equals("t")){
            microImage = new Image(new FileInputStream("src/source/t_2.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.getX());
            this.microWrapper.setTranslateY(this.getY());
        }else{
            microImage = new Image(new FileInputStream("src/source/ct_2.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.getX());
            this.microWrapper.setTranslateY(this.getY());
        }
        this.microLabel.setStyle("-fx-border-color: white; -fx-padding: 3px");
        this.microLabel.setTextFill(Color.WHITE);
        this.microImageView.setPreserveRatio(true);
        this.microImageView.setFitHeight(100.0);
        this.microImageView.setFitWidth(90.0);

        this.microWrapper.setOnMouseClicked((event) -> {
            this.changeActive();
            if(this.getActive()) {
                String styleWrapper = "-fx-border-color: yellow;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-style: solid;";
                System.out.println("call showInfo");
                this.microWrapper.setStyle(styleWrapper);
                Main.showInfoActive(this, true);
            }else {
                this.microWrapper.setStyle(" ");
                Main.showInfoActive(this, false);
            }
        });

        Rectangle setFit = new Rectangle();
        setFit.setWidth(100);
        setFit.setHeight(1);
        setFit.setFill(Color.TRANSPARENT);

        this.microWrapper.getChildren().clear();
        microWrapper.setHalignment(this.microImageView, HPos.RIGHT);
        microWrapper.setValignment(this.microImageView, VPos.CENTER);
        microWrapper.setHalignment(this.microLabel, HPos.CENTER);
        microWrapper.setValignment(this.microLabel, VPos.CENTER);

        microWrapper.add(this.microImageView, 0, 0);
        microWrapper.add(this.microLabel, 0, 1);
        microWrapper.add(setFit, 0, 2);
        microWrapper.add(this.healthBar, 0, 3);
        this.microGroup = new Group(this.microWrapper);
    }

    public MicroObjectOne(String side) throws FileNotFoundException {
        super(side);
        this.setLvl(2);
        this.setDamage(7);
        this.defaultDamage = 7;
        this.setKevlar(75);

        this.staminaRegenKoef = 0.7;
        this.speedKoef = 1.5;
        this.staminaWasteKoef = 1;

        this.microLabel = new Label("Lvl: 2, hp: "  + this.getHp());
        this.microLabel.setStyle("-fx-text-inner-color: white;");
        if(side.equals("t")){
            microImage = new Image(new FileInputStream("src/source/t_2.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.getX());
            this.microWrapper.setTranslateY(this.getY());
        }else{
            microImage = new Image(new FileInputStream("src/source/ct_2.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.getX());
            this.microWrapper.setTranslateY(this.getY());
        }
        this.microLabel.setStyle("-fx-border-color: white; -fx-padding: 3px");
        this.microLabel.setTextFill(Color.WHITE);
        this.microImageView.setPreserveRatio(true);
        this.microImageView.setFitHeight(100.0);
        this.microImageView.setFitWidth(90.0);

        this.microWrapper.setOnMouseClicked((event) -> {
            this.changeActive();
            if(this.getActive()) {
                String styleWrapper = "-fx-border-color: yellow;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-style: solid;";
                System.out.println("call showInfo");
                this.microWrapper.setStyle(styleWrapper);
                Main.showInfoActive(this, true);
            }else {
                this.microWrapper.setStyle(" ");
                Main.showInfoActive(this, false);
            }
        });

        Rectangle setFit = new Rectangle();
        setFit.setWidth(100);
        setFit.setHeight(1);
        setFit.setFill(Color.TRANSPARENT);

        this.microWrapper.getChildren().clear();
        microWrapper.setHalignment(this.microImageView, HPos.RIGHT);
        microWrapper.setValignment(this.microImageView, VPos.CENTER);
        microWrapper.setHalignment(this.microLabel, HPos.CENTER);
        microWrapper.setValignment(this.microLabel, VPos.CENTER);

        microWrapper.add(this.microImageView, 0, 0);
        microWrapper.add(this.microLabel, 0, 1);
        microWrapper.add(setFit, 0, 2);
        microWrapper.add(this.healthBar, 0, 3);
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
                ", damage" + super.getDamageProp() +
                ", x: " + super.getX() +
                ", y: " + super.getY() +
                " }";
    }

}
