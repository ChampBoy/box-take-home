import java.util.*;
public class Game
{
    private Board board;
    private int moves;
    private boolean isOver;
    private Player lower;
    private Player upper;
    private Player current_player_move;
    private Scanner sc;
    private String last_move;
    public Game(boolean interactive,String file_path)
    {
        this.moves=0;
        this.isOver=false;
        this.lower=new Player("lower");
        this.upper=new Player("UPPER");
        this.current_player_move=lower;
        if(interactive)
        {
            this.board = new Board(true); //board created with default initial pieces
            sc=new Scanner(System.in); //activates scanner for user input
            this.run_game_interactive();
        }
        else
        {
            this.board = new Board();
            this.run_game_file_mode(file_path);
        }


    }
    private void setOver() //game mode is now over
    {
        this.isOver=true;
    }
    private void run_game_interactive()
    {
        while(!isOver)
        {
            if(moves==400)
            {
                System.out.println("Tie game. Too many moves.");
                this.setOver();
            }
            System.out.print(this.board);
            System.out.println();
            upper.print_captured_list();
            lower.print_captured_list();
            System.out.println();
            if(isInCheck()) //check if current player is in check situation
            {
                System.out.println(current_player_move.getName()+" player is in check!");
                System.out.println("Available moves: ");
                ArrayList<String> avail_moves= create_available_moves();
                for(String s : avail_moves) //printing available moves
                {
                    System.out.println(s);
                }
                if(avail_moves.size()==0) //If available moves=0 i.e Checkmate
                {
                    Player other_player = get_other_player(current_player_move);
                    other_player.print_win_message("  Checkmate.");
                    this.setOver();
                    return;
                }

            }
            System.out.print(current_player_move.getName()+"> ");
            String input;
            input=sc.nextLine();
            if(input.equals(" "))
            {
                this.end_game_for_current_player(last_move);
                return;
            }
            last_move=input; //Updating last move, for printing purposes
            System.out.print((current_player_move).getName()+" player action: ");
            System.out.println(last_move);
            String[] split = input.split(" "); //splits user input to identify type of move and positions
            if(split[0].equals("move") && split.length>2)
            {
                make_move(split);
            }
            else if(split[0].equals("drop")&& split.length>2)
            {
                drop_move(split);
            }
            else
            {
                this.end_game_for_current_player(last_move);
                return;
            }
            //change player turns and increment move counter,also if move counter 200 then end
            this.moves++;
            this.current_player_move=get_other_player(this.current_player_move);
        }
    }

