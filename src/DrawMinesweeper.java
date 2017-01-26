import javax.swing.JFrame;

public class DrawMinesweeper extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DrawMinesweeperPane d;
	
	
	public DrawMinesweeper(int x, int y) {
        super("The Universe"); // Sets the title

        d = new DrawMinesweeperPane(x,y); // Creates the DrawPane class below
        setContentPane(d); // Sets it as the background

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes sure it can close

        final int xChange = 16;
        final int yChange = 39;
        
        setSize(x+xChange, y+yChange); // Sets the size to be a bit bigger than needed

        setVisible(true);  // Makes sure the window is visible
        
   }
	public void update(double scaling) {
		d.scaling = scaling;
	}
}