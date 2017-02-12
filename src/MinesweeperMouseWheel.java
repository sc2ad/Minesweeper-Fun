import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MinesweeperMouseWheel implements MouseWheelListener {

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		DrawMinesweeperPane.zoom(DrawMinesweeperPane.getIndexFromPos((int)(e.getLocationOnScreen().getX()), (int)(e.getLocationOnScreen().getY())), e.getPreciseWheelRotation());
		
	}

}
