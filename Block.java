import java.awt.*;
import java.awt.event.*;

public class Block extends Rectangle {

  private int type;
  private static int status;
  private boolean xMoveLeft = false;
  private boolean xMoveRight = false;
  private int[][] gridPosition = new int[5][2];
  public final int yVelocity = 5;
  public final int xMovement = 5;
  public static final int BLOCKLENGTH = 20; // length of block

  public int[][] getGridPostion() {
    return gridPosition;
  }

  public Block(int x, int y, int type) {
    super(x, y, BLOCKLENGTH, BLOCKLENGTH);
    this.type = type;
  }

  public void keyPressed(KeyEvent e) {
    // CW block roatation
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      // implement in rotate
      rotate();
      move();
    }

    // accelerate downwards
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      move();
    }

    // move the block the left
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      move();
    }

    // move the block to the right
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      move();
    }

    // hold the block and change status
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {

    }
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyChar() == 'd') {
      move();
    }

    if (e.getKeyChar() == 'a') {
      move();
    }

    if (e.getKeyChar() == 'w') {
      move();
    }

    if (e.getKeyChar() == 's') {
      move();
    }
  }

  public void move() {
    y = y + yVelocity;
  }

  public void rotate() {

  }

  public int getType() {
    return status;
  }

  public int getVelocity() {
    return yVelocity;
  }

  public void setStatus(int s) {
    status = s;
  }

  public void draw(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(x, y, BLOCKLENGTH, BLOCKLENGTH);

  }

}