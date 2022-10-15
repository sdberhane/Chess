import java.awt.*;
import javax.swing.*;


public class Instructions extends JPanel {
	
	public static final int BOARD_WIDTH = 647;
    public static final int BOARD_HEIGHT = 891;
    
    public Instructions() {
    	//super();
    	
    	setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	setFocusable(true);
    	
    	repaint();

    }
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	g.drawImage(new ImageIcon("Images/chessInstr.png").getImage(), 0, 0, null);
    	
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
