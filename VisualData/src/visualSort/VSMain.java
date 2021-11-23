package visualSort;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
public class VSMain extends JPanel implements Runnable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread animator;
    private final int DELAY = 5;
    private int[] sorts;
    private int biggest = 0;
    private int bI = 0;
    public VSMain() {
        super();
        setBackground(Color.BLACK);
        setFocusable(true);
        Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        sorts = new int[(int)(d.getWidth()/(1.01))];
        bI = sorts.length;
        biggest = findBig(sorts, bI);
        
        for(int i = 0; i < sorts.length; i++){
            sorts[i] = (int)(Math.random()*(d.getHeight()-50));
        }
        
        setPreferredSize(d);
    }
    public int findBig(int[] e, int top) {
    	int b = Integer.MIN_VALUE;
    	for(int i = 0; i < top; i++) {
    		if(b < e[i]) {
    			b = e[i];
    		}
    	}
    	return b;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        for(int i = 0; i < sorts.length; i++){
            g2d.drawRect(i*1 + 10, (int) (d.getHeight() - sorts[i] - 30), 1, sorts[i]);
        }
        g2d.drawString("Number of numbers in sorting batch: " + sorts.length, 50, 50);
        
    }
    public int[] swap(int a, int b, int[] ee) {
    	int temp = ee[a];
    	ee[a] = ee[b];
    	ee[b] = temp;
    	return ee;
    }
    public void cleanUp() {
    	if(sorts[bI - 1] == biggest) {
    		bI--;
    		biggest = findBig(sorts, bI);
    	}
    	for(int i = 0; i < bI - 1; i++) {
    		if(sorts[i] > sorts[i + 1]) {
    			sorts = swap(i, i + 1, sorts);
    		}
    	}
    }
    @Override
    public void addNotify(){
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
    @Override
    public void run(){
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        while(true){
            repaint();
            cleanUp();
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

    
