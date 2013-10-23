/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resource.table;

/**
 *
 * @author rajadityamukherjee
 */
public class Table 
{
    private boolean occupied_;
    private int cookId_;
    private int occupyStartTime_;
    private int occupyEndTime_;
    
    private int id_;
    
    public int getId_() {
		return id_;
	}

	public void setId_(int id_) {
		this.id_ = id_;
	}

	public Table(int i)
    {
       occupied_ = false;
       cookId_ = 0;
       occupyStartTime_ = -1;
       occupyEndTime_ = -1;   
       id_ = i;
    }
    
    public int getCookID()
    {
        return cookId_;
    }
    
    public void setCookID(int cid)
    {
        cookId_ = cid;
    }
    
    public boolean isOccupied()
    {
        return occupied_;
    }
    
    public void setOccupancy(boolean occ)
    {
        occupied_ = occ;
    }
    
    public void setStartTime(int st)
    {
        occupyStartTime_ = st;
    }
    
    public int getStartTime()
    {
        return occupyStartTime_;
    }
    
    public void setEndTime(int et)
    {
        occupyEndTime_ = et;
    }
    
    public int getEndTime()
    {
        return occupyEndTime_;
    }
    
    public String toString()
    {
        return "Associated with cook:"+cookId_+"\n";
    }
}
