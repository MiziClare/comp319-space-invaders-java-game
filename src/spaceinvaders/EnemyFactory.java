package spaceinvaders;

import java.awt.Image;

public class EnemyFactory {
    private SpriteManager spriteManager;
    private GameController controller; // New member variables added
    // Modify the constructor to add GameController parameters
    public EnemyFactory(SpriteManager spriteManager, GameController controller) {
        this.spriteManager = spriteManager;
        this.controller = controller;
    }

    /**
     * Create enemies with different icons based on type.
     * type "A" -> alien10.png, alien10.1.png
     * type "B" -> alien12.png, alien12.1.png
     * type "C" -> alien20.png, alien20.1.png
     *
     * x, y are the initial positions of the enemy.
     */
    public Enemy createEnemy(String type, int x, int y) {
        Image frame1 = null;
        Image frame2 = null;

        switch (type) {
            case "A":
                frame1 = spriteManager.getSprite("alien10.png");
                frame2 = spriteManager.getSprite("alien10.1.png");
                break;
            case "B":
                frame1 = spriteManager.getSprite("alien12.png");
                frame2 = spriteManager.getSprite("alien12.1.png");
                break;
            case "C":
                frame1 = spriteManager.getSprite("alien20.png");
                frame2 = spriteManager.getSprite("alien20.1.png");
                break;
            default:
                frame1 = spriteManager.getSprite("alien10.png");
                frame2 = spriteManager.getSprite("alien10.1.png");
        }

        Image[] frames = { frame1, frame2 };
        return new Enemy(x, y, frames, controller);
    }
}
