/**
 * 
 */
package thread.diner;

import resource.restaurant.Restaurant;
import resource.table.Table;
import resource.util.CookBuffer;
import resource.util.Order;

/**
 * @author Anirban
 *
 */
public class Customer extends Thread {
	private Order order = null;
	private Restaurant restaurant = null;
	private Table table = null;
	private CookBuffer cookBuffer = null;
	private Object servedOrder = null;
	private int entryTime=0;
	private int orderServedTime=0;
	
	private Object tellRestaurant =null;
	
	private int myId = -1;
	private int assignedCookId = -1;
	
	public int getCustId() {
		return myId;
	}
	public void setCustId(int myId) {
		this.myId = myId;
	}
	public int getAssignedCookId() {
		return assignedCookId;
	}
	public void setAssignedCookId(int assignedCookId) {
		this.assignedCookId = assignedCookId;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Object getTellRestaurant() {
		return tellRestaurant;
	}
	public void setTellRestaurant(Object tellRestaurant) {
		this.tellRestaurant = tellRestaurant;
	}
	public int getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(int entryTime) {
		this.entryTime = entryTime;
	}
	
	public void run()
	{
		int time = getEntryTime();
		try {
			Thread.sleep(time*100);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		time = waitForTable(time);
		System.out.println("Customer"+myId+" got table"+table.getId_()+" at "+time);
		try {
			waitForAvailableCook();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		time = order(time);
		try {
			waitForOrderedFood();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		time = eatAndLeave();
		synchronized(tellRestaurant) {
			tellRestaurant.notify();
		}
	}
	
	private int waitForTable(int entryTime)
	{
		table = restaurant.bookTableForNewCustomer();
		table.setStartTime(Math.max(entryTime, table.getEndTime())); 
		return table.getStartTime();
	}
	
	private void waitForAvailableCook() throws Exception
	{
		int availableCooks = restaurant.getCooksAvailable();
		if (availableCooks < 1) {
			System.out.println("Customer"+myId+" started waiting for cook");
			Object object = restaurant.getCookObject();
			synchronized (object) {
				object.wait();
			}
		}
		
		int index = restaurant.getCookIndexForNewCustomer(myId);
		if (index < 0)
			throw new Exception("ERROR: Cook not found for customer "+ getId());
		setAssignedCookId(index);System.out.println("Customer"+myId+" was assigned cook"+index);
		cookBuffer = restaurant.getCookBuffers().get(index);
		servedOrder = restaurant.getServedOrders().get(index);
	}
	
	private int order(int tableAcquireTime)
	{
		synchronized (cookBuffer) {
			cookBuffer.setOrder(getOrder());
			cookBuffer.setReadyToTakeOrderTime(Math.max(tableAcquireTime, cookBuffer.getOrderedFoodReadyTime()));
		}
		return cookBuffer.getReadyToTakeOrderTime();
	}
	
	private void waitForOrderedFood() throws InterruptedException
	{
		synchronized (cookBuffer) {
			cookBuffer.notify(); //notify cook that you are ready with your order
		}
		synchronized (servedOrder) {
			servedOrder.wait(); //wait for order delivery
		}
		synchronized (cookBuffer) {
			orderServedTime = cookBuffer.getOrderedFoodReadyTime(); //get delivery time
		}
		System.out.println("Customer"+myId+" received order at "+orderServedTime);
	}
	
	private int eatAndLeave()
	{
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setEndTime(orderServedTime+30);System.out.println("Customer"+myId+" finished, left table"+table.getId_()+" at "+table.getEndTime());
		table.setOccupancy(false);
		synchronized (restaurant) {
			restaurant.notifyAll();
		}
		return table.getEndTime();
	}
}

