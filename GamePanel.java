import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.imageio.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {

  public static final int GAME_WIDTH = 1024;
  public static final int GAME_HEIGHT = 768;

  public Thread gameThread;
  public Image image;
  public Image background;
  public Graphics graphics;
  private boolean menuScreen = false;
  private boolean gameRunning = false;
  private boolean finished = false;
  private boolean infoScreen = true;
  // public PlayerBall ball;

  public GamePanel() {
    // ball = new PlayerBall(GAME_WIDTH / 2, GAME_HEIGHT / 2);
    this.setFocusable(true);
    this.addKeyListener(this);

    // addMouseListener(new MouseAdapter() {
    // public void mousePressed(MouseEvent e) {
    // ball.mousePressed(e);
    // }
    // });
    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

    gameThread = new Thread(this);
    gameThread.start();
  }

  public void paint(Graphics g) {
    image = createImage(GAME_WIDTH, GAME_HEIGHT);
    graphics = image.getGraphics();
    draw(graphics);
    g.drawImage(image, 0, 0, this);

  }

  public void draw(Graphics g) {
    if (menuScreen) {
      g.drawImage(background, 0, 0, null);
      // ball.draw(g);
    }

  }

  public void move() {
    // ball.move();
  }

  public void checkCollision() {
  }

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
        move();
        checkCollision();
        repaint();
        delta--;
      }
    }
  }

  public void keyPressed(KeyEvent e) {
    gameStarter(e);
    // ball.keyPressed(e);
  }

  public void keyReleased(KeyEvent e) {
    // ball.keyReleased(e);
  }

  public void keyTyped(KeyEvent e) {

  }

  public void gameStarter(KeyEvent e) {
    if (infoScreen) {
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        infoScreen = false;
        menuScreen = true;
        PlayMusic.playMenuMusic();

        try {
          background = ImageIO.read(new File("Images/Background.png"));
        } catch (Exception x) {
        }
      }
    }
  }
}