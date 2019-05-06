import java.io.*;

/*
 * Writes data to a text file
 */
public class WriteToFile {

	public WriteToFile(String fileName, String content) {

		try {
			FileWriter file = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(file);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			System.out.printf("Error -- %s\n", e.toString());
		}
	}
}