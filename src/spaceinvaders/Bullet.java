package spaceinvaders;

import java.awt.Image;

public class Bullet extends GameObject {
    private int dy; // Vertical distance moved per frame (positive numbers down, negative numbers up)
    private Image[] animationFrames;
    private int currentFrameIndex = 0;
    private long lastFrameSwitchTime = 0;
    private long frameSwitchInterval = 300; // 300ms switching one frame

    // Identifies if it is an enemy bullet, used to decide whether to play an animation and other logic
    private boolean enemyBullet;

    // Assuming a screen height of 500, the actual value can subsequently be read from the GameController
    private int screenHeight = 500;

    /**
     * Generic bullet class constructor
     * @param x The initial x coordinate of the bullet.
     * @param y The initial y coordinate of the bullet.
     * @param sprite Initial image of the bullet (player bullet image for player bullets, one of the frames of enemy bullets for enemy bullets)
     * @param dy Direction and speed of movement (negative values go up, positive values go down)
     * @param enemyBullet whether or not it is an enemy bullet (used to decide whether to switch animation frames or not)
     * @param animationFrames If the enemy bullet has an animation, pass in two frame images; player bullets pass in null or a single frame.
     */
    public Bullet(int x, int y, Image sprite, int dy, boolean enemyBullet, Image[] animationFrames) {
        super(x, y, sprite);
        this.dy = dy;
        this.enemyBullet = enemyBullet;
        this.animationFrames = animationFrames;
    }

    @Override
    public void update() {
        // Moving Bullets
        y += dy;

        // Animated frame switching for enemy bullets
        if (enemyBullet && animationFrames != null && animationFrames.length > 1) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFrameSwitchTime > frameSwitchInterval) {
                currentFrameIndex = (currentFrameIndex + 1) % animationFrames.length;
                setSprite(animationFrames[currentFrameIndex]);
                lastFrameSwitchTime = currentTime;
            }
        }

        // If the bullet is out of screen range, it will be judged and removed by the BoundaryCollisionHandler and does not need to be handled here
    }

    // Used to allow external code to determine whether the bullet is an enemy bullet or not
    public boolean isEnemyBullet() {
        return enemyBullet;
    }
}
