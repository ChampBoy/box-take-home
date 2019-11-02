import java.util.ArrayList;

public class Preview extends Piece
{
    private ArrayList<Position> moves = new ArrayList<>();
    private static final int[][] lower_possibleLocations = new int[][]{{0,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0}};
    private static final int[][] upper_possibleLocations = new int[][]{{1,-1},{0,-1},{-1,-1},{1,0},{-1,0},{0,1}};
    public Preview(String name)
    {
        super(name);
    }

    @Override
    public boolean canMove(Board board, Position start, Position end) {
        Position p;
        if(isPromoted())
        {
            shield_generateMoves(board,start);
            for(Position pos : moves)
            {
                if(end.equals(pos))
                {
                    return true;
                }
            }
            return false;
        }
        else
        {
            moves.clear();
            if(this.isLower())
            {
                p = new Position(start.getX(),start.getY()+1);
            }
            else
            {
                p = new Position(start.getX(),start.getY()-1);
            }
            moves.add(p);
            if(p.equals(end))
            {
                return true;
            }

        }

        return false;
    }
    public void shield_generateMoves(Board board,Position start)
    {
        moves.clear(); //Clears moves that might have been created in some other move
        int[][] locations;
        if(this.isLower())
        {
            locations = lower_possibleLocations;
        }
        else
        {
            locations = upper_possibleLocations;
        }
        for(int i=0;i<locations.length;i++)
        {
            int x= start.getX()+locations[i][0];
            int y= start.getY()+locations[i][1];
            if(board.isOnMap(x,y))
            {
                if(board.isOccupied(x,y))
                {
                    Piece end_piece = board.getPiece(x,y);
                    if(!((this.isLower() && end_piece.isLower())|| (this.isUpper() && end_piece.isUpper())))
                    {
                        Position move_loc = new Position(x,y);
                        moves.add(move_loc);
                        System.out.print("Possible move = ");
                        System.out.println(move_loc);
                    }
                }
                else //Not occupied
                {
                    Position move_loc = new Position(x,y);
                    moves.add(move_loc);
                    System.out.print("Possible move = ");
                    System.out.println(move_loc);
                }

            }
        }
    }
}
