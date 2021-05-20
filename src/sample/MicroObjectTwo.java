package sample;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MicroObjectTwo extends MicroObjectOne{
    private double staminaWasteKoefHp;
    private double hpKoef;

    public MicroObjectTwo(String side) throws FileNotFoundException {
        super(side);
        this.setLvl(3);
        this.setDamage(30);
        this.setKevlar(100);

        this.staminaWasteKoefHp = 1.5;
        this.hpKoef = 0.5;

        this.microLabel = new Label("Lvl: 3, hp: "  + this.getHp());
        if(side.equals("t")){
            microImage = new Image(new FileInputStream("src/source/t_3.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.getX());
            this.microWrapper.setTranslateY(this.getY());

//            this.microImageView.setX(this.getX());
//            this.microImageView.setY(this.getY());
//            this.microLabel.setTranslateX(this.getX());
//            this.microLabel.setTranslateY(this.getY() - 25.0);
        }else{
            microImage = new Image(new FileInputStream("src/source/ct_3.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.getX());
            this.microWrapper.setTranslateY(this.getY());

//            this.microImageView.setX(this.getX());
//            this.microImageView.setY(this.getY());
//            this.microLabel.setTranslateX(this.getX());
//            this.microLabel.setTranslateY(this.getY() - 25.0);
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

