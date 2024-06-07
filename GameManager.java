import java.awt.Graphics;
import java.util.*;

public class GameManager {
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	public static int[][] grid = new int[11][22];

	public static void generateBlock() {
		blocks.add(new Block(500, 200, (int) Math.random() * 8));
	}

	public static ArrayList<Block> getBlocks(){
		return blocks;
	}

	public static void updatePosition() {
		// loop through all active blocks
		for (Block block : blocks) {
			if (block.getStatus() == 1) {
				int max = Arrays.stream(Arrays.copyOfRange(block.getSupportingPieces(), 0, 2)).max().getAsInt();
				if (block.getCenterPiece()[1] >= (33 + 35 * max)) {
					grid[5][max] = 1;
					for (int i = 0; i < block.getSupportingPieces().length; i++) {

					}
				}
			}
		}
	}

	public void draw(Graphics g) {
		for (Block block : blocks) {
			block.draw(g);
		}
	}
}
