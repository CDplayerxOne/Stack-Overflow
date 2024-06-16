import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void testCheckRotationVailidityGreen() {
        Block block = new Block(4);

        char[][] grid = GameManager.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j]=' ';
                if ((i==6) && (j==2)) {
                    grid[i][j]='D';
                }
            }
        }
        GameManager.activateBlock(block);

        boolean result = block.checkRotationVailidity();
        assertEquals( false, result);
    }

    @Test
    void testCheckRotationVailidityCyan() {
        Block block = new Block(1);

        char[][] grid = GameManager.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j]=' ';
            }
        }
        GameManager.activateBlock(block);

        boolean result = block.checkRotationVailidity();
        assertEquals( true, result);
    }

    @Test
    void testCheckRotationVailidityMagenta() {
        Block block = new Block(3);

        char[][] grid = GameManager.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j]=' ';
            }
        }
        GameManager.activateBlock(block);

        boolean result = block.checkRotationVailidity();
        assertEquals( true, result);
    }
}