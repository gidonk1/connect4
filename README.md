# connect4
Play connect4 against your friends or the computer! 

## Getting Started
No prerequisites - just clone the project.
\
Adjust the depth the AI searches in the game tree in the 'ComputerPlayer' class, and simply start the 
application by running 'main'.

## Implementation
Built using Java.
\
\
Graphics made using the Java Swing API.
\
\
Computer AI finds best move using minimax algorithm with alpha-beta pruning. The heuristic evaluation method 
counts number of possible 4-in-a-rows each player can still make and weights them accordingly. Weights of 
possible '4-in-a-rows' (+ for comp and - for opp) are as follows:
* 1000 if possible '4-in-a-row' has 4 of same colour
* 100 if possible '4-in-a-row' has 3 of same colour
* 10 if possible '4-in-a-row' has 2 of same colour
* 1 if possible '4-in-a-row' has 1 of same colour

## Author
Gidon Katten
