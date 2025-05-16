package spaceinvaders;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image sprite;  // Images of the objects

    protected boolean alive = true;

    // 爆炸相关字段
    protected boolean exploding = false;
    protected long explodeStartTime = 0;
    protected long explodeDuration = 500; // Explosion duration 500ms, adjustable as required

    public GameObject(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        if (sprite != null) {
            this.width = sprite.getWidth(null);
            this.height = sprite.getHeight(null);
        }
    }

    // Update methods that subclasses must implement
    public abstract void update();

    // Drawing methods provided by the parent class
    public void draw(Graphics g) {
        if (sprite != null && alive) {
            g.drawImage(sprite, x, y, null);
        }
    }

    // Get the bounding rectangle of the object for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Getter & Setter
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public Image getSprite() { return sprite; }
    public void setSprite(Image sprite) {
        this.sprite = sprite;
        if (sprite != null) {
            this.width = sprite.getWidth(null);
            this.height = sprite.getHeight(null);
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public boolean isAlive() {
        return alive;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Starts the explosion effect, converts the object to an explosion image, and records the start time.
     * @param explodeSprite Exploded image, e.g. spriteManager.getSprite(‘explode.png’)
     */
    public void startExplode(Image explodeSprite) {
        if (!exploding) {
            exploding = true;
            explodeStartTime = System.currentTimeMillis();
            setSprite(explodeSprite);
        }
    }

    /**
     * Called by a subclass at the end of update to check if the explosion is over.
     * If the explosion duration has been reached, set ALIVE to false.
     */
    protected void handleExplosion() {
        if (exploding) {
            long elapsed = System.currentTimeMillis() - explodeStartTime;
            if (elapsed > explodeDuration) {
                setAlive(false);
            }
        }
    }
}
