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
                    try {Thread.sleep(10);} catch (IllegalArgumentException e) {break;} catch (Exception ex) {} 
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
	public void kill() {
		throw new IllegalArgumentException("nah");
	}
	
    public void paintComponent(Graphics g) {
    	//draw on g here e.g.
        //g.fillRect(0, 0, width, height);
    	g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Makes the sky BLACK
        paintBkg(g);
//        g.setColor(Color.RED);
//        g.drawString(mx+", "+my, width, height/2);
        g.setColor(Color.CYAN);
		g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Mines Left: ", width+width/10-15, height/2-height/5);
        g.drawString(String.valueOf(Minesweeper.flagCountLeft), width+width/10, height/2);
        if (Minesweeper.checkWin(Minesweeper.dispBoard)) {
        	g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, height/5+Constants.textYDisp));
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
    				g.setColor(Color.LIGHT_GRAY);
    			}
    			switch (item) {
    			case 11: 
    				g.setColor(Color.RED);
    				break;
    			case 12: 
    				g.setColor(Color.BLUE);
    				break;
    			case -2: 
    				g.setColor(Color.PINK);
    				break;
    			case -1:
    				g.setColor(Color.DARK_GRAY);
    				break;
    			default: break;
    			}
    			int x = j * width / Minesweeper.dispBoard[i].length;
    			int y = i * height / Minesweeper.dispBoard.length;
    			
    			g.fillRect(x, y, width/Minesweeper.dispBoard[i].length, height/Minesweeper.dispBoard.length);
    			g.setColor(Color.BLACK);
    			g.drawRect(x, y, width/Minesweeper.dispBoard[i].length, height/Minesweeper.dispBoard.length);
    			
    			if (item < 9 && item > 0) {
    				switch (item) {
    				case 8:
    					g.setColor(Color.BLACK);
    					break;
    				case 7:
    					g.setColor(Color.DARK_GRAY);
    					break;
    				case 6:
    					g.setColor(Color.GRAY);
    					break;
    				case 5:
    					g.setColor(Color.MAGENTA);
    					break;
    				case 4:
    					g.setColor(Color.ORANGE);
    					break;
    				case 3:
    					g.setColor(Color.RED);
    					break;
    				case 2:
    					g.setColor(Color.GREEN);
    					break;
    				case 1:
    					g.setColor(Color.BLUE);
    					break;
    				default: break;
    				}
    				g.setFont(new Font("Arial", Font.PLAIN, height/Minesweeper.dispBoard.length/2));
    				g.drawString(String.valueOf(item), x+width/Minesweeper.dispBoard[i].length/2-g.getFontMetrics().charWidth(String.valueOf(item).charAt(0))/2, y+height/Minesweeper.dispBoard.length/2+g.getFontMetrics().getHeight()/2);
    			}
    			
    		}
    	}
    }
	public static void zoom(int[] indexFromPos, double preciseWheelRotation) {
		// TODO Auto-generated method stub
		// Find a way to zoom in by subtracting outlying boxes that aren't inside a window
		// make a var called window, which is smaller dispBoard
		
	}
}
