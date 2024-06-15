/*
 * Description: Controls the game
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 16th 2024
 */

//import libraries
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class GameManager {
	// declare variables, constants, and objects
	public static ArrayList<Block> blocks = new ArrayList<>();
	public static final char[] COLOURS = { 'r', 'b', 'y', 'p', 'g' };
	public static char[][] grid = new char[11][22];
	public static int[] currentCenterPiece = new int[2];
	public static ArrayList<int[]> currentSupportPieces = new ArrayList<int[]>();
	public static int currentBlock = 0;
	public static boolean next = false;
	public static Block nextblock = new Block((int) (Math.random() * 5));
	public static Block hold, tempBlock;
	public static int score = -10;
	public static int notHeld = 2;

	// GameManager constructor
	public GameManager() {
		// Fill the grid with 0s
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = ' ';
			}
		}
		for (char[] item : grid) {
			System.out.println(Arrays.toString(item));
		}
	}

	// resets the game for the next round of playin
	public static void reset() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = ' ';
			}
		}
		next = false;
		score = -10;
		notHeld = 2;
		blocks = new ArrayList<>();
		currentSupportPieces = new ArrayList<int[]>();
		currentBlock = 0;
		hold = null;
		tempBlock = null;
		GamePanel.toggleEnd();
	}

	// generates a block object
	public static void generateBlock() {
		if (!GamePanel.end) {
			nextblock = new Block((int) (Math.random() * 5));
		}
	}

	// method to allow the user to hold a block
	public static void holdBlock() {
		if (notHeld > 1) {

			notHeld = 0;
			PlaySound.PlayholdBlock();
			if (hold == null) {
				hold = new Block(blocks.get(blocks.size() - 1).getType());
				for (int[] i : blocks.get(blocks.size() - 1).getSupportingPieces()) {
					grid[i[0]][i[1]] = ' ';
				}
				grid[currentCenterPiece[0]][currentCenterPiece[1]] = ' ';
				blocks.remove(blocks.size() - 1);
				activateBlock(nextblock);
				generateBlock();
			} else {
				switchBlock();
			}
		}
	}

	public static void switchBlock() {

		tempBlock = hold;
		System.out.println(blocks.get(blocks.size() - 1).getType() + "please");
		for (int[] i : blocks.get(blocks.size() - 1).getSupportingPieces()) {
			grid[i[0]][i[1]] = ' ';
		}
		grid[currentCenterPiece[0]][currentCenterPiece[1]] = ' ';
		hold = new Block(blocks.get(blocks.size() - 1).getType());
		blocks.remove(blocks.size() - 1);
		activateBlock(tempBlock);

	}

	public static void activateBlock(Block b) {
		blocks.add(b);

		currentBlock += 1;

		// Populate current position
		currentCenterPiece = b.getCenterPiece();
		currentSupportPieces = b.getSupportingPieces();

		// Update the position of the center piece on the grid
		grid[b.getCenterPiece()[0]][b.getCenterPiece()[1]] = COLOURS[b.getType()];
		// System.out.println("Center Piece: " +
		// Arrays.toString(newBlock.getCenterPiece()));

		// Update the position of each supporting piece on the grid
		for (int[] piece : b.getSupportingPieces()) {
			// System.out.println("Support Piece: " + Arrays.toString(piece));
			grid[piece[0]][piece[1]] = COLOURS[b.getType()];
		}
		// System.out.println("current" + Arrays.toString(currentCenterPiece));
		// ends game
		// for (int i = 0; i < 11; i++) {
		// if (getGrid()[i][5] != ' ') {
		// GamePanel.setEnd();
		// break;
		// }
		// }
		for (char[] item : grid) {
			System.out.println(Arrays.toString(item));
		}

		notHeld++;

		if (notHeld > 1) {
			score += 10;
			PlaySound.playBlockPlace();
		}

	}

	// returns the positions of all blocks
	public static ArrayList<Block> getBlocks() {
		return blocks;
	}

	// updates the position of all blocks
	public static void updatePosition(int type) {
		// loop through all active blocks
		// Update the position of the center piece on the grid

		for (Block current : blocks) {
			if (current.getActive()) {
				System.out.println("start of updatePosition");
				// System.out.println("past " + Arrays.toString(currentCenterPiece));

				// Clear its old position
				grid[currentCenterPiece[0]][currentCenterPiece[1] - 1] = ' ';
				System.out.println(grid[currentCenterPiece[0]][currentCenterPiece[1]]);

				if (type == 1 && currentCenterPiece[0] - 1 >= 0 && currentCenterPiece[1] >= 0) {
					grid[currentCenterPiece[0] - 1][currentCenterPiece[1]] = ' ';
					System.out.println(grid[currentCenterPiece[0] - 1][currentCenterPiece[1]] + " why");
				}

				if (type == 2 && currentCenterPiece[0] + 1 < 11 && currentCenterPiece[1] < 21) {

					grid[currentCenterPiece[0] + 1][currentCenterPiece[1]] = ' ';
					System.out.println(grid[currentCenterPiece[0] + 1][currentCenterPiece[1]] + " how");
				}

				// This is the same fix as for the vertical drag above. However, it just doesn't
				// work
				// grid[currentCenterPiece[0] + 1][currentCenterPiece[1] - 1] = 0;
				// grid[currentCenterPiece[0] - 1][currentCenterPiece[1] - 1] = 0;

				// Clear the position of each supporting piece on the grid
				for (int[] piece : currentSupportPieces) {
					grid[piece[0]][piece[1]] = ' ';
				}

				// Populate the grid with new position
				grid[current.getCenterPiece()[0]][current.getCenterPiece()[1]] = COLOURS[current.getType()];
				// System.out.println("Center Piece: " +
				// Arrays.toString(current.getCenterPiece()));

				// Update the position of each supporting piece on the grid
				for (int[] piece : current.getSupportingPieces()) {
					// System.out.println("Support Piece: " + Arrays.toString(piece));
					grid[piece[0]][piece[1]] = COLOURS[current.getType()];
				}
				for (char[] item : grid) {
					// System.out.println(Arrays.toString(item));
				}

				// Keep track of the current position
				currentCenterPiece = current.getCenterPiece();
				currentSupportPieces = current.getSupportingPieces();
				// System.out.println("current" + Arrays.toString(currentCenterPiece));
				// System.out.println("update");
				for (char[] item : grid) {
					System.out.println(Arrays.toString(item));
				}
				System.out.println("end of updatePosition");
			}
		}
	}

	// returns the grid of all occupied squares
	public static char[][] getGrid() {
		return grid;
	}

	// checks if the game should end b/c stack overflow
	public static boolean checkEnd() {

		for (int i = 0; i < 11; i++) {
			if (getGrid()[i][5] != ' ') {
				return true;
			}

		}

		return false;

	}

	public static void rowCollapse() {
		// Loop through each row
		for (int i = 5; i < 22; i++) {
			boolean fullRow = true;
			// if at least one spot that is blank, it is not a full row
			for (int j = 0; j < 11; j++) {
				if (getGrid()[j][i] == ' ') {
					fullRow = false;
					break;
				}
			}
			// check if it's a full row. If it is reposition everything
			if (fullRow) {
				PlaySound.PlayRowClear();
				score += 100;
				System.out.println("full row");
				System.out.println(i);
				// temp array stores everything above.
				char[][] temp = new char[11][i];
				// Loop through the grid
				for (int p = 0; p < i; p++) {
					for (int j = 0; j < 11; j++) {
						// put everything in temp
						temp[j][p] = getGrid()[j][p];
					}
				}
				for (int j = 0; j < 11; j++) {
					// clear the top row
					temp[j][0] = ' ';
				}
				for (char[] array : temp) {
					System.out.println(Arrays.toString(array));
				}
				for (int j = 0; j < 11; j++) {
					System.out.println("hello");
					for (int k = 1; k < i + 1; k++) {
						// put them one row lower
						grid[j][k] = temp[j][k - 1];

					}
				}

				fullRow = false;
			}
		}
	}

	// draws the blocks

	public void draw(Graphics g) {

		nextblock.drawNextPos(g);

		if (hold != null) {
			hold.drawHoldingPos(g);
		}

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 22; j++) {
				if (getGrid()[i][j] != ' ') {
					g.setColor(Color.BLACK);
					g.fillRect(140 + i * 35, j * 35, 35, 35);
					switch (getGrid()[i][j]) {
						case 'r':
							g.setColor(Color.RED);
							break;
						case 'b':
							g.setColor(Color.CYAN);
							break;
						case 'y':
							g.setColor(Color.YELLOW);
							break;
						case 'p':
							g.setColor(Color.MAGENTA);
							break;
						case 'g':
							g.setColor(Color.GREEN);
							break;
						default:
							g.setColor(Color.BLACK);
							break;
					}
					g.fillRect(143 + i * 35, j * 35 + 3, 29, 29);
				}
			}
		}
	}
}
