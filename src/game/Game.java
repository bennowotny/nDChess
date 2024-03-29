package game;

import pieces.Piece;
import pieces.PieceFactory;
import pieces.PieceType;

import java.nio.channels.IllegalSelectorException;
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

    public void placePiece(PieceFactory pF, PieceType t, int[] coords){
        int ind = 0;
        for(int i = 0; i<coords.length; i++){
            ind += coords[i]*Math.pow(8,i);
        }
        board[ind] = pF.createPiece(t).setCoords(coords);
    }

    public void placePiece(PieceFactory pF, PieceType t, boolean alliance, int[] coords){
        int ind = 0;
        for(int i = 0; i<coords.length; i++){
            ind += coords[i]*Math.pow(8,i);
        }
        board[ind] = pF.createPiece(t,alliance).setCoords(coords);
    }

    public void movePiece(int[] startCoords, int[] endCoords){
        int ind = 0;
        Piece temp;
        for(int i = 0; i<startCoords.length; i++){
            ind += endCoords[i]*Math.pow(8,i);
        }
        temp = board[ind];
        board[ind] = null;
        for(int i = 0; i<endCoords.length; i++){
            ind += endCoords[i]*Math.pow(8,i);
        }
        board[ind] = temp;
    }

    public Object[] tokenize(String query){
        String[] st = query.replaceAll("(\\s|\\)|-|x)","").split("(\\(|,)");
        Object[] tokens = new Object[st.length+1];
        tokens[0] = st[0];
        tokens[1] = query.contains("x");
        for(int i = 2; i < tokens.length; i++) tokens[i] = Integer.parseInt(st[i-1]);
        return tokens;
    }

    public int[] getDestCoords(Object[] tokens){
        Object[] tkncoords = Arrays.copyOfRange(tokens,2, tokens.length);
        int[] coords = new int[n];
        for(int i = n; i < tkncoords.length; i++) coords[i-n] = (int) tkncoords[i];
        return coords;
    }

    public int[] getStartCoords(Object[] tokens){
        Object[] tkncoords = Arrays.copyOfRange(tokens,2, tokens.length);
        int[] coords = new int[n];
        for(int i = 0; i < n; i++) coords[i] = (int) tkncoords[i];
        return coords;
    }

    public int[] getDelta(Object[] tokens){
        int[] s = getStartCoords(tokens);
        int[] e = getDestCoords(tokens);
        int[] delta = new int[n];
        for(int i = 0; i < s.length; i++)delta[i] = e[i]-s[i];
        return delta;
    }

    public boolean movePiece(String query){
        Object[] tokens = tokenize(query);
        Piece p;
        if(!(p = getPiece(getStartCoords(tokens))).getType().getStringValue().equals(tokens[0]))
            throw new IllegalArgumentException("Incorrect PieceType, moving piece should be: "+getPiece(getStartCoords(tokens)).getType().getStringValue());
        if(p.checkValidMove(getDelta(tokens), this)){
            p.setCoords(getDestCoords(tokens));
            movePiece(getStartCoords(tokens), getDestCoords(tokens));
        }
        return false;
    }
}
