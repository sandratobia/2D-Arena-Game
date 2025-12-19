public class Mage extends Fighter{
    public Mage(String name, int playerNumber) {
        super(name, "Mage", 100, 2.5, playerNumber);


        Weapon sorcererBomb = new Weapon("Sorcerer's Bomb", 12, 3.0, 1000);
        Weapon wand = new Weapon("Wand", 10, 4.0, 500);

        this.availableWeapons = new Weapon[]{sorcererBomb, wand};
        this.currentWeapon = wand;
    }
public String goodAt(){
        return "low cooldown";
}
}
