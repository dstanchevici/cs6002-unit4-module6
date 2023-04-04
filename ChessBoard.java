
import java.util.*;
import java.awt.*;
import javax.swing.*;


public class ChessBoard {

    // Instance variables.
    int size;
    char[][] board;          // board[i][j] == 'X' if there's a queen on it.


    // Constructor.

    public ChessBoard (int size) 
    {
        // Build the board. Initially empty: board[i][j] == 'O'.
        this.size = size;
        board = new char [size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                board[i][j] = 'O';
            }
        }
    }


    public int size ()
    {
        return size;
    }
    

    public String toString ()
    {
        String str = "Chessboard: \n";
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                str += " " + board[i][j];
            }
            str += "\n";
        }
        return str;
    }


    public void addQueen (int row, int col)
    {
        board[row][col] = 'X';
    }
    

    public void removeQueen (int row, int col)
    {
        board[row][col] = 'O';
    }


    public boolean isForbidden (int row, int col)
    {
        // First, try the row.
        for (int c=0; c<size; c++) {
            if (board[row][c] == 'X') {
                return true;
            }
        }

        // Now try the column
        for (int r=0; r<size; r++) {
            if (board[r][col] == 'X') {
                return true;
            }
        }

        // Now the diagonals.
        for (int r=0; r<size; r++) {
            for (int c=0; c<size; c++) {
                if ( (r != row) && (c != col) && (board[r][c] == 'X') ) {
                    // See if (r,c) can be attacked by (row,col).
                    if (Math.abs(r-row) == Math.abs(c-col)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }


    public void display ()
    {
        // Make the frame and set some of its parameters:
        JFrame f = new JFrame ();
        f.setSize (300,300);
        f.setTitle ("N Queens problem");

        // Create our extension of the JPanel:
        ChessPanel drawPanel = new ChessPanel ();

        // We'll need to pass on the data:
        drawPanel.size = size;
        drawPanel.board = board;

        // Add this to the frame. Notice the strange syntax.
        f.getContentPane().add (drawPanel);

        // Bring up the frame.
        f.setVisible (true);
    }

}


class ChessPanel extends JPanel {
    
    int size;
    char[][] board;

    public void paintComponent (Graphics g)
    {
        // This is required.
        super.paintComponent (g);

        // First, compute the minimum dimension. The board size can't be larger.
        Dimension D = this.getSize();
        int minD = D.height;
        if (D.width < minD) {
            minD = D.width;
        }
        
        // This is the size of each square.
        int cellSize = minD / size;

        // We'll draw an image for each queen.
        ImageTool im = new ImageTool ();
        Image queen = im.readImageFile ("queen.jpg");
        
        // Now draw the chessboard.
        boolean isWhite = true;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {

                // Compute top-left.
                int topLeftX = j*cellSize;
                int topLeftY = i*cellSize;

                if (! isWhite) {
                    // For black (gray) squares:
                    g.setColor (Color.gray);
                    g.fillRect (topLeftX, topLeftY, cellSize, cellSize);
                }
                g.setColor (Color.black);
                g.drawRect (topLeftX, topLeftY, cellSize, cellSize);

                // Alternate between black and white, except for end-of-row.
                if (j < size-1) {
                    isWhite = !isWhite;
                }

                if (board[i][j] == 'X') {
                    // Draw the queen image.
                    g.drawImage (queen, topLeftX+1, topLeftY+1, cellSize-2, cellSize-2, null);
                }

            } // inner-for
        } // outer-for

    }

}
