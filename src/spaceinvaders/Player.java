package spaceinvaders;

import java.awt.Image;

public class Player extends GameObject {
    private int dx = 0; // Horizontal velocity
    private int speed = 3; // Player movement speed

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean firing = false;

    private GameController controller; // Reference GameController to call playerFire()

    public Player(int x, int y, Image sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        // Horizontal movement speed is determined by the current movement status
        if (movingLeft && !movingRight) {
            dx = -speed;
        } else if (movingRight && !movingLeft) {
            dx = speed;
        } else {
            dx = 0;
        }

        // Update Location
        x += dx;

        // Ensure that the player does not move out of screen range, assuming a screen width of 700.
        if (x < 0) {
            x = 0;
        }
        if (x + width > 700) {
            x = 700 - width;
        }

        // Firing Bullet Logic: If firing is true, then fire a bullet once.
        if (firing) {
            fire();
            firing = false; // Reset after one launch
        }
        handleExplosion();
    }

    private void fire() {
        // Firing bullets through GameController's interface
        if (controller != null && isAlive()) {
            controller.playerFire();
        }
    }

    // Method to set GameController references for Player
    public void setGameController(GameController controller) {
        this.controller = controller;
    }

    // Method that control the player's movement, called by KeyInputHandler in key events
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }
}
