/* PlayerBall class defines behaviours for the player-controlled ball  

child of Rectangle because that makes it easy to draw and check for collision

In 2D GUI, basically everything is a rectangle even if it doesn't look like it!
*/ 
import java.awt.*;
import java.awt.event.*;

public class PlayerBall extends Rectangle{

  public int yVelocity;
  public int xVelocity;
  public final int SPEED = 5; //movement speed of ball
  public static final int BALL_DIAMETER = 20; //size of ball

  //constructor creates ball at given location with given dimensions
  public PlayerBall(int x, int y){
    super(x, y, BALL_DIAMETER, BALL_DIAMETER);
  }

  //called from GamePanel when any keyboard input is detected
  //updates the direction of the ball based on user input
  //if the keyboard input isn't any of the options (d, a, w, s), then nothing happens
  public void keyPressed(KeyEvent e){
    if(e.getKeyChar() == 'd'){
      setXDirection(SPEED);
      move();
    }

    if(e.getKeyChar() == 'a'){
      setXDirection(SPEED*-1);
      move();
    }

    if(e.getKeyChar() == 'w'){
      setYDirection(SPEED*-1);
      move();
    }

    if(e.getKeyChar() == 's'){
      setYDirection(SPEED);
      move();
    }
  }

  //called from GamePanel when any key is released (no longer being pressed down)
  //Makes the ball stop moving in that direction
  public void keyReleased(KeyEvent e){
    if(e.getKeyChar() == 'd'){
      setXDirection(0);
      move();
    }

    if(e.getKeyChar() == 'a'){
      setXDirection(0);
      move();
    }

    if(e.getKeyChar() == 'w'){
      setYDirection(0);
      move();
    }

    if(e.getKeyChar() == 's'){
      setYDirection(0);
      move();
    }
  }

  //called from GamePanel whenever a mouse click is detected
  //changes the current location of the ball to be wherever the mouse is located on the screen
  public void mousePressed(MouseEvent e){
    x = e.getX();
		y = e.getY();
  }

  //called whenever the movement of the ball changes in the y-direction (up/down)
  public void setYDirection(int yDirection){
    yVelocity = yDirection;
  }

  //called whenever the movement of the ball changes in the x-direction (left/right)
  public void setXDirection(int xDirection){
    xVelocity = xDirection;
  }

  //called frequently from both PlayerBall class and GamePanel class
  //updates the current location of the ball
  public void move(){
    y = y + yVelocity;
    x = x + xVelocity;
  }

  //called frequently from the GamePanel class
  //draws the current location of the ball to the screen
  public void draw(Graphics g){
    g.setColor(Color.black);
    g.fillOval(x, y, BALL_DIAMETER, BALL_DIAMETER);
  }
  
}