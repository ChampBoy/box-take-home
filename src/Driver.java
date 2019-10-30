import java.io.*;
import java.util.*;

public class Driver
{
    public static void main(String[] args) throws Exception
    {
//        Utils u = new Utils();
//        List<Utils.InitialPosition> initialPieces=new ArrayList<>();
//        List<String> upperCaptures=new ArrayList<>();
//        List<String> lowerCaptures=new ArrayList<>();
//        List<String> moves=new ArrayList<>();
//        initialPieces.add(new Utils.InitialPosition("K","e4"));
//        initialPieces.add(new Utils.InitialPosition("s","a2"));
//        Utils.TestCase tc = new Utils.TestCase(initialPieces,upperCaptures,lowerCaptures,moves);
//        Utils.TestCase temp = Utils.parseTestCase("/home/paraw001/Desktop/box-take-home/test/BoxShogi Test Cases/basicCheck.in");
//        Cell c = new Cell(0,1);
//
//        System.out.println(temp.toString());
        Board b = new Board();
        Drive d = new Drive("d");
        Drive d2 = new Drive("a");
        Position start = new Position('b',2);
        Position startd2 = new Position('b',3);
        System.out.println("Position start x = "+start.getX());
        System.out.println("Position start y = "+start.getY());
        b.setPiece(start , d);
        b.setPiece(startd2,d2);
        Position end = new Position('b',3);
        if(d.canMove(b,start,end))
        {
            System.out.println("Can move");
        }
        System.out.println(b);
    }
}