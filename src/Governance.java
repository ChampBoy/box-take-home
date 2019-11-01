import java.util.ArrayList;

public class Governance extends Piece
{
    private ArrayList<Position> moves = new ArrayList<>();
    public Governance(String name)
    {
        super(name);
    }
    public void generateMoves(Board board , Position start)
    {
        moves.clear();
        int x =start.getX();
        int y =start.getY();
        int temp_x=x+1;
        int temp_y=y-1;
        while(temp_x<5 && temp_y>=0)
        {
            boolean obstruction=add_move(board,temp_x,temp_y);
            if(obstruction)
            {
                break;
            }
            temp_x++;
            temp_y--;
        }
        temp_x=x-1;
        temp_y=y+1;
        while(temp_x>=0 && temp_y<5)
        {
            boolean obstruction=add_move(board,temp_x,temp_y);
            if(obstruction)
            {
                break;
            }
            temp_x--;
            temp_y++;
        }
        temp_x=x-1;
        temp_y=y-1;
        while(temp_x>=0 && temp_y>=0)
        {
            boolean obstruction=add_move(board,temp_x,temp_y);
            if(obstruction)
            {
                break;
            }
            temp_x--;
            temp_y--;
        }
        temp_x=x+1;
        temp_y=y+1;
        while(temp_x<5 && temp_y<5)
        {
            boolean obstruction=add_move(board,temp_x,temp_y);
            if(obstruction)
            {
                break;
            }
            temp_x++;
            temp_y++;
        }
    }

    @Override
    public boolean canMove(Board board, Position start, Position end)
    {
        generateMoves(board,start);
        for(Position p : moves)
        {
            if(end.equals(p))
            {
                return true;
            }
        }
        System.out.println("Illegal Move,printing from inside Governance piece");
        return false;

    }
    public boolean add_move(Board board,int x ,int y)
    {
        if(!board.isOnMap(x,y))
        {
            return false;
        }
        if(board.isOccupied(x,y))
        {
            Piece end_piece = board.getPiece(x,y);
            if(!((this.isLower() && end_piece.isLower())|| (this.isUpper() && end_piece.isUpper())))
            {
                Position move_loc = new Position(x,y);
                moves.add(move_loc);
                return true;
            }
        }
        else
        {
            Position move_loc = new Position(x,y);
            moves.add(move_loc);
            return false;
        }
        return false;
    }
}