    private void make_move(String[] split)
    {
        boolean promote=false;
        if(split.length==4 && split[3].equals("promote"))
        {
            promote=true;
        }

        String initial_position=split[1];
        String final_position=split[2];
        Position initial_p = new Position(initial_position.charAt(0),Character.getNumericValue(initial_position.charAt(1)));
        Position final_p = new Position(final_position.charAt(0),Character.getNumericValue(final_position.charAt(1)));
        if(initial_p.equals(final_p)) //trying to move piece to same position
        {
             this.end_game_for_current_player(last_move);
            return;
        }
        if(!board.isOnMap(initial_p) || !board.isOnMap(final_p) || !board.isOccupied(initial_p)) //checks if move is on the map and initial position is not empty
        {
            this.end_game_for_current_player(last_move);
            return;
        }
        Piece current_piece = board.getPiece(initial_p);
        if(!current_piece.belongsTo(current_player_move)) //If piece to be moved does not belong to current player then illegal
        {
            this.end_game_for_current_player(last_move);
            return;
        }
        if(current_piece.canMove(board,initial_p,final_p)) //If move is legal
        {

            if(promote) //If trying to promote piece
            {
                if((current_player_move.piece_in_promote_row(final_p)||current_player_move.piece_in_promote_row(initial_p)) && current_piece.canBePromoted() && !current_piece.isPromoted())
                {//Can be promoted if final or initial position in promoted row
                    current_piece.Promote();
                }
                else
                {
                    this.end_game_for_current_player(last_move); //illegal move,trying to promote in not promotable row
                    return;
                }
            }
            if(current_player_move.piece_in_promote_row(final_p) && current_piece instanceof Preview && !current_piece.isPromoted()) //Preview piece should be promoted forcibly if in promote row
            {
                current_piece.Promote(); //Force promotion without mentioning promote
            }
            if(!board.isOccupied(final_p)) //if final position not occupied
            {
                board.removePiece(initial_p);
                board.setPiece(final_p,current_piece);
                if(isInCheck()) //if this move caused check on own player then IT IS ILLEGAL AND GAME OVER
                {
                    board.setPiece(initial_p,current_piece);
                    board.removePiece(final_p);
                    this.end_game_for_current_player(last_move);
                }
            }
            else //if final position occupied
            {
                Piece end_piece=board.getPiece(final_p);
                if(end_piece.belongsTo(current_player_move)) //If final spot contained a piece from same player
                {
                    this.end_game_for_current_player(last_move);
                    return;
                }

                end_piece.dePromote(); //handles case if not promoted already
                //If end piece occupied by opponent then :
                end_piece.change_teams();
                current_player_move.capturePiece(end_piece);
                board.removePiece(final_p);
                board.removePiece(initial_p);
                board.setPiece(final_p,current_piece);
                //if this move caused check on own player then IT IS ILLEGAL AND GAME OVER
                if(isInCheck())
                {
                    board.setPiece(initial_p,current_piece);
                    board.setPiece(final_p, end_piece);
                    this.end_game_for_current_player(last_move);
                }
            }
        }
        else //illegal move
        {
            this.end_game_for_current_player(last_move);
        }
    }
    private void drop_move(String[] split)
    {
        Piece to_drop = current_player_move.captured_piece(split[1]);
        if(to_drop==null)
        {
            this.end_game_for_current_player(last_move);
            return;
        }
        Position pos = new Position(split[2].charAt(0),Character.getNumericValue(split[2].charAt(1)));
        if(!board.isOnMap(pos) || board.isOccupied(pos))
        {
            this.end_game_for_current_player(last_move);
            return;
        }
        //check if position is legal to place dropped piece
        if(to_drop instanceof Preview)
        {
            if(current_player_move.piece_in_promote_row(pos)) //If trying to drop in promotion row
            {
                this.end_game_for_current_player(last_move);
                return;
            }
            int temp_y = pos.getY()-1;
            while(temp_y>=0)
            {
                Piece temp=board.getPiece(pos.getX(),temp_y);
                if(temp!=null && temp.belongsTo(current_player_move)&& temp instanceof Preview && !temp.isPromoted())
                {
                    this.end_game_for_current_player(last_move);
                    return;
                }
                temp_y--;
            }
            temp_y=pos.getY()+1;
            while(temp_y<5)
            {
                Piece temp=board.getPiece(pos.getX(),temp_y);
                if(temp!=null && temp.belongsTo(current_player_move)&& temp instanceof Preview && !temp.isPromoted())
                {
                    this.end_game_for_current_player(last_move);
                    return;
                }
                temp_y++;
            }
            //CHECK IF PREVIEW CAUSING CHECKMATE
            board.setPiece(pos,to_drop);
            //Simulate changing player move to check if enemy got a checkmate
            this.current_player_move=get_other_player(this.current_player_move);
            if(isInCheck() && create_available_moves().size()==0)
            {
                board.removePiece(pos);
                this.current_player_move=get_other_player(this.current_player_move);
                this.end_game_for_current_player(last_move);
                return;
            }
            this.current_player_move=get_other_player(this.current_player_move);
        }
        board.setPiece(pos,to_drop);
        current_player_move.remove_captured(to_drop);
    }

