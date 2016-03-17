


/**
 * Predict whether a job will be cancelled or not
 * @author axr574
 */
import java.util.concurrent.ConcurrentHashMap;

public class CancellationPrediction {

	private ConcurrentHashMap<Integer, JobList> jobMap;
	private Probabilities prob;

	public CancellationPrediction(ConcurrentHashMap<Integer, JobList> jobMap, int start) {
		this.jobMap = jobMap;
		prob = new Probabilities(jobMap, start);
	}

	private double reward(boolean b, double reward) {
		double p = 0;
		int j =0;
			for (int i = 0; i < 260; i+=2) {
				if (reward < i+2) {
					p = prob.get(b, "rewards").get(j);
					break;
				}
				j++;
			}
		if (reward > 260)
			p = prob.get(b, "rewards").get(j);

		if (p == 0)
			p = 0.00001;
		return p;
	}
	
	
	private double reward2(boolean b, double reward) {
		double p=0;
		int j =0;
			for (int i = 0; i < 260; i+=2) {
				if (reward < i+2) {
					p = prob.getNumber("rewards",j);
					break;
				}
				j++;
			}
		return p;
	}

	private double weights(boolean b, double weight) {
		double p = 0;
		int j = 0;
		for (double i = 0; i < 60; i += 2) {
			if (weight < i + 2) {
				p = prob.get(b, "weights").get(j);
				break;
			}
			j++;
		}
		if (weight > 60) {
			p = prob.get(b, "weights").get(j);
		}
		if (p == 0)
			p = 0.00001;
		return p;
	}
	private double weights2(boolean b, double weight) {
		double p = 0;
		int j = 0;
		for (double i = 0; i < 60; i += 2) {
			if (weight < i + 2) {
				p = prob.getNumber("weights",j);
				break;
			}
			j++;
		}
		return p;
	}

	private double number(boolean b, double number) {
		double p = 0;
		int j = 0;
		for (int i = 0; i < 20; i+=1){
			if (number < i + 1) {
				p = prob.get(b, "number").get(j);
				break;
			}
			j++;
		}
		if (number > 20)
			p = prob.get(b, "number").get(j);
		if (p == 0)
			p = 0.00001;

		return p;
	}
	
	private double number2(boolean b, double number) {
		double p = 0;
		int j = 0;
		for (int i = 0; i < 20; i+=1){
			if (number < i + 1) {
				p = prob.getNumber("number",j);
				break;
			}
			j++;
		}
		return p;
	}

	/**
	 * Predict whether a job will be cancelled or not
	 * 
	 * @param index
	 *            the job ID
	 * @return true if the job will be cancelled or false it will not
	 */
	public boolean getPrediction(int index) {
		// get the reward, weight and number of objects of this job
		double reward = jobMap.get(index).getTotalReward();
		double weight = jobMap.get(index).getTotalWeight();
		double number = jobMap.get(index).getTotalN();

		double pYes = 0.5, pNo = 0.5;

		// get the probabilities
		
		double rewardProb = reward(true, reward);
		double weightProb = weights(true, weight);
		double numberProb = number(true, number);
		
		double rewardProb2 = reward2(true, reward);
		double weightProb2 = weights2(true, weight);
		double numberProb2 = number2(true, number);
		if(numberProb2 +weightProb2+rewardProb2==0)
			pYes=1;
			else
		pYes =(rewardProb + weightProb + numberProb)/(numberProb2 +weightProb2+rewardProb2);

		//rewardProb = reward(false, reward);
		//weightProb = weights(false, weight);
	//	numberProb = number(false, number);
	//	pNo = pNo * rewardProb * weightProb * numberProb;

		// classifier-pYes is multiplied by 0.5 because, otherwise, the
		// algorithm would tend to wrongly predict that some jobs would be
		// cancelled (this reduces the errors of type "True instead of false"
		// from the testing)
		pNo=1-pYes;
		if (pYes * 0.5 > pNo)
			return true;
		else
			return false;
	}
}
