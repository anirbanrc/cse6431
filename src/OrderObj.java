
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rajadityamukherjee
 */
public class OrderObj
{
    private int guestC_;
    private int cookC_;
    private int tabC_;
    private Vector<OrderType> list;
    
    public OrderObj()
    {
        guestC_ = -1;
        cookC_ = -1;
        tabC_ = -1;
        list = new Vector<OrderType>();
    }
    
    public void setGuestCount(int g)
    {
        guestC_ = g;
    }
    
    public int getGuestCount()
    {
        return guestC_;
    }
    
    public void setTableCount(int t)
    {
        tabC_ = t;
    }
    
    public int getTableCount()
    {
        return tabC_;
    }
    
    public void setCookCount(int c)
    {
        cookC_ = c;
    }
    
    public int getCookCount()
    {
        return cookC_;
    }
    
    public void addOrder(OrderType o)
    {
        list.add(o);
    }
    
    public OrderType getOrder(int i)
    {
        return list.elementAt(i);
    }
    
    public String toString()
    {
        String output="";
        output += "Number of Guests :";
        output += guestC_;
        output += "\n";
        output += "Number of Tables :";
        output += tabC_;
        output += "\n";
        output += "Number of Cooks :";
        output += cookC_;
        output += "\n";
        for(int i =0;i<guestC_;i++)
        {
            output += this.getOrder(i);
        }
        return output;
    }
    
}
