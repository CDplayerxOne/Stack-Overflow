/*
 * Description: child of JPanel and creates the window for displaying the game
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 4th 2024
 */

//import libraries
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.imageio.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {

  // declare objects, variables, and constants
  public static final int GAME_WIDTH = 1024;
  public static final int GAME_HEIGHT = 768;

  public Thread gameThread;
  public Image image;
  public Image background;
  public Image infoScreenBackground;
  public Image menuScreenBackground;
  public Graphics graphics;
  public Block block;
  private boolean infoScreen = true;
  private boolean menuScreen = false;

  // gameRunning and finished game states not implemented yet
  private boolean gameRunning = false;
  private boolean end = false;

  public GamePanel() {
    // ball = new PlayerBall(GAME_WIDTH / 2, GAME_HEIGHT / 2);
    this.setFocusable(true);
    this.addKeyListener(this);

    block = new Block(296, 104, 4);
    // addMouseListener(new MouseAdapter() {
    // public void mousePressed(MouseEvent e) {
    // ball.mousePressed(e);
    // }
    // });
    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

    gameThread = new Thread(this);
    gameThread.start();

    try {
      background = ImageIO.read(new File("Images/Background.png"));
      infoScreenBackground = ImageIO.read(new File("Images/infoScreen.png"));
      menuScreenBackground = ImageIO.read(new File("Images/menuScreen.png"));
    } catch (Exception x) {
    }

    PlayMusic.playMenuMusic();

  }

  // paints aspects depending on game state
  public void paint(Graphics g) {
    image = createImage(GAME_WIDTH, GAME_HEIGHT);

    // update the positions and moves images onto the screen
    graphics = image.getGraphics();
    draw(graphics);
    g.drawImage(image, 0, 0, this);
  }

  // draws elements depending on game state
  public void draw(Graphics g) {
    if (infoScreen) {
      g.drawImage(infoScreenBackground.getScaledInstance(1025, 768, Image.SCALE_DEFAULT), 0, 0, null);
    }

    if (menuScreen) {
      g.drawImage(menuScreenBackground.getScaledInstance(1025, 768, Image.SCALE_DEFAULT), 0, 0, null);
    }

    if (gameRunning) {
      g.drawImage(background, 0, 0, null);

      // grid lines
      for (int i = 0; i < 11; i++) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(120 + i * 35, GAME_HEIGHT - 735, 120 + i * 35, GAME_HEIGHT);

      }
      for (int i = 0; i < 21; i++) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(120, GAME_HEIGHT - 735 + i * 35, 505, GAME_HEIGHT - 735 + i * 35);

      }
      block.draw(g);
    }
  }

  // not implemented yet
  public void move() {
  }

  // makes the game continue running without end
  public void run() {
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000 / amountOfTicks;
    double delta = 0;
    long now;

    while (true) {
      now = System.nanoTime();
      delta = delta + (now - lastTime) / ns;
      lastTime = now;

      if (delta >= 1) {
        block.autoFall();
        move();
        repaint();
        delta--;
      }
    }
  }

  // calls other methods and objects for when the user presses their keyboard key
  public void keyPressed(KeyEvent e) {
    changeGameState(e);
    block.keyPressed(e);
  }

  // calls other methods for when the user releases their keyboard key
  public void keyReleased(KeyEvent e) {
    block.keyReleased(e);
  }

  public void keyTyped(KeyEvent e) {
  }

  // method to change the state of the game
  public void changeGameState(KeyEvent e) {

    // trasition from start info screen to menu screen
    if (infoScreen) {
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        infoScreen = false;
        menuScreen = true;
      }
    }

    // transition from memnu to start game
    if (menuScreen) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        PlayMusic.stopMenuMusic();
        menuScreen = false;
        gameRunning = true;
        PlayMusic.playGameMusic();
      }
    }

  }

}