/**
 * Class to represent Box Shogi board
 */
public class Board {

    private Piece[][] board;

    private final int BOARD_SIZE = 5;

    public Board(boolean initialized)
    {

        board=new Piece[BOARD_SIZE][BOARD_SIZE];
        this.setPiece(new Position('a',1),new Drive("d"));
        this.setPiece(new Position('e',1),new Notes("n"));
        this.setPiece(new Position('d',1),new Governance("g"));
        this.setPiece(new Position('b',1),new Shield("s"));
        this.setPiece(new Position('c',1),new Relay("r"));
        this.setPiece(new Position('a',2),new Preview("p"));
        this.setPiece(new Position('e',5),new Drive("D"));
        this.setPiece(new Position('a',5),new Notes("N"));
        this.setPiece(new Position('b',5),new Governance("G"));
        this.setPiece(new Position('d',5),new Shield("S"));
        this.setPiece(new Position('c',5),new Relay("R"));
        this.setPiece(new Position('e',4),new Preview("P"));
    }
    public Board()
    {
        board=new Piece[BOARD_SIZE][BOARD_SIZE];
    }

    /* Print board */
    public String toString() {
        String[][] pieces = new String[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece curr = (Piece) board[col][row];
                pieces[col][row] = this.isOccupied(col, row) ? board[col][row].toString() : "";
            }
        }
        return stringifyBoard(pieces);
    }

    boolean isOccupied(int col, int row) {
        return board[col][row] != null;
    }
    boolean isOccupied(Position p)
    {
        return board[p.getX()][p.getY()]!=null;
    }

    private String stringifyBoard(String[][] board) {
        StringBuilder str = new StringBuilder();

        for (int row = board.length - 1; row >= 0; row--) {

            str.append(Integer.toString(row + 1)).append(" |");
            for (int col = 0; col < board[row].length; col++) {
                str.append(stringifySquare(board[col][row]));
            }
            str.append(System.getProperty("line.separator"));
        }

        str.append("    a  b  c  d  e").append(System.getProperty("line.separator"));

        return str.toString();
    }

    private String stringifySquare(String sq) {
        switch (sq.length()) {
            case 0:
                return "__|";
            case 1:
                return " " + sq + "|";
            case 2:
                return sq + "|";
        }

        throw new IllegalArgumentException("Board must be an array of strings like \"\", \"P\", or \"+P\"");
    }
    boolean isOnMap(int x_board, int y_board)
    {
        return x_board >= 0 && y_board >= 0 && x_board < BOARD_SIZE && y_board < BOARD_SIZE;
    }
    boolean isOnMap(Position p)
    {
        return isOnMap(p.getX(),p.getY());
    }
    public Piece getPiece(int x ,int y)
    {
        return this.board[x][y];
    }
    void setPiece(Position p, Piece place)
    {
        this.board[p.getX()][p.getY()]=place; //y starts from 1 not 0
    }
    void removePiece(Position p)
    {
        this.board[p.getX()][p.getY()]=null;
    }
    public Piece getPiece(Position p)
    {
        return this.board[p.getX()][p.getY()];
    }
    Position findDrive(Player p)
    {
        char to_find=' ';
        if(p.getName().equals("UPPER"))
        {
            to_find='D';
        }
        else if(p.getName().equals("lower"))
        {
            to_find='d';
        }
        for(int i =0;i<BOARD_SIZE;i++)
        {
            for(int j=0;j<BOARD_SIZE;j++)
            {
                Piece temp = board[i][j];
                if(temp!=null)
                {
                    if(temp.toString().charAt(0)==to_find)
                    {
                        return new Position(i,j);
                    }
                }
            }
        }
        return null; //Should not reach here if drive is present
    }

}

