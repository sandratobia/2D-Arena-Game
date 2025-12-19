public class Warrior extends Fighter{
    public Warrior(String name, int playerNumber) {
        super(name, "Warrior", 120, 3.0, playerNumber);

        Weapon pistol = new Weapon("Pistol", 10, 4.0, 400);
        Weapon rocks = new Weapon("Rocks", 8, 2.0, 800);

        this.availableWeapons = new Weapon[]{pistol, rocks};
        this.currentWeapon = rocks;
    }
public String goodAt(){
    return "High health";
}
}
