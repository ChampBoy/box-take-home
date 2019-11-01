import java.util.ArrayList;

public class Player
{
    private String name;
    private ArrayList<Piece> captured;
    private Position Drive_position;
    public Player(String name)
    {
        this.name=name;
        this.captured=new ArrayList<>();
    }
    public String getName()
    {
        return this.name;
    }
    public ArrayList<Piece> getCaptured()
    {
        return captured;
    }
    public void capturePiece(Piece p)
    {
        this.captured.add(p);
    }
    public void setDrivePosition(Position p)
    {
        Drive_position=p;
    }
    public Position getDrive_position()
    {
        return Drive_position;
    }
    public void remove_captured(Piece p)
    {
        captured.remove(p);
    }



}
