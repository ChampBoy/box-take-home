import java.util.*;
public class Drive extends Piece
{
    private static final int[][] possibleLocations = new int[][]{{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0}};
    private ArrayList<Position> moves = new ArrayList<>();
    public Drive(String name)
    {
        super(name);
    }

    @Override
    public boolean canMove(Board board, Position start, Position end) {
        generateMoves(board,start);
        for(Position p : moves)
        {
            if(end.equals(p))
            {
                return true;
            }
        }
        System.out.println("Illegal Move,printing from inside drive piece");
        return false;

    }
    public void generateMoves(Board board,Position start)
    {
        moves.clear(); //Clears moves that might have been created in some other move
        for(int i=0;i<possibleLocations.length;i++)
        {
            int x= start.getX()+possibleLocations[i][0];
            int y= start.getY()+possibleLocations[i][1];
            if(board.isOnMap(x,y))
            {
                if(board.isOccupied(x,y))
                {
                    Piece end_piece = board.getPiece(x,y);
                    if(!((this.isLower() && end_piece.isLower())|| (this.isUpper() && end_piece.isUpper())))
                    {
                        Position move_loc = new Position(x,y);
                        moves.add(move_loc);

                    }
                }
                else //Not occupied
                {
                    Position move_loc = new Position(x,y);
                    moves.add(move_loc);

                }

            }

        }
    }
}
