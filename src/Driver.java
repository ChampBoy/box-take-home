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
        Notes n1 = new Notes("n");
        Position startn = new Position('b',5);
        Position endn = new Position('e',5);
        b.setPiece(startn,n1);
        if(n1.canMove(b,startn,endn))
        {
            b.removePiece(startn);
            b.setPiece(endn,n1);
        }

        Position start = new Position('b',2);
        System.out.println("Position start x = "+start.getX());
        System.out.println("Position start y = "+start.getY());
        b.setPiece(start , d);
        System.out.println(b);
        Position end = new Position('b',3);
        Governance g = new Governance("g");
        Position pg = new Position('e',1);
        b.setPiece(pg,g);
        Position endg = new Position('c',3);
        Position endg2 = new Position('b',2);
        if(g.canMove(b,pg,endg))
        {
            System.out.println("Moving");
            b.removePiece(pg);
            b.setPiece(endg,g);
        }
        b.removePiece(endg2);
        b.setPiece(new Position('c',2),d);
        if(g.canMove(b,endg,endg2))
        {
            System.out.println("Moving");
            b.removePiece(endg);
            b.setPiece(endg2,g);
        }
        System.out.println(b);
    }
}