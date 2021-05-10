package sample;

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

    public MicroObjectOne(String side){
        super(side);
        this.setLvl(2);
        this.setDamage(25);
        this.setKevlar(75);

        this.staminaRegenKoef = 0.7;
        this.speedKoef = 1.5;
        this.staminaWasteKoef = 1;
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
                "site: " + super.getSide() +
                ", hp: " + super.getHp() +
                ", lvl: " + characterLevel +
                ", kevlar" + super.getKevlar() +
                ", damage" + super.getDamage() +
                " }";
    }

}
