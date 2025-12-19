public class Weapon {
    private String name;
    private int damage;
    private double projectileSpeed;
    private long cooldownTime;
    private long lastFireTime;

    public Weapon(String name, int damage, double projectileSpeed, long cooldownTime) {
        this.name = name;
        this.damage = damage;
        this.projectileSpeed = projectileSpeed;
        this.cooldownTime = cooldownTime;
        this.lastFireTime = 0;
    }

    public boolean canFire() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastFireTime) >= cooldownTime;
    }

    public Projectile fire(double x, double y, double directionX, double directionY, int playerNumber) {
        if (!canFire()) {
            return null;
        }
        lastFireTime = System.currentTimeMillis();
        return new Projectile(name + " projectile", projectileSpeed, damage,
                x, y, directionX, directionY, playerNumber);
    }
    public String getName() {

        return name;
    }
    public int getDamage() {

        return damage;
    }
    public double getProjectileSpeed() {

        return projectileSpeed;
    }
    public long getCooldownTime() {
        return cooldownTime;
    }

}
