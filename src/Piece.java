import java.util.ArrayList;

public abstract class Piece
{
    private String name;
    private boolean isPromoted;
    private boolean isCaptured; //see if u use this
    public Piece(String name)
    {
        this.name=name; //CHECK FOR ILLEGAL PIECE NAME ADDED
        this.isPromoted=false;
        this.isCaptured=false;
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
    public boolean isPromoted()
    {
        return isPromoted;
    }
    public boolean isCaptured()
    {
        return isCaptured;
    }
    public void Capture()
    {
        this.isCaptured=true;
    }
    public void Promote()
    {
        this.isPromoted=true;
        this.name="+"+this.name;
    }
    public abstract boolean canMove(Board board , Position start ,Position end);
    public abstract boolean canBePromoted();
    public abstract ArrayList<Position> all_moves();
    public abstract void generateMoves(Board board , Position start);
    public boolean belongsTo(Player pl)
    {
        if(pl.getName().equals("UPPER") && this.isUpper())
        {
            return true;
        }
        else if (pl.getName().equals("lower") && this.isLower())
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public void change_teams()
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
    public void dePromote() //handles case if not promoted already
    {
        if(this.isPromoted)
        {
            this.isPromoted=false;
            this.name = this.name.substring(1);
        }

    }

}
