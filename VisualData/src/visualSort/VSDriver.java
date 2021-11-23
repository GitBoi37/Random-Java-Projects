package visualSort;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class VSDriver extends JFrame{
	private static final long serialVersionUID = 1L;
	public VSDriver(){
        initUI();
    }
    private void initUI(){
        add(new VSMain());
        pack();
        setResizable(false);
        pack();
        setTitle("Ok.");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    public static void main(String[] args){
        EventQueue.invokeLater(() -> { 
            JFrame ex = new VSDriver();
            ex.setVisible(true);
        });
    }
}
