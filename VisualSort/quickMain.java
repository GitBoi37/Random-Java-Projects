package sort;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
public class QuickMain extends JPanel implements Runnable{
    /**
     * 
     */
    private static ArrayList<actionObject> actionList = new ArrayList<actionObject>();
    private static final long serialVersionUID = 1L;
    private Thread animator;
    private final int DELAY = 5;
    private int[] sorts;
    private int[] display;
    private int index = 0;
    private int aI = 0;
    public QuickMain() {
        super();
        setBackground(Color.BLACK);
        setFocusable(true);
        Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        sorts = new int[(int)(d.getWidth()/(1.01))];
        for(int i = 0; i < sorts.length; i++){
            sorts[i] = (int)(Math.random()*(d.getHeight()-50));
        }
        display = sorts.clone();
        quickSort();
        setPreferredSize(d);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        for(int i = 0; i < display.length; i++){
            g2d.drawRect(i*1 + 10, (int) (d.getHeight() - display[i] - 30), 1, display[i]);
        }
        g2d.drawString("Numbers in sorting batch: " + sorts.length, 50, 50);
        
    }
    public int[] swap(int[] ee, int a, int b) {
        int temp = ee[a];
        ee[a] = ee[b];
        ee[b] = temp;
        return ee;
    }
    public void insertSort(){
        if(index < sorts.length){
            for(int i = 0; i < index; i++){
                int jeff = sorts[index];
                if(sorts[i] > jeff){
                    sorts[index] = sorts[i];
                    sorts[i] = jeff;
                }
            }
            index++;
        }
    }
    public boolean isSorted(){
        for(int i = 0; i < sorts.length - 1; i++){
            if(sorts[i] > sorts[i + 1]){
                return false;
            }
        }
        return true;
    }
    public void quickSort(){
        sort(sorts, 0, sorts.length - 1);
    }
    public static void sort(int[] e, int start, int end){
        if(start < end){
            int pIndex = partition(e, start, end);
            sort(e, start, pIndex - 1);
            sort(e, pIndex + 1, end);
        }
    }
    public static int partition(int[] e, int start, int end){
        int pivot  = e[end];
        int j = start - 1;
        for(int i = start; i < end; i++){
            if(e[i] <= pivot){
                j++;
                int temp = e[i];
                e[i] = e[j];
                e[j] = temp;
                actionList.add(new actionObject(j, i));
            }
        }
        int temp = e[j + 1];
        e[j + 1] = e[end];
        e[end] = temp;
        actionList.add(new actionObject(j + 1, end));
        return j + 1;
    }
    @Override
    public void addNotify(){
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
    public void iterate() {
    	//if(aI < actionList.size()) {
    		/*
    		int temp = sorts[actionList.get(aI).swapA];
    		sorts[actionList.get(aI).swapA] = sorts[actionList.get(aI).swapB];
    		sorts[actionList.get(aI).swapB] = temp;*/
    		display = swap(display, actionList.get(aI).swapA, actionList.get(aI).swapB);
    		aI++;
    	//}
    }
    @Override
    public void run(){
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        while(true){
            repaint();
            iterate();
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
