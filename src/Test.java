public class Test
{
    public static void main(String[] args) throws Exception
    {

        System.out.println(args.length);
        System.out.println(args[0]);
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
//            if(!args[0].equals("-f"))
//            {
//                System.out.println("Error in Command Line Arguments");
//                return;
//            }
//            Player lower=new Player("lower");
//            Player upper=new Player("upper");
//            String filepath = args[1];
//            Utils.TestCase temp = Utils.parseTestCase(filepath);
//            System.out.println(temp);
//            Board b = new Board();
//            for(Utils.InitialPosition ipiece: temp.initialPieces)
//            {
//
//                Piece p =Utils.create_piece(ipiece.piece);
//                System.out.println(p);
//                Position pos =new Position(ipiece.position);
//                System.out.println(pos);
//                b.setPiece(pos,p);
//            }
//            for(String upper_captured : temp.upperCaptures)
//            {
//                if(upper_captured.equals(""))
//                {
//                    break;
//                }
//                Piece p =Utils.create_piece(upper_captured);
//                upper.capturePiece(p);
//            }
//            for(String lower_captured : temp.lowerCaptures)
//            {
//                if(lower_captured.equals(""))
//                {
//                    break;
//                }
//                Piece p =Utils.create_piece(lower_captured);
//                lower.capturePiece(p);
//            }
//            for(String move : temp.moves)
//            {
//                String[] split = move.split(" ");
//                if(split[0].equals("move"))
//                {
//                    //continue from here
//                }
//            }
//            System.out.println(b);
        }


    }
}
