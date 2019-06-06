package pieces;

public enum PieceType {
    KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN;//Bit Order:King|Diagonal|Sideways|Knight|Pawn

    public static final int BITS_KING = 0b10000;
    public static final int BITS_DIAG = 0b01000;
    public static final int BITS_SIDE = 0b00100;
    public static final int BITS_KNGT = 0b00010;
    public static final int BITS_PAWN = 0b00001;

    interface TypeIterator{
        void forEach(PieceType t);
    }

    public int getLogicalValue(){
        switch(this){
            case KING:
                return 0b10000;
            case QUEEN:
                return 0b01100;
            case BISHOP:
                return 0b01000;
            case KNIGHT:
                return 0b00010;
            case ROOK:
                return 0b00100;
            case PAWN:
                return 0b00001;
        }
        return -1;
    }

    public String getStringValue(){
        switch(this){
            case KING:
                return "K";
            case QUEEN:
                return "Q";
            case BISHOP:
                return "B";
            case KNIGHT:
                return "N";
            case ROOK:
                return "R";
            case PAWN:
                return "";
        }
        return null;
    }

    public static void forEach(TypeIterator t){
        t.forEach(PieceType.KING);
        t.forEach(PieceType.QUEEN);
        t.forEach(PieceType.BISHOP);
        t.forEach(PieceType.KNIGHT);
        t.forEach(PieceType.ROOK);
        t.forEach(PieceType.PAWN);
    }
}
