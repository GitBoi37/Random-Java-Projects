package snake;


import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JOptionPane;
import java.awt.event.*;
public class SnakeGame extends JPanel implements Runnable, KeyListener{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Thread animator;
    private final int DELAY = 50;
    private int score = 0;
    private int snakeHeadY = 100;
    private int snakeHeadX = 100;
    private final int snakeUnit = 20;
    private int snakeSize = 3;
    private int w;
    private int h;
    private Rectangle[] bounds;
    private ArrayList<Rectangle> snake;
    private static String[] directions = {"Up", "Down", "Left", "Right"};
    private String direction = "Right";
    private boolean lose = false;
    public SnakeGame() {
        super();
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(d);
        w = (int)d.getWidth();
        h = (int)d.getHeight();
        bounds = new Rectangle[4];
        bounds[0] = new Rectangle(0,0,20,h);
        bounds[1] = new Rectangle(w - 20,0,20,h);
        bounds[2] = new Rectangle(0,0,w,20);
        bounds[3] = new Rectangle(0,h-65,w,50);
        snake = new ArrayList<Rectangle>();
        for(int i = 0; i < snakeSize; i++) {
        	snake.add(new Rectangle(snakeHeadX + snakeUnit*(i + 1) + snakeUnit*(i + 1)/10, snakeHeadY, snakeUnit, snakeUnit));
        }
        //all.add(new Platform(150, (int)d.getHeight() - 100, 100, 10));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        for(Rectangle r : snake){
           g2d.fillRect(r.x, r.y, r.width, r.height);
    	}
        g2d.fillRect(snakeHeadX, snakeHeadY, snakeUnit, snakeUnit);
        g2d.setColor(Color.RED);
        for(Rectangle r : bounds){
            g2d.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
        }
        Toolkit.getDefaultToolkit().sync();
    }
    public void keyTyped(KeyEvent key){ } 
    public void keyReleased(KeyEvent key){
    }
    public void keyPressed(KeyEvent key){
        if(key.getKeyCode() == KeyEvent.VK_A){
            direction = "Left";
        }
        if(key.getKeyCode() == KeyEvent.VK_S){
            direction = "Down";
        }
        if(key.getKeyCode() == KeyEvent.VK_D){
            direction = "Right";
        }
        if(key.getKeyCode() == KeyEvent.VK_W){
            direction = "Up";
        }
        if(key.getKeyCode() == KeyEvent.VK_R) {
            reset();
        }
        if(key.getKeyCode() == KeyEvent.VK_E) {

        }
    }
    public void reset(){
        snakeHeadY = 100;
        snakeHeadX = 100;
        lose = false;
        direction = null;
        snakeSize = 0;
    }
    @Override
    public void addNotify(){
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
    public boolean checkBounds(){
        Rectangle s = new Rectangle(snakeHeadX, snakeHeadY, snakeUnit, snakeUnit);
        for(Rectangle r : bounds){
            
            if(s.intersects(r)){
            	direction = null;
                lose = true;
                return true;
            }
        }
        return false;
    }
    public void move() {
    	if(snakeSize == 0) {
    		snake.removeAll(snake);
    	}
    	for(int i = 0; i < snake.size(); i++) {
    		//implement snakemoving
    	}
    }
    public void update(){
    	if(checkBounds() == false) {
    		if(lose == false){
    			if(direction == null){
    				lose = false;
    			}
    			else{
    				if(direction.equals(directions[0])){
    					snakeHeadY-=snakeUnit;
    				}
    				if(direction.equals(directions[1])){
    					snakeHeadY+=snakeUnit;
    				}
    				if(direction.equals(directions[2])){
    					snakeHeadX-=snakeUnit;
    				}
    				if(direction.equals(directions[3])){
    					snakeHeadX+=snakeUnit;
    				}
    			}
    		}
    	}
        
        move();
    }
    @Override
    public void run(){
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while(true){
            repaint();
            update();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if(sleep < 0){
                sleep = 2;
            }
            try{
                Thread.sleep(sleep);
            } catch (InterruptedException e){
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                JOptionPane.showMessageDialog(this,msg, "Error", JOptionPane.ERROR_MESSAGE);
            }
            beforeTime = System.currentTimeMillis();
        }
    }
}
