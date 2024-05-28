//import libraries
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class PlayMusic {
    //declare objects
    private static Clip menuMusic;
    private static Clip gameMusic;
    private static Clip endMusic;

    //plays menu music
    static void playMenuMusic(){
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

    //stops menu music
    static void stopMenuMusic(){
        if (menuMusic != null && menuMusic.isOpen()) {
            // Stop the menu music and reset its position to the beginning
            menuMusic.stop();
            menuMusic.setMicrosecondPosition(0);
            menuMusic.close();
        }
    }

    //plays game music
    static void playGameMusic(){
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

    //stops the game music
    static void stopGameMusic(){
        if (gameMusic != null && gameMusic.isOpen()) {
            // Stop the game music and reset its position to the beginning
            gameMusic.stop();
            gameMusic.setMicrosecondPosition(0);
            gameMusic.close();
        }
    }

    //plays the finish music
    static void playFinishMusic(){
        try {
            // open finish music file
            File file = new File("Music/EndMusic.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            endMusic = AudioSystem.getClip();

            // open and play the finish music
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
}
