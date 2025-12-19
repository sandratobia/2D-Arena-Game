public abstract class Fighter {
    protected String name;
    protected String type;
    protected int health;
    protected int maxHealth;
    protected double x;
    protected double y;
    protected double speed;
    protected Weapon currentWeapon;
    protected Weapon[] availableWeapons;
    protected int playerNumber;

    public Fighter(String name, String type, int health, double speed, int playerNumber) {
        this.name = name;
        this.type = type;
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.playerNumber = playerNumber;
    }
    public abstract String goodAt();

    public void moveUp(double minY) {
        if (y - speed >= minY) {
            y -= speed;
        }
    }

    public void moveDown(double maxY) {
        if (y + speed <= maxY) {
            y += speed;
        }
    }

    public void moveLeft(double minX) {
        if (x - speed >= minX) {
            x -= speed;
        }
    }

    public void moveRight(double maxX) {
        if (x + speed <= maxX) {
            x += speed;
        }
    }
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public boolean isAlive() {

        return health > 0;
    }

    public Projectile shoot(double targetX, double targetY) {
        if (currentWeapon == null) {
            return null;
        }

        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance == 0) {
            dx = playerNumber == 1 ? 1 : -1;
            dy = 0;
        } else {
            dx /= distance;
            dy /= distance;
        }

        return currentWeapon.fire(x, y, dx, dy, playerNumber);
    }

    public void selectWeapon(int index) {
        if (index >= 0 && index < availableWeapons.length) {
            currentWeapon = availableWeapons[index];
        }
    }

    public void switchWeapon() {
        if (availableWeapons.length > 1) {
            for (int i = 0; i < availableWeapons.length; i++) {
                if (availableWeapons[i] == currentWeapon) {
                    currentWeapon = availableWeapons[(i + 1) % availableWeapons.length];
                    break;
                }
            }
        }
    }
    public String getName() {

        return name;
    }
    public String getType() {

        return type;
    }
    public int getHealth() {

        return health;
    }
    public int getMaxHealth() {

        return maxHealth;
    }
    public double getX() {

        return x;
    }
    public double getY() {

        return y;
    }
    public void setX(double x) {

        this.x = x;
    }
    public void setY(double y) {

        this.y = y;
    }
    public double getSpeed() {

        return speed;
    }
    public Weapon getCurrentWeapon() {

        return currentWeapon;
    }
    public Weapon[] getAvailableWeapons() {

        return availableWeapons;
    }
    public int getPlayerNumber() {
        return playerNumber
                ; }

}
