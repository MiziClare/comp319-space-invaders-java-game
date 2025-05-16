package spaceinvaders;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Screen extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Thread gameThread;
	private boolean running = false;

	private GameController controller;

	public Screen(GameController controller) {
		this.controller = controller;

		KeyInputHandler keyHandler = new KeyInputHandler(controller);
		addKeyListener(keyHandler);
		setFocusable(true);
		requestFocus();
	}

	public void init() {
		frame = new JFrame("Space Invaders");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// Set the window size, which can be adjusted as needed
		setSize(700, 500);

		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		start();
	}

	private void start() {
		if (running) return;
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	private void stop() {
		if (!running) return;
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// game loop
		while (running) {
			updateGame();
			renderGame();

			// Control frame rate
			try {
				Thread.sleep(16); // ~60fps
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stop();
	}

	private void updateGame() {
		controller.update();
	}

	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		// 清空背景
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Here let the controller take care of drawing the game object
		controller.render(g);

		g.dispose();
		bs.show();
	}
}
