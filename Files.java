// Author: Corey Dai
// Project/Assignment: GameShow
// Date: June 5th, 2024
// Description: methods for creating, reading and writing to files 

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Files {

	// Creating files
	public static void createFile() {
		// creates a file call highScore.txt and if there is an error, outputs the
		// error.
		try {
			File highScore = new File("highScores.txt");
			highScore.createNewFile();
		} catch (Exception e) {
			System.out.println("WARNING: HIGH SCORES CANNOT BE SAVED");
		}
	}

	// Takes a high score as an argument and writes it to the file
	public static void writeFile(int hs) {
		// Writes in the high score and outputs an error if there is an error.
		try {
			FileWriter writer = new FileWriter("highScores.txt");
			writer.write(hs + "");
			writer.close();
			System.out.println("New High Score: " + hs);
		} catch (Exception e) {
			System.out.println("Could not write high score");
		}
	}

	// Reads the current high score from the file
	public static int readFile() {

		// Reads the current high score and returns it.
		try {
			File highScore = new File("highScores.txt");
			Scanner reader = new Scanner(highScore);
			int score = 0;
			score = Integer.parseInt(reader.next());
			reader.close();
			return score;
		} catch (Exception e) {
			return -1;
		}

	}
}
