package spaceinvaders;

import java.awt.Image;

public class Enemy extends GameObject {
    private Image[] animationFrames;
    private int currentFrameIndex = 0;
    private long lastFrameSwitchTime = 0;
    private long frameSwitchInterval = 500; // Switches one map every 500 milliseconds

    private int dx = 1; // Initial horizontal travelling speed (moving to the right)
    private int moveDownDistance = 10; // Downward travelling distance
    private long lastShootTime = 0;
    private long shootInterval = 5000; // One bullet every five seconds

    private int screenWidth = 700;
    private int screenHeight = 500;

    private GameController controller; // add a citation

    public Enemy(int x, int y, Image[] animationFrames, GameController controller) {
        super(x, y, animationFrames[0]);
        this.animationFrames = animationFrames;
        this.controller = controller;
    }

    @Override
    public void update() {
        x += dx;

        if (x + width >= screenWidth) {
            x = screenWidth - width;
            moveDownAndReverse();
        }

        if (x <= 0) {
            x = 0;
            moveDownAndReverse();
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameSwitchTime > frameSwitchInterval) {
            currentFrameIndex = (currentFrameIndex + 1) % animationFrames.length;
            setSprite(animationFrames[currentFrameIndex]);
            lastFrameSwitchTime = currentTime;
        }

        // 发射子弹逻辑
        if (currentTime - lastShootTime > shootInterval) {
            shoot();
            lastShootTime = currentTime;
        }
        handleExplosion();
    }

    private void moveDownAndReverse() {
        y += moveDownDistance;
        dx = -dx;
        if (y + height >= screenHeight) {
            // If the enemy reaches the bottom, game over logic can be handled here by the controller
        }
    }

    private void shoot() {
        // The actual call to the GameController to fire the bullet method
        if (controller != null && isAlive()) {
            controller.enemyFire(this);
        }
    }
}
