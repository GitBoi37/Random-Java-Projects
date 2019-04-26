package VisualSort;


import java.awt.EventQueue;
import javax.swing.JFrame;

public class insertionDriver extends JFrame{
	private static final long serialVersionUID = 1L;
	public insertionDriver(){
        initUI();
    }
    private void initUI(){
        add(new insertionMain());
        pack();
        setResizable(false);
        pack();
        setTitle("Ok.");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    public static void main(String[] args){
        EventQueue.invokeLater(() -> { 
            JFrame ex = new insertionDriver();
            ex.setVisible(true);
        });
    }
}
