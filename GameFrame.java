/*
 * Description: child of JPanel and creates the window for displaying the game
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 16th 2024
 */

//import libraries
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

  // declare object
  GamePanel panel;

  public GameFrame() {
    // run GamePanel Constructor
    panel = new GamePanel();

    // creates the window that displays the game
    this.add(panel);
    this.setTitle("Stack Overflow");
    this.setResizable(false);
    this.setBackground(Color.white);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
  }
}