    private void run_game_file_mode(String path)
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
                if (this.moves == 400) {
                    System.out.println("Tie game. Too many moves.");
                    this.setOver();
                }
                last_move=move;
                String[] split = move.split(" ");
                if (split[0].equals("move")) {
                    make_move(split);//continue from here
                }
                else if(split[0].equals("drop"))
                {
                    drop_move(split);
                }
                last_move=move;
                if(isOver)
                {
                    return;
                }
                this.current_player_move = get_other_player(this.current_player_move);
                this.moves++;
                    //move ends here
            }
            System.out.print(get_other_player(current_player_move).getName()+" player action: ");
            System.out.println(last_move);
            System.out.println(this.board);
            upper.print_captured_list();
            lower.print_captured_list();
            System.out.println();
            if(isInCheck())
            {
                ArrayList<String> avail_moves= create_available_moves();
                if(avail_moves.size()==0) //last move checkmate check
                {
                    Player other_player = get_other_player(current_player_move);
                    other_player.print_win_message("  Checkmate.");
                    this.setOver();
                    return;
                }
                //If not checkmate then check if moves=400
                if (this.moves == 400) {
                    System.out.println("Tie game.  Too many moves.");
                    this.setOver();
                    return;
                }
                else
                {
                    System.out.println(current_player_move.getName()+" player is in check!");
                    System.out.println("Available moves: ");
                    for(String s : avail_moves)
                    {
                        System.out.println(s);
                    }


                }
            }
            if (this.moves == 400) {
                System.out.println("Tie game.  Too many moves.");
                this.setOver();
            }
            else
            {
                System.out.println(current_player_move.getName()+">");
            }

        }
        catch(Exception e)
        {
            System.out.println("Error with opening filepath");
        }


    }
    private boolean isInCheck() //checks if current player is in check
    {
        Position check_position = board.findDrive(current_player_move);
        Player other_player =get_other_player(current_player_move);
        ArrayList<Position> all_targets= other_player.all_possible_moves(this.board);
        return all_targets.contains(check_position);
    }

    private ArrayList<String> create_available_moves()
    {
        Set<String> save_moves = new LinkedHashSet<>();
        Position current_drive_pos=board.findDrive(current_player_move);
        Piece drive = board.getPiece(current_drive_pos);
        Player other_player =get_other_player(current_player_move);
        ArrayList<Position> all_targets= other_player.all_possible_moves(this.board);
        //First find moves which can move drive
        drive.generateMoves(board,current_drive_pos);
        ArrayList<Position> drive_moves = drive.all_moves();
        for(Position move : drive_moves)
        {
            if(!all_targets.contains(move))
            {
                //simulate moving drive to see if it avoids check
                Piece maybe_enemy=board.getPiece(move);
                if(maybe_enemy!=null && !(maybe_enemy.belongsTo(current_player_move))) //if position contains enemy
                {
                    Piece enemy_piece =board.getPiece(move);
                    board.setPiece(move,drive);
                    board.removePiece(current_drive_pos);
                    if(!isInCheck())
                    {
                        String str="move ";
                        str+=current_drive_pos+" ";
                        str+=move;
                        save_moves.add(str);
                    }
                    board.setPiece(current_drive_pos,drive);
                    board.setPiece(move,enemy_piece);
                }
                else //if position is empty then simulate
                {
                    board.setPiece(move,drive);
                    board.removePiece(current_drive_pos);
                    if(!isInCheck())
                    {
                        String str="move ";
                        str+=current_drive_pos+" ";
                        str+=move;
                        save_moves.add(str);
                    }
                    board.setPiece(current_drive_pos,drive);
                    board.removePiece(move);
                }

            }
        }
        for(int i=0;i<5;i++) //For the case when a piece can come in the way of check or capture piece causing check
        {
            for(int j=0;j<5;j++)
            {
                Piece p_temp=board.getPiece(i,j);
                Position pos = new Position(i,j);
                if(p_temp!=null && p_temp.belongsTo(current_player_move))
                {
                    p_temp.generateMoves(board,pos);
                    ArrayList<Position> all_moves = p_temp.all_moves();
                    for(Position move : all_moves)
                    {
                        Piece maybe_enemy=board.getPiece(move);
                        if(maybe_enemy!=null && !(maybe_enemy.belongsTo(current_player_move)))
                        {
                            Piece enemy_piece =board.getPiece(move);
                            board.setPiece(move,p_temp);
                            board.removePiece(pos);
                            if(!isInCheck())
                            {
                                String str="move ";
                                str+=pos+" ";
                                str+=move;
                                save_moves.add(str);
                            }
                            board.setPiece(pos,p_temp);
                            board.setPiece(move,enemy_piece);
                        }
                        if(all_targets.contains(move))
                        {
                            //Simulate moving the piece and check for check again
                            board.setPiece(move,p_temp);
                            board.removePiece(pos);
                            if(!isInCheck())
                            {
                                String str="move ";
                                str+=pos+" ";
                                str+=move;
                                save_moves.add(str);
                            }
                            board.setPiece(pos,p_temp);
                            board.removePiece(move);
                        }
                    }
                }
            }
        }
        for(Piece p :current_player_move.getCaptured()) //Finding available drop moves
        {
            for(int i=0;i<5;i++)
            {
                for(int j=0;j<5;j++)
                {
                    Position pos =new Position(i,j);
                    if(!board.isOccupied(pos))
                    {
                        board.setPiece(pos,p);
                        if(!isInCheck())
                        {
                            char c=Character.toLowerCase(p.toString().charAt(0));
                            String str="drop "+c+" "+pos;
                            save_moves.add(str);
                        }
                        board.removePiece(pos);
                    }
                }
            }
        }
        ArrayList<String> ans = new ArrayList<>(save_moves);
        Collections.sort(ans); //to match required case
        return ans;
    }
    private Player get_other_player(Player pl)
    {
        if (pl.getName().equals("UPPER"))
        {
            return this.lower;
        }
        else
            return this.upper;
    }
    private void end_game_for_current_player(String last_move)
    {
        System.out.print((current_player_move).getName()+" player action: "); //might be wrong
        System.out.println(last_move);
        System.out.println(this.board);
        upper.print_captured_list();
        lower.print_captured_list();
        System.out.println();
        Player other_player = get_other_player(current_player_move);
        other_player.print_win_message("  Illegal move.");
        this.setOver();
    }
}
