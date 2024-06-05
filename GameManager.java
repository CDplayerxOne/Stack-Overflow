import java.awt.Graphics;
import java.util.ArrayList;

public class GameManager {
	public static ArrayList<Block> blocks = new ArrayList<Block>();

	public static void generateBlock() {
		blocks.add(new Block(100, 200, (int) Math.random() * 8));
	}

	public void draw(Graphics g) {
		for (Block block : blocks) {
			block.draw(g);
		}
	}
}
