import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Set;

public class Main extends Application {
    private Stage window;
    private Scene welcomeScene;
    private Scene characterSelectionScene;
    private Scene battleScene;
    private String player1CharacterType = "";
    private String player2CharacterType = "";
    private GameManager gameManager;
    private AnimationTimer gameLoop;
    private Canvas canvas;
    private GraphicsContext gc;
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private static final double ARENA_WIDTH = 900;
    private static final double ARENA_HEIGHT = 600;
    private Label p1HealthLabel;
    private Label p2HealthLabel;
    private Label p1WeaponLabel;
    private Label p2WeaponLabel;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        createWelcomeScene();
        createCharacterSelectionScene();
        window.setScene(welcomeScene);
        window.setTitle("War Game");
        window.setMaximized(true);
        window.show();
    }
    private void createWelcomeScene() {
        Label welcomeLabel = new Label("⚔ ARENA WAR GAME ⚔");
        welcomeLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label questionLabel = new Label("Real-time combat. Choose your warrior wisely.");
        questionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #ecf0f1;");

        Button readyButton = new Button("ENTER ARENA ☠");
        readyButton.setStyle("-fx-font-size: 18px; -fx-padding: 15 40; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        readyButton.setOnAction(e -> {
            window.setScene(characterSelectionScene);
            window.setMaximized(false);
            window.setMaximized(true);
        });

        VBox welcomeLayout = new VBox(25);
        welcomeLayout.setAlignment(Pos.CENTER);
        welcomeLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #0f0c29, #302b63, #24243e); -fx-padding: 20;");
        welcomeLayout.getChildren().addAll(welcomeLabel, questionLabel, readyButton);

        welcomeScene = new Scene(welcomeLayout);
    }

    private void createCharacterSelectionScene() {
        Label titleLabel = new Label("SELECT YOUR FIGHTERS");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label player1Label = new Label("PLAYER 1");
        player1Label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #3498db;");

        Label p1Info = new Label("WASD - Move\nSPACE - Shoot\nQ - Switch Weapon");
        p1Info.setStyle("-fx-font-size: 12px; -fx-text-fill: #95a5a6;");

        Button p1Warrior = createCharacterButton("Warrior", "HP: 120 | Speed: 3.0\nPistol & Rocks");
        Button p1Mage = createCharacterButton("Mage", "HP: 100 | Speed: 2.5\nWand & Bomb");
        Button p1Archer = createCharacterButton("Archer", "HP: 80 | Speed: 3.5\nBow & Bomb");

        p1Warrior.setOnAction(e -> {
            player1CharacterType = "Warrior";
            updateButtonStyles(p1Warrior, p1Mage, p1Archer);
        });
        p1Mage.setOnAction(e -> {
            player1CharacterType = "Mage";
            updateButtonStyles(p1Mage, p1Warrior, p1Archer);
        });
        p1Archer.setOnAction(e -> {
            player1CharacterType = "Archer";
            updateButtonStyles(p1Archer, p1Warrior, p1Mage);
        });

        VBox player1Layout = new VBox(12);
        player1Layout.setAlignment(Pos.CENTER);
        player1Layout.setStyle("-fx-padding: 25; -fx-background-color: rgba(52, 152, 219, 0.2); -fx-background-radius: 10;");
        player1Layout.getChildren().addAll(player1Label, p1Info, p1Warrior, p1Mage, p1Archer);

        Label player2Label = new Label("PLAYER 2");
        player2Label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");

        Label p2Info = new Label("Arrow Keys - Move\nENTER - Shoot\nRSHIFT - Switch Weapon");
        p2Info.setStyle("-fx-font-size: 12px; -fx-text-fill: #95a5a6;");

        Button p2Warrior = createCharacterButton("Warrior", "HP: 120 | Speed: 3.0\nPistol & Rocks");
        Button p2Mage = createCharacterButton("Mage", "HP: 100 | Speed: 2.5\nWand & Bomb");
        Button p2Archer = createCharacterButton("Archer", "HP: 80 | Speed: 3.5\nBow & Bomb");

        p2Warrior.setOnAction(e -> {
            player2CharacterType = "Warrior";
            updateButtonStyles(p2Warrior, p2Mage, p2Archer);
        });
        p2Mage.setOnAction(e -> {
            player2CharacterType = "Mage";
            updateButtonStyles(p2Mage, p2Warrior, p2Archer);
        });
        p2Archer.setOnAction(e -> {
            player2CharacterType = "Archer";
            updateButtonStyles(p2Archer, p2Warrior, p2Mage);
        });

        VBox player2Layout = new VBox(12);
        player2Layout.setAlignment(Pos.CENTER);
        player2Layout.setStyle("-fx-padding: 25; -fx-background-color: rgba(231, 76, 60, 0.2); -fx-background-radius: 10;");
        player2Layout.getChildren().addAll(player2Label, p2Info, p2Warrior, p2Mage, p2Archer);

        HBox playersLayout = new HBox(40);
        playersLayout.setAlignment(Pos.CENTER);
        playersLayout.getChildren().addAll(player1Layout, player2Layout);

        Button startBattleButton = new Button("START BATTLE");
        startBattleButton.setStyle("-fx-font-size: 20px; -fx-padding: 15 50; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        startBattleButton.setOnAction(e -> {
            if (!player1CharacterType.isEmpty() && !player2CharacterType.isEmpty()) {
                startBattle();
            } else {
                showAlert("Character Selection Required", "Both players must choose a character!");
            }
        });

        VBox selectionLayout = new VBox(30);
        selectionLayout.setAlignment(Pos.CENTER);
        selectionLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #0f0c29, #302b63, #24243e); -fx-padding: 20;");
        selectionLayout.getChildren().addAll(titleLabel, playersLayout, startBattleButton);

        characterSelectionScene = new Scene(selectionLayout);
    }

    private Button createCharacterButton(String name, String stats) {
        Button btn = new Button(name + "\n" + stats);
        btn.setStyle("-fx-min-width: 180; -fx-min-height: 70; -fx-font-size: 14px; -fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold;");
        return btn;
    }

    private void updateButtonStyles(Button selected, Button other1, Button other2) {
        selected.setStyle("-fx-min-width: 180; -fx-min-height: 70; -fx-font-size: 14px; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        other1.setStyle("-fx-min-width: 180; -fx-min-height: 70; -fx-font-size: 14px; -fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold;");
        other2.setStyle("-fx-min-width: 180; -fx-min-height: 70; -fx-font-size: 14px; -fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold;");
    }

    private void startBattle() {
        gameManager = new GameManager(ARENA_WIDTH, ARENA_HEIGHT);
        Fighter player1 = createFighter(player1CharacterType, 1);
        Fighter player2 = createFighter(player2CharacterType, 2);
        gameManager.initializePlayers(player1, player2); //sets players at their position
        createBattleScene();
        window.setScene(battleScene);
        window.setMaximized(false);
        window.setMaximized(true);
        startGameLoop();
    }

    private Fighter createFighter(String type, int playerNumber) {
        String name = playerNumber == 1 ? "Player 1" : "Player 2";
        switch (type) {
            case "Warrior":
                return new Warrior(name, playerNumber);
            case "Mage":
                return new Mage(name, playerNumber);
            case "Archer":
                return new Archer(name, playerNumber);
            default:
                return new Warrior(name, playerNumber);
        }
    }

    private void createBattleScene() {
        BorderPane battleLayout = new BorderPane();
        battleLayout.setStyle("-fx-background-color: #1a1a2e;");

        HBox topHUD = createTopHUD();
        battleLayout.setTop(topHUD);

        canvas = new Canvas(ARENA_WIDTH, ARENA_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        StackPane canvasPane = new StackPane(canvas);
        canvasPane.setStyle("-fx-background-color: #16213e;");
        battleLayout.setCenter(canvasPane);

        battleScene = new Scene(battleLayout);  // Let it resize dynamically
        battleScene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        battleScene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    private HBox createTopHUD() {
        Label p1Name = new Label(gameManager.getPlayer1().getName() + ": " + gameManager.getPlayer1().getType() );
        p1Name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #3498db;");

        p1HealthLabel = new Label();
        p1HealthLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #27ae60;");

        p1WeaponLabel = new Label();
        p1WeaponLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #f39c12;");

        VBox p1Box = new VBox(5, p1Name, p1HealthLabel, p1WeaponLabel);
        p1Box.setStyle("-fx-padding: 10; -fx-background-color: rgba(52, 152, 219, 0.2);");

        Label p2Name = new Label(gameManager.getPlayer2().getName() + ": " + gameManager.getPlayer2().getType() );
        p2Name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");

        p2HealthLabel = new Label();
        p2HealthLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #27ae60;");

        p2WeaponLabel = new Label();
        p2WeaponLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #f39c12;");

        VBox p2Box = new VBox(5, p2Name, p2HealthLabel, p2WeaponLabel);
        p2Box.setStyle("-fx-padding: 10; -fx-background-color: #E74C3C33;");
        p2Box.setAlignment(Pos.TOP_RIGHT);

        HBox hud = new HBox();
        hud.setStyle("-fx-background-color: #0f0c29; -fx-padding: 10;");
        HBox.setHgrow(p1Box, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(p2Box, javafx.scene.layout.Priority.ALWAYS);
        hud.getChildren().addAll(p1Box, p2Box);

        return hud;
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {//works like a while loop
            private boolean gameEnded = false;

            @Override
            public void handle(long now) {
                if (gameEnded) return;

                handleInput();
                gameManager.update();
                render();
                updateHUD();

                Fighter p1 = gameManager.getPlayer1();
                Fighter p2 = gameManager.getPlayer2();

                if (p1.getHealth() <= 0 || p2.getHealth() <= 0) {
                    gameEnded = true;
                    Fighter winner;
                    if (p1.getHealth() > 0) {
                        winner = p1;
                    } else {
                        winner = p2;
                    }
                    // Show alert while game is still visible
                    javafx.application.Platform.runLater(() -> endGameWithWinner(winner));
                    // Stop the loop after showing alert
                    stop();
                }
            }
        };
        gameLoop.start();
    }
    //Get references to both players so you can control them and check their positions.
    private void handleInput() {
        Fighter p1 = gameManager.getPlayer1();
        Fighter p2 = gameManager.getPlayer2();
//checks if W key is currently pressed
        if (pressedKeys.contains(KeyCode.W)) {//Move up and boundaries so player can't leave the arena
            gameManager.movePlayer1("UP", 0, ARENA_WIDTH, 0, ARENA_HEIGHT);
        }
        if (pressedKeys.contains(KeyCode.S)) {
            gameManager.movePlayer1("DOWN", 0, ARENA_WIDTH, 0, ARENA_HEIGHT);
        }
        if (pressedKeys.contains(KeyCode.A)) {
            gameManager.movePlayer1("LEFT", 0, ARENA_WIDTH, 0, ARENA_HEIGHT);
        }
        if (pressedKeys.contains(KeyCode.D)) {
            gameManager.movePlayer1("RIGHT", 0, ARENA_WIDTH, 0, ARENA_HEIGHT);
        }
        if (pressedKeys.contains(KeyCode.SPACE)) {//When spacebar is pressed, player 1 shoots toward player 2's position
            gameManager.player1Shoot(p2.getX(), p2.getY());
        }// After pressing Q to switch the weapon, we remove Q from pressedKeys
// This makes sure the weapon only switches once per key press
        if (pressedKeys.contains(KeyCode.Q)) {
            p1.switchWeapon();
            pressedKeys.remove(KeyCode.Q);
        }

        if (pressedKeys.contains(KeyCode.UP)) {
            gameManager.movePlayer2("UP", 0, ARENA_WIDTH, 0, ARENA_HEIGHT);
        }
        if (pressedKeys.contains(KeyCode.DOWN)) {
            gameManager.movePlayer2("DOWN", 0, ARENA_WIDTH, 0, ARENA_HEIGHT);
        }
        if (pressedKeys.contains(KeyCode.LEFT)) {
            gameManager.movePlayer2("LEFT", 0, ARENA_WIDTH, 0, ARENA_HEIGHT);
        }
        if (pressedKeys.contains(KeyCode.RIGHT)) {
            gameManager.movePlayer2("RIGHT", 0, ARENA_WIDTH, 0, ARENA_HEIGHT);
        }
        if (pressedKeys.contains(KeyCode.ENTER)) {
            gameManager.player2Shoot(p1.getX(), p1.getY());
        }
        if (pressedKeys.contains(KeyCode.SHIFT)) {
            p2.switchWeapon();
            pressedKeys.remove(KeyCode.SHIFT);
        }
    }

    private void render() {
        gc.setFill(Color.web("#001540"));
        gc.fillRect(0, 0, ARENA_WIDTH, ARENA_HEIGHT);

        gc.setStroke(Color.DARKBLUE);//color of lines

        for (int i = 0; i < ARENA_WIDTH; i += 50) {
            gc.strokeLine(i, 0, i, ARENA_HEIGHT);
        }
        for (int i = 0; i < ARENA_HEIGHT; i += 50) {
            gc.strokeLine(0, i, ARENA_WIDTH, i);
        }

        for (Projectile proj : gameManager.getProjectiles()) {
            drawProjectile(proj);
        }

        Fighter p1 = gameManager.getPlayer1();
        Fighter p2 = gameManager.getPlayer2();

        drawPlayerFacingTarget(p1, p2, Color.RED, Color.RED);
        drawPlayerFacingTarget(p2, p1, Color.BLUEVIOLET, Color.BLUEVIOLET);
    }
    private void drawProjectile(Projectile proj) {
        double x = proj.getX();
        double y = proj.getY();
        Color projectileColor;

        if (proj.getOwnerPlayer() == 1) {
            projectileColor = Color.CYAN;
        } else {
            projectileColor = Color.ORANGERED;
        }

        String weaponName = proj.getName().toLowerCase();

        if (weaponName.contains("rock")) {
            gc.setFill(Color.GRAY);
            gc.fillOval(x - 6, y - 6, 12, 12);//center the circle and size 12x12
        } else if (weaponName.contains("pistol")) {
            gc.setFill(Color.YELLOWGREEN);
            gc.fillOval(x - 3, y - 3, 6, 6);//draws filled circles
        } else if (weaponName.contains("wand")) {
            gc.setFill(Color.YELLOW);
            gc.fillPolygon( //draws custom shapes
                    new double[]{x, x - 6, x, x + 6},
                    new double[]{y - 8, y, y + 8, y}, 4
            );
            gc.setFill(Color.HOTPINK);
            gc.setGlobalAlpha(0.5);//adds transparency for a glow effect
            gc.fillOval(x - 10, y - 10, 20, 20);
            gc.setGlobalAlpha(1.0);//need to be added .. without it all projectiles will have the 0.5 transparency

        } else if (weaponName.contains("bomb")) {
            gc.setFill(Color.BLACK);
            gc.fillOval(x - 8, y - 8, 16, 16);
            gc.setStroke(Color.ORANGE);
            gc.strokeLine(x, y - 8, x + 3, y - 12);
        } else if (weaponName.contains("bow")) {
            gc.setStroke(Color.WHITE);
            gc.strokeLine(x - 8, y, x + 8, y);
        }
    }


    private void drawPlayerFacingTarget(Fighter fighter, Fighter target, Color mainColor, Color darkColor) {
        double x = fighter.getX();
        double y = fighter.getY();
        double dx = target.getX() - x;
        boolean facingRight = dx >= 0;

        gc.save();
        gc.translate(x, y);

        if (!facingRight) {
            gc.scale(-1, 1);
        }

        gc.setGlobalAlpha(0.3);
        gc.setFill(Color.BLACK);
        gc.fillOval(-18, 20, 36, 10);
        gc.setGlobalAlpha(1.0);

        gc.setFill(mainColor);
        gc.fillOval(-15, -15, 30, 30);

        gc.setStroke(darkColor);
        gc.setLineWidth(3);
        gc.strokeOval(-15, -15, 30, 30);
        gc.setFill(Color.BLACK);
        gc.fillOval(5, -3, 6, 6);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        String typeSymbol = getTypeSymbol(fighter.getType());
        gc.fillText(typeSymbol, -5, 5);
        gc.save();
        if (!facingRight) {
            gc.scale(-1, 1);
        }

        double healthPercent = (double) fighter.getHealth() / fighter.getMaxHealth();
        String healthText = fighter.getHealth() + " / " + fighter.getMaxHealth();

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Lato", FontWeight.BOLD, 10));
        gc.fillText(healthText, -20, -35);

        gc.setFill(Color.DARKGRAY);
        gc.fillRect(-20, -30, 40, 5);

        if (healthPercent > 0.6) {
            gc.setFill(Color.LIMEGREEN);
        } else if (healthPercent > 0.3) {
            gc.setFill(Color.ORANGE);
        } else {
            gc.setFill(Color.RED);
        }
        gc.fillRect(-20, -30, 40 * healthPercent, 5);
        gc.restore();
        gc.restore();
    }

    private String getTypeSymbol(String type) {
        switch (type) {
            case "Warrior":
                return "⚔";
            case "Mage":
                return "⚡";
            case "Archer":
                return "🏹";
            default:
                return "";
        }
    }
    private void updateHealthColor(Label label, int health, int maxHealth) {
        double percent = health/(double)maxHealth;
        if (percent > 0.5) {
            label.setStyle("-fx-font-size: 15px; -fx-text-fill: #27ae60;");
        } else if (percent > 0.3) {
            label.setStyle("-fx-font-size: 15px; -fx-text-fill: #f39c13;");
        } else {
            label.setStyle("-fx-font-size: 15px; -fx-text-fill: #e74c3c;");
        }
    }
    private void updateHUD() {
        Fighter p1 = gameManager.getPlayer1();
        Fighter p2 = gameManager.getPlayer2();

        p1HealthLabel.setText("HP: " + p1.getHealth() + "/" + p1.getMaxHealth());
        p1WeaponLabel.setText("⚡ " + p1.getCurrentWeapon().getName() + " (DMG: " + p1.getCurrentWeapon().getDamage() + ")");

        p2HealthLabel.setText("HP: " + p2.getHealth() + "/" + p2.getMaxHealth());
        p2WeaponLabel.setText("⚡ " + p2.getCurrentWeapon().getName() + " (DMG: " + p2.getCurrentWeapon().getDamage() + ")");

        updateHealthColor(p1HealthLabel, p1.getHealth(), p1.getMaxHealth());
        updateHealthColor(p2HealthLabel, p2.getHealth(), p2.getMaxHealth());
    }



    private void endGameWithWinner(Fighter winner) {
        int winnerNumber = winner.getPlayerNumber();
        String winnerText = "PLAYER " + winnerNumber + " HAS WON!";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("🏆 GAME OVER 🏆");
        alert.setHeaderText(winnerText);
        alert.setContentText("Winner: " + winner.getName() + " (" + winner.getType() +
                "\nFinal HP: " + winner.getHealth() + "/" + winner.getMaxHealth() +
                "\n\nCongratulations Player " + winnerNumber + "!");
        alert.initOwner(window);


        alert.showAndWait().ifPresent(response -> {
            createCharacterSelectionScene();
            player1CharacterType="";
            player2CharacterType="";
            window.setScene(characterSelectionScene);
            window.setMaximized(false);
            window.setMaximized(true);

        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
