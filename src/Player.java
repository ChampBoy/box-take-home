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
    public void print_win_message(String reason)
    {
        System.out.println(this.name+" players wins."+reason);
    }
    public void print_captured_list()
    {
        System.out.print("Captures "+this.name+": ");
        for(Piece p : captured)
        {
            System.out.print(p+" "); //Remember to change case of captured while adding to this list
        }
        System.out.println("");
    }



}
