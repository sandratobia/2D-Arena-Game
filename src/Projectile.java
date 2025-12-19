public class Projectile {
    private String name;
    private double speed;
    private int damage;
    private double x;
    private double y;
    private double directionX;
    private double directionY;
    private boolean active;
    private int ownerPlayer;

    public Projectile(String name, double speed, int damage, double x, double y,
                      double directionX, double directionY, int ownerPlayer) {
        this.name = name;
        this.speed = speed;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.directionX = directionX;
        this.directionY = directionY;
        this.active = true;
        this.ownerPlayer = ownerPlayer;
    }

    public void update() {
        x += directionX * speed;
        y += directionY * speed;
    }

    public boolean isOutOfBounds(double maxWidth, double maxHeight) {
        return x < 0 || x > maxWidth || y < 0 || y > maxHeight;
    }

    public String getName() {
        return name;
    }
    public double getSpeed() {
        return speed;
    }
    public int getDamage() {
        return damage;
    }
    public double getX() {
        return x;
    }
    public double getY() {

        return y;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {

        this.active = active;
    }
    public int getOwnerPlayer() {

        return ownerPlayer;
    }
}

