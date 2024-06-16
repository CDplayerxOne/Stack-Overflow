/*
 * Description: Controls the game
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 16th 2024
 */

//import libraries
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
import javax.imageio.*;
import java.io.*;

public class GameManager {
    // declare variables, constants, and objects
    public static ArrayList<Block> blocks = new ArrayList<>();
    public static final char[] COLOURS = { 'r', 'b', 'y', 'p', 'g', 'a', 'x', 'c', 'd', 'e' };
    public static char[][] grid = new char[11][22];
    public static int[] currentCenterPiece = new int[2];
    public static ArrayList<int[]> currentSupportPieces = new ArrayList<>();
    public static Block nextblock = new Block((int) (Math.random() * 5));
    public static Block hold, tempBlock;
    public static boolean generateNextBlock = false;
    public static int score = -10;
    public static int notHeld = 2;
    private Image scoreMultiplierImage;

    // GameManager constructor
    public GameManager() {
        // set up score multiplier image
        try {
            scoreMultiplierImage = ImageIO.read(new File("Images/ScoreMultiplier.png"));
        } catch (IOException e) {
            System.out.println("uh oh");
        }

        // make the grid empty
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
        score = -10;
        notHeld = 2;
        blocks = new ArrayList<>();
        currentSupportPieces = new ArrayList<>();
        hold = null;
        tempBlock = null;
    }

    // generates a block object
    public static void generateBlock() {
        if (!checkEnd()) {
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
                System.out.println(blocks.get(blocks.size() - 1).hasScoreMultiplier() + "status");
                hold.setScoreMultiplier(blocks.get(blocks.size() - 1).hasScoreMultiplier());
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
        hold.setScoreMultiplier(blocks.get(blocks.size() - 1).hasScoreMultiplier());
        blocks.remove(blocks.size() - 1);
        activateBlock(tempBlock);

    }

    public static void activateBlock(Block b) {
        blocks.add(b);

        // Populate current position
        currentCenterPiece = b.getCenterPiece();
        currentSupportPieces = b.getSupportingPieces();

        // Update the position of the center piece on the grid
        if (b.hasScoreMultiplier()) {

            grid[b.getCenterPiece()[0]][b.getCenterPiece()[1]] = COLOURS[b.getType() + 5];
        } else {

            grid[b.getCenterPiece()[0]][b.getCenterPiece()[1]] = COLOURS[b.getType()];
        }

        // Update the position of each supporting piece on the grid
        for (int[] piece : b.getSupportingPieces()) {
            grid[piece[0]][piece[1]] = COLOURS[b.getType()];
        }

        for (char[] item : grid) {
            System.out.println(Arrays.toString(item));
        }

        notHeld++;

        if (notHeld > 1) {
            score += 10;
            if (GamePanel.gameRunning) {
                PlaySound.PlayholdBlock();
            }
        }

    }

    // updates the position of all blocks
    public static void updatePosition(int type) {
        // loop through all active blocks
        // Update the position of the center piece on the grid

        System.out.println("start of updatePosition");
        // System.out.println("past " + Arrays.toString(currentCenterPiece));

        // Clear its old position
        grid[currentCenterPiece[0]][currentCenterPiece[1] - 1] = ' ';
        System.out.println(grid[currentCenterPiece[0]][currentCenterPiece[1] - 1]);

        if (type == 1 && currentCenterPiece[0] - 1 >= 0 && currentCenterPiece[1] >= 0) {
            grid[currentCenterPiece[0] - 1][currentCenterPiece[1]] = ' ';
            System.out.println(grid[currentCenterPiece[0] - 1][currentCenterPiece[1]] + " right");
        }

        if (type == 2 && currentCenterPiece[0] + 1 < 11 && currentCenterPiece[1] < 21) {

            grid[currentCenterPiece[0] + 1][currentCenterPiece[1]] = ' ';
            System.out.println(grid[currentCenterPiece[0] + 1][currentCenterPiece[1]] + " left");
        }

        // Clear the position of each supporting piece on the grid
        for (int[] piece : currentSupportPieces) {
            grid[piece[0]][piece[1]] = ' ';
        }

        // Populate the grid with new position
        if (blocks.get(blocks.size() - 1).hasScoreMultiplier()) {

            grid[blocks.get(blocks.size() - 1).getCenterPiece()[0]][blocks.get(blocks.size() - 1)
                    .getCenterPiece()[1]] = COLOURS[blocks.get(blocks.size() - 1).getType() + 5];
        } else {

            grid[blocks.get(blocks.size() - 1).getCenterPiece()[0]][blocks.get(blocks.size() - 1)
                    .getCenterPiece()[1]] = COLOURS[blocks.get(blocks.size() - 1).getType()];
        }

        // Update the position of each supporting piece on the grid
        for (int[] piece : blocks.get(blocks.size() - 1).getSupportingPieces()) {
            grid[piece[0]][piece[1]] = COLOURS[blocks.get(blocks.size() - 1).getType()];
        }

        // Keep track of the current position
        currentCenterPiece = blocks.get(blocks.size() - 1).getCenterPiece();
        currentSupportPieces = blocks.get(blocks.size() - 1).getSupportingPieces();
        for (char[] item : grid) {
            System.out.println(Arrays.toString(item));
        }
        System.out.println("end of updatePosition");
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
            int multiplier = 1;
            // if at least one spot that is blank, it is not a full row
            for (int j = 0; j < 11; j++) {
                if (getGrid()[j][i] == 'a' || getGrid()[j][i] == 'x' || getGrid()[j][i] == 'c' || getGrid()[j][i] == 'd'
                        || getGrid()[j][i] == 'e') {
                    multiplier++;
                }
                if (getGrid()[j][i] == ' ') {
                    fullRow = false;
                    break;
                }
            }
            // check if it's a full row. If it is reposition everything
            if (fullRow) {
                PlaySound.PlayRowClear();
                score += 100 * multiplier;
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
                    // put them one row lower
                    System.arraycopy(temp[j], 0, grid[j], 1, i + 1 - 1);
                }
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
                        case 'a':
                            g.setColor(Color.RED);
                            break;
                        case 'x':
                            g.setColor(Color.CYAN);
                            break;
                        case 'c':
                            g.setColor(Color.YELLOW);
                            break;
                        case 'd':
                            g.setColor(Color.MAGENTA);
                            break;
                        case 'e':
                            g.setColor(Color.GREEN);
                            break;
                        default:
                            g.setColor(Color.BLACK);
                            break;
                    }
                    g.fillRect(143 + i * 35, j * 35 + 3, 29, 29);
                    switch (getGrid()[i][j]) {
                        case 'a':
                            g.drawImage(scoreMultiplierImage, 143 + i * 35, j * 35 + 3, null);
                            break;
                        case 'x':
                            g.drawImage(scoreMultiplierImage, 143 + i * 35, j * 35 + 3, null);
                            g.setColor(Color.CYAN);
                            break;
                        case 'c':
                            g.drawImage(scoreMultiplierImage, 143 + i * 35, j * 35 + 3, null);
                            g.setColor(Color.YELLOW);
                            break;
                        case 'd':
                            g.drawImage(scoreMultiplierImage, 143 + i * 35, j * 35 + 3, null);
                            g.setColor(Color.MAGENTA);
                            break;
                        case 'e':
                            g.drawImage(scoreMultiplierImage, 143 + i * 35, j * 35 + 3, null);
                            g.setColor(Color.GREEN);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
