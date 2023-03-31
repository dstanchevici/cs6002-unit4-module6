// 4.6.2

public class TowerOfHanoi {

    public static void main (String[] argv)
    {
	// 3-disk puzzle
	System.out.println ("3-Disk solution: ");
	solveHanoi (2, 0, 1);

	// 4-disk puzzle  
	//System.out.println ("4-Disk solution: ");
	//solveHanoi (3, 0, 1);
	
    }

    static void solveHanoi (int n, int i, int j)
    {
	// Base case
	if (n == 0) {
	    // The smallest disk
	    move (0, i, j);
	    return;
	}

	int k = other (i, j);
	solveHanoi (n-1, i, k); // Step 1
	move (n, i, j); // Step 2
	solveHanoi (n-1, k, j); // Step 3
    }

    static void move (int n, int i, int j)
    {
	System.out.println ("=> Move disk# " + n + " from tower " + i + " to tower " + j);
    }

    static int other (int i, int j)
    {
	if ( (i==0 && j==1) || (i==1 && j==0) ) return 2;
	if ( (i==0 && j==2) || (i==2 && j==0) ) return 1;

	return 0;
		
    }

}
