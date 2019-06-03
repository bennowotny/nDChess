package pieces;

public class PieceFactory {

    private int n;

    public PieceFactory(int n){
        this.n = n;
    }

    public Piece createPiece(PieceType t){
        return new Piece(t, new int[n]);
    }

}
