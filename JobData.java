package testPackage;

/**
 * 
 * @author ovoido
 *
 */
public class JobData {

	//instead of storing string, store an object of the item.
	private int n;
	private ItemData item;
	private String itemID = null;

	/**
	 * Construct Job Data, with initial item and number of it
	 * 
	 * @param item
	 *            Item's identifier
	 * @param n
	 *            Number of the item required (for the job)
	 */
	public JobData(ItemData item, int n) {
		this.item = item;
		this.n = n;
	}
	
	public JobData(ItemData item, String itemID, int n) {
		this.item = item;
		this.n = n;
		this.itemID = itemID;
	}
	
	public String getItemID()
	{
		return itemID;
	}

	/**
	 * @return The item that needs to be picked up for this job
	 */
	public ItemData getItem() {
		return item;
	}

	/**
	 * @return The number of items that need to be picked up
	 */
	public int getN() {
		return n;
	}
}
