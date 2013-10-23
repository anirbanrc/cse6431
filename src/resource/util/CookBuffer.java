package resource.util;

/**
 * @author Anirban
 *
 */
public class CookBuffer {
	private Order order = null;
	private int readyToTakeOrderTime = 0;
	private int orderedFoodReadyTime = 0;
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public int getReadyToTakeOrderTime() {
		return readyToTakeOrderTime;
	}
	public void setReadyToTakeOrderTime(int readyToTakeOrderTime) {
		this.readyToTakeOrderTime = readyToTakeOrderTime;
	}
	public int getOrderedFoodReadyTime() {
		return orderedFoodReadyTime;
	}
	public void setOrderedFoodReadyTime(int orderedFoodReadyTime) {
		this.orderedFoodReadyTime = orderedFoodReadyTime;
	}
}
