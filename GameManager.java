import java.awt.Graphics;
import java.util.*;

public class GameManager {
	public static ArrayList<Block> blocks = new ArrayList<>();
	// grid
	// 0 = empty, 1 = occupied
	public static char[] colours = { 'r', 'b', 'y', 'p', 'g' };
	public static char[][] grid = new char[11][22];
	public static int[] currentCenterPiece = new int[2];
	public static ArrayList<int[]> currentSupportPieces = new ArrayList<int[]>();
	public static int currentBlock = 0;
	public static boolean next = false;
	public static Block nextblock = new Block((int) (Math.random() * 5));
	public static Block hold, tempBlock;
	public static int score = 0;

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

	// generates a block object
	public static void generateBlock() {
		nextblock = new Block((int) (Math.random() * 5));
	}

	public static void holdBlock() {

		if (hold == null) {
			hold = new Block(blocks.get(blocks.size() - 1).getType());
			for (int[] i : blocks.get(blocks.size() - 1).getSupportingPieces()) {
				grid[i[0]][i[1]] = ' ';
			}
			grid[currentCenterPiece[0]][currentCenterPiece[1]] = ' ';
			blocks.remove(blocks.get(blocks.size() - 1));
			activateBlock(nextblock);
			generateBlock();
		} else {
			switchBlock();
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
		blocks.remove(blocks.get(blocks.size() - 1));
		activateBlock(tempBlock);

	}

	public static void activateBlock(Block b) {
		blocks.add(b);

		currentBlock += 1;

		// Populate current position
		currentCenterPiece = b.getCenterPiece();
		currentSupportPieces = b.getSupportingPieces();

		// Update the position of the center piece on the grid
		grid[b.getCenterPiece()[0]][b.getCenterPiece()[1]] = colours[b.getType()];
		// System.out.println("Center Piece: " +
		// Arrays.toString(newBlock.getCenterPiece()));

		// Update the position of each supporting piece on the grid
		for (int[] piece : b.getSupportingPieces()) {
			// System.out.println("Support Piece: " + Arrays.toString(piece));
			grid[piece[0]][piece[1]] = colours[b.getType()];
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
				// System.out.println("past " + Arrays.toString(currentCenterPiece));

				// Clear its old position
				grid[currentCenterPiece[0]][currentCenterPiece[1] - 1] = ' ';
				System.out.println(grid[currentCenterPiece[0]][currentCenterPiece[1]]);

				if (type == 1 && currentCenterPiece[0] - 1 >= 0 && currentCenterPiece[1] >= 0) {
					grid[currentCenterPiece[0] - 1][currentCenterPiece[1]] = ' ';
				}

				if (type == 2 && currentCenterPiece[0] + 1 < 11 && currentCenterPiece[1] < 21) {

					grid[currentCenterPiece[0] + 1][currentCenterPiece[1]] = ' ';
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
				grid[current.getCenterPiece()[0]][current.getCenterPiece()[1]] = colours[current.getType()];
				// System.out.println("Center Piece: " +
				// Arrays.toString(current.getCenterPiece()));

				// Update the position of each supporting piece on the grid
				for (int[] piece : current.getSupportingPieces()) {
					// System.out.println("Support Piece: " + Arrays.toString(piece));
					grid[piece[0]][piece[1]] = colours[current.getType()];
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
			}
		}
	}

	// returns the grid of all occupied squares
	public static char[][] getGrid() {
		return grid;
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
				System.out.println("full row");
				System.out.println(i);
				fullRow = false;
				char[][] temp = new char[11][21 - i];
				for (int p = i; p < 22; p++) {
					for (int j = 0; i < 11; j++) {
						temp[j][p - i] = getGrid()[j][p];
					}
				}
			}
		}
	}

	// draws the blocks
	public void draw(Graphics g) {
		for (Block block : blocks) {
			block.draw(g);
		}

		nextblock.drawNextPos(g);

		if (hold != null) {
			hold.drawHoldingPos(g);
		}

		// for (int i = 0; i < 11; i++) {
		// for (int j = 0; j < 22; j++) {
		// g.fillRect(140 + (i+1)*35, j*35, i, i);
		// }
		// }
	}
}
