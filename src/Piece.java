import java.util.ArrayList;

public abstract class Piece
{
    private String name;
    private boolean isPromoted;
    public Piece(String name)
    {
        this.name=name;
        this.isPromoted=false;
    }
    public String toString() {
        return name;
    }
    public boolean isLower()
    {
        if(name.length()==1)
        {
            return Character.isLowerCase(name.charAt(0));
        }
        return Character.isLowerCase(name.charAt(1));
    }
    public boolean isUpper()
    {
        if(name.length()==1)
        {
            return Character.isUpperCase(name.charAt(0));
        }
        return Character.isUpperCase(name.charAt(1));
    }
    boolean isPromoted()
    {
        return isPromoted;
    }
    void Promote()
    {
        this.isPromoted=true;
        this.name="+"+this.name;
    }
    public abstract boolean canMove(Board board , Position start ,Position end);
    public abstract boolean canBePromoted();
    public abstract ArrayList<Position> all_moves();
    public abstract void generateMoves(Board board , Position start);
    boolean belongsTo(Player pl)
    {
        if(pl.getName().equals("UPPER") && this.isUpper())
        {
            return true;
        }
        else return pl.getName().equals("lower") && this.isLower();

    }
    void change_teams()
    {
        if(isLower())
        {
            this.name=String.valueOf(Character.toUpperCase(this.name.charAt(0)));
        }
        else
        {
            this.name=String.valueOf(Character.toLowerCase(this.name.charAt(0)));
        }
    }
    void dePromote() //handles case if not promoted already
    {
        if(this.isPromoted)
        {
            this.isPromoted=false;
            this.name = this.name.substring(1);
        }

    }

}
