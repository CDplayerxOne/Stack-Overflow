/*
 * Description: deals with the drawing and manipulation of the block object
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 4th 2024
 */

//import libraries

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

// ! NOTE for later: Draw method will draw differently based on status!

public class Block extends Rectangle {

    // declare objects, variables, and constants
    private final int[][] TYPES = { { 1, 0, 1, 0, 1, 0, 1, 0 }, { 0, 1, 1, 0, 0, 0, 1, 1 }, { 2, 0, 0, 1, 1, 0, 0, 0 },
            { 1, 0, 1, 1, 1, 0, 0, 0 }, { 2, 0, 0, 0, 2, 0, 0, 0 } };
    private final int[][] STARTING_POSITIONS = { { 5, 1 }, { 5, 1 }, { 5, 2 }, { 5, 1 }, { 5, 2 } };
    private final Color[] COLOURS = { Color.RED, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.GREEN };
    private boolean isActive;
    private final int type;
    private int[] centerPiece;
    private int[] supportingPieces;
    public static final int BLOCKLENGTH = 35; // length of block
    private int internalCount = 0;

    public Block(int type) {
        this.type = type;
        isActive = true;
        switch (type) {
            case 0:
                supportingPieces = TYPES[0];
                centerPiece = STARTING_POSITIONS[0];
                break;
            case 1:
                supportingPieces = TYPES[1];
                centerPiece = STARTING_POSITIONS[1];
                break;
            case 2:
                supportingPieces = TYPES[2];
                centerPiece = STARTING_POSITIONS[2];
                break;
            case 3:
                supportingPieces = TYPES[3];
                centerPiece = STARTING_POSITIONS[3];
                break;
            case 4:
                supportingPieces = TYPES[4];
                centerPiece = STARTING_POSITIONS[4];
                break;

            default:
                break;
        }
    }

    // returns location of the centerPiece
    public int[] getCenterPiece() {
        return centerPiece;
    }

