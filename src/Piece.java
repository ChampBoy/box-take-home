public abstract class Piece
{
    private String name;
    private boolean isPromoted;
    private boolean isCaptured;
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
        return Character.isLowerCase(name.charAt(0));
    }
    public boolean isUpper()
    {
        return Character.isUpperCase(name.charAt(0));
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
    }
    public abstract boolean canMove(Board board , Position start ,Position end);


}
