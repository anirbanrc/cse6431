/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rajadityamukherjee
 */
public class OrderType 
{
    private int inTime_;
    private int b_;
    private int f_;
    private int c_;
    
    public OrderType(int inTime,int b,int f,int c)
    {
        inTime_ = inTime;
        b_ = b;
        f_ = f;
        c_ = c;
    }
    
    public int getInTime()
    {
        return inTime_;
    }
    
    public int getBurgerCount()
    {
        return b_;
    }
    
    public int getFriesCount()
    {
        return f_;
    }
    
    public int getCokeCount()
    {
        return c_;
    }
    
    public String toString()
    {
        return "Guest Arrives at "+inTime_+ "\n Number of Burger Orders : "+b_ +"\n Number of Fries Order :" + f_ + "\n Number of Coke Orders : " + c_ + "\n"; 
    }
}
