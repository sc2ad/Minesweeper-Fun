import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinesweeperMouse implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		final int mouseXFix = -8;
		final int mouseYFix = -30;
		int[] p = DrawMinesweeperPane.getIndexFromPos(e.getX()+mouseXFix, e.getY()+mouseYFix);
		if (Minesweeper.gameOver) Minesweeper.resetBoard();
		else if (e.getButton() == 3) {
			// Flag
			Minesweeper.flag(p);
		}
		else if (e.getButton() == 1) {
			// Clear
			if (Minesweeper.firstPress) Minesweeper.initClear(p);
			else Minesweeper.specificClear(1, p);
		}
		else if (e.getButton() == 2) {
			// Question
			Minesweeper.question(p);
		}
		

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
