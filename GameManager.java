import java.awt.Graphics;
import java.util.*;

public class GameManager {
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	// grid
	// 0 = empty, 1 = occupied
	public static int[][] grid = new int[11][22];
	public static int[] currentCenterPiece = new int[2];
	public static ArrayList<int[]> currentSupportPieces = new ArrayList<int[]>();
	public static int currentBlock = 0;
	public static boolean next = false;

	public GameManager() {
		// Fill the grid with 0s
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = 0;
			}
		}
	}

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
		grid[newBlock.getCenterPiece()[0]][newBlock.getCenterPiece()[1]] = 1;
		// System.out.println("Center Piece: " +
		// Arrays.toString(newBlock.getCenterPiece()));

		// Update the position of each supporting piece on the grid
		for (int[] piece : newBlock.getSupportingPieces()) {
			// System.out.println("Support Piece: " + Arrays.toString(piece));
			grid[piece[0]][piece[1]] = 1;
		}
		for (int[] item : grid) {
			// System.out.println(Arrays.toString(item));
		}
		// System.out.println("current" + Arrays.toString(currentCenterPiece));

	}

	public static ArrayList<Block> getBlocks() {
		return blocks;
	}

	public static void updatePosition() {
		// loop through all active blocks
		// Update the position of the center piece on the grid

		for (Block current : blocks) {
			if (current.getActive()) {
				// System.out.println("past " + Arrays.toString(currentCenterPiece));

				// Clear its old position
				grid[currentCenterPiece[0]][currentCenterPiece[1] - 1] = 0;
				System.out.println(grid[currentCenterPiece[0]][currentCenterPiece[1]]);

				// grid[currentCenterPiece[0] + 1][currentCenterPiece[1] - 1] = 0;
				// grid[currentCenterPiece[0] - 1][currentCenterPiece[1] - 1] = 0;

				// if (type == 1 && (current.getType() == 2 || current.getType() == 4)) {

				// }

				// Clear the position of each supporting piece on the grid
				for (int[] piece : currentSupportPieces) {
					grid[piece[0]][piece[1]] = 0;
				}

				// Populate the grid with new position
				grid[current.getCenterPiece()[0]][current.getCenterPiece()[1]] = 1;
				// System.out.println("Center Piece: " +
				// Arrays.toString(current.getCenterPiece()));

				// Update the position of each supporting piece on the grid
				for (int[] piece : current.getSupportingPieces()) {
					// System.out.println("Support Piece: " + Arrays.toString(piece));
					grid[piece[0]][piece[1]] = 1;
				}
				for (int[] item : grid) {
					// System.out.println(Arrays.toString(item));
				}

				// Keep track of the current position
				currentCenterPiece = current.getCenterPiece();
				currentSupportPieces = current.getSupportingPieces();
				// System.out.println("current" + Arrays.toString(currentCenterPiece));
				System.out.println("update");
				for (int[] item : grid) {
					System.out.println(Arrays.toString(item));
				}
			}
		}
	}

	public static int[][] getGrid() {
		return grid;
	}

	public void draw(Graphics g) {
		for (Block block : blocks) {
			block.draw(g);
		}
	}
}
