package testPackage;

import java.awt.Point;

/**
 * 
 * @author ovoido
 *
 */
public class ItemData {

	private Float weight = null;
	private Float reward = null;
	private Point coordinates;

	/**
	 * @return The weight of the item
	 */
	public Float getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            Set the new weight of the item
	 */
	public void setWeight(Float weight) {
		this.weight = weight;
	}

	/**
	 * @param reward
	 *            Set the new reward of the item
	 */
	public void setReward(Float reward) {
		this.reward = reward;
	}

	/**
	 * @param x
	 *            The x-coordinate of the item
	 * @param y
	 *            The y-coordinate of the item
	 */
	public void setPoint(int x, int y) {
		coordinates.setLocation(x, y);
	}

	/**
	 * @return The reward of the item
	 */
	public Float getReward() {
		return reward;
	}

	/**
	 * @return The x coordinate of the item
	 */
	public Integer getX() {
		return (int) coordinates.getX();
	}

	/**
	 * @return The y coordinate of the item
	 */
	public Integer getY() {
		return (int) coordinates.getY();
	}

	/**
	 * @return The coordinates (Point) of the item
	 */
	public Point getCoordinates() {
		return coordinates;
	}

	/**
	 * Constructing ItemData only with weight and reward
	 * 
	 * @param weight
	 *            The weight of the item
	 * @param reward
	 *            The reward for the time
	 */
	public ItemData(Float weight, Float reward) {
		this.weight = weight;
		this.reward = reward;
		coordinates = new Point(-1, -1);
	}

	/**
	 * Constructing ItemData only with x and y coordinates
	 * 
	 * @param x
	 *            The x-coordinate of item's location
	 * @param y
	 *            The y-coordinate of item's location
	 */
	public ItemData(Integer x, Integer y) {
		coordinates = new Point(x, y);
	}

	/**
	 * To check if all variables of the item have a value, none are null
	 * 
	 * @return true or false, where or not all variables are assigned non null
	 *         value.
	 */
	public boolean complete() {
		
		if (weight != null && reward != null && coordinates.getX() < 0 && coordinates.getY() < 0) {
			return true;
		} else {
			return false;
		}
	}
}
