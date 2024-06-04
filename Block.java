/*
 * Description: deals with the drawing and manipulation of the block object
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 4th 2024
 */

//import libraries
import java.awt.*;
import java.awt.event.*;

public class Block extends Rectangle {

    //declare objects, variables, and constants
    private final int[][] TYPES = {{1, 0, 1, 0, 1, 0, 1, 0}, {0, 1, 1, 0, 0, 0, 1, 1}, {2, 0, 0, 1, 1, 0, 0, 0},
            {1, 0, 1, 1, 1, 0, 0, 0}, {2, 0, 0, 0, 2, 0, 0, 0}};
    private final Color[] COLOURS = {Color.RED, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.GREEN};
    private int status;
    private int type;
    private final int[] centerPiece = {120, 100};
    private int[] supportingPieces;
    public final int yVelocity = 35;
    public final int xVelocity = 35;
    public static final int BLOCKLENGTH = 35; // length of block

    public Block(int x, int y, int type) {
        super(x, y, BLOCKLENGTH, BLOCKLENGTH);
        this.type = type;
        switch (type) {
            case 0:
                supportingPieces = TYPES[0];
                break;
            case 1:
                supportingPieces = TYPES[1];
                break;
            case 2:
                supportingPieces = TYPES[2];
                break;
            case 3:
                supportingPieces = TYPES[3];
                break;
            case 4:
                supportingPieces = TYPES[4];
                break;

            default:
                break;
        }
    }

    //returns location of the centerPiece
    public int[] getCenterPiece() {
        return centerPiece;
    }


    //returns location of supporting pieces
    public int[] getSupportingPieces() {
        return supportingPieces;
    }

    public void keyPressed(KeyEvent e) {
        // CW block roatation
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            // implement in rotate
            rotate();
        }

        // accelerate downwards
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            move(0,1);
        }

        // move the block the left
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            move(-1,0);
        }

        // move the block to the right
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move(1,0);
        }

        // hold the block and change status
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            move(0,0);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            move(0,0);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move(0,0);
        }
    }

    //moves the block according to its x and y velocity
    public void move(int xVel, int yVel) {
        centerPiece[0] += xVel * xVelocity;
        centerPiece[1] += yVel * yVelocity;
    }

    public void rotate() {
        int[] tempArray = new int[8];
        tempArray[0] = supportingPieces[6];
        tempArray[1] = supportingPieces[7];
        tempArray[2] = supportingPieces[0];
        tempArray[3] = supportingPieces[1];
        tempArray[4] = supportingPieces[2];
        tempArray[5] = supportingPieces[3];
        tempArray[6] = supportingPieces[4];
        tempArray[7] = supportingPieces[5];
        supportingPieces = tempArray;
        // for (int i = 0; i < supportingPieces.length; i++) {
        // System.out.print(supportingPieces[i] + " ");
        // }
        // System.out.println();
    }

    //returns type of the block
    public int getType() {
        return status;
    }

    //returns the yVelocity of the block
    public int getVelocity() {
        return yVelocity;
    }

    //modifies the status of the block
    public void setStatus(int s) {
        status = s;
    }

    //draws the current location of the block on the screen
    public void draw(Graphics g) {

        // Center Piece
        g.setColor(Color.black);
        g.fillRect(centerPiece[0], centerPiece[1], BLOCKLENGTH, BLOCKLENGTH);
        g.setColor(COLOURS[type]);
        g.fillRect(centerPiece[0] + 3, centerPiece[1] + 3, BLOCKLENGTH - 6, BLOCKLENGTH - 6);

        // Supporting Pieces
        // 0 slot
        for (int i = 0; i < supportingPieces[0]; i++) {
            g.setColor(Color.black);
            g.fillRect(centerPiece[0], centerPiece[1] - (i + 1) * 35, BLOCKLENGTH, BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(centerPiece[0] + 3, centerPiece[1] - (i + 1) * 35 + 3, BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        }

        // 1 slot
        for (int i = 0; i < supportingPieces[1]; i++) {

            g.setColor(Color.black);
            g.fillRect(centerPiece[0] + (i + 1) * 35, centerPiece[1] - (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(centerPiece[0] + (i + 1) * 35 + 3, centerPiece[1] - (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        }

        // 2 slot
        for (int i = 0; i < supportingPieces[2]; i++) {

            g.setColor(Color.black);
            g.fillRect(centerPiece[0] + (i + 1) * 35, centerPiece[1], BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(centerPiece[0] + (i + 1) * 35 + 3, centerPiece[1] + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 3 slot
        for (int i = 0; i < supportingPieces[3]; i++) {

            g.setColor(Color.black);
            g.fillRect(centerPiece[0] + (i + 1) * 35, centerPiece[1] + (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(centerPiece[0] + (i + 1) * 35 + 3, centerPiece[1] + (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        }

        // 4 slot
        for (int i = 0; i < supportingPieces[4]; i++) {

            g.setColor(Color.black);
            g.fillRect(centerPiece[0], centerPiece[1] + (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(centerPiece[0] + 3, centerPiece[1] + (i + 1) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 5 slot
        for (int i = 0; i < supportingPieces[5]; i++) {

            g.setColor(Color.black);
            g.fillRect(centerPiece[0] - (i + 1) * 35, centerPiece[1] + (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(centerPiece[0] - (i + 1) * 35 + 3, centerPiece[1] + (i + 1) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 6 slot
        for (int i = 0; i < supportingPieces[6]; i++) {

            g.setColor(Color.black);
            g.fillRect(centerPiece[0] - (i + 1) * 35, centerPiece[1], BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(centerPiece[0] - (i + 1) * 35 + 3, centerPiece[1] + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 7 slot
        for (int i = 0; i < supportingPieces[7]; i++) {

            g.setColor(Color.black);
            g.fillRect(centerPiece[0] - (i + 1) * 35, centerPiece[1] - (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(centerPiece[0] - (i + 1) * 35 + 3, centerPiece[1] - (i + 1) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

    }

}