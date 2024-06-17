/*
 * Description: child of JPanel and creates the window for displaying the game
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 17th 2024
 */

//import libraries
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    // declare objects, variables, and constants
    public static final int GAME_WIDTH = 1024;
    public static final int GAME_HEIGHT = 770;
    public Thread gameThread;
    public Image image;
    public Image helpScreenBackground;
    public Image background;
    public Image infoScreenBackground;
    public Image menuScreenBackground;
    public Image endScreen;
    public Graphics graphics;
    public GameManager manager;
    public static boolean infoScreen = true;
    public static boolean menuScreen = false;
    public static boolean gameRunning = false;
    public boolean helpScreen = false;
    public static boolean end = false;
    private static boolean ranEnd = false;
    Font scoreFont;

    // Game Panel constructor
    public GamePanel() {

        // declare objects and variables

        // creates a thread for GamePanel
        gameThread = new Thread(this);

        manager = new GameManager();

        // image declarations
        try {
            background = ImageIO.read(new File("Images/Background.png"));
            infoScreenBackground = ImageIO.read(new File("Images/infoScreen.png"));
            menuScreenBackground = ImageIO.read(new File("Images/menuScreen.png"));
            endScreen = ImageIO.read(new File("Images/EndScreen.png"));
            helpScreenBackground = ImageIO.read(new File("Images/helpScreen.png"));
        } catch (Exception x) {
            x.printStackTrace();
        }

        // starts GamePanel thread
        gameThread.start();

        // listens for key input
        this.setFocusable(true);
        this.addKeyListener(this);

        // sets game window dimentions
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        // activates the starting blocks
        if (!end) {
            GameManager.activateBlock(GameManager.nextblock);
            GameManager.generateBlock();
        }

        // plays menu music
        PlaySound.playMenuMusic();
    }

    // paints aspects depending on game state
    public void paint(Graphics g) {
        image = createImage(GAME_WIDTH, GAME_HEIGHT);

        // update the positions and moves images onto the screen
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);

        // prints the three highest scores
        if (menuScreen) {
            scoreFont = new Font("Arial", Font.BOLD, 45);
            g.setFont(scoreFont);
            g.setColor(Color.white);
            g.drawString("1. " + Files.findScore(1), 390, 370);
            g.drawString("2. " + Files.findScore(2), 390, 430);
            g.drawString("3. " + Files.findScore(3), 390, 490);
        }

        // prints the current and all time high score
        if (gameRunning) {
            scoreFont = new Font("Arial", Font.BOLD, 40);
            g.setFont(scoreFont);
            g.setColor(Color.white);
            g.drawString("" + GameManager.score, 700, 448);
            g.drawString("" + Files.findScore(1), 700, 520);
        }

        // prints the final score
        if (end) {
            scoreFont = new Font("Arial", Font.BOLD, 55);
            g.drawImage(endScreen, 175, 175, null);
            g.setFont(scoreFont);
            g.setColor(Color.white);
            g.drawString("" + Files.getLatest(), 460, 485);
        }
    }

    // draws elements depending on game state
    public void draw(Graphics g) {

        // draws the info screen
        if (infoScreen) {
            g.drawImage(infoScreenBackground.getScaledInstance(1025, 770, Image.SCALE_DEFAULT), 0, 0, null);
        }

        // draws the menu screen
        if (menuScreen) {
            g.drawImage(menuScreenBackground.getScaledInstance(1025, 770, Image.SCALE_DEFAULT), 0, 0, null);
        }

        // draws the help screen
        if (helpScreen) {
            g.drawImage(helpScreenBackground.getScaledInstance(1025, 770, Image.SCALE_DEFAULT), 0, 0, null);
        }

        // draws the game screen if the game is running or if the game is ended
        if (gameRunning || end) {
            g.drawImage(background, 0, 0, null);

            // grid lines
            for (int i = 0; i < 11; i++) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(140 + i * 35, GAME_HEIGHT - 560, 140 + i * 35, GAME_HEIGHT);

            }
            for (int i = 0; i < 21; i++) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(140, GAME_HEIGHT - 560 + i * 35, 525, GAME_HEIGHT - 560 + i * 35);

            }
            manager.draw(g);
        }
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
                // runs the autofall method for the cureently active block
                if (gameRunning) {
                    GameManager.blocks.get(GameManager.blocks.size() - 1).autoFall();
                }

                // checks when a new block is generated
                if (GameManager.generateNextBlock) {
                    GameManager.rowCollapse();

                    // checks if the game should end
                    if (GameManager.checkEnd()) {
                        setEnd();

                        // activates and generates the next blocks
                    } else {
                        GameManager.activateBlock(GameManager.nextblock);
                        GameManager.generateBlock();
                        GameManager.generateNextBlock = false;
                    }
                }
                repaint();
                delta--;
            }
        }
    }

    // override method for keyTyped
    public void keyTyped(KeyEvent e) {
    }

    // calls other methods and objects for when the user presses their keyboard key
    public void keyPressed(KeyEvent e) {
        changeGameState(e);
        if (gameRunning) {
            GameManager.blocks.get(GameManager.blocks.size() - 1).keyPressed(e);
        }
    }

    // calls other methods for when the user releases their keyboard key
    public void keyReleased(KeyEvent e) {
        if (gameRunning) {
            GameManager.blocks.get(GameManager.blocks.size() - 1).keyReleased(e);
        }
    }

    // Ends the game
    public static void setEnd() {
        if (!ranEnd) {
            ranEnd = true;
            gameRunning = false;
            end = true;
            Files.writeFile(GameManager.score);
            GameManager.score = 0;
            PlaySound.stopGameMusic();
            PlaySound.playEndMusic();
        }
    }

    // method to change the state of the game
    public void changeGameState(KeyEvent e) {
        boolean checkedHelp = false;

        // trasition from start info screen to menu screen
        if (infoScreen) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                PlaySound.playButtonClick();
                infoScreen = false;
                menuScreen = true;
            }
        }

        // transition from the help screen to the menu screen
        if (helpScreen) {
            if (e.getKeyChar() == 'h') {
                menuScreen = true;
                helpScreen = false;
                checkedHelp = true;
            }
        }

        // transition from menu to start game
        if (menuScreen && !checkedHelp) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                PlaySound.stopMenuMusic();
                PlaySound.playGameMusic();
                PlaySound.playButtonClick();
                menuScreen = false;
                gameRunning = true;
            }

            if (e.getKeyChar() == 'h') {
                menuScreen = false;
                helpScreen = true;

            }
        }

        // transition from the end screen to the menu screen
        if (end) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                end = false;
                menuScreen = true;
                ranEnd = false;
                GameManager.reset();
                PlaySound.stopEndMusic();
                PlaySound.playMenuMusic();
                PlaySound.playButtonClick();
                GameManager.generateBlock();
                GameManager.activateBlock(GameManager.nextblock);
                GameManager.generateBlock();
            }
        }

    }
}