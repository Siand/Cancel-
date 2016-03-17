
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public abstract class CsvFileReader {

	/**
	 * @param fileLocation
	 *            Location of the file to be read
	 * @param list
	 *            Where each row of the CSV file will be stored
	 * @throws FileNotFoundException
	 *             If incorrect location of the file is passed in.
	 */
	public void readFile(String fileLocation, List<String[]> list) throws FileNotFoundException {
		BufferedReader br;
		String line;
		String splitBy = ",";
		String[] separatedLine;

		try {
			br = new BufferedReader(new FileReader(fileLocation));

			// while there are more lines in the file
			while ((line = br.readLine()) != null) {
				// split up the line and store it in array
				separatedLine = line.split(splitBy);

				list.add(separatedLine);
			}
			// Close the reader
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
