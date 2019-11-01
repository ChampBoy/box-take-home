import java.io.*;
import java.util.*;

public class Driver
{
    public static void main(String[] args) throws Exception
    {
        if(args.length>2 || args.length<1)
        {
            System.out.println("Error in Command Line Arguments");
            return;
        }
        if(args.length==1 && args[0].equals("-i")) //Interactive Mode
        {
            Game g = new Game(true,"");

        }
        else //file mode
        {
            if(!args[0].equals("-f"))
            {
                System.out.println("Error in Command Line Arguments");
                return;
            }
            String filepath = args[1];
            return; //Implement Later

        }


    }
}