import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawMinesweeperPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	public static int width;
	public static int height;
	public static int mx = 0;
	public static int my = 0;
	double scaling = 1.0; // Default scaling
		
	public DrawMinesweeperPane(int w, int h) {
		width = w;
		height = h;
		// Creates thread for graphical repainting
		Thread animationThread = new Thread(new Runnable() {
			public void run() {
                do {
                    repaint();
                    try {Thread.sleep(10);} catch (Exception ex) {}
                } while (true);
            }
		});
		animationThread.start();
	}
	public static int[] getIndexFromPos(int... p) {
		mx = p[0];
		my = p[1];
		int y = p[1]/ (height/Minesweeper.dispBoard.length);
		int x = p[0]/ (width/Minesweeper.dispBoard[y].length);
		int[] out = {x,y};
		return out;
	}
	
    public void paintComponent(Graphics g) {
    	//draw on g here e.g.
        //g.fillRect(0, 0, width, height);
    	g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Makes the sky BLACK
        paintBkg(g);
        g.setColor(Color.RED);
        g.drawString(mx+", "+my, width, height/2);
        if (Minesweeper.checkWin(Minesweeper.dispBoard)) {
        	g.setFont(Font.decode("arial BOLD"));
			g.drawString("YOU WIN!", width/2, height/2);
			Minesweeper.gameOver = true;
			//Minesweeper.resetBoard();
		}
    }
    
    public void paintBkg(Graphics g) {
    	for (int i = 0; i < Minesweeper.dispBoard.length; i++) {
    		for (int j = 0; j < Minesweeper.dispBoard[i].length; j++) {
    			int item = Minesweeper.dispBoard[i][j];
    			g.setColor(Color.WHITE);
    			if (item <= 10 && item > 0) {
    				g.setColor(Color.GRAY);
    			} else if (item == 11) {
    				g.setColor(Color.RED);
    			} else if (item == 12) {
    				g.setColor(Color.BLUE);
    			} else if (item == -2) {
    				g.setColor(Color.PINK);
    			} else if (item == -1) {
    				g.setColor(Color.DARK_GRAY);
    			}
    			int x = j * width / Minesweeper.dispBoard[i].length;
    			int y = i * height / Minesweeper.dispBoard.length;
    			
    			g.fillRect(x, y, width/Minesweeper.dispBoard[i].length, height/Minesweeper.dispBoard.length);
    			g.setColor(Color.BLACK);
    			g.drawRect(x, y, width/Minesweeper.dispBoard[i].length, height/Minesweeper.dispBoard.length);
    			
    			if (item < 9 && item > 0) {
    				g.setColor(Color.BLACK);
    				//TODO: Add color severity for mines
    				g.drawString(String.valueOf(item), x+width/Minesweeper.dispBoard[i].length/2, y+height/Minesweeper.dispBoard.length/2);
    			}
    			
    		}
    	}
    }
}
