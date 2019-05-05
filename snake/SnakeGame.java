package snake;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import java.awt.event.*;

public class SnakeGame extends JPanel implements Runnable, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread animator;
	private final int DELAY = 2;
	private int score = 0;
	private int snakeHeadY = 100;
	private int snakeHeadX = 100;
	private int waitTick = 0;
	private final int snakeUnit = 20;
	private final int appleW = 20;
	private final int appleH = 20;
	private int snakeMove = 0;
	private int debug = 0;
	private int snakeSize = 1;
	public boolean firstMove = true;
	private int w;
	private int segmentsToAdd = 30;
	private int h;
	private Rectangle[] bounds;
	private ArrayList<Rectangle> snake;
	private Point apple;
	private static String[] directions = { "Up", "Down", "Left", "Right" };
	private String direction = null;
	private boolean lose = false;

	public SnakeGame() {
		super();
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(d);
		w = (int) d.getWidth();
		h = (int) d.getHeight();
		apple = new Point();
		apple.randomize(200, w - 200, 200, (h - 200));
		apple.rectify();
		bounds = new Rectangle[4];
		bounds[0] = new Rectangle(0, 0, 20, h);
		bounds[1] = new Rectangle(w - 20, 0, 20, h);
		bounds[2] = new Rectangle(0, 0, w, 20);
		bounds[3] = new Rectangle(0, h - 65, w, 50);
		snake = new ArrayList<Rectangle>();
		for (int i = 0; i < snakeSize; i++) {
			snake.add(new Rectangle(snakeHeadX - snakeUnit * (i), snakeHeadY, snakeUnit, snakeUnit));
		}
		// all.add(new Platform(150, (int)d.getHeight() - 100, 100, 10));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Times New Roman", Font.BOLD, 12));
		g2d.drawString("Score: " + score, 50, 50);
		if(debug == 1) {
			g2d.drawString("SnakeHeadX: " + snakeHeadX, 50, 75);
			g2d.drawString("SnakeHeadY: " + snakeHeadY, 50, 100);
			g2d.drawString("AppleX: " + apple.x, 200, 75);
			g2d.drawString("AppleY: " + apple.y, 200, 100);
		}
		for (Rectangle r : snake) {
			g2d.fillRect(r.x, r.y, r.width, r.height);
		}
		g2d.setColor(Color.GREEN);
		g2d.fillRect(snakeHeadX, snakeHeadY, snakeUnit, snakeUnit);
		g2d.setColor(Color.RED);
		for (Rectangle r : bounds) {
			g2d.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
		}
		g2d.fillRect(apple.x, apple.y, appleW, appleH);
		if (lose == true) {
			g2d.setFont(new Font("Times New Roman Italic", Font.BOLD, 40));
			g2d.drawString("You suck", w / 2 - 55, h / 2);
			g2d.setFont(new Font("Times New Roman", Font.BOLD, 20));
			if(score > 100) {
				g2d.drawString("Final Score: " + score, w/2 - 45, h/2 + 20);
			}
			else {
				g2d.drawString("Final Score: " + score, w/2 - 35, h/2 + 20);
			}
			g2d.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			g2d.drawString("Press R to retry", -25+w/2, h/2 + 40);
		}
		Toolkit.getDefaultToolkit().sync();
	}

	public void keyTyped(KeyEvent key) {
	}

	public void keyReleased(KeyEvent key) {
	}

	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_A) {
			if (direction != "Right") {
				direction = "Left";
			}
			snakeMove = 1;
		}
		if (key.getKeyCode() == KeyEvent.VK_S) {
			if (direction != "Up") {
				direction = "Down";
			}
			snakeMove = 1;
		}
		if (key.getKeyCode() == KeyEvent.VK_D) {
			if (direction != "Left") {
				direction = "Right";
			}
			snakeMove = 1;
		}
		if (key.getKeyCode() == KeyEvent.VK_W) {
			if (direction != "Down") {
				direction = "Up";
			}
			snakeMove = 1;
		}
		if (key.getKeyCode() == KeyEvent.VK_R) {
			reset();
		}
		if (key.getKeyCode() == KeyEvent.VK_E) {
			if(debug == 0) {
				debug = 1;
			}
			else {
				debug = 0;
			}
		}
	}

	public void reset() {
		snakeHeadY = 100;
		snakeHeadX = 100;
		score = 0;
		lose = false;
		direction = null;
		snakeSize = 1;
		snakeMove = 0;
		firstMove = true;
		apple.randomize(200, w - 200, 200, (h - 200));
		apple.rectify();
		snake.removeAll(snake);
		for (int i = 0; i < snakeSize; i++) {
			snake.add(new Rectangle(snakeHeadX - snakeUnit * (i), snakeHeadY, snakeUnit, snakeUnit));
		}
	}

	@Override
	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}

	public boolean intersect(Rectangle a, Rectangle b) {
		return false;
	}

	public boolean checkBounds() {
		Rectangle s = new Rectangle(snakeHeadX, snakeHeadY, snakeUnit, snakeUnit);
		if (s.intersects(new Rectangle(apple.x, apple.y, appleW, appleH))) {
			apple.randomize(200, w - 200, 200, (h - 200));
			apple.rectify();
			snake.add(new Rectangle(0, 0, snakeUnit, snakeUnit));
			for (int i = 0; i < segmentsToAdd; i++) {
				snake.add(new Rectangle(0, 0, snakeUnit, snakeUnit));
			}
			score += 1 * segmentsToAdd;
		}
		for (int i = 2; i < snake.size(); i++) {
			if (s.intersects(snake.get(i))) {
				lose = true;
				return true;
			}
			while (snake.get(i).intersects(new Rectangle(apple.x, apple.y, appleW, appleH))) {
				apple.randomize(200, w - 200, 200, (h - 200));
				apple.rectify();
			}
		}
		if (s.x < bounds[0].x + bounds[0].width) {
			lose = true;
			return true;
		}
		if (s.x > bounds[1].x) {
			lose = true;
			return true;
		}
		if (s.y < bounds[2].y + bounds[2].height) {
			lose = true;
			return true;
		}
		if (s.y > bounds[3].y) {
			lose = true;
			return true;
		}
		/*
		 * for(Rectangle r : bounds){
		 * 
		 * if(s.intersects(r)){ direction = null; lose = true; return true; } }
		 */
		return false;
	}

	public void move() {
		if (snakeSize == 0) {

		} else {
			snake.get(0).x = snakeHeadX;
			snake.get(0).y = snakeHeadY;
		}
		if (snakeMove == 1) {
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).x = snake.get(i - 1).x;
				snake.get(i).y = snake.get(i - 1).y;
			}
		}
		if (checkBounds() == false) {
			if (lose == false) {
				if (direction == null) {
					lose = false;
				} else {
					if (direction.equals(directions[0])) {
						snakeHeadY -= snakeUnit;
					}
					if (direction.equals(directions[1])) {
						snakeHeadY += snakeUnit;
					}
					if (direction.equals(directions[2])) {
						snakeHeadX -= snakeUnit;
					}
					if (direction.equals(directions[3])) {
						snakeHeadX += snakeUnit;
					}
				}
			}
		}

	}

	public void update() {
		if (waitTick > 0) {
			waitTick--;
		} else {
			waitTick = 16;
			move();
		}
		snakeHeadX = snakeHeadX / 10 * 10;
		snakeHeadY = snakeHeadY / 10 * 10;
	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		while (true) {
			repaint();
			update();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;
			if (sleep < 0) {
				sleep = 2;
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				String msg = String.format("Thread interrupted: %s", e.getMessage());
				JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
			}
			beforeTime = System.currentTimeMillis();
		}
	}
}
