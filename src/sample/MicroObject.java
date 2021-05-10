package sample;

import java.util.Comparator;

public class MicroObject implements Comparable<MicroObject>, Cloneable {
    private String characterSite;
    public int characterLevel;
    private double characterSpeed;
    private int damage;
    private int characterKevlar;
    private int characterHp;
    private boolean alive = true;

    private int allAmmo = 100;
    private int chamber = 25;
    private int stamina = 100;
    private long charCordsX = 0;
    private long charCordsY = 0;
    private double angle = 0;

    //    public int objId = MicroObject.id;
//    public static int id = 0;
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

    public MicroObject(String side){
        this.characterSite = side;
        this.characterLevel = 1;
        this.characterSpeed = 20;
        this.damage = 15;
        this.characterKevlar = 0;
        this.characterHp = 100;
//        MicroObject.id++;
    }

    public void print(){
        System.out.println("Object's site: " + this.characterSite +
                " his HP and Armour: " + this.characterHp + ", " + this.characterKevlar +
                " he can damage to " + this.damage + "HP" +
                " his lvl is: " + this.characterLevel //+ " id: " + this.objId
        );
    }


    public String interactWith(MicroObject enemy){
        while(!(enemy.getHp() <= 0 || this.getHp() <= 0)){
            System.out.println("interacting: " + this + " with: " + enemy);
            enemy.getDamage(this);
            this.getDamage(enemy);
        }
        if (enemy.getHp() <= 7){
            enemy.changeAlive();
            System.out.println("Enemy has died, end of the battle");
            return "t";
        }else if(this.getHp() <= 7){
            this.changeAlive();
            System.out.println("This has died, end of the battle");
            return "ct";
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
        this.charCordsX = Math.round(Math.cos(this.angle) * this.characterSpeed);
        this.charCordsY = Math.round(Math.sin(this.angle) * this.characterSpeed);

    }

    public void reload(){
        this.chamber = 25;
        this.allAmmo -= 25;
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
        return ((this.getLvl() == (character.getLvl())) && (this.getHp() == character.getHp()));
    }

    @Override
    public String toString() {
        return "Unit#1: { " +
                "site: " + characterSite +
                ", hp: " + characterHp +
                ", lvl: " + characterLevel +
                ", kevlar" + characterKevlar +
                ", damage" + damage +
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
        tmp = new MicroObject(this.characterSite);
        return tmp;
    }

    public void callBaseMethodWithMsg(){
        System.out.println("not this one");
    }

    public void callBaseMethod(){
        System.out.println("not this one");
    }

}
