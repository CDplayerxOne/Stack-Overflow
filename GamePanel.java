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
  public Image infoScreenBackground;
  public Graphics graphics;
  public Block block;
  private boolean menuScreen = false;
  private boolean gameRunning = false;
  private boolean finished = false;
  private boolean infoScreen = true;
  // public PlayerBall ball;

  public GamePanel() {
    // ball = new PlayerBall(GAME_WIDTH / 2, GAME_HEIGHT / 2);
    this.setFocusable(true);
    this.addKeyListener(this);

    block = new Block(100, 200, 3);
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
    } catch (Exception x) {
    }

  }

  public void paint(Graphics g) {
    image = createImage(GAME_WIDTH, GAME_HEIGHT);
    graphics = image.getGraphics();
    draw(graphics);
    g.drawImage(image, 0, 0, this);

  }

  public void draw(Graphics g) {
    if (infoScreen) {
      g.drawImage(infoScreenBackground.getScaledInstance(1024, 768, Image.SCALE_DEFAULT), 0, 0, null);
      // ball.draw(g);
    }

    if (menuScreen) {
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
      // ball.draw(g);
      block.draw(g);
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
    block.keyPressed(e);
    // ball.keyPressed(e);
  }

  public void keyReleased(KeyEvent e) {
    // ball.keyReleased(e);
    block.keyReleased(e);
  }

  public void keyTyped(KeyEvent e) {

  }

  public void gameStarter(KeyEvent e) {
    if (infoScreen) {
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        infoScreen = false;
        menuScreen = true;
        PlayMusic.playMenuMusic();
      }
    }
  }

}