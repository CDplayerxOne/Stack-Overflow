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

    public int getType() {
        return type;
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
                GameManager.updatePosition(1);
            }
        } else {
            if (checkMoveVailidityLeft()) {
                centerPiece[0] += xVel;
                centerPiece[1] += yVel;
                GameManager.updatePosition(2);
            }
        }
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

    public boolean checkVerticalCollision() {
        boolean collision = false;
        boolean cont = true;

        // if the row under it is above the bottom, if itself is not 0 and if the square
        // under it is taken
        if ((centerPiece[1] + supportingPieces[4] + 1) <= 21 && supportingPieces[4] != 0
                && GameManager.getGrid()[centerPiece[0]][centerPiece[1] + supportingPieces[4] + 1] == 1) {

            collision = true;
            cont = false;
            if (isActive) {
                GameManager.next = true;
                System.out.println("collision 1");
                for (int[] item : GameManager.getGrid()) {
                    System.out.println(Arrays.toString(item));
                }
            }
            isActive = false;

        }

        // if the square under it can exist. If the it itself is not 0 and if the square
        // under it is taken
        if ((centerPiece[1] + 2) <= 21 && supportingPieces[3] != 0
                && GameManager.getGrid()[centerPiece[0] + 1][centerPiece[1] + 2] == 1 && cont) {

            collision = true;
            cont = false;
            if (isActive) {
                GameManager.next = true;
                System.out.println("collision 2");
            }
            isActive = false;

        }

        // if the square under it can exist. If the it itself is not 0 and if the square
        // under it is taken
        if ((centerPiece[1] + 2) <= 21 && supportingPieces[5] != 0
                && GameManager.getGrid()[centerPiece[0] - 1][centerPiece[1] + 2] == 1 && cont) {

            collision = true;
            cont = false;
            if (isActive) {
                GameManager.next = true;
                System.out.println("collision 3");
            }
            isActive = false;

        }

        // if it is not the bottom row and the block below is occupied
        if ((centerPiece[1] + 1) <= 21
                && GameManager.getGrid()[centerPiece[0]][centerPiece[1] + 1] == 1 && supportingPieces[4] == 0) {

            collision = true;
            cont = false;
            if (isActive) {
                GameManager.next = true;
                System.out.println("collision 4");
                for (int[] item : GameManager.getGrid()) {
                    System.out.println(Arrays.toString(item));
                }
            }
            isActive = false;

        }

        // the 6 spot
        // if itself is not 0. If it does not go beyond 21 and if the spot under it is
        // occupied
        if ((centerPiece[1] + 1) <= 21 && supportingPieces[6] != 0
                && GameManager.getGrid()[centerPiece[0] - 1][centerPiece[1] + 1] == 1 && cont
                && supportingPieces[5] == 0) {

            collision = true;
            cont = false;
            if (isActive) {
                GameManager.next = true;
                System.out.println("collision 5");
            }
            isActive = false;

        }

        if ((centerPiece[1] + 1) <= 21 && supportingPieces[2] != 0
                && GameManager.getGrid()[centerPiece[0] + 1][centerPiece[1] + 1] == 1 && cont
                && supportingPieces[3] == 0) {

            collision = true;
            cont = false;
            if (isActive) {
                GameManager.next = true;
                System.out.println("collision 6");
                for (int[] item : GameManager.getGrid()) {
                    System.out.println(Arrays.toString(item));
                }
            }
            isActive = false;

        }

        if ((centerPiece[1]) <= 21 && supportingPieces[7] != 0
                && GameManager.getGrid()[centerPiece[0] - 1][centerPiece[1]] == 1 && cont
                && supportingPieces[5] == 0 && supportingPieces[6] == 0) {

            collision = true;
            cont = false;
            if (isActive) {
                GameManager.next = true;
                System.out.println("collision 7");
            }
            isActive = false;

        }

        if ((centerPiece[1]) <= 21 && supportingPieces[1] != 0
                && GameManager.getGrid()[centerPiece[0]][centerPiece[1]] == 1 && cont
                && supportingPieces[3] == 0 && supportingPieces[2] == 0) {

            collision = true;
            cont = false;
            if (isActive) {
                GameManager.next = true;
                System.out.println("collision 8");
            }
            isActive = false;

        }

        return collision;
    }

    public boolean checkHorizontalCollision() {

        return true;
    }

    public void autoFall() {
        if ((getCenterPiece()[1] + supportingPieces[3] < 21) && (getCenterPiece()[1] + supportingPieces[4] < 21)
                && (getCenterPiece()[1] + supportingPieces[5] < 21)) {

            if (!checkVerticalCollision()) {
                // Every 33 frames
                internalCount++;
                if (internalCount == 33) {
                    // move down
                    centerPiece[1] += 1;
                    internalCount = 0;
                    GameManager.updatePosition(0);
                    System.out.println(checkVerticalCollision() + " collision");
                }
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
            GameManager.updatePosition(0);
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