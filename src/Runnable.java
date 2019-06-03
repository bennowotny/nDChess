import game.Game;
import pieces.Piece;
import pieces.PieceFactory;
import pieces.PieceType;

import java.util.HashMap;
import java.util.Scanner;

public class Runnable {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Running standard checks...");
        HashMap<String, Boolean> testCases = new HashMap<>();
        testCases.put("N(1,2)", true);
        testCases.put("N(2,1)", true);
        PieceFactory pF = new PieceFactory(2);
        Game g = new Game(2);

        final double[] good = {0};
        testCases.forEach((s, b)->{
            Piece p = null;
            switch((String)g.tokeize(s)[0]){
                case "":
                    p = pF.createPiece(PieceType.PAWN);
                    break;
                case "K":
                    p = pF.createPiece(PieceType.KING);
                    break;
                case "N":
                    p = pF.createPiece(PieceType.KNIGHT);
                    break;
                case "B":
                    p = pF.createPiece(PieceType.BISHOP);
                    break;
                case "Q":
                    p = pF.createPiece(PieceType.QUEEN);
                    break;
            }
            try {
                assert p.checkValidMove(s, g) == b;
                good[0]++;
            }catch(AssertionError e){
                System.err.println("Error on input \""+s+"\".  Expected Output: "+b);
            }
        });
        System.out.println("Test cases completed.  "+good[0]/testCases.size()*100+"% Success.");
        System.out.println("Enter moves to check validity. [ENTER] to quit.");
        String in =  null;
        while(!(in = sc.nextLine()).equals("")){
            Piece p = null;
            switch((String)g.tokeize(in)[0]){
                case "":
                    p = pF.createPiece(PieceType.PAWN);
                    break;
                case "K":
                    p = pF.createPiece(PieceType.KING);
                    break;
                case "N":
                    p = pF.createPiece(PieceType.KNIGHT);
                    break;
                case "B":
                    p = pF.createPiece(PieceType.BISHOP);
                    break;
                case "Q":
                    p = pF.createPiece(PieceType.QUEEN);
                    break;
            }
            System.out.println(p.checkValidMove(in, g));
        }
    }
}
