package spaceinvaders;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputHandler extends KeyAdapter {
    private GameController controller;

    public KeyInputHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                // Player moves to the left
                controller.playerMoveLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                // Player moves to the right
                controller.playerMoveRight(true);
                break;
            case KeyEvent.VK_SPACE:
                // Player fires bullets
                controller.playerFire();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                // Stop moving left
                controller.playerMoveLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                // Stop moving to the right
                controller.playerMoveRight(false);
                break;
            default:
                break;
        }
    }
}
