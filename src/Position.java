public class Position
{
    private char x;
    private int y;
    private int x_int;
    public Position(char x ,int y)
    {
        this.x=x;
        this.y=y;
        if(x=='a')
        {
            x_int=0;
        }
        else if(x=='b')
        {
            x_int=1;
        }
        else if(x=='c')
        {
            x_int=2;
        }
        else if(x=='d')
        {
            x_int=3;
        }
        else if(x=='e')
        {
            x_int=4;
        }
        else
        {
            System.out.println("Illegal Move");
        }
    }
    public Position(int x,int y)
    {
        this.y=y;
        this.x_int=x;
    }
    public int getX()
    {
        return this.x_int;
    }
    public int getY()
    {
        return this.y;
    }
    @Override
    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }
        if(!(o instanceof Position))
        {
            return false;
        }
        Position p = (Position) o;
        return (x_int==p.getX() && y==p.getY());
    }
}
