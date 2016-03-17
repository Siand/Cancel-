


/**
 * Save probabilities in some ArrayLists according to every feature
 * @author axr574
 */
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Probabilities{

	ConcurrentHashMap<Integer, JobList> jobMap;

	// yes - cancelled jobs; no - not cancelled 
	
	ArrayList<Float> rewardsYes = new ArrayList<Float>();
	ArrayList<Float> rewardsNo = new ArrayList<Float>();
	
	ArrayList<Float> weightsYes = new ArrayList<Float>();
	ArrayList<Float> weightsNo = new ArrayList<Float>();
	
	ArrayList<Float> numberYes = new ArrayList<Float>();
	ArrayList<Float> numberNo = new ArrayList<Float>();
	
	//number of cancelled jobs
	int yes = 0;
	
	//number of not cancelled jobs
	int no = 0;
	
	//the lower bound of the training set
	private int start;

	public Probabilities(ConcurrentHashMap<Integer, JobList> jobMap, int start) {
		this.jobMap = jobMap;
		this.start = start;
		for (int i = start; i < start + 80; i++) {
			if (jobMap.get(i).getCancelled())
				yes++;
			else
				no++;

		}

		//
		countRewards(true, yes, rewardsYes);
		countRewards(false, no, rewardsNo);
		countWeights(true, yes, weightsYes);
		countWeights(false, no, weightsNo);
		countNumber(true, yes, numberYes);
		countNumber(false, no, numberNo);
	}

	public double getNumber(String type,int index)
	{
		if (type.equals("rewards"))
			return rewardsYes.get(index) +rewardsNo.get(index);
		else if (type.equals("weights"))
			return weightsYes.get(index) +weightsNo.get(index);
		else 
			return numberYes.get(index)+numberNo.get(index);
	}
	// Counts how many jobs(out of 80) have their rewards/weights/nrOfObj within
	// a specific interval, taking into consideration if the jobs will be
	// cancelled or not.

	// Parameters:
	// type - the feature (rewards/weights/nrOfObj) on which this method is
	// applied; instead of creating a method for every feature, I have created
	// a universal one that requires the feature as a parameter
	// min - the lower bound of the interval
	// max - the upper bound of the interval
	// cancelled - whether the job will be cancelled or not, reading from the
	// file

	private int count(String type, double min, double max, boolean cancelled) {
		int count = 0;
		float value = 0;
		for (int i = start; i < start + 80; i++) {
			if (type.equals("rewards")) {
				value = jobMap.get(i).getTotalReward();
			} else if (type.equals("weights")) {
				value = jobMap.get(i).getTotalWeight();
			} else if (type.equals("number")) {
				value = jobMap.get(i).getTotalN();
			}
			if (value >= min && value < max && cancelled == jobMap.get(i).getCancelled()) {
				count++;
			}
		}
		return count;
	}

	// Intervals:
	// [0,3)
	// [3,4)
	// [4,5)
	// [5,6)
	// ...
	// [16,17)
	// [17,18)
	// [18,10000)
	// the ArrayList will retain the probabilities of jobs to be cancelled or
	// not, according to their rewards and split in
	// intervals; the size of the ArrayList is equal to the number of intervals
	private void countRewards(boolean cancelled, int nr, ArrayList<Float> array) {
		float count = 0;
		// the first interval

		for (int i = 0; i < 260; i+=2) {
			count = count("rewards", i, i + 2, cancelled);
			//array.add(count / nr);
			array.add(count);
		}
		// the last interval
		count = count("rewards", 260, 10000, cancelled);
		//array.add(count / nr);
		array.add(count);
	}

	// Intervals
	// [0, 0.3)
	// [0.3, 0.6)
	// [0.6, 0.9)
	// ...
	// [5.1, 5.4)
	// [5.4, 10000)
	// the ArrayList will retain the probabilities of jobs to be cancelled or
	// not, according to their weights and split in
	// intervals; the size of the ArrayList is equal to the number of intervals
	private void countWeights(boolean cancelled, int nr, ArrayList<Float> array) {
		float count = 0;
		for (double i = 0; i < 60; i += 2) {
			count = count("weights", i, i + 2, cancelled);
			//array.add(count / nr);
			array.add(count);
		}
		//the last interval
		count = count("weights", 60, 10000, cancelled);
		//array.add(count / nr);
		array.add(count);
	}

	// Intervals
	// [0,1)
	// [1,2)
	// [2,3)
	// ...
	// [19,20)
	// [20,1000)
	// the ArrayList will retain the probabilities of jobs to be cancelled or
	// not, according to their nrOfObjects and split in
	// intervals ; the size of the ArrayList is equal to the number of intervals
	private void countNumber(boolean cancelled, int nr, ArrayList<Float> array) {
		float count = 0;

		for (double i = 0; i < 20; i += 1) {
			count = count("number", i, i + 1, cancelled);
			//array.add(count / nr);
			array.add(count);
		}
		//the last interval
		count = count("number", 20, 10000, cancelled);
		//array.add(count / nr);
		array.add(count);
	}

	/**
	 * Get the arrayList of a specific feature, taking into consideration whether the job will be cancelled or not
	 * 
	 * @param cancelled
	 *            whether the job will be cancelled or not, reading from the
	 *            file
	 * @param type
	 *            the feature (rewards/weights/number)
	 * @return the arrayList
	 */
	public ArrayList<Float> get(boolean cancelled, String type) {
		if (cancelled) {
			if (type.equals("rewards"))
				return rewardsYes;
			else if (type.equals("weights"))
				return weightsYes;
			else if (type.equals("number"))
				return numberYes;
			else
				return null;
		} else {
			if (type.equals("rewards"))
				return rewardsNo;
			else if (type.equals("weights"))
				return weightsNo;
			else if (type.equals("number"))
				return numberNo;
			else
				return null;
		}
	}
}