    // returns location of supporting pieces
    public ArrayList<int[]> getSupportingPieces() {
        ArrayList<int[]> pieces = new ArrayList<>();
        for (int i = 0; i < supportingPieces[0]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0];
            coordinate[1] = centerPiece[1] - (i + 1);
            pieces.add(coordinate);
        }
        for (int i = 0; i < supportingPieces[1]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] + (i + 1);
            coordinate[1] = centerPiece[1] - (i + 1);
            pieces.add(coordinate);
        }
        for (int i = 0; i < supportingPieces[2]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] + (i + 1);
            coordinate[1] = centerPiece[1];
            pieces.add(coordinate);
        }
        for (int i = 0; i < supportingPieces[3]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] + (i + 1);
            coordinate[1] = centerPiece[1] + (i + 1);
            pieces.add(coordinate);
        }
        for (int i = 0; i < supportingPieces[4]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0];
            coordinate[1] = centerPiece[1] + (i + 1);
            pieces.add(coordinate);
        }
        for (int i = 0; i < supportingPieces[5]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] - (i + 1);
            coordinate[1] = centerPiece[1] + (i + 1);
            pieces.add(coordinate);
        }
        for (int i = 0; i < supportingPieces[6]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] - (i + 1);
            coordinate[1] = centerPiece[1];
            pieces.add(coordinate);
        }
        for (int i = 0; i < supportingPieces[7]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] - (i + 1);
            coordinate[1] = centerPiece[1] - (i + 1);
            pieces.add(coordinate);
        }
        return pieces;
    }

    public boolean getActive() {
        return isActive;
    }

    public void keyPressed(KeyEvent e) {
        // CW block roatation
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            // implement in rotate
            if (isActive) {
                rotate();
            }
        }

        // accelerate downwards
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (isActive) {
                move(0, 1);
            }
        }

        // move the block the left
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (isActive) {
                move(-1, 0);
            }
        }

        // move the block to the right
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (isActive) {
                move(1, 0);
            }
        }

        // hold the block and change status
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            move(0, 0);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            move(0, 0);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move(0, 0);
        }
    }

    // moves the block according to its x and y velocity
    public void move(int xVel, int yVel) {
        if (xVel > 0) {
            if (checkMoveVailidityRight()) {
                centerPiece[0] += xVel;
                centerPiece[1] += yVel;
            }
        } else {
            if (checkMoveVailidityLeft()) {
                centerPiece[0] += xVel;
                centerPiece[1] += yVel;
            }
        }
        GameManager.updatePosition();
    }

    public boolean checkMoveVailidityRight() {
        if ((getCenterPiece()[0] + supportingPieces[1] < 10) && (getCenterPiece()[0] + supportingPieces[2] < 10)
                && (getCenterPiece()[0] + supportingPieces[3] < 10)) {
            return true;
        }
        return false;
    }

    public boolean checkMoveVailidityLeft() {
        if ((getCenterPiece()[0] - supportingPieces[5] > 0) && (getCenterPiece()[0] - supportingPieces[6] > 0)
                && (getCenterPiece()[0] - supportingPieces[7] > 0)) {
            return true;
        }
        return false;
    }

    public void autoFall() {
        if ((getCenterPiece()[1] + supportingPieces[3] < 21) && (getCenterPiece()[1] + supportingPieces[4] < 21)
                && (getCenterPiece()[1] + supportingPieces[5] < 21)) {
            // Every 33 frames
            internalCount++;
            if (internalCount == 33) {
                System.out.println("ok: " + Arrays.toString(GameManager.currentCenterPiece));
                // move down
                centerPiece[1] += 1;
                System.out.println("ok: " + Arrays.toString(GameManager.currentCenterPiece));
                internalCount = 0;
                GameManager.updatePosition();
            }
        } else {
            // change status here
            if (isActive) {
                GameManager.next = true;
            }
            isActive = false;

            // GameManager.generateBlock();
        }
    }

    // method to rotate the block
    public void rotate() {
        int[] tempArray = new int[8];

        if (checkRotationVailidity()) {
            tempArray[0] = supportingPieces[6];
            tempArray[1] = supportingPieces[7];
            tempArray[2] = supportingPieces[0];
            tempArray[3] = supportingPieces[1];
            tempArray[4] = supportingPieces[2];
            tempArray[5] = supportingPieces[3];
            tempArray[6] = supportingPieces[4];
            tempArray[7] = supportingPieces[5];
            supportingPieces = tempArray;
            GameManager.updatePosition();
        }
    }

    public boolean checkRotationVailidity() {
        if ((supportingPieces[7] + getCenterPiece()[0] < 11) && (supportingPieces[0] + getCenterPiece()[0] < 11)
                && (supportingPieces[1] + getCenterPiece()[0] < 11) && (getCenterPiece()[0] - supportingPieces[3] > -1)
                && (getCenterPiece()[0] - supportingPieces[4] > -1)
                && (getCenterPiece()[0] - supportingPieces[5] > -1)) {
            return true;
        }
        return false;
    }

    // returns the yVelocity of the block

    // draws the current location of the block on the screen
    public void draw(Graphics g) {

        // Center Piece
        g.setColor(Color.black);
        g.fillRect(120 + (centerPiece[0]) * 35, (centerPiece[1]) * 35, BLOCKLENGTH, BLOCKLENGTH);
        g.setColor(COLOURS[type]);
        g.fillRect((centerPiece[0]) * 35 + 123, (centerPiece[1]) * 35 + 3, BLOCKLENGTH - 6, BLOCKLENGTH - 6);

        // Supporting Pieces
        // 0 slot
        for (int i = 0; i < supportingPieces[0]; i++) {
            g.setColor(Color.black);
            g.fillRect(120 + (centerPiece[0]) * 35, (centerPiece[1]) * 35 - (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((centerPiece[0]) * 35 + 123, (centerPiece[1]) * 35 - (i + 1) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 1 slot
        for (int i = 0; i < supportingPieces[1]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (centerPiece[0]) * 35 + (i + 1) * 35, (centerPiece[1]) * 35 - (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(120 + (centerPiece[0]) * 35 + (i + 1) * 35 + 3, (centerPiece[1]) * 35 - (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        }

        // 2 slot
        for (int i = 0; i < supportingPieces[2]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (centerPiece[0]) * 35 + (i + 1) * 35, (centerPiece[1]) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((centerPiece[0]) * 35 + (i + 1) * 35 + 123, (centerPiece[1]) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 3 slot
        for (int i = 0; i < supportingPieces[3]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (centerPiece[0]) * 35 + (i + 1) * 35, (centerPiece[1]) * 35 + (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((centerPiece[0]) * 35 + (i + 1) * 35 + 123, (centerPiece[1]) * 35 + (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        }

        // 4 slot
        for (int i = 0; i < supportingPieces[4]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (centerPiece[0]) * 35, (centerPiece[1]) * 35 + (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((centerPiece[0]) * 35 + 123, (centerPiece[1]) * 35 + (i + 1) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 5 slot
        for (int i = 0; i < supportingPieces[5]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (centerPiece[0]) * 35 - (i + 1) * 35, (centerPiece[1]) * 35 + (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((centerPiece[0]) * 35 - (i + 1) * 35 + 123, (centerPiece[1]) * 35 + (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 6 slot
        for (int i = 0; i < supportingPieces[6]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (centerPiece[0]) * 35 - (i + 1) * 35, (centerPiece[1]) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((centerPiece[0]) * 35 - (i + 1) * 35 + 123, (centerPiece[1]) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 7 slot
        for (int i = 0; i < supportingPieces[7]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (centerPiece[0]) * 35 - (i + 1) * 35, (centerPiece[1]) * 35 - (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((centerPiece[0]) * 35 - (i + 1) * 35 + 123, (centerPiece[1]) * 35 - (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

    }

}