package spaceinvaders;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameController {
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> playerBullets = new ArrayList<>();
    private List<Bullet> enemyBullets = new ArrayList<>();

    private SpriteManager spriteManager;
    private BulletFactory bulletFactory;
    private EnemyFactory enemyFactory;

    // 职责链
    private CollisionHandler playerBulletChain;  // Collision detection chain for player bullets
    private CollisionHandler enemyBulletChain;   // Collision detection chain for enemy bullets

    // Screen size (corresponds to Screen)
    private int screenWidth = 700;
    private int screenHeight = 500;

    // Game status indicator
    private boolean gameOver = false;

    public GameController() {
        // Initialise SpriteManager
        spriteManager = new SpriteManager();
        // Rely on the implementation of the getSprite method in SpriteManager to load images from the images folder.

        // Initialise a factory
        bulletFactory = new BulletFactory(spriteManager);
        enemyFactory = new EnemyFactory(spriteManager, this);

        // Create player (bottom centre of screen)
        player = new Player(screenWidth/2 - 16, screenHeight - 40, spriteManager.getSprite("player.png"));

        // Create enemy array: 3 rows, 7 enemies per row
        // Set initial coordinates and spacing as needed
        int enemyRows = 3;
        int enemyCols = 7;
        int startX = 50;
        int startY = 10;
        int xGap = 110; // horizontal interval between enemies
        int yGap = 60; // vertical space between lines

        for (int row = 0; row < enemyRows; row++) {
            String type = (row == 0) ? "A" : (row == 1 ? "B" : "C");
            for (int col = 0; col < enemyCols; col++) {
                int ex = startX + col * xGap;
                int ey = startY + row * yGap;
                Enemy e = enemyFactory.createEnemy(type, ex, ey);
                enemies.add(e);
            }
        }

        // Building a chain of responsibility
        // Player bullet chain: EnemyCollisionHandler -> BoundaryCollisionHandler
        CollisionHandler enemyCollisionHandler = new EnemyCollisionHandler(spriteManager);
        CollisionHandler boundaryCollisionHandler1 = new BoundaryCollisionHandler(screenWidth, screenHeight);
        enemyCollisionHandler.setNextHandler(boundaryCollisionHandler1);
        playerBulletChain = enemyCollisionHandler;

        // Enemy bullet chain: PlayerCollisionHandler -> BoundaryCollisionHandler
        CollisionHandler playerCollisionHandler = new PlayerCollisionHandler(spriteManager);
        CollisionHandler boundaryCollisionHandler2 = new BoundaryCollisionHandler(screenWidth, screenHeight);
        playerCollisionHandler.setNextHandler(boundaryCollisionHandler2);
        enemyBulletChain = playerCollisionHandler;
    }

    public void update() {
        if (gameOver) {
            // If the game has ended, can wait here to restart the logic
            return;
        }

        // Updating players
        player.update();

        // Update enemies
        for (Enemy e : enemies) {
            e.update();
        }

        // Updated bullets (player bullets and enemy bullets)
        for (Bullet b : playerBullets) {
            b.update();
        }
        for (Bullet b : enemyBullets) {
            b.update();
        }

        // collision detection
        handleCollisions();

        // Clean up invalidated objects (isAlive = false)
        cleanupObjects();

        // Check for game over conditions: if the player survives, if the enemies reach the bottom, or if they are all destroyed.
        if (!player.isAlive()) {
            gameOver = true;
            System.out.println("Player is dead. Game Over!");
            resetGame();
        }

        // If all enemies are eliminated, it can also be considered as a pass or a game win, where it is simple to restart the game
        if (enemies.isEmpty()) {
            gameOver = true;
            System.out.println("All enemies destroyed! You Win!");
            resetGame();
        }
    }

    public void render(Graphics g) {
        // Drawing Players
        if (player.isAlive()) {
            player.draw(g);
        }

        // Drawing the enemy
        for (Enemy e : enemies) {
            e.draw(g);
        }

        // Drawing bullets
        for (Bullet b : playerBullets) {
            b.draw(g);
        }
        for (Bullet b : enemyBullets) {
            b.draw(g);
        }
    }

    private void handleCollisions() {
        // Player bullet collision detection chain with enemy
        // Call playerBulletChain.handleCollision for each player bullet and each enemy
        for (Bullet pb : playerBullets) {
            if (!pb.isEnemyBullet()) { // Make sure it's a player bullet.
                for (Enemy e : enemies) {
                    playerBulletChain.handleCollision(pb, e);
                }
            }
        }

        // Enemy bullet collision detection chain with player
        for (Bullet eb : enemyBullets) {
            if (eb.isEnemyBullet()) {
                enemyBulletChain.handleCollision(eb, player);
            }
        }
    }

    private void cleanupObjects() {
        // Clearance of ineffective bullets
        Iterator<Bullet> pbIter = playerBullets.iterator();
        while (pbIter.hasNext()) {
            if (!pbIter.next().isAlive()) {
                pbIter.remove();
            }
        }

        Iterator<Bullet> ebIter = enemyBullets.iterator();
        while (ebIter.hasNext()) {
            if (!ebIter.next().isAlive()) {
                ebIter.remove();
            }
        }

        // Clearing ineffective enemies
        Iterator<Enemy> eIter = enemies.iterator();
        while (eIter.hasNext()) {
            if (!eIter.next().isAlive()) {
                eIter.remove();
            }
        }
    }

    // Methods called in Player for use by KeyInputHandler or other logic
    public void playerMoveLeft(boolean moveLeft) {
        player.setMovingLeft(moveLeft);
    }

    public void playerMoveRight(boolean moveRight) {
        player.setMovingRight(moveRight);
    }

    public void playerFire() {
        if (!gameOver && player.isAlive()) {
            // Creating Player Bullets
            Bullet b = bulletFactory.createBullet("player", player.getX() + player.getWidth()/2, player.getY());
            playerBullets.add(b);
        }
    }

    // Called when an enemy fires a bullet (triggered in an Enemy update or in the GameController timer)
    public void enemyFire(Enemy enemy) {
        if (!gameOver && enemy.isAlive()) {
            Bullet b = bulletFactory.createBullet("enemy", enemy.getX() + enemy.getWidth()/2, enemy.getY()+enemy.getHeight());
            if (b != null) {
                enemyBullets.add(b);
            }
        }
    }

    private void resetGame() {
        // Simple reset logic: reinitialise the game state
        // Can restart the game after a certain delay or handle restart logic in Main/Screen.
        // Example: set gameOver to true and wait for the GameController to be recreated externally.

        // Or reset immediately
        // Empty the list
        enemies.clear();
        playerBullets.clear();
        enemyBullets.clear();
        player.setAlive(true);
        player.setX(screenWidth/2 - 16);
        player.setY(screenHeight - 40);

        // Re-create enemy arrays
        int enemyRows = 3;
        int enemyCols = 7;
        int startX = 50;
        int startY = 10;
        int xGap = 110;
        int yGap = 60;

        for (int row = 0; row < enemyRows; row++) {
            String type = (row == 0) ? "A" : (row == 1 ? "B" : "C");
            for (int col = 0; col < enemyCols; col++) {
                int ex = startX + col * xGap;
                int ey = startY + row * yGap;
                Enemy e = enemyFactory.createEnemy(type, ex, ey);
                enemies.add(e);
            }
        }

        gameOver = false;
    }
}
