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

public class MicroObjectTwo extends MicroObjectOne{
    private double staminaWasteKoefHp;
    private double hpKoef;

    public MicroObjectTwo(String side) throws FileNotFoundException {
        super(side);
        this.setLvl(3);
        this.setDamage(9);
        this.setKevlar(100);

        this.staminaWasteKoefHp = 1.5;
        this.hpKoef = 0.5;

        this.microLabel = new Label("Lvl: 3, hp: "  + this.getHp());
        this.microLabel.setStyle("-fx-text-inner-color: white;");
        if(side.equals("t")){
            microImage = new Image(new FileInputStream("src/source/t_3.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.getX());
            this.microWrapper.setTranslateY(this.getY());
        }else{
            microImage = new Image(new FileInputStream("src/source/ct_3.png"));
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
                this.microWrapper.setStyle(styleWrapper);
            }else {
                this.microWrapper.setStyle(" ");
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

    @Override
    public String toString() {
        return "Unit#3: { " +
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

