/*
 * Description: used to save the high scores
 * Author: Corey Dai and Jeffrey Zhu
 * Date: June 4th 2024
 */

import java.io.*;
import java.util.*;

public class Files {

	// Takes a high score as an argument and writes it to the file
	public static void writeFile(int hs) {
		// Writes in the high score and outputs an error if there is an error.
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("highScores.txt", true))) {
			writer.write(Integer.toString(hs));
			writer.newLine();
		} catch (Exception e) {
		}
	}

	// Returns the score in a certain place after the list of scores is sorted by
	// highest to lowest
	public static int findScore(int place) {
		ArrayList<Integer> scores = new ArrayList<>();
		String line;

		try {
			BufferedReader reader = new BufferedReader(new FileReader("highScores.txt"));
			while ((line = reader.readLine()) != null) {
				scores.add(Integer.valueOf(line));
			}

		} catch (Exception e) {
		}

		Collections.sort(scores);

		return scores.get(scores.size() - place);
	}
}
