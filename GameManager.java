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

	// GameManager constructor
	public GameManager() {
		// Fill the grid with 0s
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = ' ';
			}
		}
	}

	// generates a block object
	public static void generateBlock() {
		System.out.println("generate");

		// Create a new block
		Block newBlock = new Block((int) (Math.random() * 5));
		// Block newBlock = new Block(2);

		blocks.add(newBlock);

		currentBlock += 1;

		// Populate current position
		currentCenterPiece = newBlock.getCenterPiece();
		currentSupportPieces = newBlock.getSupportingPieces();

		// Update the position of the center piece on the grid
		grid[newBlock.getCenterPiece()[0]][newBlock.getCenterPiece()[1]] = colours[newBlock.getType()];
		// System.out.println("Center Piece: " +
		// Arrays.toString(newBlock.getCenterPiece()));

		// Update the position of each supporting piece on the grid
		for (int[] piece : newBlock.getSupportingPieces()) {
			// System.out.println("Support Piece: " + Arrays.toString(piece));
			grid[piece[0]][piece[1]] = colours[newBlock.getType()];
		}
		for (char[] item : grid) {
			// System.out.println(Arrays.toString(item));
		}
		// System.out.println("current" + Arrays.toString(currentCenterPiece));

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

				if (type == 1) {
					grid[currentCenterPiece[0] - 1][currentCenterPiece[1]] = ' ';
				}

				if (type == 2) {
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
				System.out.println("update");
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
		int fullRow;
		for (int i = 0; i < 11; i++) {
			// if()
		}
	}

	// draws the blocks
	public void draw(Graphics g) {
		for (Block block : blocks) {
			block.draw(g);
		}

	}
}
