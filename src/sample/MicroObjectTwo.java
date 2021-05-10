package sample;

public class MicroObjectTwo extends MicroObjectOne{
    private double staminaWasteKoefHp;
    private double hpKoef;

    public MicroObjectTwo(String side){
        super(side);
        this.setLvl(3);
        this.setDamage(30);
        this.setKevlar(100);

        this.staminaWasteKoefHp = 1.5;
        this.hpKoef = 0.5;
    }

    @Override
    public String toString() {
        return "Unit#3: { " +
                "site: " + super.getSide() +
                ", hp: " + super.getHp() +
                ", lvl: " + characterLevel +
                ", kevlar" + super.getKevlar() +
                ", damage" + super.getDamage() +
                " }";
    }
}

