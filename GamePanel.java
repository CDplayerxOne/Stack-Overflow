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

    try {
      background = ImageIO.read(new File("Images/background.png"));
    } catch (Exception e) {
    }
  }

  public void paint(Graphics g) {
    image = createImage(GAME_WIDTH, GAME_HEIGHT);
    graphics = image.getGraphics();
    draw(graphics);
    g.drawImage(image, 0, 0, this);

  }

  public void draw(Graphics g) {
    g.drawImage(background, 0, 0, null);
    g.setColor(Color.RED);
    g.fillRect(90, 20, 400, 750);
    // ball.draw(g);
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
    // ball.keyPressed(e);
  }

  public void keyReleased(KeyEvent e) {
    // ball.keyReleased(e);
  }

  public void keyTyped(KeyEvent e) {

  }
}