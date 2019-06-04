package pieces;

import game.Game;

public class Piece {

    private int[] coords;
    private PieceType type;
    private boolean hasMoved = false;
    private boolean alliance;

    protected Piece(PieceType t, int[] coords, boolean alliance) {
        this.coords = coords;
        this.type = t;
        this.alliance = alliance;
    }

    public int[] getCoords() {
        return coords;
    }

    public Piece setCoords(int[] coords) {
        if (coords.length != this.coords.length)
            throw new IllegalArgumentException("Inconsistent dimensionality, should be " + this.coords.length + " dimension.");
        this.coords = coords;
        return this;
    }

    public PieceType getType() {
        return type;
    }

    //TODO: Add collision and en passent logic
    //TODO: GENERAL FIXING AND FINISHING
    //TODO: Separate each check

    private boolean checkKing(int[] delta, Game g) {
        boolean retVal = true;
        if ((type.getLogicalValue() & PieceType.BITS_KING) == PieceType.BITS_KING) //KING logic
            for (int i = 0; i < delta.length; i++) retVal &= Math.abs(delta[i]) == 1;
        else return false;
        return retVal;
    }

    private boolean checkDiagonal(int[] delta, Game g) {
        boolean retVal = true;
        if ((type.getLogicalValue() & PieceType.BITS_DIAG) == PieceType.BITS_DIAG) { //DIAGONAL logic
            int d1d = -1;
            for (int i = 0; i < delta.length; i++) {
                if (d1d == -1 && delta[i] != 0) {
                    d1d = Math.abs(delta[i]);
                    continue;
                }
                if (d1d != -1 && Math.abs(delta[i]) != d1d) {
                    retVal = false;
                    break;
                }
            }
        } else return false;
        return retVal;
    }

    private boolean checkSide(int[] delta, Game g) {
        boolean retVal;
        if ((type.getLogicalValue() & PieceType.BITS_SIDE) == PieceType.BITS_SIDE) { //SIDE logic
            int side_check = 1;
            for (int i = 0; i < delta.length; i++) {
                if (delta[i] != 0) side_check--;
            }
            retVal = side_check == 0;
        } else return false;
        return retVal;
    }

    private boolean checkKnight(int[] delta, Game g) {
        boolean retVal;
        if ((type.getLogicalValue() & PieceType.BITS_KNGT) == PieceType.BITS_KNGT) { //KNIGHT logic
            boolean two = false, one = false;
            for (int i = 0; i < delta.length; i++) {
                if (Math.abs(delta[i]) == 2 && !two) two = true;
                else if (Math.abs(delta[i]) == 1 && !one) one = true;
                else if ((two || one) && delta[i] != 0 || Math.abs(delta[i]) > 2) return false;
            }
            retVal = two & one;
        } else return false;
        return retVal;
    }

    private boolean checkPawn(int[] delta, Game g){
        boolean retVal;
        if((type.getLogicalValue() & PieceType.BITS_PAWN) == PieceType.BITS_PAWN){ //PAWN logic
            int dF = delta[1];
            retVal = dF == 1 || (dF == 2 && !hasMoved);
            for(int i = 0; i < delta.length; i++){
                if(i==1) continue;
                if(Math.abs(delta[i]) == 1){
                    int[] testPos = getCoords();
                    for(int j = 0; j < delta.length; j++){
                        testPos[j] += delta[j];
                    }
                    Piece other;
                    if((other = g.getPiece(testPos)) != null && other.alliance != alliance);
                    else retVal = false;
                }
            }
        }else return false;
        return retVal;
    }

    public boolean checkValidMove(int[] delta, Game g) {
        if (delta.length != coords.length)
            throw new IllegalArgumentException("Invalid dimensionality, should be " + coords.length + " dimension.");
        final int moveAlignment = ((checkKing(delta, g) ? 1 : 0) << 4) | ((checkDiagonal(delta, g) ? 1 : 0) << 3) | ((checkSide(delta, g) ? 1 : 0) << 2) | ((checkKnight(delta, g) ? 1 : 0) << 1) | (checkPawn(delta, g) ? 1 : 0);
        final boolean[] b = {false};
        PieceType.forEach((t)->{
            boolean x = (moveAlignment&t.getLogicalValue())==t.getLogicalValue();
            b[0]^=x;
        });
        if(b[0]){
            for(int i = 0; i < coords.length; i++){
                coords[i]+=delta[i];
            }
            hasMoved = true;
        }
        return b[0];
    }

}
