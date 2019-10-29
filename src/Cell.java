public class Cell
{
    private int x,y;
    private boolean isOccupied;
    private Piece current_piece;
    public Cell(int x, int y)
    {
        this.x=x;
        this.y=y;
        this.isOccupied=false;
        current_piece=null;
    }
    public boolean isOccupied()
    {
        return this.isOccupied;
    }
    public void putPiece(Piece p)
    {
        this.isOccupied=true;
        this.current_piece=null;
    }
    public Piece getPiece()
    {
        return this.current_piece;
    }
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    public boolean isOnMap()
    {
        return this.x >= 0 && this.y >= 0 && this.x < 5 && this.y < 5;
    }
}
