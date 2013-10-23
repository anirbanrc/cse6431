
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rajadityamukherjee
 */
public class SimpleFileIO {

    private File fName_;
    private OrderObj master;
    
    public SimpleFileIO(String fileName) throws FileNotFoundException
    {
        fName_ = new File(fileName);
        String curLine;
        int count = 0;
        int temp;
        int timeIn_;
        int burC_;
        int friC_;
        int cokC_;
        master = new OrderObj();
        Scanner fileScanner = new Scanner(new FileReader(fName_));
        try 
        {
            while ( fileScanner.hasNextLine() )
            {
                curLine = fileScanner.nextLine().trim();
                if(count == 0)
                {
                    temp = Integer.parseInt(curLine);
                    master.setGuestCount(temp);
                    
                }
                else if (count ==1)
                {
                    temp = Integer.parseInt(curLine);
                    master.setTableCount(temp);
                }
                else if(count ==2)
                {
                    temp = Integer.parseInt(curLine);
                    master.setCookCount(temp);
                }
                else
                {
                    Scanner lineScanner = new Scanner(curLine);
                    lineScanner.useDelimiter(Pattern.compile("\\s+"));
                    if(lineScanner.hasNext())
                    {
                        timeIn_ = Integer.parseInt(lineScanner.next());
                        burC_ = Integer.parseInt(lineScanner.next());
                        friC_ = Integer.parseInt(lineScanner.next());
                        cokC_ = Integer.parseInt(lineScanner.next());
                        if(cokC_ > 1)
                        {
                            cokC_ = 1;
                        }
                        //Sanity Checking 
                        if(burC_<1)
                        {
                            System.out.println("Burger Count cannot be 0. Invalid data set.");
                        }
                        OrderType tempObj = new OrderType(timeIn_,burC_,friC_,cokC_); 
                        master.addOrder(tempObj);
                    }
                    lineScanner.close();                    
                }
                count++;
            }
        }
        finally
        {
            fileScanner.close();
        }
    }
    
    public OrderObj getFinalOrder()
    {
        return master;
    }
    
}
