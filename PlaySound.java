/*
 * Description: used to play music
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 4th 2024
 */

//import libraries
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class PlaySound {
    // declare objects
    private static Clip menuMusic;
    private static Clip gameMusic;
    private static Clip endMusic;
    private static Clip blockPlace;
    private static Clip buttonClick;
    private static Clip holdBlock;
    private static Clip rowClear;

    // plays menu music
    static void playMenuMusic() {
        try {
            // open menu music file
            File file = new File("Music/MenuMusic.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            menuMusic = AudioSystem.getClip();

            // Open and play the menu music
            menuMusic.open(audio);
            menuMusic.addLineListener(event -> {
                // Restart the music if it stops
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    menuMusic.setMicrosecondPosition(0);
                    menuMusic.start();
                }
            });
            menuMusic.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // stops menu music
    static void stopMenuMusic() {
        if (menuMusic != null && menuMusic.isOpen()) {
            // Stop the menu music and reset its position to the beginning
            menuMusic.stop();
            menuMusic.setMicrosecondPosition(0);
            menuMusic.close();
        }
    }

    // plays game music
    static void playGameMusic() {
        try {
            // open game music file
            File file = new File("Music/GameMusic.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            gameMusic = AudioSystem.getClip();

            // Open and play the game music
            gameMusic.open(audio);
            gameMusic.addLineListener(event -> {
                // Restart the music if it stops
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    gameMusic.setMicrosecondPosition(0);
                    gameMusic.start();
                }
            });
            gameMusic.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // stops the game music
    static void stopGameMusic() {
        if (gameMusic != null && gameMusic.isOpen()) {
            // Stop the game music and reset its position to the beginning
            gameMusic.stop();
            gameMusic.setMicrosecondPosition(0);
            gameMusic.close();
        }
    }

    // plays the end music
    static void playEndMusic() {
        try {
            // open end music file
            File file = new File("Music/EndMusic.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            endMusic = AudioSystem.getClip();

            // open and play the end music
            endMusic.open(audio);
            endMusic.addLineListener(event -> {
                // Restart the music if it stops
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    endMusic.setMicrosecondPosition(0);
                    endMusic.start();
                }
            });
            endMusic.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // stops the end music
    static void stopEndMusic() {
        if (endMusic != null && endMusic.isOpen()) {
            // Stop the game music and reset its position to the beginning
            endMusic.stop();
            endMusic.setMicrosecondPosition(0);
            endMusic.close();
        }
    }

    // plays the block place sound effect
    static void playBlockPlace() {
        try {
            // open blockPlace file
            File file = new File("SoundEffects/blockPlace.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            blockPlace = AudioSystem.getClip();

            // open and play block place sound effect
            blockPlace.open(audio);
            blockPlace.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // plays the buttonClick sound effect
    static void playButtonClick() {
        try {
            // open buttonClick file
            File file = new File("SoundEffects/buttonClick.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            buttonClick = AudioSystem.getClip();

            // open and play buttonClick sound effect
            buttonClick.open(audio);
            buttonClick.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // plays the holdBlock sound effect
    static void PlayholdBlock() {
        try {
            // open holdBlock file
            File file = new File("SoundEffects/holdBlock.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            holdBlock = AudioSystem.getClip();

            // open and play holdBlock sound effect
            holdBlock.open(audio);
            holdBlock.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // plays the holdBlock sound effect
    static void PlayRowClear() {
        try {
            // open rowClear file
            File file = new File("SoundEffects/rowClear.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            rowClear = AudioSystem.getClip();

            // open and play rowClear sound effect
            rowClear.open(audio);
            rowClear.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
