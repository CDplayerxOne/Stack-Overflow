/*
 * Description: Used to save the high scores in a text file
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 17th 2024
 */

//import libraries
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Files {

	// Takes a high score as an argument and writes it to the file
	public static void writeFile(int hs) {
		// Writes in the high score and outputs an error if there is an error.
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("highScores.txt", true))) {
			writer.write(Integer.toString(hs));
			writer.newLine();
		} catch (Exception e) {
			System.out.println("An error occurred");
		}
	}

	// Returns the score in a certain place after the list of scores is sorted from
	// highest to lowest
	public static int findScore(int place) {
		ArrayList<Integer> scores = new ArrayList<>();
		String line;

		// Reads the file and puts all the scores in an array
		try {
			BufferedReader reader = new BufferedReader(new FileReader("highScores.txt"));
			while ((line = reader.readLine()) != null) {
				scores.add(Integer.valueOf(line));
			}
			reader.close();

		} catch (Exception e) {
			System.out.println("An error occurred");
		}

		// Sorts and retuns the score corresponding to the place requested
		Collections.sort(scores);
		return scores.get(scores.size() - place);
	}

	// Returns the last entry
	public static int getLatest() {
		ArrayList<Integer> scores = new ArrayList<>();
		String line;

		try {
			BufferedReader reader = new BufferedReader(new FileReader("highScores.txt"));
			while ((line = reader.readLine()) != null) {
				scores.add(Integer.valueOf(line));
			}
			reader.close();

		} catch (Exception e) {
			System.out.println("An error occurred");
		}

		return scores.get(scores.size() - 1);
	}
}
