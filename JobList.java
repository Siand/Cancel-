

import java.util.ArrayList;

/**
 * @author ovoido
 *
 */
public class JobList {
	private ArrayList<JobData> list;
	private float totalReward = 0;
	private float totalWeight = 0;
	private boolean cancelled = false;
	private float average = 0;
	private int totalN = 0;

	/**
	 * @param list
	 *            Where each job will be stored and relevant information
	 * @param totalReward
	 *            Summed up reward of all jobs
	 * @param totalWeight
	 *            Summed up weight of all jobs
	 */
	public JobList(ArrayList<JobData> list, float totalReward, float totalWeight, int totalN) {
		this.list = list;
		this.totalReward = totalReward;
		this.totalWeight = totalWeight;
		this.totalN = totalN;
		average = totalReward/totalN;
		
	}

	public ArrayList<JobData> getList() {
		return list;
	}

	public int getTotalN() {
		return totalN;
	}

	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

	/**
	 * @param list
	 *            Where each job will be stored and relevant information
	 */
	public JobList(ArrayList<JobData> list) {
		this.list = list;

		for (JobData job : list) {
			totalReward += job.getItem().getReward()*job.getN();
			totalWeight += job.getItem().getWeight()*job.getN();
			totalN += job.getN();
		}
		average = totalReward/totalN;
	}

	/**
	 * @param cancelled
	 *            Set where this list of jobs got cancelled
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * @return Whether the list of jobs is cancelled
	 */
	public boolean getCancelled() {
		return cancelled;

	}

	/**
	 * @return The total reward of all jobs in the list
	 */
	public float getTotalReward() {
		return totalReward;
	}

	/**
	 * @return The total weight of all jobs in the list
	 */
	public float getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param i
	 *            The position of the item in list
	 * @return The item at the position i
	 */
	public ItemData getJobItem(int i) {
		return list.get(i).getItem();
	}

	/**
	 * @param i
	 *            The position of the item in list
	 * @return The number of items that are need to be picked up for the job
	 */
	public int getJobNum(int i) {
		return list.get(i).getN();
	}

	/**
	 * @return The number of jobs in the job list.
	 */
	public int size() {
		return list.size();
	}
}
