import java.awt.*;
import java.awt.event.*;

public class PlayerBall extends Rectangle{

  public int yVelocity;
  public int xVelocity;
  public final int SPEED = 5; //movement speed of ball
  public static final int BALL_DIAMETER = 20; //size of ball

  public PlayerBall(int x, int y){
    super(x, y, BALL_DIAMETER, BALL_DIAMETER);
  }

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

  public void mousePressed(MouseEvent e){
    x = e.getX();
		y = e.getY();
  }

  public void setYDirection(int yDirection){
    yVelocity = yDirection;
  }

  public void setXDirection(int xDirection){
    xVelocity = xDirection;
  }

  public void move(){
    y = y + yVelocity;
    x = x + xVelocity;
  }

  public void draw(Graphics g){
    g.setColor(Color.black);
    g.fillOval(x, y, BALL_DIAMETER, BALL_DIAMETER);
  }
  
}