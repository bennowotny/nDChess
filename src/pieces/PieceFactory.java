package pieces;

public class PieceFactory {

    private int n;
    private Boolean alliance = null;

    public PieceFactory(int n){
        this.n = n;
    }
    public PieceFactory(int n, boolean alliance){
        this.n = n;
        this.alliance = alliance;
    }

    public Piece createPiece(PieceType t, boolean alliance){
        return new Piece(t, new int[n], alliance);
    }

    public Piece createPiece(PieceType t){
        if(alliance == null) throw new NullPointerException("Current PieceFactory Alliance not set.");
        return new Piece(t, new int[n], alliance);
    }

}
