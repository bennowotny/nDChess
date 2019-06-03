package game;

import pieces.Piece;

import java.util.Arrays;

public class Game {

    private Piece[] board;
    private int n;

    public Game(int n){
        board = new Piece[(int)Math.pow(8,n)];
        this.n = n;
    }

    public Piece getPiece(int[] coords){
        if(coords.length != n) throw new IllegalArgumentException("Inconsistent dimensionality.  Should be "+n+" dimension.");
        int ind = 0;
        for(int i = 0; i<coords.length; i++){
            ind += coords[i]*Math.pow(8,i);
        }
        return board[ind];
    }

    public Object[] tokeize(String query){
        String[] st = query.replaceAll("(\\s|\\))","").split("(\\(|,)");
        Object[] tokens = new Object[st.length];
        tokens[0] = st[0];
        for(int i = 1; i < tokens.length; i++) tokens[i] = Integer.parseInt(st[i]);
        return tokens;
    }

    public int[] getDestCoords(Object[] tokens){
        Object[] tkncoords = Arrays.copyOfRange(tokens,1, tokens.length);
        int[] coords = new int[tkncoords.length];
        for(int i = 0; i < tkncoords.length; i++) coords[i] = (int) tkncoords[i];
        return coords;
    }
}
