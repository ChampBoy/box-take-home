# BoxShogi
BoxShogi is basically a modified version of the game Shogi.
The project is written in Java 8. 
Compile the code by : 
"javac Driver.java"

There are 2 game modes present in the code
1) Interactive : 
Executed by "java Driver -i"
2 Players Upper and lower can continuously take turns and play their moves until one player
get checkmate or each player has played 200 turns in which case it will be a tie.

2) File Mode :
Executed by "java Driver -f {filepath}"
Include filepath without the "{}"
Allows the program to read initial map settings,initial captures and moves from
the file provided.

## System Design

The game uses different classes which can be found in the src folder.
Like Player,Piece,Board,Game.
Different pieces extended the abstract Piece class.
The main game is run inside the Game class.

## Performance

Since the board is only 5X5, max moves is 400 and there are not a lot of piece,
Even a brute force approach to finding pieces on the board, or simulating piece moves/drops
for getting out of checks does not affect the program run time a lot.

## Optimizations for the future

Future optimizations might include the following things :

1)Breaking the code down into even more functions (Especially when trying to compute available moves from inside check)

2)Creating a hashmap for each player which would store locations of their pieces (This would help in reducing a lot of performance time).

3)Finding a better approach towards dropping/moving pieces to get out of check

Creating a hashmap alone would create a huge difference in time if the game is expanded to
a lot of blocks or lot more pieces.
 
