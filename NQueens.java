
import java.awt.*;
import java.util.*;


public class NQueens {

    static ChessBoard board;


    public static void main (String[] argv)
    {
        solveQueens (3, 4);
    }


    static void solveQueens (int numQueens, int size)
    {
        board = new ChessBoard (size);

        boolean solutionExists = solve (numQueens, 0);

        if (! solutionExists) {
            System.out.println ("No solution for " + numQueens + " on a " + size + " x " + size + " board");
            return;
        }

        System.out.println ("Solution found for " + numQueens + " on a " + size + " x " + size + " board");

        System.out.println (board);
        board.display();
    }
    

    static boolean solve (int numQueens, int whichCol)
    {
        // Bottom-out condition 1:
        if (numQueens == 0) {
            // None to assign - done.
            return true;
        }
        

        // Bottom-out condition 2:
        if (whichCol >= board.size()) {
            // No columns left to try: done.
            return false;
        }
        
        // Try every un-forbidden spot in each row.
        for (int row=0; row<board.size(); row++) {
            if ( ! board.isForbidden(row,whichCol) ) {

                // Try this location.
                board.addQueen (row, whichCol);
                
                boolean solutionExists = solve (numQueens-1, whichCol+1);

                if (solutionExists) {
                    return true;
                }

                // Else, un-do
                board.removeQueen (row, whichCol);
            }

        }
        
        // Couldn't find a solution.
        return false;
    }

}
