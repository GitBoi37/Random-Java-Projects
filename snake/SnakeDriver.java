import java.awt.EventQueue;
import javax.swing.JFrame;

public class SnakeDriver extends JFrame{
	private static final long serialVersionUID = 1L;
	public SnakeDriver(){
        initUI();
    }
    private void initUI(){
        add(new SnakeGame());
        pack();
        setResizable(false);
        pack();
        setTitle("Dear god... so many rectangles");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    public static void main(String[] args){
        EventQueue.invokeLater(() -> { 
            JFrame ex = new SnakeDriver();
            ex.setVisible(true);
        });
    }
}
