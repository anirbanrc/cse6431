/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thread.cook;

import resource.machine.Machine;
import resource.restaurant.Restaurant;
import resource.util.CookBuffer;
import resource.util.Order;

/**
 *
 * @author Anirban
 */
public class Cook2 extends Thread
{
    private int orderStartTime_;
    private int orderFinishTime_;
    private boolean busy_ ;
    private Order currentOrder_;
    private int cookId_;
    private Restaurant restObj_;
    private CookBuffer myBuffer;
    private Object machineMonitor;
    private int customerId;
    
    public Cook2(int cid,Restaurant robj)
    {
        cookId_ = cid;
        orderStartTime_ = -1;
        orderFinishTime_ = -1;
        busy_ = false;
        currentOrder_ = null;
        restObj_ = robj;
        customerId=-1;
    }
    
    public int getCookId()
    {
        return cookId_;
    }
    
    public void setOrderStartTime(int orderStartTime)
    {
        orderStartTime_ = orderStartTime;
    }
    
    public int getOrderStartTime()
    {
        return orderStartTime_;
    }
    
    public void setOrderFinishTime(int orderFinishTime)
    {
        orderFinishTime_ = orderFinishTime;
    }
    
    public int getOrderFinishTime()
    {
        return orderFinishTime_;
    }
    
    public void setOrder(Order o)
    {
        currentOrder_ = o;
    }
    
    public Order getOrder()
    {
        return currentOrder_;
    }
    
    public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void setBusy(boolean busy)
    {
        busy_ = busy;
    }
    
    public boolean isBusy()
    {
        return busy_;
    }
    
    
    public void run()
    {
        Machine burgerMachine = restObj_.getBurgerMachine();
        Machine friesMachine = restObj_.getFriesMachine();
        Machine cokeMachine = restObj_.getCokeMachine();
        
        myBuffer = restObj_.getCookBuffers().get(cookId_);
        machineMonitor = restObj_.getMachineMonitor();
        while(true)
        {
        	synchronized (myBuffer) {
        		if (myBuffer.getOrderedFoodReadyTime() >= myBuffer.getReadyToTakeOrderTime())
        			try {
        				myBuffer.wait();
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}   
        		this.setOrder(myBuffer.getOrder());
        	}
        	if (restObj_.isRestaurantClosing())
        		return;
			this.setBusy(true);
			
            boolean finishedCookingBurger=false;
            boolean finishedCookingCoke=false;
            boolean finishedCookingFries=false;
            boolean allDone = false;
            int endTimeB=-1,endTimeC=-1,endTimeF=-1;
       
            int orderReadyTime = myBuffer.getReadyToTakeOrderTime();
            System.out.println("Cook"+cookId_+" got order at "+orderReadyTime);
            while (!allDone)
            {
                if (!finishedCookingBurger)
                {
                	if (!burgerMachine.getOccupancy())
                	{
                		int startTimeB = Math.max(burgerMachine.getEndTime(), orderReadyTime);System.out.println("Cook"+cookId_+" started burger(s) for customer"+customerId+" at "+startTimeB);
                        endTimeB = burgerMachine.startCooking(this.getOrder().getNumberOfBurgers(),startTimeB,this.getCookId());
                        finishedCookingBurger = true;System.out.println("Cook"+cookId_+": burger m/c released at "+endTimeB);
                        orderReadyTime = endTimeB;
                        synchronized (machineMonitor) {
							machineMonitor.notify();
						}
                	}
                	else
                		System.out.println("Cook"+cookId_+" found burger m/c busy");
                }
                if (!finishedCookingCoke)
                {
                	if (!cokeMachine.getOccupancy())
                	{
                		int startTimeC = Math.max(cokeMachine.getEndTime(), orderReadyTime);System.out.println("Cook"+cookId_+" started coke for customer"+customerId+" at "+startTimeC);
                        endTimeC = cokeMachine.startCooking(this.getOrder().getNumberOfCokes(),startTimeC,this.getCookId());
                        finishedCookingCoke = true;System.out.println("Cook"+cookId_+": coke m/c released at "+endTimeC);
                        orderReadyTime = endTimeC;
                        synchronized (machineMonitor) {
							machineMonitor.notify();
						}
                	}
                	else
                		System.out.println("Cook"+cookId_+" found coke m/c busy");
                }
                if (!finishedCookingFries)
                {
                	if (!friesMachine.getOccupancy())
                	{
                        int startTimeF = Math.max(friesMachine.getEndTime(), orderReadyTime);System.out.println("Cook"+cookId_+" started fries for customer"+customerId+" at "+startTimeF);
                        endTimeF = friesMachine.startCooking(this.getOrder().getNumberofFries(),startTimeF,this.getCookId());
                        finishedCookingFries = true;System.out.println("Cook"+cookId_+": fries m/c released at "+endTimeF);
                        orderReadyTime = endTimeF;
                        synchronized (machineMonitor) {
							machineMonitor.notify();
						}
                	}
                	else
                		System.out.println("Cook"+cookId_+" found fries m/c busy");
                }
                allDone = finishedCookingBurger && finishedCookingCoke && finishedCookingFries;    
                if (!allDone)
					try {
						synchronized (machineMonitor) {
							machineMonitor.wait();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }
            synchronized (myBuffer) {
				myBuffer.setOrderedFoodReadyTime(orderReadyTime);
			}
			
			Object myServedOrder = restObj_.getServedOrders().get(cookId_);
			synchronized(myServedOrder) {
				myServedOrder.notify();
			}
			System.out.println("Cook"+cookId_+" finished at "+orderReadyTime);
			this.setBusy(false);
			customerId =-1;
			restObj_.newCookAvailable();
        }
    }
}
