package pieces;

import game.Game;

public class Piece {

    private int[] coords;
    private PieceType type;
    private boolean hasMoved = false;

    protected Piece(PieceType t, int[] coords) {
        this.coords = coords;
        this.type = t;
    }

    public int[] getCoords() {
        return coords;
    }

    public void setCoords(int[] coords) {
        if (coords.length != this.coords.length)
            throw new IllegalArgumentException("Inconsistent dimensionality, should be " + this.coords.length + " dimension.");
        this.coords = coords;
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
            boolean diag_check = false;
            for (int i = 0; i < delta.length; i++) {
                if (d1d == -1 && delta[i] != 0) {
                    d1d = Math.abs(delta[i]);
                    continue;
                }
                if (d1d != -1 && delta[i] != 0 && delta[i] != d1d) {
                    retVal = false;
                    break;
                } else if (delta[i] == d1d) diag_check = true;
            }
            retVal &= diag_check;
        } else return false;
        return retVal;
    }

    private boolean checkSide(int[] delta, Game g) {
        boolean retVal = true;
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
        boolean retVal = true;
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

    public boolean checkValidMove(String poss, Game g) {
        int[] dest = g.getDestCoords(g.tokeize(poss));
        if (dest.length != coords.length)
            throw new IllegalArgumentException("Invalid dimensionality, should be " + coords.length + " dimension.");
        int[] delta = new int[dest.length];
        for (int i = 0; i < delta.length; i++) delta[i] = dest[i] - coords[i];
        boolean retVal = true;
        if ((type.getLogicalValue() & PieceType.BITS_KING) == PieceType.BITS_KING) //KING logic
            for (int i = 0; i < delta.length; i++) retVal &= Math.abs(delta[i]) == 1;
        if ((type.getLogicalValue() & PieceType.BITS_DIAG) == PieceType.BITS_DIAG) { //DIAGONAL logic
            int d1d = -1;
            boolean diag_check = false;
            for (int i = 0; i < delta.length; i++) {
                if (d1d == -1 && delta[i] != 0) {
                    d1d = Math.abs(delta[i]);
                    continue;
                }
                if (d1d != -1 && delta[i] != 0 && delta[i] != d1d) {
                    retVal = false;
                    break;
                } else if (delta[i] == d1d) diag_check = true;
            }
            retVal &= diag_check;
        }
        if ((type.getLogicalValue() & PieceType.BITS_SIDE) == PieceType.BITS_SIDE) { //SIDE logic
            int side_check = 1;
            for (int i = 0; i < delta.length; i++) {
                if (delta[i] != 0) side_check--;
            }
            retVal = side_check == 0;
        }
        if ((type.getLogicalValue() & PieceType.BITS_KNGT) == PieceType.BITS_KNGT) { //KNIGHT logic
            boolean two = false, one = false;
            for (int i = 0; i < delta.length; i++) {
                if (Math.abs(delta[i]) == 2 && !two) two = true;
                else if (Math.abs(delta[i]) == 1 && !one) one = true;
                else if ((two || one) && delta[i] != 0 || Math.abs(delta[i]) > 2) return false;
            }
            retVal = two & one;
        }
        return retVal;
    }

}
