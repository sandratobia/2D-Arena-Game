public class Archer extends Fighter{
    public Archer(String name, int playerNumber) {
        super(name, "Archer", 80, 3.5, playerNumber);

        Weapon bomb = new Weapon("Bomb", 14, 2.0, 1200);
        Weapon bow = new Weapon("Bow", 12, 6.0, 600);

        this.availableWeapons = new Weapon[]{bomb, bow};
        this.currentWeapon = bow;
    }
    public String goodAt(){
        return "Fast";
    }

}
