import java.util.ArrayList;
import java.util.Iterator;

public class GameManager {
    private Fighter player1;
    private Fighter player2;
    private ArrayList<Projectile> projectiles;
    private boolean gameOver;
    private Fighter winner;
    private double arenaWidth;
    private double arenaHeight;
    private static final double COLLISION_RADIUS = 20.0;

    public GameManager(double arenaWidth, double arenaHeight) {
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;
        this.projectiles = new ArrayList();
        this.gameOver = false;
    }

    public void initializePlayers(Fighter p1, Fighter p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.player1.setX(this.arenaWidth * 0.25);
        this.player1.setY(this.arenaHeight * 0.5);
        this.player2.setX(this.arenaWidth * 0.75);
        this.player2.setY(this.arenaHeight * 0.5);
    }

    public void update() {
        if (!this.gameOver) {
            Iterator<Projectile> iterator = this.projectiles.iterator();

            while(iterator.hasNext()) {
                Projectile proj = iterator.next();
                proj.update();
                if (proj.isOutOfBounds(this.arenaWidth, this.arenaHeight)) {
                    iterator.remove();
                } else {
                    Fighter target = proj.getOwnerPlayer() == 1 ? this.player2 : this.player1;
                    if (this.checkCollision(proj, target)) {
                        target.takeDamage(proj.getDamage());
                        iterator.remove();

                        if (!target.isAlive()) {
                            this.gameOver = true;
                            this.winner = proj.getOwnerPlayer() == 1 ? this.player1 : this.player2;
                        }
                    }
                }
            }

        }
    }

    private boolean checkCollision(Projectile proj, Fighter fighter) {
        double dx = proj.getX() - fighter.getX();
        double dy = proj.getY() - fighter.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < 20.0;
    }

    public void player1Shoot(double targetX, double targetY) {
        Projectile proj = this.player1.shoot(targetX, targetY);
        if (proj != null) {
            this.projectiles.add(proj);
        }

    }

    public void player2Shoot(double targetX, double targetY) {
        Projectile proj = this.player2.shoot(targetX, targetY);
        if (proj != null) {
            this.projectiles.add(proj);
        }

    }

    public void movePlayer1(String direction, double minX, double maxX, double minY, double maxY) {
        switch (direction.toUpperCase()) {
            case "UP" -> this.player1.moveUp(minY);
            case "DOWN" -> this.player1.moveDown(maxY);
            case "LEFT" -> this.player1.moveLeft(minX);
            case "RIGHT" -> this.player1.moveRight(maxX);
        }

    }

    public void movePlayer2(String direction, double minX, double maxX, double minY, double maxY) {
        switch (direction.toUpperCase()) {
            case "UP" -> this.player2.moveUp(minY);
            case "DOWN" -> this.player2.moveDown(maxY);
            case "LEFT" -> this.player2.moveLeft(minX);
            case "RIGHT" -> this.player2.moveRight(maxX);
        }

    }

    public boolean isGameOver() {
        return this.gameOver;
    }
    public Fighter getPlayer1() {
        return this.player1;
    }

    public Fighter getPlayer2() {
        return this.player2;
    }

    public ArrayList<Projectile> getProjectiles() {
        return this.projectiles;
    }



    public Fighter getWinner() {
        return this.winner;
    }

    public double getArenaWidth() {

        return this.arenaWidth;
    }

    public double getArenaHeight() {
        return this.arenaHeight;
    }}