// 4.6.5

public class TowerOfHanoi3 {

    static int dayCount = 0;

    public static void main (String[] argv)
    {
        // A 5-disk puzzle:
	solveHanoi (4, 0, 1);
    }

    static void solveHanoi (int n, int i, int j)
    {
	// Bottom-out.
	if (n == 0) {
            // The smallest disk.
	    move (0, i, j);
	    return;
	}

	int k = other (i, j);
	solveHanoi (n-1, i, k);    // Step 1.
	move (n, i, j);            // Step 2.
	solveHanoi (n-1, k, j);    // Step 3.
    }


    static void move (int n, int i, int j)
    {
        // INSERT YOUR CODE HERE.
        // Before printing, convert n=0 to 'A', n=1 to 'B' etc.

	// ASCII code for 'A' is 65.
	char nCh = (char) (n + 65);
	System.out.println ("Day " + dayCount + " - use disk " + nCh);
	
	dayCount ++;
	
    }
   


    static int other (int i, int j)
    {
	if ( (i==0 && j==1) || (i==1 && j==0) ) return 2;
	if ( (i==0 && j==2) || (i==2 && j==0) ) return 1;

	return 0;
    }

}
