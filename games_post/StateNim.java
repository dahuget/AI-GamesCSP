/*
 This particular version of Nim has a pile of
 13 coins on the table, starts with human as player 1
 */
public class StateNim extends State {
    public int pile;

    public StateNim() {
        pile = 13;
        player = 1;
    }

    public StateNim(StateNim state) {
        this.pile = state.pile;
        player = state.player;
    }
    public String toString() {

        return String.valueOf(pile);
    }
}