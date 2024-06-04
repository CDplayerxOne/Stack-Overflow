/*
 * Description: child of JPanel and creates the window for displaying the game
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 4th 2024
 */

//import libraries
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

  //declare object
  GamePanel panel;

  public GameFrame() {
    //run GamePanel Constructor
    panel = new GamePanel();

    //creates the window that displays the game
    this.add(panel);
    this.setTitle("Stack Overflow"); // set title for frame
    this.setResizable(false); // frame can't change size
    this.setBackground(Color.white);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X button will stop program execution
    this.pack();// makes components fit in window - don't need to set JFrame size, as it will
                // adjust accordingly
    this.setVisible(true); // makes window visible to user
    this.setLocationRelativeTo(null);// set window in middle of screen
  }

}