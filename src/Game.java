import java.util.Scanner;
public class Game
{
    private Board board;
    private int moves;
    private boolean isOver;
    private Player lower;
    private Player upper;
    private Player current_player_move;
    private boolean interactive;
    private Scanner sc;
    private String file_path;

    public Game(boolean interactive,String file_path)
    {
        this.moves=0;
        this.isOver=false;
        this.lower=new Player("lower");
        this.upper=new Player("UPPER");
        this.current_player_move=lower;
        this.interactive=interactive;
        if(interactive)
        {
            this.board = new Board(true);
            sc=new Scanner(System.in);
            this.run_game_interactive();
        }
        else
        {
            this.board = new Board();
            this.file_path=file_path;
            this.run_game_file_mode(file_path);
        }


    }
    public void setOver()
    {
        this.isOver=true;
    }
    public void run_game_interactive()
    {
        while(!isOver)
        {
            System.out.println(this.board);
            System.out.println("");
            upper.print_captured_list();
            lower.print_captured_list();
            System.out.println(current_player_move.getName()+">");
            //CHECK for CHECK here



            String input="";
            input=sc.nextLine();
            if(input.equals(" "))
            {
                this.end_game_for_current_player();
                return;
            }
            String[] split = input.split(" ");
            if(split[0].equals("move"))
            {
                make_move(split);
                //continue from here
            }
            //change player turns and increment move counter,also if move counter 200 then end
            this.moves++;
            this.current_player_move=get_other_player(this.current_player_move);
            if(moves==200)
            {
                System.out.println("Tie game. Too many moves.");
                this.setOver();
            }
        }
    }

    public void make_move(String[] split)
    {
        String initial_position=split[1];
        String final_position=split[2];
        Position initial_p = new Position(initial_position.charAt(0),Character.getNumericValue(initial_position.charAt(1)));
        Position final_p = new Position(final_position.charAt(0),Character.getNumericValue(final_position.charAt(1)));
//        System.out.print("Start position  ");
//        System.out.println(initial_position);
//        System.out.print("End Position    ");
//        System.out.println(final_position);
        if(!board.isOnMap(initial_p) || !board.isOnMap(final_p))
        {
            System.out.println("Illegal outside map");
            this.end_game_for_current_player();
            return;
        }
        if(!board.isOccupied(initial_p)) //or end
        {
            System.out.println("Illegal occupied by same player");
            this.end_game_for_current_player();
            return;
        }
        Piece current_piece = board.getPiece(initial_p);
        if(!current_piece.belongsTo(current_player_move)) //If piece to be moved does not belong to current player then illegal
        {
            System.out.println("piece does not belong to current player");
            this.end_game_for_current_player();
            return;
        }
        if(current_piece.canMove(board,initial_p,final_p)) //If move is legal
        {
            if(!board.isOccupied(final_p)) //if final position not occupied
            {
                board.removePiece(initial_p);
                board.setPiece(final_p,current_piece);
            }
            else //if final position occupied
            {
                Piece end_piece=board.getPiece(final_p);
                if(end_piece.belongsTo(current_player_move)) //If final spot contained a piece from same player
                {
                    this.end_game_for_current_player();
                    return;
                }
                //If end piece occupied by opponent then :
                end_piece.change_teams();
                current_player_move.capturePiece(end_piece);
                board.removePiece(final_p);
                board.removePiece(initial_p);
                board.setPiece(final_p,current_piece);
            }
            //if this move caused check then IT IS ILLEGAL AND GAME OVER



        }
        else //illegal move
        {
            this.end_game_for_current_player();
            return;
        }
    }

    public void run_game_file_mode(String path)
    {
        try
        {
            Utils.TestCase temp = Utils.parseTestCase(path);
            for(Utils.InitialPosition ipiece: temp.initialPieces) //filling initial positions from file
            {

                Piece p =Utils.create_piece(ipiece.piece);
                Position pos =new Position(ipiece.position);
                board.setPiece(pos,p);
            }
            for(String upper_captured : temp.upperCaptures) //filling provided upper captures
            {
                if(upper_captured.equals(""))
                {
                    break;
                }
                Piece p =Utils.create_piece(upper_captured);
                upper.capturePiece(p);
            }
            for(String lower_captured : temp.lowerCaptures)
            {
                if(lower_captured.equals(""))
                {
                    break;
                }
                Piece p =Utils.create_piece(lower_captured);
                lower.capturePiece(p);
            }
            for(String move : temp.moves)
            {
                if(!isOver) {
                    String[] split = move.split(" ");
                    if (split[0].equals("move")) {
                        make_move(split);
                        //continue from here
                    }
                    this.moves++;
                    this.current_player_move = get_other_player(this.current_player_move);
                    if (moves == 200) {
                        System.out.println("Tie game. Too many moves.");
                        this.setOver();
                    }
                    //move ends here

                }
            }
            System.out.println(this.board);
            upper.print_captured_list();
            lower.print_captured_list();
            System.out.println("");
            System.out.println(current_player_move.getName()+">");

        }
        catch(Exception e)
        {
            System.out.println("Error with opening filepath");
        }




    }
    public Player get_other_player(Player pl)
    {
        if (pl.getName().equals("UPPER"))
        {
            return this.lower;
        }
        else
            return this.upper;
    }
    public void end_game_for_current_player()
    {
        Player other_player = get_other_player(current_player_move);
        other_player.print_win_message("Illegal Move.");
        this.setOver();
    }
}
