package sample;

import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class MicroObject implements Comparable<MicroObject>, Cloneable {
    private String characterSite;
    public int characterLevel;
    private double characterSpeed;
    private int damage;
    private int characterKevlar;
    private int characterHp;
    private boolean alive = true;
    private boolean active = false;
    private boolean inMacroSite = false;
    public long lastHealtTime = 0;

    private double charCordsX;
    private double charCordsY;
    private double destinationX;
    private double destinationY;
    public int id;
    public double defaultSpeed;
    public int defaultDamage;

    protected GridPane microWrapper = new GridPane();
    protected Rectangle healthBar = new Rectangle();
    protected Label microLabel;
    protected Group microGroup;
    protected Image microImage;
    protected ImageView microImageView;
    public static int idCounter = 0;

    public int getLvl(){
        return this.characterLevel;
    }

    public int getHp(){
        return this.characterHp;
    }
    public void setHp(int newHp){
        this.characterHp = newHp;
    }

    public void setSpeed(double newSpeed){
        this.characterSpeed = newSpeed;
    }

    public double getSpeed(){
        return this.characterSpeed;
    }

    public void setLvl(int newLvl){
        this.characterLevel = newLvl;
    }

    public String getSide(){
        return this.characterSite;
    }

    public void changeAlive(){
        this.alive = !this.alive;
    }

    public boolean getAlive(){
        return this.alive;
    }

    public int getKevlar(){
        return this.characterKevlar;
    }

    public void setKevlar(int newKevlar){
        this.characterKevlar = newKevlar;
    }

    public int getDamageProp(){
        return this.damage;
    }

    public void setDamage(int newDamage){
        this.damage = newDamage;
    }

    public double getX(){
        return this.charCordsX;
    }

    public void setXCoord(double newX){
        this.charCordsX = newX;
    }

    public double getY(){
        return this.charCordsY;
    }

    public void setYCoord(double newY){
        this.charCordsY = newY;
    }

    public double getXDest(){
        return this.destinationX;
    }

    public void setXCoordDest(double newX){
        this.destinationX = newX;
    }

    public double getYDest(){
        return this.destinationY;
    }

    public void setYCoordDest(double newY){
        this.destinationY = newY;
    }

    public void changeActive(){
        this.active = !this.active;
    }

    public boolean getActive(){
        return this.active;
    }

    public void changeInMacro(Boolean value){
        this.inMacroSite = value;
    }

    public boolean getInMacro(){
        return this.inMacroSite;
    }

    public MicroObject(String side) throws FileNotFoundException {
        this.characterSite = side;
        this.characterLevel = 1;
        this.characterSpeed = 20;
        defaultSpeed = 20;
        this.damage = 5;
        this.defaultDamage = 5;
        this.characterKevlar = 0;
        this.characterHp = 100;
        this.id = MicroObject.idCounter;
        this.destinationX = (double)Main.random.nextInt(3700);
        this.destinationY = (double)Main.random.nextInt(3000);

        this.microLabel = new Label("Lvl: 1, hp: "  + this.getHp());
        if(side.equals("t")){
            this.charCordsX = 1030.0 + Main.random.nextInt(100);
            this.charCordsY = 2700.0 + Main.random.nextInt(75);

            this.microWrapper.getRowConstraints().add(new RowConstraints(100));
            this.microWrapper.getRowConstraints().add(new RowConstraints(35));
            this.microWrapper.getRowConstraints().add(new RowConstraints(1));
            this.microWrapper.getRowConstraints().add(new RowConstraints(5));

            this.microWrapper.getColumnConstraints().add(new ColumnConstraints(100));

            microImage = new Image(new FileInputStream("src/source/t_1.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.charCordsX);
            this.microWrapper.setTranslateY(this.charCordsY);
        }else{
            this.charCordsX = 2100.0 + Main.random.nextInt(100);
            this.charCordsY = 500.0 + Main.random.nextInt(75);

            microImage = new Image(new FileInputStream("src/source/ct_1.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.charCordsX);
            this.microWrapper.setTranslateY(this.charCordsY);
        }
        this.microLabel.setStyle("-fx-border-color: white; -fx-padding: 3px");
        this.microLabel.setTextFill(Color.WHITE);
        this.microImageView.setPreserveRatio(true);
        this.microImageView.setFitHeight(100.0);
        this.microImageView.setFitWidth(90.0);
        Rectangle setFit = new Rectangle();
        setFit.setWidth(100);
        setFit.setHeight(1);
        setFit.setFill(Color.TRANSPARENT);

        healthBar.setHeight(5);
        healthBar.setWidth(100);
        healthBar.setFill(Color.RED);

        this.microWrapper.setOnMouseClicked((event) -> {
            this.changeActive();
            if(this.getActive()) {
                String styleWrapper = "-fx-border-color: yellow;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-style: solid;";
                this.microWrapper.setStyle(styleWrapper);
                Main.showInfoActive(this, true);
            }else {
                this.microWrapper.setStyle(" ");
                Main.showInfoActive(this, false);
            }
        });

        microWrapper.setHalignment(this.microImageView, HPos.RIGHT);
        microWrapper.setValignment(this.microImageView, VPos.CENTER);
        microWrapper.setHalignment(this.microLabel, HPos.CENTER);
        microWrapper.setValignment(this.microLabel, VPos.CENTER);

        microWrapper.add(this.microImageView, 0, 0);
        microWrapper.add(this.microLabel, 0, 1);
        microWrapper.add(setFit, 0, 2);
        microWrapper.add(this.healthBar, 0, 3);

        this.microGroup = new Group(this.microWrapper);

        MicroObject.idCounter++;
    }

    public void interactInMacro(MicroObject enemy){
        if(!(enemy.getHp() <= 7)){
            enemy.getDamage(this);
        }else{
            enemy.changeAlive();
        }

        if(!(this.getHp() <= 7)){
            this.getDamage(enemy);
        }else{
            this.changeAlive();
        }
    }

    public void print(){
        System.out.println("Object's site: " + this.characterSite +
                " his HP and Armour: " + this.characterHp + ", " + this.characterKevlar +
                " he can damage to " + this.damage + "HP" +
                " his lvl is: " + this.characterLevel //+ " id: " + this.objId
        );
    }

    public void run(double x, double y){
        this.setXCoord(this.getX() + this.getSpeed() * x);
        this.setYCoord(this.getY() + this.getSpeed() * y);

        this.microWrapper.setTranslateX(this.getX());
        this.microWrapper.setTranslateY(this.getY());
    }

    public String interactWith(MicroObject enemy) {
        if(!(this.getHp() <= 7)){
            this.getDamage(enemy);
            if(enemy.getLvl() == 3){
                if(enemy.lastHealtTime == 0){
                    enemy.lastHealtTime = new Date().getTime();
                }
                if((new Date().getTime() - enemy.lastHealtTime) > 250){
                    enemy.useHealt();
                }
            }
        }

        if(this.getHp() <= 7){
            this.changeAlive();
            AudioClip die = new AudioClip(new File("src/audio/die_rofl.mp3").toURI().toString());
            die.play();
            return "this died";
        }
        return "err";
    }

    public void useHealt() {
    }

    public void getDamage(MicroObject enemy){
        switch (this.characterLevel){
            case 1:
                this.characterHp -= enemy.getDamageProp();
                this.healthBar.setWidth(this.characterHp);
                break;
            case 2:
                this.characterHp -= Math.round(enemy.getDamageProp() / 1.25);
                this.healthBar.setWidth(this.characterHp);
                this.characterKevlar -= 10;
                break;
            case 3:
                this.characterHp -= Math.round(enemy.getDamageProp() / 1.5);
                this.healthBar.setWidth(this.characterHp);
                this.characterKevlar -= 7;
                break;
        }
    }

    public void run(boolean toMacro){

        if(this.getActive() || this.getInMacro()){
            return;
        }

        if(Math.round(this.getX()) == Math.round(this.destinationX) && !toMacro){

            this.destinationX = (double)Main.random.nextInt(3650);

        }else if(Math.round(this.getY()) == Math.round(this.destinationY) && !toMacro){

            this.destinationY = (double)Main.random.nextInt(2850);

        }else{
            double xDiff = this.destinationX - this.getX();
            double yDiff = this.destinationY - this.getY();
            //handling x difference
            if(xDiff < 0){

                if(Math.abs(xDiff) <= this.characterSpeed){
                    this.setXCoord(this.getX() - (Math.abs(xDiff / 10)));
                }else{
                    this.setXCoord(this.getX() - (this.characterSpeed / 10));
                }

            }else if(xDiff > 0){

                if(xDiff <= this.characterSpeed){
                    this.setXCoord(this.getX() + (xDiff / 10));
                }else{
                    this.setXCoord(this.getX() + (this.characterSpeed / 10));
                }

            }
            //handling y difference
            if(yDiff < 0){

                if(Math.abs(yDiff) <= this.characterSpeed){
                    this.setYCoord(this.getY() - (Math.abs(yDiff / 10)));
                }else{
                    this.setYCoord(this.getY() - (this.characterSpeed / 10));
                }

            }else if(yDiff > 0){

                if(yDiff <= this.characterSpeed){
                    this.setYCoord(this.getY() + (yDiff / 10));
                }else{
                    this.setYCoord(this.getY() + (this.characterSpeed / 10));
                }

            }
        }

        if(!this.getActive()){
            this.microWrapper.setTranslateX(this.charCordsX);
            this.microWrapper.setTranslateY(this.charCordsY);
        }
    }

    public void sayToChild(){
        System.out.println("Hello child :)");
    }

    public void sayToChild(String msg){
        System.out.println("Follow this advice: " + msg);
    }

    public void changeHp(int hp){
        this.characterHp = hp;
    }

    @Override
    public boolean equals(Object obj) {
//        if (obj == null || getClass() != obj.getClass()) return false;
        MicroObject character = (MicroObject) obj;
        return this.id == character.id;
//        return ((this.getLvl() == (character.getLvl())) && (this.getHp() == character.getHp()));
    }

    @Override
    public String toString() {
        return "Unit#1: { " +
                "id: " + id +
                ", site: " + characterSite +
                ", hp: " + characterHp +
                ", lvl: " + characterLevel +
                ", kevlar: " + characterKevlar +
                ", damage: " + damage +
                ", x: " + charCordsX +
                ", y: " + charCordsY +
                " }";
    }

    @Override
    public int compareTo(MicroObject obj) {
        if (this.characterLevel < obj.characterLevel) return -1;
        if (this.characterLevel > obj.characterLevel) return 1;
        return 0;
    }

    public static Comparator<MicroObject> lvlComparator = new Comparator<MicroObject>() {
        @Override
        public int compare(MicroObject obj1, MicroObject obj2) {
            if (obj1.characterLevel < obj2.characterLevel) return -1;
            if (obj1.characterLevel > obj2.characterLevel) return 1;
            return 0;
        }


    };

    @Override
    public Object clone() throws CloneNotSupportedException {
        MicroObject tmp = null;
        try {
            tmp = new MicroObject(this.characterSite);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    public void callBaseMethodWithMsg(){
        System.out.println("not this one");
    }

    public void callBaseMethod(){
        System.out.println("not this one");
    }

}
