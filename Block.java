/*
 * Description: deals with the drawing and manipulation of the block object
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 16th 2024
 */

//import libraries
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.*;
import java.io.*;

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

    // constructor for Block
    public Block(int type) {
        this.type = type;

        // 25% chance the block has a score multiplier
        if (Math.random() < 0.15) {
            scoreMultiplier = true;
            try {
                scoreMultiplierImage = ImageIO.read(new File("Images/ScoreMultiplier.png"));
            } catch (IOException e) {
                System.out.println("uh oh");
            }
        } else {
            scoreMultiplier = false;
        }

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

    // method to respond to a key press
    public void keyPressed(KeyEvent e) {
        // CW block roatation
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            // implement in rotate
            rotate();
        }

        // accelerate downwards
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
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

    // sets the x-velocity of the center piece
    public void setCenterPieceX(int xVel) {
        centerPiece[0] += xVel;
    }

    // moves the block according to its x and y velocity
    public void move(int xVel, int yVel) {

        if (xVel > 0) {
            if (!checkHorizontalCollisionRight()) {

                centerPiece[0] += xVel;
                GameManager.updatePosition(1);
                System.out.println(Arrays.toString(centerPiece));
            }
        }
        if (xVel < 0) {
            if (!checkHorizontalCollisionLeft()) {
                centerPiece[0] += xVel;
                GameManager.updatePosition(2);
            }
        }
        if (!checkVerticalCollision()) {
            System.out.println("no coollision");
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
        System.out.println(type);
        System.out.println(supportingPieces[2]);
        System.out.println(supportingPieces[3]);

        // System.out.println("what is supporting pieces 4 " + supportingPieces[4]);

        boolean collision = false;

        // check if the center piece is above the bottom
        if ((centerPiece[1] + 1) >= 22) {
            collision = true;
            System.out.println("collision 1");

            // check if the other pieces are above the bottom
        }

        if (((centerPiece[1] + supportingPieces[3] + 1) >= 22)
                || ((centerPiece[1] + supportingPieces[4] + 1) >= 22)
                || ((centerPiece[1] + supportingPieces[5] + 1) >= 22)) {
            collision = true;
            System.out.println("collision 2");

            // check if there is a block below position 2
        } else {

            // check if there is a block under postion 1
            if (supportingPieces[1] > supportingPieces[2] && supportingPieces[1] > supportingPieces[3]) {
                if (GameManager.getGrid()[centerPiece[0] + 1][centerPiece[1] + 1] != ' ') {
                    collision = true;
                }
            }

            if (supportingPieces[2] > supportingPieces[3]) {
                for (int i = 1; i <= supportingPieces[2]; i++) {
                    if (GameManager.getGrid()[centerPiece[0] + i][centerPiece[1] + 1] != ' ') {
                        collision = true;
                        System.out.println("collision 3");
                    }
                }

                // check if there is a block below position 3
            }
            if (supportingPieces[3] != 0) {
                if (GameManager.getGrid()[centerPiece[0] + 1][centerPiece[1] + 2] != ' ') {
                    collision = true;
                    System.out.println("collision 4");

                }

                // check if there is a block below position 4
            }
            if (supportingPieces[4] != 0) {
                if (GameManager.getGrid()[centerPiece[0]][centerPiece[1] + 1 + supportingPieces[4]] != ' ') {
                    collision = true;
                    System.out.println("collision 5");
                }

                // check if there is a block below position 5
            }
            if (supportingPieces[5] != 0) {
                if (GameManager.getGrid()[centerPiece[0] - 1][centerPiece[1] + 2] != ' ') {
                    collision = true;
                    System.out.println("collision 6");
                }

                // check if there is a block below position 6
            }
            if (supportingPieces[6] > supportingPieces[5]) {
                for (int i = 1; i <= supportingPieces[6]; i++) {
                    if (GameManager.getGrid()[centerPiece[0] - i][centerPiece[1] + 1] != ' ') {
                        collision = true;
                        System.out.println("collision 7");
                    }
                }
            }
            if (supportingPieces[4] == 0) {
                if (GameManager.getGrid()[centerPiece[0]][centerPiece[1] + 1] != ' ') {
                    collision = true;
                    System.out.println("collsiion 8");
                }
            }
        }

        if (collision) {
            GameManager.generateNextBlock = true;
            return true;
        } else {
            System.out.println("Validation for collision 5: "
                    + GameManager.getGrid()[centerPiece[0]][centerPiece[1] + 1 + supportingPieces[4]]);
            System.out.println(supportingPieces[4]);
            return false;
        }
    }

    // checks if there is a right horizontal collision between blocks
    public boolean checkHorizontalCollisionRight() {
        if (checkMoveVailidityRight()) {

            // checks the if there are blocks on the right of position 1
            if (GameManager.getGrid()[getCenterPiece()[0] + supportingPieces[1] + 1][getCenterPiece()[1] - 1] != ' ') {
                for (char[] item : GameManager.getGrid()) {
                    System.out.println(Arrays.toString(item));
                }
                return true;

                // checks the if there are blocks on the right of position 2
            }

            if (GameManager.getGrid()[getCenterPiece()[0] + supportingPieces[2]
                    + 1][getCenterPiece()[1]] != ' ') {
                return true;

                // checks the if there are blocks on the right of position 3
            }
            if (GameManager.getGrid()[getCenterPiece()[0] + supportingPieces[3] + 1][getCenterPiece()[1]
                    + 1] != ' ') {
                return true;

                // checks the if there are blocks on the right of position 0
            }
            if (supportingPieces[0] > supportingPieces[1]) {

                for (int i = 0; i < supportingPieces[0]; i++) {
                    if (GameManager.getGrid()[getCenterPiece()[0] + 1][getCenterPiece()[1] - 1 - i] != ' ') {
                        return true;
                    }
                }

                // checks the if there are blocks on the right of position 4
            }
            if (supportingPieces[4] > supportingPieces[3]) {

                for (int i = 0; i < supportingPieces[4]; i++) {
                    if (GameManager.getGrid()[getCenterPiece()[0] + 1][getCenterPiece()[1] + 1 + i] != ' ') {
                        return true;
                    }
                }
            }

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

                // checks the if there are blocks on the left of position 6
            }

            if (GameManager.getGrid()[getCenterPiece()[0] - supportingPieces[6]
                    - 1][getCenterPiece()[1]] != ' ') {
                return true;

                // checks the if there are blocks on the left of position 1
            }
            if (getCenterPiece()[1] > 22) {
                if (GameManager.getGrid()[getCenterPiece()[0] - supportingPieces[5] - 1][getCenterPiece()[1]
                        + 1] != ' ') {
                    return true;
                }

                // checks the if there are blocks on the left of position 0
            }
            if (supportingPieces[0] > supportingPieces[7]) {
                for (int i = 0; i < supportingPieces[0]; i++) {
                    if (GameManager.getGrid()[getCenterPiece()[0] - 1][getCenterPiece()[1] - 1 - i] != ' ') {
                        return true;
                    }
                }

                // checks the if there are blocks on the left of position 4
            }
            if (supportingPieces[4] > supportingPieces[5]) {
                for (int i = 0; i < supportingPieces[4]; i++) {
                    if (GameManager.getGrid()[getCenterPiece()[0] - 1][getCenterPiece()[1] + 1 + i] != ' ') {
                        return true;
                    }
                }
            }
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
                move(0, 1);

                // GameManager.updatePosition(0);
                // System.out.println(checkVerticalCollision() + " collision");

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


    private boolean checkPixel(int discountR, int discountC) {
        char target = GameManager.getGrid()[centerPiece[0] + discountR][centerPiece[1] + discountC];
        if (target != ' ' && target != GameManager.COLOURS[type]) {
            return false;
        }
        return true;
    }

    // checks if the block is restricted by the boarder for rotating
    public boolean checkRotationVailidity() {

        // true means that it is ok to rotate

        boolean canRotate = true;
        char[][]  theGrid = GameManager.getGrid();

        if ((supportingPieces[7] + getCenterPiece()[0] < 11) && (supportingPieces[0] + getCenterPiece()[0] < 11)
                && (supportingPieces[1] + getCenterPiece()[0] < 11) && (getCenterPiece()[0] - supportingPieces[3] > -1)
                && (getCenterPiece()[0] - supportingPieces[4] > -1)
                && (getCenterPiece()[0] - supportingPieces[5] > -1)) {

            // check rotation for position 1
            if (supportingPieces[1] != 0) {
                if (supportingPieces[3] == 0){
                    if (theGrid[centerPiece[0]+1][centerPiece[1] + 1] != ' '){
                        canRotate = false;
                    }
                }
            }

            // check rotation for position 3
            if (supportingPieces[3] != 0) {
                if (supportingPieces[5] == 0){
                    if (theGrid[centerPiece[0] - 1][centerPiece[1] + 1] != ' ') {
                        canRotate = false;
                    }
                }
            }

            // check rotation for position 5
            if (supportingPieces[5] != 0) {
                if (supportingPieces[7] == 0){
                    if (theGrid[centerPiece[0] - 1][centerPiece[1] - 1] != ' ') {
                        canRotate = false;
                    }
                }
            }

            // check rotation for position 7
            if (supportingPieces[7] != 0) {
                if (supportingPieces[1] == 0){
                    if (theGrid[centerPiece[0] + 1][centerPiece[1] - 1] != ' ') {
                        canRotate = false;
                    }
                }
            }

            // check rotation for position 2
            if (supportingPieces[2] != 0) {
                if (supportingPieces[4] == 0){
                    for (int i = 1; i <= supportingPieces[2]; i++) {
                        if (theGrid[centerPiece[0]][centerPiece[1] + i] != ' ') {
                            canRotate = false;
                        }
                    }
                }
            }

            // check rotation for position 4
            if (supportingPieces[4] != 0) {
                if (supportingPieces[6] == 0){
                    for (int i = 1; i <= supportingPieces[4]; i++) {
                        if (theGrid[centerPiece[0] + i][centerPiece[1]] != ' ') {
                            canRotate = false;
                        }
                    }
                }
            }

            // check rotation for position 6
            if (supportingPieces[6] != 0) {
                if (supportingPieces[0] == 0){
                    for (int i = 1; i <= supportingPieces[6]; i++) {
                        if (theGrid[centerPiece[0]][centerPiece[1] - i] != ' ') {
                            canRotate = false;
                        }
                    }
                }
            }

            // check rotation for position 0
            if (supportingPieces[0] != 0) {
                if (supportingPieces[2] == 0){
                    for (int i = 1; i <= supportingPieces[0]; i++) {
                        if (theGrid[centerPiece[0] - i][centerPiece[1]] != ' ') {
                            canRotate = false;
                        }
                    }
                }
            }

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

    // draws the holding location of the block on the screen
    public void drawHoldingPos(Graphics g) {

        // Center Piece
        g.setColor(Color.black);
        g.fillRect(120 + (holdCenterPos[0]) * 35, (holdCenterPos[1]) * 35, BLOCKLENGTH, BLOCKLENGTH);
        g.setColor(COLOURS[type]);
        g.fillRect((holdCenterPos[0]) * 35 + 123, (holdCenterPos[1]) * 35 + 3, BLOCKLENGTH - 6, BLOCKLENGTH - 6);
        if (scoreMultiplier) {
            g.drawImage(scoreMultiplierImage, (holdCenterPos[0]) * 35 + 123, (holdCenterPos[1]) * 35 + 3,
                    null);
        }

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