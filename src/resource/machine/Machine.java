/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resource.machine;

/**
 *
 * @author rajadityamukherjee
 */
public interface Machine 
{
	boolean getOccupancy();
    void setOccupancy(boolean occ);
    int getCookId();
    void setCookId(int id);
    void setStartTime(int st);
    int getStartTime();
    void setEndTime(int et);
    int getEndTime();
    
    int startCooking(int number, int startTime, int cid);
}
