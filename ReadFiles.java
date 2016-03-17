

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ovoido
 * 
 *         Read Files.
 *
 */
public class ReadFiles extends CsvFileReader {

	/**
	 * @param fileLocation
	 *            Location of the file to be read
	 * @param jobMap
	 *            Where the list of jobs will be stored
	 * @param itemMap
	 *            Where all the items are stored.
	 * @throws FileNotFoundException
	 *             If incorrect location of the file is passed in.
	 */
	public void readJobFile(String fileLocation, Map<Integer, JobList> jobMap, Map<String, ItemData> itemMap)
			throws FileNotFoundException {
		ArrayList<String[]> temp = new ArrayList<String[]>();
		readFile(fileLocation, temp);
		String[] tempLine;
		ArrayList<JobData> tempJob;
		JobList tempList;
		
		for (int j = 0; j < temp.size(); j++) {
			tempJob = new ArrayList<JobData>();
			tempLine = temp.get(j);
			
			//For every job in the list of jobs
			for (int i = 1; i < tempLine.length; i += 2) {
				tempJob.add(new JobData(itemMap.get(tempLine[i]), Integer.parseInt(tempLine[i + 1])));
			}
			tempList = new JobList(tempJob);
			jobMap.put(Integer.parseInt(tempLine[0]), tempList);
		}
	}

	/**
	 * @param fileLocation
	 *            Location of the file to be read
	 * @param jobMap
	 *            Where the information about the job is stored
	 * @throws FileNotFoundException
	 *             If incorrect location of the file is passed in.
	 */
	public void readCancellationFile(String fileLocation, Map<Integer, JobList> jobMap) throws FileNotFoundException {
		ArrayList<String[]> temp = new ArrayList<String[]>();
		readFile(fileLocation, temp);
		JobList jobList;
		String[] tempLine;

		for (int j = 0; j < temp.size(); j++) {
			tempLine = temp.get(j);
			jobList = jobMap.get(Integer.parseInt(tempLine[0]));

			if (jobList != null && Integer.parseInt(tempLine[1]) == 1) {
				jobList.setCancelled(true);
			}
		}
	}

	/**
	 * @param fileLocation
	 *            Location of the file to be read
	 * @param itemMap
	 *            Where the information about the item is stored
	 * @throws FileNotFoundException
	 *             If incorrect location of the file is passed in.
	 */
	public void readItemLocationFile(String fileLocation, Map<String, ItemData> itemMap) throws FileNotFoundException {
		ArrayList<String[]> temp = new ArrayList<String[]>();
		readFile(fileLocation, temp);

		ItemData tempItem;

		// Try to retrieve the item at the current line from the
		// ConcurrentMap
		// if it doesn't fail, change the existing record
		// else add a new record.
		for (int i = 0; i < temp.size(); i++) {
			tempItem = itemMap.get(temp.get(i)[2]);
			if (tempItem != null) {
				tempItem.setPoint(Integer.valueOf(temp.get(i)[0]), Integer.valueOf(temp.get(i)[1]));
			} else {
				itemMap.put(temp.get(i)[2],
						new ItemData(Integer.valueOf(temp.get(i)[0]), Integer.valueOf(temp.get(i)[1])));
			}
		}
	}

	/**
	 * @param fileLocation
	 *            Location of the file to be read
	 * @param map
	 *            Where the information about the item is stored
	 * @throws FileNotFoundException
	 *             If incorrect location of the file is passed in.
	 */
	public void readItemRWFile(String fileLocation, Map<String, ItemData> itemMap) throws FileNotFoundException {
		ArrayList<String[]> temp = new ArrayList<String[]>();
		readFile(fileLocation, temp);
		ItemData tempItem;

		for (int i = 0; i < temp.size(); i++) {
			tempItem = itemMap.get(temp.get(i)[0]);
			if (tempItem != null) {
				tempItem.setReward(Float.parseFloat(temp.get(i)[1]));
				tempItem.setWeight(Float.parseFloat(temp.get(i)[2]));
			} else {
				itemMap.put(temp.get(i)[0],
						new ItemData(Float.parseFloat(temp.get(i)[1]), Float.parseFloat(temp.get(i)[2])));
			}
		}
	}

