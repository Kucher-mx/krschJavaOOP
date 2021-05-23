package sample;

import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

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

    private int stamina = 100;
    private double charCordsX;
    private double charCordsY;
    private double destinationX;
    private double destinationY;
    public int id;

    protected VBox microWrapper = new VBox();
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

    public void setSide(String newSide){
        this.characterSite = newSide;
    }

    public int getKevlar(){
        return this.characterKevlar;
    }

    public void setKevlar(int newKevlar){
        this.characterKevlar = newKevlar;
    }

    public int getDamage(){
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

    public static double coordsTX = 1300.0;
    public static double coordsTY = 2700.0;
    public static double coordsCTX = 2050.0;
    public static double coordsCTY = 570.0;

    public MicroObject(String side) throws FileNotFoundException {
        this.characterSite = side;
        this.characterLevel = 1;
        this.characterSpeed = 20;
        this.damage = 50;
        this.characterKevlar = 0;
        this.characterHp = 100;
        this.id = MicroObject.idCounter;
        this.destinationX = (double)Main.random.nextInt(3700);
        this.destinationY = (double)Main.random.nextInt(3000);

        this.microLabel = new Label("Lvl: 1, hp: "  + this.getHp());
        if(side.equals("t")){
            this.charCordsX = MicroObject.coordsTX;
            this.charCordsY = MicroObject.coordsTY;

            microImage = new Image(new FileInputStream("src/source/t_1.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.charCordsX);
            this.microWrapper.setTranslateY(this.charCordsY);

            MicroObject.coordsTX += 75;
        }else{
            this.charCordsX = MicroObject.coordsCTX;
            this.charCordsY = MicroObject.coordsCTY;

            microImage = new Image(new FileInputStream("src/source/ct_1.png"));
            this.microImageView = new ImageView(microImage);

            this.microWrapper.setTranslateX(this.charCordsX);
            this.microWrapper.setTranslateY(this.charCordsY);

            MicroObject.coordsCTX += 75;
        }
        this.microLabel.setStyle("-fx-border-color: white;" + "-fx-text-inner-color: white;");
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

        this.microWrapper.getChildren().addAll(this.microImageView, this.microLabel);
        this.microGroup = new Group(this.microWrapper);

//        this.microGroup = new Group(this.microImageView, this.microLabel);
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

//        if(this.getHp() <= 7){
//            this.changeAlive();
//        }
//
//        if(enemy.getHp() <= 7){
//            enemy.changeAlive();
//        }
    }

    public void print(){
        System.out.println("Object's site: " + this.characterSite +
                " his HP and Armour: " + this.characterHp + ", " + this.characterKevlar +
                " he can damage to " + this.damage + "HP" +
                " his lvl is: " + this.characterLevel //+ " id: " + this.objId
        );
    }

    public void run(double x, double y){
        this.setXCoord(this.getX() + x);
        this.setYCoord(this.getY() + y);

        this.microWrapper.setTranslateX(this.charCordsX);
        this.microWrapper.setTranslateY(this.charCordsY);
    }

    public String interactWith(MicroObject enemy) {
        if(!(this.getHp() <= 7)){
            this.getDamage(enemy);
        }

        if(this.getHp() <= 7){
            this.changeAlive();
            AudioClip die = new AudioClip(new File("src/audio/die_rofl.mp3").toURI().toString());
            die.play();
            return "this died";
        }
        return "err";
    }

    public void getDamage(MicroObject enemy){
        switch (this.characterLevel){
            case 1:
                this.characterHp -= enemy.getDamage();
                break;
            case 2:
                this.characterHp -= Math.round(enemy.getDamage() / 1.25);
                this.characterKevlar -= 10;
                break;
            case 3:
                this.characterHp -= Math.round(enemy.getDamage() / 1.5);
                this.characterKevlar -= 7;
                break;
        }
    }

    public void run(){

        if(this.getActive() || this.getInMacro()){
            return;
        }

        if(Math.round(this.getX()) == Math.round(this.destinationX)){

            this.destinationX = (double)Main.random.nextInt(3700);

        }else if(Math.round(this.getY()) == Math.round(this.destinationY)){

            this.destinationY = (double)Main.random.nextInt(3000);

        }else{
            double xDiff = this.destinationX - this.getX();
            double yDiff = this.destinationY - this.getY();
            //handling x difference
            if(xDiff < 0){

                if(Math.abs(xDiff) <= this.characterSpeed){
                    this.setXCoord(this.getX() - (Math.abs(xDiff)/30));
                }else{
                    this.setXCoord(this.getX() - (this.characterSpeed/30));
                }

            }else if(xDiff > 0){

                if(xDiff <= this.characterSpeed){
                    this.setXCoord(this.getX() + (xDiff/30));
                }else{
                    this.setXCoord(this.getX() + (this.characterSpeed/30));
                }

            }
            //handling y difference
            if(yDiff < 0){

                if(Math.abs(yDiff) <= this.characterSpeed){
                    this.setYCoord(this.getY() - (Math.abs(yDiff)/10));
                }else{
                    this.setYCoord(this.getY() - (this.characterSpeed/10));
                }

            }else if(yDiff > 0){

                if(yDiff <= this.characterSpeed){
                    this.setYCoord(this.getY() + (yDiff/10));
                }else{
                    this.setYCoord(this.getY() + (this.characterSpeed/10));
                }

            }
        }

//        this.microImageView.setX(this.charCordsX);
//        this.microImageView.setY(this.charCordsY);
//        this.microLabel.setTranslateX(this.charCordsX);
//        this.microLabel.setTranslateY(this.charCordsY);
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
                "site: " + characterSite +
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
