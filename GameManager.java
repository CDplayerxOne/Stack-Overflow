/*
 * Description: Controls the flow of the game
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 17th 2024
 */

//import libraries
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
    public static boolean finished = true;

    // GameManager constructor
    public GameManager() {
        // set up score multiplier image
        try {
            scoreMultiplierImage = ImageIO.read(new File("Images/ScoreMultiplier.png"));
        } catch (IOException e) {
        }

        // make the grid empty
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    // resets the game for the next round of playing
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
        generateNextBlock = false;
    }

    // generates a block object
    public static void generateBlock() {
        // only if the game hasn't ended
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
                hold.setScoreMultiplier(blocks.get(blocks.size() - 1).hasScoreMultiplier());
                // Clears the current block off the grid and creates a new block
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

    // Swaps the current block on the grid with the held block
    public static void switchBlock() {

        tempBlock = hold;
        // clears the current block from the grid
        for (int[] i : blocks.get(blocks.size() - 1).getSupportingPieces()) {
            grid[i[0]][i[1]] = ' ';
        }
        grid[currentCenterPiece[0]][currentCenterPiece[1]] = ' ';
        // Make a copy and put it in hold
        hold = new Block(blocks.get(blocks.size() - 1).getType());
        hold.setScoreMultiplier(blocks.get(blocks.size() - 1).hasScoreMultiplier());
        blocks.remove(blocks.size() - 1);

        // activate the previously held block
        activateBlock(tempBlock);

    }

    // Sets the block to the current block
    public static void activateBlock(Block b) {
        blocks.add(b);

        // Populate current position
        currentCenterPiece = b.getCenterPiece();
        currentSupportPieces = b.getSupportingPieces();

        // Update the position of the center piece on the grid. It's value depends on
        // whether it is a special or normal block
        if (b.hasScoreMultiplier()) {

            grid[b.getCenterPiece()[0]][b.getCenterPiece()[1]] = COLOURS[b.getType() + 5];
        } else {

            grid[b.getCenterPiece()[0]][b.getCenterPiece()[1]] = COLOURS[b.getType()];
        }

        // Update the position of each supporting piece on the grid
        for (int[] piece : b.getSupportingPieces()) {
            grid[piece[0]][piece[1]] = COLOURS[b.getType()];
        }

        // makes sure that block cannot be held more than once before being placed
        notHeld++;
        if (notHeld > 1) {
            score += 10;
            if (GamePanel.gameRunning) {
                PlaySound.playBlockPlace();
            }
        }

    }

    // Updates the position of all blocks
    public static void updatePosition(int type) {

        // Clear the previous location of the supporting pieces of the block
        for (int[] piece : currentSupportPieces) {
            grid[piece[0]][piece[1]] = ' ';
        }

        // Clear the old center piece's location
        // If it is just vertical movement. If/else statements is to prevent weird
        // things from happening. A.K.A drag
        if (type == 0) {
            if (grid[currentCenterPiece[0]][currentCenterPiece[1] - 1] == ' ') {
                grid[currentCenterPiece[0]][currentCenterPiece[1]] = ' ';

            } else {
                grid[currentCenterPiece[0]][currentCenterPiece[1] - 1] = ' ';
            }
        }

        // Movement to the right
        if (type == 1 && currentCenterPiece[0] - 1 >= 0 && currentCenterPiece[1] >= 0) {
            if (grid[currentCenterPiece[0] - 1][currentCenterPiece[1]] == ' ') {
                grid[currentCenterPiece[0]][currentCenterPiece[1]] = ' ';

            } else {
                grid[currentCenterPiece[0] - 1][currentCenterPiece[1]] = ' ';
            }
        }

        // Movement to the left
        if (type == 2 && currentCenterPiece[0] + 1 < 11 && currentCenterPiece[1] < 21) {

            if (grid[currentCenterPiece[0] + 1][currentCenterPiece[1]] == ' ') {
                grid[currentCenterPiece[0]][currentCenterPiece[1]] = ' ';

            } else {
                grid[currentCenterPiece[0] + 1][currentCenterPiece[1]] = ' ';
            }
        }

        // Populate the grid with new position of the center piece
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

        // Keep track of the current block
        currentCenterPiece = blocks.get(blocks.size() - 1).getCenterPiece();
        currentSupportPieces = blocks.get(blocks.size() - 1).getSupportingPieces();
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

    // Handles the row collapsing when it is full
    public static void rowCollapse() {
        // Loop through each row
        for (int i = 5; i < 22; i++) {
            boolean fullRow = true;
            int multiplier = 1;
            // if at least one spot that is blank, it is not a full row
            for (int j = 0; j < 11; j++) {
                // keeps track of how many multipliers there are
                if (getGrid()[j][i] == 'a' || getGrid()[j][i] == 'x' || getGrid()[j][i] == 'c' || getGrid()[j][i] == 'd'
                        || getGrid()[j][i] == 'e') {
                    multiplier++;
                }
                if (getGrid()[j][i] == ' ') {
                    fullRow = false;
                    break;
                }
            }

            // check if it's a full row. If it is, reposition everything
            if (fullRow) {
                PlaySound.PlayRowClear();
                score += 100 * multiplier;
                char[][] temp = new char[11][i];
                // Loop through the grid
                for (int p = 0; p < i; p++) {
                    for (int j = 0; j < 11; j++) {
                        // store everything above in a temporary 2D array
                        temp[j][p] = getGrid()[j][p];
                    }
                }

                // clear the top row
                for (int j = 0; j < 11; j++) {
                    temp[j][0] = ' ';
                }

                // put everything above the collapsed row, one row lower
                for (int j = 0; j < 11; j++) {
                    System.arraycopy(temp[j], 0, grid[j], 1, i + 1 - 1);
                }
            }
        }
    }

    // draws the blocks
    public void draw(Graphics g) {

        // Next block
        nextblock.drawNextPos(g);

        // Held blocks
        if (hold != null) {
            hold.drawHoldPosition(g);
            //Draws the star
            if(hold.hasScoreMultiplier()){
                g.drawImage(scoreMultiplierImage, (hold.getHoldCenterPos()[0]) * 35 + 123, (hold.getHoldCenterPos()[1]) * 35 + 3,
                        null);
            }
        }

        // Draws the grid
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

                    // Special score multiplier stars
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
