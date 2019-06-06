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
        testCases.put("N(0,0)-(1,2)", true);
        testCases.put("N(0,0)-(2,1)", true);
        testCases.put("K(0,0)-(1,-1)", true);
        testCases.put("B(0,0)-(5,5)", true);
        testCases.put("B(0,0)-(-3,3)", true);
        testCases.put("R(0,0)-(0,5)", true);
        testCases.put("Q(0,0)-(5,5)", true);
        testCases.put("Q(0,0)-(-3,3)", true);
        testCases.put("Q(0,0)-(0,5)", true);
        PieceFactory pF = new PieceFactory(2, true);
        Game g = new Game(2);

        final double[] good = {0};
        testCases.forEach((s, b)->{
            Piece p = null;
            switch((String)g.tokenize(s)[0]){
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
                case "R":
                    p = pF.createPiece(PieceType.ROOK);
                    break;
                case "Q":
                    p = pF.createPiece(PieceType.QUEEN);
                    break;
            }
            try {
                p.setCoords(g.getStartCoords(g.tokenize(s)));
                assert p.checkValidMove(g.getDelta(g.tokenize(s)), g) == b;
                good[0]++;
            }catch(AssertionError e){
                System.err.println("Error on input \""+s+"\".  Expected Output: "+b);
            }
        });
        System.out.println("Test cases completed.  "+Math.round(good[0]/testCases.size()*1000)/10.+"% Success.");
        System.out.println("Enter moves to check validity. [ENTER] to quit.");
        String in =  null;
        while(!(in = sc.nextLine()).equals("")){
            Piece p = null;
            switch((String)g.tokenize(in)[0]){
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
                case "R":
                    p = pF.createPiece(PieceType.ROOK);
                    break;
                case "Q":
                    p = pF.createPiece(PieceType.QUEEN);
                    break;
            }
            p.setCoords(g.getStartCoords(g.tokenize(in)));
            System.out.println(p.checkValidMove(g.getDelta(g.tokenize(in)), g));
        }
    }
}
