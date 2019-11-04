import java.util.ArrayList;

public class Relay extends Piece
{
    private static final int[][] lower_possibleLocations = new int[][]{{-1,-1},{1,-1},{1,1},{0,1},{-1,1}};
    private static final int[][] upper_possibleLocations = new int[][]{{1,-1},{1,1},{0,-1},{-1,1},{-1,-1}};
    private static final int[][] promoted_lower = new int[][]{{0,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0}};
    private static final int[][] promoted_upper = new int[][]{{1,-1},{0,-1},{-1,-1},{1,0},{-1,0},{0,1}};
    private ArrayList<Position> moves = new ArrayList<>();
    public Relay(String name)
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
        return false;
    }
    public void generateMoves(Board board,Position start)
    {
        moves.clear(); //Clears moves that might have been created in some other move
        int[][] locations;
        if(this.isPromoted())
        {
            if(this.isLower())
            {
                locations = promoted_lower;
            }
            else
            {
                locations = promoted_upper;
            }
        }
        else {
            if (this.isLower()) {
                locations = lower_possibleLocations;
            } else {
                locations = upper_possibleLocations;
            }
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
    public boolean canBePromoted()
    {
        return true;
    }
    public ArrayList<Position> all_moves()
    {
        return this.moves;
    }

}
