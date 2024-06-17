/*
 * Description: deals with the drawing(certain states) and manipulation of the block object
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 17th 2024
 */

//import libraries
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Block extends Rectangle {

    // declare objects, variables, and constants
    private final int[][] TYPES = { { 1, 0, 1, 0, 1, 0, 1, 0 }, { 0, 1, 1, 0, 0, 0, 1, 1 }, { 2, 0, 0, 1, 1, 0, 0, 0 },
            { 1, 0, 1, 1, 1, 0, 0, 0 }, { 2, 0, 0, 0, 2, 0, 0, 0 } };
    private final int[][] STARTING_POSITIONS = { { 5, 1 }, { 5, 1 }, { 5, 2 }, { 5, 1 }, { 5, 2 } };
    private final int[][] NEXT_POSITIONS = { { 13, 18 }, { 13, 19 }, { 13, 19 }, { 13, 18 }, { 13, 18 } };
    private final int[][] HOLD_POSITIONS = { { -2, 18 }, { -2, 19 }, { -2, 19 }, { -2, 18 }, { -2, 18 } };
    private final Color[] COLOURS = { Color.RED, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.GREEN };
    private final int type;
    private int[] centerPiece;
    private int[] supportingPieces;
    private int[] nextCenterPos;
    private int[] holdCenterPos;
    public static final int BLOCKLENGTH = 35; // length of block
    private int internalCount = 0;
    private boolean scoreMultiplier;
    private Image scoreMultiplierImage;
    public boolean holdingDown = false;

    // constructor for Block
    public Block(int type) {
        this.type = type;

        // % chance the block has a score multiplier
        if (Math.random() < 0.2) {
            scoreMultiplier = true;
            try {
                scoreMultiplierImage = ImageIO.read(new File("Images/ScoreMultiplier.png"));
            } catch (IOException e) {
            }
        } else {
            scoreMultiplier = false;
        }

        // assign the correct supporting pecies, center position, next center position,
        // and hold center position for a block type
        switch (type) {
            case 0:
                supportingPieces = TYPES[0];
                centerPiece = STARTING_POSITIONS[0];
                nextCenterPos = NEXT_POSITIONS[0];
                holdCenterPos = HOLD_POSITIONS[0];
                break;
            case 1:
                supportingPieces = TYPES[1];
                centerPiece = STARTING_POSITIONS[1];
                nextCenterPos = NEXT_POSITIONS[1];
                holdCenterPos = HOLD_POSITIONS[1];
                break;
            case 2:
                supportingPieces = TYPES[2];
                centerPiece = STARTING_POSITIONS[2];
                nextCenterPos = NEXT_POSITIONS[2];
                holdCenterPos = HOLD_POSITIONS[2];
                break;
            case 3:
                supportingPieces = TYPES[3];
                centerPiece = STARTING_POSITIONS[3];
                nextCenterPos = NEXT_POSITIONS[3];
                holdCenterPos = HOLD_POSITIONS[3];
                break;
            case 4:
                supportingPieces = TYPES[4];
                centerPiece = STARTING_POSITIONS[4];
                nextCenterPos = NEXT_POSITIONS[4];
                holdCenterPos = HOLD_POSITIONS[4];
                break;
            default:
                break;
        }
    }

    // returns location of the centerPiece
    public int[] getCenterPiece() {
        return centerPiece;
    }

    // method to return type of block
    public int getType() {
        return type;
    }

    // returns location of supporting pieces
    public ArrayList<int[]> getSupportingPieces() {
        ArrayList<int[]> pieces = new ArrayList<>();

        // coords for supporting piece 0
        for (int i = 0; i < supportingPieces[0]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0];
            coordinate[1] = centerPiece[1] - (i + 1);
            pieces.add(coordinate);
        }

        // coords for supporting piece 1
        for (int i = 0; i < supportingPieces[1]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] + (i + 1);
            coordinate[1] = centerPiece[1] - (i + 1);
            pieces.add(coordinate);
        }

        // coords for supporting piece 2
        for (int i = 0; i < supportingPieces[2]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] + (i + 1);
            coordinate[1] = centerPiece[1];
            pieces.add(coordinate);
        }

        // coords for supporting piece 3
        for (int i = 0; i < supportingPieces[3]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] + (i + 1);
            coordinate[1] = centerPiece[1] + (i + 1);
            pieces.add(coordinate);
        }

        // coords for supporting piece 4
        for (int i = 0; i < supportingPieces[4]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0];
            coordinate[1] = centerPiece[1] + (i + 1);
            pieces.add(coordinate);
        }

        // coords for supporting piece 5
        for (int i = 0; i < supportingPieces[5]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] - (i + 1);
            coordinate[1] = centerPiece[1] + (i + 1);
            pieces.add(coordinate);
        }

        // coords for supporting piece 6
        for (int i = 0; i < supportingPieces[6]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] - (i + 1);
            coordinate[1] = centerPiece[1];
            pieces.add(coordinate);
        }

        // coords for supporting piece 7
        for (int i = 0; i < supportingPieces[7]; i++) {
            int[] coordinate = new int[2];
            coordinate[0] = centerPiece[0] - (i + 1);
            coordinate[1] = centerPiece[1] - (i + 1);
            pieces.add(coordinate);
        }

        return pieces;
    }

    // method to respond to a key press
    public void keyPressed(KeyEvent e) {

        // CW block roatation
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            rotate();
        }

        // accelerate downwards
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            holdingDown = true;
            move(0, 1);
        }

        // move the block the left
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            move(-1, 0);
        }

        // move the block to the right
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move(1, 0);
        }

        // hold the block and change status
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (GamePanel.gameRunning) {
                GameManager.holdBlock();
            }
        }
    }

    // method to respond to a key being released
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            move(0, 0);
            holdingDown = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            move(0, 0);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move(0, 0);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move(0, 0);
        }
    }

    // moves the block according to its x and y velocity
    public void move(int xVel, int yVel) {

        // updates position if block is moving to the right
        if (xVel > 0) {
            if (!checkHorizontalCollisionRight()) {

                centerPiece[0] += xVel;
                GameManager.updatePosition(1);
            }
        }

        // updates position if block is moving to the left
        if (xVel < 0) {
            if (!checkHorizontalCollisionLeft()) {
                centerPiece[0] += xVel;
                GameManager.updatePosition(2);
            }
        }

        // updates position and checks for a vertical collision if the block is moving
        // down
        if (!checkVerticalCollision()) {
            centerPiece[1] += yVel;
            GameManager.updatePosition(0);
        } else {
            GameManager.generateNextBlock = true;
        }
    }

    // checks if the block is restricted by the right boarder
    public boolean checkMoveVailidityRight() {
        if ((getCenterPiece()[0] + supportingPieces[1] < 10) && (getCenterPiece()[0] + supportingPieces[2] < 10)
                && (getCenterPiece()[0] + supportingPieces[3] < 10)) {
            return true;
        }
        return false;
    }

    // checks if the block is restricted by the left boarder
    public boolean checkMoveVailidityLeft() {
        if ((getCenterPiece()[0] - supportingPieces[5] > 0) && (getCenterPiece()[0] - supportingPieces[6] > 0)
                && (getCenterPiece()[0] - supportingPieces[7] > 0)) {
            return true;
        }
        return false;
    }

    // checks if the block collides with another block
    public boolean checkVerticalCollision() {
        boolean collision = false;

        // check if the center piece is above the bottom
        if ((centerPiece[1] + 1) >= 22) {
            collision = true;
        }

        // check if the bottom pieces are touching the bottom
        if (((centerPiece[1] + supportingPieces[3] + 1) >= 22)
                || ((centerPiece[1] + supportingPieces[4] + 1) >= 22)
                || ((centerPiece[1] + supportingPieces[5] + 1) >= 22)) {
            collision = true;

        } else {

            // check if there is a block under postion 1
            if (supportingPieces[1] > supportingPieces[2] && supportingPieces[1] > supportingPieces[3]) {
                if (GameManager.getGrid()[centerPiece[0] - 1][centerPiece[1]] != ' ') {
                    collision = true;
                }
            }

            // edge case: blue block when it is c shaped to the right
            if (supportingPieces[1] != 0 && supportingPieces[3] != 0) {
                if (GameManager.getGrid()[centerPiece[0] + 1][centerPiece[1]] != ' ') {
                    collision = true;
                }
            }

            // check if there is a block below position 2
            if (supportingPieces[2] > supportingPieces[3]) {
                for (int i = 1; i <= supportingPieces[2]; i++) {
                    if (GameManager.getGrid()[centerPiece[0] + i][centerPiece[1] + 1] != ' ') {
                        collision = true;
                    }
                }
            }

            // check if there is a block below position 3
            if (supportingPieces[3] != 0) {
                if (GameManager.getGrid()[centerPiece[0] + 1][centerPiece[1] + 2] != ' ') {
                    collision = true;
                }
            }

            // check if there is a block below position 4
            if (supportingPieces[4] != 0) {
                if (GameManager.getGrid()[centerPiece[0]][centerPiece[1] + 1 + supportingPieces[4]] != ' ') {
                    collision = true;
                }
            }

            // check if there is a block below position 5
            if (supportingPieces[5] != 0) {
                if (GameManager.getGrid()[centerPiece[0] - 1][centerPiece[1] + 2] != ' ') {
                    collision = true;
                }
            }

            // check if there is a position below position 6
            if (supportingPieces[6] > supportingPieces[5]) {
                for (int i = 1; i <= supportingPieces[6]; i++) {
                    if (GameManager.getGrid()[centerPiece[0] - i][centerPiece[1] + 1] != ' ') {
                        collision = true;
                    }
                }
            }

            // edge case: blue block when it is c shaped to the left
            if (supportingPieces[7] != 0 && supportingPieces[5] != 0) {
                if (GameManager.getGrid()[centerPiece[0] - 1][centerPiece[1]] != ' ') {
                    collision = true;
                }
            }

            // check if there is a block under position 7
            if (supportingPieces[7] > supportingPieces[6] && supportingPieces[7] > supportingPieces[5]) {
                if (GameManager.getGrid()[centerPiece[0] - 1][centerPiece[1]] != ' ') {
                    collision = true;

                }
            }

            // check if there is a block below center piece
            if (supportingPieces[4] == 0) {
                if (GameManager.getGrid()[centerPiece[0]][centerPiece[1] + 1] != ' ') {
                    collision = true;
                }
            }
        }

        // return if there is a collsion or not
        if (collision) {
            GameManager.generateNextBlock = true;
            return true;
        } else {
            return false;
        }
    }

    // checks if there is a right horizontal collision between blocks
    public boolean checkHorizontalCollisionRight() {
        if (checkMoveVailidityRight()) {

            // checks the if there are blocks on the right of position 1
            if (GameManager.getGrid()[getCenterPiece()[0] + supportingPieces[1] + 1][getCenterPiece()[1] - 1] != ' ') {
                return true;
            }

            // checks the if there are blocks on the right of position 2
            if (GameManager.getGrid()[getCenterPiece()[0] + supportingPieces[2]
                    + 1][getCenterPiece()[1]] != ' ') {
                return true;
            }

            // checks the if there are blocks on the right of position 3
            if (GameManager.getGrid()[getCenterPiece()[0] + supportingPieces[3] + 1][getCenterPiece()[1]
                    + 1] != ' ') {
                return true;
            }

            // checks the if there are blocks on the right of position 0
            if (supportingPieces[0] > supportingPieces[1]) {

                for (int i = 0; i < supportingPieces[0]; i++) {
                    if (GameManager.getGrid()[getCenterPiece()[0] + 1][getCenterPiece()[1] - 1 - i] != ' ') {
                        return true;
                    }
                }
            }

            // checks the if there are blocks on the right of position 4
            if (supportingPieces[4] > supportingPieces[3]) {

                for (int i = 0; i < supportingPieces[4]; i++) {
                    if (GameManager.getGrid()[getCenterPiece()[0] + 1][getCenterPiece()[1] + 1 + i] != ' ') {
                        return true;
                    }
                }
            }

            // checks the right postiion to supporting peice 1
            if (supportingPieces[1] != 0) {
                if (GameManager.getGrid()[getCenterPiece()[0] + 2][getCenterPiece()[1] - 1] != ' ') {
                    return true;
                }
            }

            // checks the right postiion to supporting peice 3
            if (supportingPieces[3] != 0) {
                if (GameManager.getGrid()[getCenterPiece()[0] + 2][getCenterPiece()[1] + 1] != ' ') {
                    return true;
                }
            }

            // return result of the checks
            return false;
        } else {
            return true;
        }
    }

    // checks if there is a left horizontal collision between blocks
    public boolean checkHorizontalCollisionLeft() {
        if (checkMoveVailidityLeft()) {

            // checks the if there are blocks on the left of position 7
            if (GameManager.getGrid()[getCenterPiece()[0] - supportingPieces[7] - 1][getCenterPiece()[1] - 1] != ' ') {
                return true;
            }

            // checks the if there are blocks on the left of position 6
            if (GameManager.getGrid()[getCenterPiece()[0] - supportingPieces[6]
                    - 1][getCenterPiece()[1]] != ' ') {
                return true;
            }

            // checks the if there are blocks on the left of position 1
            if (getCenterPiece()[1] > 22) {
                if (GameManager.getGrid()[getCenterPiece()[0] - supportingPieces[5] - 1][getCenterPiece()[1]
                        + 1] != ' ') {
                    return true;
                }
            }

            // checks the if there are blocks on the left of position 0
            if (supportingPieces[0] > supportingPieces[7]) {
                for (int i = 0; i < supportingPieces[0]; i++) {
                    if (GameManager.getGrid()[getCenterPiece()[0] - 1][getCenterPiece()[1] - 1 - i] != ' ') {
                        return true;
                    }
                }
            }

            // checks the if there are blocks on the left of position 4
            if (supportingPieces[4] > supportingPieces[5]) {
                for (int i = 0; i < supportingPieces[4]; i++) {
                    if (GameManager.getGrid()[getCenterPiece()[0] - 1][getCenterPiece()[1] + 1 + i] != ' ') {
                        return true;
                    }
                }
            }

            // checks if there is a block to the left of supporting peice 5
            if (supportingPieces[5] != 0) {
                if (GameManager.getGrid()[getCenterPiece()[0] - 2][getCenterPiece()[1] + 1] != ' ') {
                    return true;
                }
            }

            // checks if there is a block to the left of supporting peice 7
            if (supportingPieces[7] != 0) {
                if (GameManager.getGrid()[getCenterPiece()[0] - 2][getCenterPiece()[1] - 1] != ' ') {
                    return true;
                }
            }

            // return result of the checks
            return false;
        } else {
            return true;
        }
    }

    // moves the block while auto falling down
    public void autoFall() {
        internalCount++;
        if (internalCount == 33) {
            if (!checkVerticalCollision()) {
                // Every 33 frames
                // move down
                if (holdingDown == false) {
                    move(0, 1);
                }
            } else {
                GameManager.generateNextBlock = true;
            }
            internalCount = 0;
        }
    }

    // method to rotate the block
    public void rotate() {
        int[] tempArray = new int[8];

        if (checkRotationVailidity()) {

            // use temp array to rotate the block
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

    // checks if the block has a score muliplier
    public boolean hasScoreMultiplier() {
        return scoreMultiplier;

    }

    // Sets the block's score multiplier status
    public void setScoreMultiplier(boolean status) {
        scoreMultiplier = status;
    }

    // checks if the block is restricted by the boarder for rotating
    // true means that it is ok to rotate
    public boolean checkRotationVailidity() {

        boolean canRotate = true;
        char[][] theGrid = GameManager.getGrid();

        // checks if the block will rotate out of the grid
        if ((supportingPieces[7] + getCenterPiece()[0] < 11) && (supportingPieces[0] + getCenterPiece()[0] < 11)
                && (supportingPieces[1] + getCenterPiece()[0] < 11) && (getCenterPiece()[0] - supportingPieces[3] > -1)
                && (getCenterPiece()[0] - supportingPieces[4] > -1)
                && (getCenterPiece()[0] - supportingPieces[5] > -1)) {

            // check rotation for position 1
            if (supportingPieces[1] != 0) {
                if (supportingPieces[3] == 0) {
                    if (theGrid[centerPiece[0] + 1][centerPiece[1] + 1] != ' ') {
                        canRotate = false;
                    }
                }
            }

            // check rotation for position 3
            if (supportingPieces[3] != 0) {
                if (supportingPieces[5] == 0) {
                    if (theGrid[centerPiece[0] - 1][centerPiece[1] + 1] != ' ') {
                        canRotate = false;
                    }
                }
            }

            // check rotation for position 5
            if (supportingPieces[5] != 0) {
                if (supportingPieces[7] == 0) {
                    if (theGrid[centerPiece[0] - 1][centerPiece[1] - 1] != ' ') {
                        canRotate = false;
                    }
                }
            }

            // check rotation for position 7
            if (supportingPieces[7] != 0) {
                if (supportingPieces[1] == 0) {
                    if (theGrid[centerPiece[0] + 1][centerPiece[1] - 1] != ' ') {
                        canRotate = false;
                    }
                }
            }

            // check rotation for position 2
            if (supportingPieces[2] != 0) {
                if (supportingPieces[4] == 0) {
                    for (int i = 1; i <= supportingPieces[2]; i++) {
                        if (theGrid[centerPiece[0]][centerPiece[1] + i] != ' ') {
                            canRotate = false;
                        }
                    }
                }
            }

            // check rotation for position 4
            if (supportingPieces[4] != 0) {
                if (supportingPieces[6] == 0) {
                    for (int i = 1; i <= supportingPieces[4]; i++) {
                        if (theGrid[centerPiece[0] - i][centerPiece[1]] != ' ') {
                            canRotate = false;
                        }
                    }
                }
            }

            // check rotation for position 6
            if (supportingPieces[6] != 0) {
                if (supportingPieces[0] == 0) {
                    for (int i = 1; i <= supportingPieces[6]; i++) {
                        if (theGrid[centerPiece[0]][centerPiece[1] - i] != ' ') {
                            canRotate = false;
                        }
                    }
                }
            }

            // check rotation for position 0
            if (supportingPieces[0] != 0) {
                if (supportingPieces[2] == 0) {
                    for (int i = 1; i <= supportingPieces[0]; i++) {
                        if (theGrid[centerPiece[0] + i][centerPiece[1]] != ' ') {
                            canRotate = false;
                        }
                    }
                }
            }

            // return result of the checks
        } else {
            return false;
        }
        return canRotate;
    }

    // draws the next location of the block on the screen
    public void drawNextPos(Graphics g) {

        // Center Piece
        g.setColor(Color.black);
        g.fillRect(120 + (nextCenterPos[0]) * 35, (nextCenterPos[1]) * 35, BLOCKLENGTH, BLOCKLENGTH);
        g.setColor(COLOURS[type]);
        g.fillRect((nextCenterPos[0]) * 35 + 123, (nextCenterPos[1]) * 35 + 3, BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        if (scoreMultiplier) {
            g.drawImage(scoreMultiplierImage, (nextCenterPos[0]) * 35 + 123, (nextCenterPos[1]) * 35 + 3,
                    null);
        }

        // Supporting Pieces
        // 0 slot
        for (int i = 0; i < supportingPieces[0]; i++) {
            g.setColor(Color.black);
            g.fillRect(120 + (nextCenterPos[0]) * 35, (nextCenterPos[1]) * 35 - (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((nextCenterPos[0]) * 35 + 123, (nextCenterPos[1]) * 35 - (i + 1) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 1 slot
        for (int i = 0; i < supportingPieces[1]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (nextCenterPos[0]) * 35 + (i + 1) * 35, (nextCenterPos[1]) * 35 - (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(120 + (nextCenterPos[0]) * 35 + (i + 1) * 35 + 3,
                    (nextCenterPos[1]) * 35 - (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        }

        // 2 slot
        for (int i = 0; i < supportingPieces[2]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (nextCenterPos[0]) * 35 + (i + 1) * 35, (nextCenterPos[1]) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((nextCenterPos[0]) * 35 + (i + 1) * 35 + 123, (nextCenterPos[1]) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 3 slot
        for (int i = 0; i < supportingPieces[3]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (nextCenterPos[0]) * 35 + (i + 1) * 35, (nextCenterPos[1]) * 35 + (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((nextCenterPos[0]) * 35 + (i + 1) * 35 + 123, (nextCenterPos[1]) * 35 + (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        }

        // 4 slot
        for (int i = 0; i < supportingPieces[4]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (nextCenterPos[0]) * 35, (nextCenterPos[1]) * 35 + (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((nextCenterPos[0]) * 35 + 123, (nextCenterPos[1]) * 35 + (i + 1) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 5 slot
        for (int i = 0; i < supportingPieces[5]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (nextCenterPos[0]) * 35 - (i + 1) * 35, (nextCenterPos[1]) * 35 + (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((nextCenterPos[0]) * 35 - (i + 1) * 35 + 123, (nextCenterPos[1]) * 35 + (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 6 slot
        for (int i = 0; i < supportingPieces[6]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (nextCenterPos[0]) * 35 - (i + 1) * 35, (nextCenterPos[1]) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((nextCenterPos[0]) * 35 - (i + 1) * 35 + 123, (nextCenterPos[1]) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 7 slot
        for (int i = 0; i < supportingPieces[7]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (nextCenterPos[0]) * 35 - (i + 1) * 35, (nextCenterPos[1]) * 35 - (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((nextCenterPos[0]) * 35 - (i + 1) * 35 + 123, (nextCenterPos[1]) * 35 - (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

    }

    // Returns the hold center position
    public int[] getHoldCenterPos() {
        return holdCenterPos;
    }

    // draws the holding location of the block on the screen
    public void drawHoldPosition(Graphics g) {

        // Center Piece
        g.setColor(Color.black);
        g.fillRect(120 + (holdCenterPos[0]) * 35, (holdCenterPos[1]) * 35, BLOCKLENGTH, BLOCKLENGTH);
        g.setColor(COLOURS[type]);
        g.fillRect((holdCenterPos[0]) * 35 + 123, (holdCenterPos[1]) * 35 + 3, BLOCKLENGTH - 6, BLOCKLENGTH - 6);

        // Supporting Pieces
        // 0 slot
        for (int i = 0; i < supportingPieces[0]; i++) {
            g.setColor(Color.black);
            g.fillRect(120 + (holdCenterPos[0]) * 35, (holdCenterPos[1]) * 35 - (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((holdCenterPos[0]) * 35 + 123, (holdCenterPos[1]) * 35 - (i + 1) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 1 slot
        for (int i = 0; i < supportingPieces[1]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (holdCenterPos[0]) * 35 + (i + 1) * 35, (holdCenterPos[1]) * 35 - (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect(120 + (holdCenterPos[0]) * 35 + (i + 1) * 35 + 3,
                    (holdCenterPos[1]) * 35 - (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        }

        // 2 slot
        for (int i = 0; i < supportingPieces[2]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (holdCenterPos[0]) * 35 + (i + 1) * 35, (holdCenterPos[1]) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((holdCenterPos[0]) * 35 + (i + 1) * 35 + 123, (holdCenterPos[1]) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 3 slot
        for (int i = 0; i < supportingPieces[3]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (holdCenterPos[0]) * 35 + (i + 1) * 35, (holdCenterPos[1]) * 35 + (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((holdCenterPos[0]) * 35 + (i + 1) * 35 + 123, (holdCenterPos[1]) * 35 + (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        }

        // 4 slot
        for (int i = 0; i < supportingPieces[4]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (holdCenterPos[0]) * 35, (holdCenterPos[1]) * 35 + (i + 1) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((holdCenterPos[0]) * 35 + 123, (holdCenterPos[1]) * 35 + (i + 1) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 5 slot
        for (int i = 0; i < supportingPieces[5]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (holdCenterPos[0]) * 35 - (i + 1) * 35, (holdCenterPos[1]) * 35 + (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((holdCenterPos[0]) * 35 - (i + 1) * 35 + 123, (holdCenterPos[1]) * 35 + (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 6 slot
        for (int i = 0; i < supportingPieces[6]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (holdCenterPos[0]) * 35 - (i + 1) * 35, (holdCenterPos[1]) * 35, BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((holdCenterPos[0]) * 35 - (i + 1) * 35 + 123, (holdCenterPos[1]) * 35 + 3, BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

        // 7 slot
        for (int i = 0; i < supportingPieces[7]; i++) {

            g.setColor(Color.black);
            g.fillRect(120 + (holdCenterPos[0]) * 35 - (i + 1) * 35, (holdCenterPos[1]) * 35 - (i + 1) * 35,
                    BLOCKLENGTH,
                    BLOCKLENGTH);
            g.setColor(COLOURS[type]);
            g.fillRect((holdCenterPos[0]) * 35 - (i + 1) * 35 + 123, (holdCenterPos[1]) * 35 - (i + 1) * 35 + 3,
                    BLOCKLENGTH - 6,
                    BLOCKLENGTH - 6);
        }

    }
}