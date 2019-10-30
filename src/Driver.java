import java.io.*;
import java.util.*;

public class Driver
{
    public static void main(String[] args) throws Exception
    {

//        Utils.TestCase tc = new Utils.TestCase(initialPieces,upperCaptures,lowerCaptures,moves);
//        Utils.TestCase temp = Utils.parseTestCase("/home/paraw001/Desktop/box-take-home/test/BoxShogi Test Cases/basicCheck.in");
//        System.out.println(temp.toString());
        Relay r_lower = new Relay("r");
        Relay r_upper = new Relay("R");
        Position p = new Position('b',3);
        Position p2 = new Position('d',4);
        Board b = new Board();
        b.setPiece(p,r_lower);
        b.setPiece(p2,r_upper);
        r_lower.generateMoves(b,p);
        System.out.println("Upper moves");
        r_upper.generateMoves(b,p2);
        System.out.println(b);
    }
}