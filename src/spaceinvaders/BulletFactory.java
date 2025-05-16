package spaceinvaders;

import java.awt.Image;
import java.util.Random;

public class BulletFactory {
    private SpriteManager spriteManager;
    private Random random = new Random(); // Used to generate random numbers

    public BulletFactory(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }

    /**
     * Creating a bullet object
     * @param type ‘player’ or ‘enemy’
     * @param x The initial x coordinate of the bullet.
     * @param y The initial y coordinate of the bullet.
     * - To player bullets:
     * - move up, dy is negative, e.g. -5
     * - use playerbullet.png
     * - For enemy bullets:
     * - move down, dy is positive, e.g. -3 * - use bullet.png, bullet.1.png
     * - Use bullet.png, bullet.1.png for two-frame animation.
     */
    public Bullet createBullet(String type, int x, int y) {
        if ("player".equals(type)) {
            Image playerBulletSprite = spriteManager.getSprite("playerbullet.png");
            // Player bullets without animation frames
            return new Bullet(x, y, playerBulletSprite, -5, false, null);
        } else if ("enemy".equals(type)) {
            // 50% chance of firing a bullet
            if (random.nextDouble() > 0.5) {
                return null; // No bullets fired
            }
            Image frame1 = spriteManager.getSprite("bullet.png");
            Image frame2 = spriteManager.getSprite("bullet.1.png");
            Image[] frames = {frame1, frame2};
            return new Bullet(x, y, frame1, 1, true, frames);
        }

        // Unknown type, returns the default player bullet or throws an exception
        Image defaultSprite = spriteManager.getSprite("playerbullet.png");
        return new Bullet(x, y, defaultSprite, -3, false, null);
    }
}
