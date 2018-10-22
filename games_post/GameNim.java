/*
 * Dana Huget, V00860786
 * October 22, 2018
 * CSC 421 A2 Question 1 Solution
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class GameNim extends Game {

    int WinningScore = 10;
    int LosingScore = -10;

    public GameNim() {
        currentState = new StateNim();
    }

    public boolean isWinState(State state) {
        StateNim tstate = (StateNim) state;

        if (tstate.pile == 1) {
            return true;
        }

        return false;
    }

    public boolean isStuckState(State state) {
        // there is always a winner in Nim
        return false;
    }

    public Set<State> getSuccessors(State state)
    {
        if(isWinState(state) || isStuckState(state))
            return null;

        Set<State> successors = new HashSet<>();
        StateNim tState = (StateNim) state;

        StateNim successor_state;

        for (int i = 1; i <= 3; i++) {
            if (tState.pile - i > 0) {
                successor_state = new StateNim(tState);
                successor_state.pile = tState.pile - i;
                successor_state.player = (state.player==0 ? 1 : 0);

                successors.add(successor_state);
            }
        }

        return successors;

    }

    public double eval(State state)
    {
        if(isWinState(state)) {
            //player who made last move
            int previous_player = (state.player==0 ? 1 : 0);

            if (previous_player==0) //computer wins
                return WinningScore;
            else //human wins
                return LosingScore;
        }

        return 0;
    }

    public static int checkValidMove(BufferedReader in) throws IOException {
        int move = Integer.parseInt( in.readLine() );
        while (true) {
            if (move >= 1 && move <= 3) {
                //valid move
                break;
            } else {
                System.out.print("Invalid move, please choose 1, 2, or 3 > ");
                move = Integer.parseInt( in.readLine() );
            }
        }
        return move;
    }
    public static void main(String[] args) throws Exception {

        Game game = new GameNim();
        Search search = new Search(game);
        int depth = 12;

        //needed to get human's move
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            StateNim nextState = null;

            switch ( game.currentState.player ) {
                case 1: //Human

                    //get human's move
                    System.out.print("How many *valid* coins do you want to take > ");
                    int move = checkValidMove(in);

                    nextState = new StateNim((StateNim)game.currentState);
                    nextState.player = 1;
                    nextState.pile -= move;
                    System.out.printf("You took %d coins, the pile has %s left\n", move, nextState);
                    break;

                case 0: //Computer

                    nextState = (StateNim)search.bestSuccessorState(depth);
                    nextState.player = 0;
                    System.out.printf("Computer took some coins, the pile has %s left\n", nextState);
                    break;
            }

            game.currentState = nextState;
            //change player
            game.currentState.player = (game.currentState.player==0 ? 1 : 0);

            //Who wins?
            if ( game.isWinState(game.currentState) ) {

                if (game.currentState.player == 1) //i.e. last move was by the computer
                    System.out.println("Computer wins!");
                else
                    System.out.println("You win!");

                break;
            }
        }
    }
}
