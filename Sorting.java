
/**
 * This class contains methods to print the rewards/weights/nrOfObjects corresponding to some specific jobs in ascending order 
 * @author axr574
 */

package testPackage;
import java.util.concurrent.ConcurrentHashMap;

public class Sorting {

	float[] rewards;
	float[] weights;
	float[] number;
	ConcurrentHashMap<Integer, JobList> jobMap;
	QuickSort sorter;
	private int min;
	private int max;

	public Sorting(ConcurrentHashMap<Integer, JobList> jobMap, int min, int max) {
		this.jobMap = jobMap;
		this.min = min;
		this.max = max;
		rewards = new float[500];
		weights = new float[500];
		number = new float[500];
		sorter = new QuickSort();
	}

	private void sortAndPrint(float[] nr){
		sorter.sort(nr);
		for (float i : nr)
			System.out.println(i);
	}
	
	/**
	 * Print the rewards of the jobs 10000 - 10500 in ascending order
	 */
	public void sortingRewards() {
		int j = 0;
		for (int i = min; i < max; i++) {
			rewards[j] = jobMap.get(i).getTotalReward();
			j++;
		}
		sortAndPrint(rewards);
	}

	/**
	 * Print the weights of the jobs 10000 - 10500 in ascending order
	 */
	public void sortingWeights() {
		int j = 0;
		for (int i = min; i < max; i++) {
			weights[j] = jobMap.get(i).getTotalWeight();
			j++;
		}
		sortAndPrint(weights);
	}

	/**
	 * Print the number of objects corresponding to the jobs 10000 - 10500 in ascending order
	 */
	public void sortingNrOfObj() {
		int j = 0;
		for (int i = min; i < max; i++) {
			number[j] = jobMap.get(i).getTotalN();
			j++;
		}
		sortAndPrint(number);
	}
}
