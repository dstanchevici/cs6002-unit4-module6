// 4.6.3

import java.util.*;

public class TowerOfHanoi2 {

    static Stack<Integer>[] towers;
    
    public static void main (String[] argv)
    {
	// 3-disk puzzle
	System.out.println ("3-Disk solution: ");
	solveHanoi (2, 0, 1);

	// 4-disk puzzle  
	System.out.println ("4-Disk solution: ");
	solveHanoi (3, 0, 1);
	
    }

    
    static void solveHanoi (int n, int i, int j)
    {
	towers = new Stack [3];
	for (int k=0; k<3; k++) {
	    towers[k] = new Stack<>();
	}

	// Put disks n...0 on stack 0
	for (int k=n; k>=0; k--) {
	    towers[0].add (k); // Should it be push(k)?
	}
	
	printTowers ();

	solveHanoiRecursive (n, i, j);
    }

	
    static void solveHanoiRecursive (int n, int i, int j)
    {
	// Base case
	if (n == 0) {
	    // The smallest disk
	    move (0, i, j);
	    return;
	}

	int k = other (i, j);
	solveHanoiRecursive (n-1, i, k); // Step 1
	move (n, i, j); // Step 2
	solveHanoiRecursive (n-1, k, j); // Step 3
    }

    static void move (int n, int i, int j)
    {
	
	Integer disk = towers[i].pop ();
	
	towers[j].push (disk);
	
	System.out.println ("Towers after moving " + n + " from tower " + i + " to tower " + j);
	printTowers ();
	
    }

    static int other (int i, int j)
    {
	if ( (i==0 && j==1) || (i==1 && j==0) ) return 2;
	if ( (i==0 && j==2) || (i==2 && j==0) ) return 1;

	return 0;
		
    }


    static void printTowers ()
    {
	for (int i=0; i<towers.length; i++) {
	    System.out.print ("Tower " + i + ": ");
	    if ( ! towers[i].isEmpty() ) {
		for (Integer I: towers[i]) {
		    System.out.print (" " + I);
		}
	    }
	    System.out.println ();
	}
    }

}
