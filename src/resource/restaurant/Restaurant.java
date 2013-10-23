package resource.restaurant;

import java.util.ArrayList;
import java.util.Iterator;

import resource.machine.BurgerMachine;
import resource.machine.CokeMachine;
import resource.machine.FriesMachine;
import resource.table.Table;
import resource.util.CookBuffer;
import thread.cook.Cook2;


/**
 * @author Anirban
 *
 */

public class Restaurant {
	private ArrayList<Cook2> cooks = null;
	private ArrayList<CookBuffer> cookBuffers = null;
	private ArrayList<Table> tables = null;
	private ArrayList<Object> servedOrders = null;
	
	private BurgerMachine burgerMachine = null;
	private FriesMachine friesMachine = null;
	private CokeMachine cokeMachine = null;
	
	private Object machineMonitor = null;
	
	private Object cooksAvailable;
	private int availableCooks = -1;
	
	private boolean restaurantClosing = false;
	
	public Restaurant() {
		super();
		cooks = new ArrayList<Cook2>();
		cookBuffers = new ArrayList<CookBuffer>();
		tables = new ArrayList<Table>();
		servedOrders = new ArrayList<Object>();
		
		cooksAvailable = new Object();
		
		setBurgerMachine(new BurgerMachine());
		setFriesMachine(new FriesMachine());
		setCokeMachine(new CokeMachine());
		
		machineMonitor = new Object();
	}
	
	public Restaurant(int cookCount, int tableCount) {
		super();
		cooks = new ArrayList<Cook2>(cookCount);
		cookBuffers = new ArrayList<CookBuffer>(cookCount);
		tables = new ArrayList<Table>(tableCount);
		servedOrders = new ArrayList<Object>(cookCount);

		cooksAvailable = new Object();
		availableCooks = cookCount;
		
		setBurgerMachine(new BurgerMachine());
		setFriesMachine(new FriesMachine());
		setCokeMachine(new CokeMachine());
		
		machineMonitor = new Object();
	}

	public ArrayList<Cook2> getCooks() {
		return cooks;
	}
	public void setCooks(ArrayList<Cook2> cooks) {
		this.cooks = cooks;
	}
	public ArrayList<CookBuffer> getCookBuffers() {
		return cookBuffers;
	}
	public void setCookBuffers(ArrayList<CookBuffer> cookBuffers) {
		this.cookBuffers = cookBuffers;
	}
	public ArrayList<Table> getTables() {
		return tables;
	}
	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}
	public BurgerMachine getBurgerMachine() {
		return burgerMachine;
	}

	public void setBurgerMachine(BurgerMachine burgerMachine) {
		this.burgerMachine = burgerMachine;
	}

	public FriesMachine getFriesMachine() {
		return friesMachine;
	}

	public void setFriesMachine(FriesMachine friesMachine) {
		this.friesMachine = friesMachine;
	}

	public Object getMachineMonitor() {
		return machineMonitor;
	}

	public void setMachineMonitor(Object machineMonitor) {
		this.machineMonitor = machineMonitor;
	}

	public CokeMachine getCokeMachine() {
		return cokeMachine;
	}

	public void setCokeMachine(CokeMachine cokeMachine) {
		this.cokeMachine = cokeMachine;
	}

	public ArrayList<Object> getServedOrders() {
		return servedOrders;
	}
	public void setServedOrders(ArrayList<Object> servedOrders) {
		this.servedOrders = servedOrders;
	}
	public synchronized int getCooksAvailable() {
		return availableCooks;
	}

	public synchronized void incCooksAvailable() {
		availableCooks++;
	}
	
	public synchronized void decCooksAvailable() {
		availableCooks--;
	}
	
	public synchronized int getCookIndexForNewCustomer(int customerId) {
		for (int i=0; i<cooks.size(); i++)
			if (!cooks.get(i).isBusy()) {
				decCooksAvailable();
				cooks.get(i).setCustomerId(customerId);
				return i;
			}
				
		return -1;
	}
	
	public synchronized Table bookTableForNewCustomer() {
		Iterator<Table> tableIterator = null;
		while (true) {
			tableIterator = tables.iterator();
			Table table = null;
			while(tableIterator.hasNext()) {
				table = tableIterator.next();
				if (!table.isOccupied()) {
					table.setOccupancy(true);
					return table;
				}
			}
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void newCookAvailable(){
		incCooksAvailable();
		synchronized (cooksAvailable) {
			cooksAvailable.notify();
		}
	}
	
	public Object getCookObject(){
		return cooksAvailable;
	}

	public boolean isRestaurantClosing() {
		return restaurantClosing;
	}

	public void setRestaurantClosing(boolean restaurantClosing) {
		this.restaurantClosing = restaurantClosing;
	}
}
