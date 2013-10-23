
import java.io.FileNotFoundException;
import java.util.ArrayList;

import resource.restaurant.Restaurant;
import resource.table.Table;
import resource.util.CookBuffer;
import resource.util.Order;
import thread.cook.Cook2;
import thread.diner.Customer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rajadityamukherjee
 */
public class Rest6431 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException 
    {
       System.out.println("Starting to read input information");
       SimpleFileIO fileReaderObj = new SimpleFileIO(args[0]);
       OrderObj masterOrderList = fileReaderObj.getFinalOrder();
       System.out.println("Input information successfully read");
       
       int cookCount = masterOrderList.getCookCount();
       int tableCount = masterOrderList.getTableCount();
       int guestCount = masterOrderList.getGuestCount();
       
       System.out.println("Restaurant object initialization");
       Restaurant rObj = new Restaurant(cookCount,tableCount);
       
       Table tempTab = null;
       for(int i =0; i<tableCount;i++)
       {
           tempTab = new Table(i);
           rObj.getTables().add(tempTab);
       }
       
       //Initialize cooks and start the threads 
       Cook2 tempCook = null;
       CookBuffer tempCBuf = null;
       for(int i = 0;i<cookCount;i++)
       {
           tempCook = new Cook2(i,rObj);
           tempCBuf = new CookBuffer();
           tempCBuf.setReadyToTakeOrderTime(0);
           tempCBuf.setOrderedFoodReadyTime(0);
           Object tempSerOrd = new Object();
           rObj.getServedOrders().add(tempSerOrd);
           rObj.getCooks().add(tempCook);
           rObj.getCookBuffers().add(tempCBuf);
           tempCook.start();
       }
       
       //Initialize customers and start the threads
       ArrayList<Customer> customers = new ArrayList<Customer>();
       Customer tempCust = null;
       Order tempOrder = null;
       Object tellRestaurant = new Object();
       
       for(int i=0;i<guestCount;i++)
       {
           tempCust = new Customer();
           tempCust.setCustId(i);
           tempCust.setRestaurant(rObj);
           
           tempOrder = new Order();
           tempOrder.setNumberOfBurgers(masterOrderList.getOrder(i).getBurgerCount());
           tempOrder.setNumberofFries(masterOrderList.getOrder(i).getFriesCount());
           tempOrder.setNumberOfCokes(masterOrderList.getOrder(i).getCokeCount());
           tempCust.setOrder(tempOrder);
           
           tempCust.setEntryTime(masterOrderList.getOrder(i).getInTime());
           
           customers.add(tempCust);
           
           tempCust.setTellRestaurant(tellRestaurant);
           tempCust.start();
       }
       
       for (Customer customer : customers) {
    	   try {
    		   customer.join();
    	   } catch (InterruptedException e) {
    		   // TODO Auto-generated catch block
    		   e.printStackTrace();
    	   }
       }
       System.out.println("All customers have left.");
       
       rObj.setRestaurantClosing(true);
       ArrayList<CookBuffer> cookBuffers = rObj.getCookBuffers();
       ArrayList<Cook2> cooks = rObj.getCooks();
       CookBuffer buffer = null;
       for (int i=0; i<cooks.size(); i++) {
    	   buffer = cookBuffers.get(i);
    	   synchronized (buffer) {
			buffer.notify();
    	   }
    	   try {
    		   cooks.get(i).join();
    	   } catch (InterruptedException e) {
    		   // TODO Auto-generated catch block
    		   e.printStackTrace();
    	   }
       }
       System.out.println("Restaurant closed.");
    }
}
