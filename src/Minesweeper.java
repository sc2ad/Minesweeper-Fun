import java.awt.Point;

import javax.swing.JOptionPane;

public class Minesweeper {
	
	public static final int MINE_NUMBER_SIZE = 3; // Must be odd.
	public static final int[] WINDOW_SIZE = {1000, 600};
	
	public static int[][] board;
	public static int[][] dispBoard;
	
	public static int xLength;
	public static int yLength;
	public static int mineCount;
	
	public static int[][] minePos;
	
	public static boolean firstPress = true;
	public static boolean gameOver = false;
	
	public static DrawMinesweeper dm;
	public static int flagCountLeft;
	
	public static int[][] fill(int[][] board, int numMines) {
		minePos = new int[numMines][2];
		for (int i = 0; i < numMines; i++) {
			int y,x;
			do {
				y = (int)(Math.random() * board.length);
				x = (int)(Math.random() * board[y].length);
			} while (board[y][x] == -1);
			board[y][x] = -1;
			int[] t = {x,y};
			minePos[i] = t;
		}
		int [][] out = board;
		return out;
	}
	@SuppressWarnings("unused")
	public static int[][] calcMineNums(int[][] board) {
		// 1 + 1/2 * MINE_NUMBER_SIZE
		for (int[] xy : minePos) {
			if (MINE_NUMBER_SIZE % 2 == 0) throw new IllegalArgumentException("MINE_NUMBER_SIZE must be an odd number!");
			for (int dx = -MINE_NUMBER_SIZE/2; dx <= MINE_NUMBER_SIZE/2; dx++) {
				for (int dy = -MINE_NUMBER_SIZE/2; dy <= MINE_NUMBER_SIZE/2; dy++) {
					if (dx == 0 && dy == 0) continue;
					try {
						if (board[xy[1] + dy][xy[0] + dx] == -1) continue;
						board[xy[1] + dy][xy[0] + dx] ++;
					} catch (ArrayIndexOutOfBoundsException e) {
						continue;
					}
				}
			}
		}
		return board;
	}
	public static void resetBoard() {
		board = new int[yLength][xLength];
		int[][] temp = fill(board, mineCount);
		flagCountLeft = mineCount;
		board = calcMineNums(temp);
		dispBoard = new int[yLength][xLength];
		gameOver = false;
		firstPress = true;
	}
	public static void run() {
		try {
			yLength = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the number of rows for minesweeper..."));
			xLength = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the number of coloumns for minesweeper..."));
			mineCount = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the number of mines for minesweeper..."));
		} catch (NumberFormatException e) {
			if (JOptionPane.showConfirmDialog(null, "Do you want to quit?") == 0) {
				return;
			}
			run();
			return;
			
		}
//		yLength = 5;
//		xLength = 5;
//		mineCount = 7;
		
		resetBoard();
		dm = new DrawMinesweeper(WINDOW_SIZE[0], WINDOW_SIZE[1]);
		dm.addMouseListener(new MinesweeperMouse());
		
	}
	public static void initClear(int... pos) {
		while (board[pos[1]][pos[0]] != 0) {
			resetBoard();
		}
		clear(1, pos);
		firstPress = false;
	}
	public static void lose() {
		for (int i = 0; i < minePos.length; i++) {
			dispBoard[minePos[i][1]][minePos[i][0]] = -1;
		}
		gameOver = true;
	}
	public static void specificClear(int tiles, int...pos) {
		if (dispBoard[pos[1]][pos[0]] < 10 && dispBoard[pos[1]][pos[0]] > 0) {
			int count = 0;
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					try {
						if (dispBoard[pos[1]+i][pos[0]+j] == 11) {
							count++;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						continue;
					}
				}
			}
			if (count >= board[pos[1]][pos[0]]) {
				clear(3, pos);
			}
		} else {
			clear(tiles, pos);
		}
	}
	public static void clear(int tiles, int... pos) {
		for (int i = -tiles/2; i <= tiles/2; i++) {
			for (int j = -tiles/2; j <= tiles/2; j++) {
				try {
					if (dispBoard[pos[1]-i][pos[0]-j] == 10 || dispBoard[pos[1]-i][pos[0]-j] == 11 || dispBoard[pos[1]-i][pos[0]-j] == 12) {
						continue;
					}
					else if (board[pos[1]-i][pos[0]-j] == 0 && dispBoard[pos[1]-i][pos[0]-j] != 10) {
						dispBoard[pos[1]-i][pos[0]-j] = 10;
						clear(3, pos[0]-j, pos[1]-i);
						continue;
					}
					else if (board[pos[1]-i][pos[0]-j] == -1) {
						// Game ends, player dead.
						lose();
						dispBoard[pos[1]-i][pos[0]-j] = -2;
						return;
					}
					dispBoard[pos[1]-i][pos[0]-j] = board[pos[1]-i][pos[0]-j];
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		
	}
	public static void print(int[][] dBoard) {
		for (int k = 0; k < board.length; k++) {
			for (int j = 0; j < board[k].length; j++) {
				int i = dBoard[k][j];
				String out;
				
				if (i==11) out = "+";
				else if (i==12) out = "?";
				else if (i==10) out = " ";
				else if (board[k][j]==-1) out = "0";
////				else if (i==0) out = "-";
				else out = String.valueOf(dBoard[k][j]);
				
				System.out.print(out+"  ");
			}
			System.out.println();
		}
	}
	public static void question(int...pos) {
		if (dispBoard[pos[1]][pos[0]] == 0) {
			dispBoard[pos[1]][pos[0]] = 12;
		} else if (dispBoard[pos[1]][pos[0]] == 12) {
			dispBoard[pos[1]][pos[0]] = 0;
		}
	}
	
	public static void flag(int... pos) {
		if (dispBoard[pos[1]][pos[0]] == 0 || dispBoard[pos[1]][pos[0]] == 12) {
			dispBoard[pos[1]][pos[0]] = 11;
			flagCountLeft--;
		} else if (dispBoard[pos[1]][pos[0]] == 11) {
			dispBoard[pos[1]][pos[0]] = 0;
			flagCountLeft++;
		}
		
	}
	public static boolean checkWin(int[][] dispBoard) {
		int countMine = 0;
		for (int[] xy : minePos) {
			int i = dispBoard[xy[1]][xy[0]];
			if (i == 11) { // Makes sure that it isn't a mine or a flag or a question
				countMine ++;
			}
		}
		int countTile = 0;
		for (int[] xy : dispBoard) {
			for (int i : xy) {
				if (i == 11 || i == 12 || i == 0) {
					continue;
				}
				countTile++;
			}
		}
		return countMine == mineCount && countTile == xLength*yLength-mineCount;
	}
}
