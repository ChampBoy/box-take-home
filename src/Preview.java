import java.util.ArrayList;

public class Preview extends Piece
{
    private ArrayList<Position> moves = new ArrayList<>();
    public Preview(String name)
    {
        super(name);
    }

    @Override
    public boolean canMove(Board board, Position start, Position end) {
        Position p;
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

        return false;
    }
}