	/**
	 * @param fileLocation
	 *            Location of the file to be read
	 * @param list
	 *            Where the information about the drop points is stored
	 * @throws FileNotFoundException
	 *             If incorrect location of the file is passed in.
	 */
	public void readDPFile(String fileLocation, List<Point> list) throws FileNotFoundException {
		ArrayList<String[]> temp = new ArrayList<String[]>();
		readFile(fileLocation, temp);

		for (int i = 0; i < temp.size() - 1; i++) {

			list.add(new Point(Integer.parseInt(temp.get(i)[0]), Integer.parseInt(temp.get(i)[1])));
		}
	}

	// Testing
	public static void main(String args[]) {

		ReadFiles ts = new ReadFiles();

		ConcurrentHashMap<Integer, JobList> jobMap = new ConcurrentHashMap<Integer, JobList>();
		ConcurrentHashMap<String, ItemData> itemMap = new ConcurrentHashMap<String, ItemData>();
		ArrayList<Point> dpList = new ArrayList<Point>();

		try {
			ts.readItemLocationFile("file/locations.csv", itemMap);
			ts.readItemRWFile("file/items.csv", itemMap);

			ts.readJobFile("file/jobs.csv", jobMap, itemMap);
			ts.readCancellationFile("file/cancellations.csv", jobMap);

			ts.readDPFile("file/drops.csv", dpList);

			/*
			 * for (Entry<Integer, JobList> l : jobMap.entrySet()) {
			 * 
			 * System.out.println(l.getKey() + " " +
			 * l.getValue().getCancelled());
			 * 
			 * }
			 */

			// --- Cancellation prediction ---
			// The following code is used for printing the values of the
			// features(rewards,weights,nrOfObj) in ascending order so as to
			// find out their range and to be able to set some intervals
			
//			 Sorting sorting = new Sorting(jobMap,10000,10500);
//			 System.out.println("---Rewards---");
//			 sorting.sortingRewards();
//			
//			 System.out.println("");
//			 System.out.println("---Weights---");
//			 sorting.sortingWeights();
//			
//			 System.out.println("");
//			 System.out.println("---Number---");
//			 sorting.sortingNrOfObj();

			// Testing
			
			//total number of errors
			float total = 0;
			
			//number of tested jobs
			int nr = 0;
			
			//error: our prediction is true instead of being false; used for debugging
			int trueInsteadOfFalse = 0;
			
			//error: our prediction is false instead of being true; used for debugging
			int falseInsteadOfTrue = 0;
			
			//80 training sets, 20 tests sets for each iteration
			//every iteration is from 50 to 50 jobs

			for (int i = 10000; i < 29000; i += 50) {
				int error = 0;		
				
				//work on the training sets
				CancellationPrediction prediction = new CancellationPrediction(jobMap, i);
				
				//work on the tests sets (which are the following 20 jobs after every training set)
				for (int j = i + 80; j < i + 100; j++) {
					
					//if our prediction does not correspond to the value from the file
					if (prediction.getPrediction(j) != jobMap.get(j).getCancelled()) {
						if (prediction.getPrediction(j)) {
							trueInsteadOfFalse++;
						} else {
							falseInsteadOfTrue++;
						}
						error++;
					}

				} 
			
				nr += 20;
				total += error;
				System.out.println("Errors in this iteration: " + error);

			}

			System.out.println(" ");
			System.out.println("Total number of errors: " + total);
			System.out.println("Total number of tests: " + nr);
			total = total* 100 / nr;
			System.out.println("The percentage of errors: " + total + "%");
			System.out.println("True instead of false: " + trueInsteadOfFalse);
			System.out.println("False instead of true: " + falseInsteadOfTrue);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}