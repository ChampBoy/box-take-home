import java.io.*;
import java.util.*;

public class Driver
{
    public static void main(String[] args)
    {
        Utils u = new Utils();
        List<Utils.InitialPosition> initialPieces=new ArrayList<>();
        List<String> upperCaptures=new ArrayList<>();
        List<String> lowerCaptures=new ArrayList<>();
        List<String> moves=new ArrayList<>();
        initialPieces.add(new Utils.InitialPosition("K","e4"));
        initialPieces.add(new Utils.InitialPosition("s","a2"));
        Utils.TestCase tc = new Utils.TestCase(initialPieces,upperCaptures,lowerCaptures,moves);
        System.out.println(tc.toString());
    }
}