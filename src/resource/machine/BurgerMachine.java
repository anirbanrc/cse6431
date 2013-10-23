/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resource.machine;

/**
 *
 * @author rajadityamukherjee
 */
public class BurgerMachine implements Machine
{
    private boolean occupied_;
    private int cookId_;
    private int startTime_;
    private int endTime_;
    
    public boolean getOccupancy()
    {
        return occupied_;
    }
    
    public void setOccupancy(boolean occ)
    {
        occupied_ = occ;
    }
    
    public int getCookId()
    {
        return cookId_;
    }
    
    public void setCookId(int id)
    {
        cookId_ = id;
    }
    
    public void setStartTime(int st)
    {
        startTime_ = st;
    }
    
    public int getStartTime()
    {
        return startTime_;
    }
   
    public void setEndTime(int et)
    {
        endTime_ = et;
    }
    
    public int getEndTime()
    {
        return endTime_;
    }
    
    public synchronized int startCooking(int number,int startTime,int cid)
    {
        this.setCookId(cid);
        this.setStartTime(startTime);
        this.setOccupancy(true);
        try {
			Thread.sleep(500*number);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.setEndTime(startTime+number*5);
        this.setOccupancy(false);
        notifyAll();
        return this.endTime_;
        
        
    }
}
