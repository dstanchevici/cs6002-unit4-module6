import java.util.*;

public class MazeMaker2 {

    static int desiredPathLength;
    static Maze maze;
    static Random rand = new Random ();


    public static void main (String[] argv)
    {
        generateMaze (5, 5);
    }


    public static void generateMaze (int numRows, int numCols)
    {
        maze = new Maze (numRows, numCols);

	desiredPathLength = numRows * numCols;

	// Initially, we'll start with the top left cell.
        Coord start = new Coord (0, 0);
	maze.markVisited (start);

        // Generate the maze path recursively.
	boolean found = recursiveGenerate (start, 1);

        if (! found) {
            System.out.println ("Could not create the whole maze");
        }
        maze.display();
    }


    static boolean recursiveGenerate (Coord c, int pathLength)
    {
        // Bottom out condition 1:
	if (pathLength == desiredPathLength) {
	    return true;
	}

        // Bottom out condition 1: see if we're stuck.
	Coord[] validNeighbors = maze.getUnvisitedClosedNeighbors (c);
	if ((validNeighbors == null) || (validNeighbors.length == 0)) {
	    return false;
	}


	// Otherwise, we have some neighbors to explore.
	// Permute the directions randomly.
	permute (validNeighbors);

	for (int i=0; i < validNeighbors.length; i++) {

            // Try neighbor i.
            maze.breakWall (c, validNeighbors[i]);
            maze.markVisited (validNeighbors[i]);
            
	    boolean ok = recursiveGenerate (validNeighbors[i], pathLength+1);
	    if (ok) {
                // If neighbor i worked out, great.
		return true;
	    }

	    // Otherwise, undo assignment to i.
	    maze.fixWall (c, validNeighbors[i]);
	    maze.markUnvisited (validNeighbors[i]);

	} // end-for

	// Couldn't make it work.
	return false;
    }


    static int pickRandom (int a, int b) // Betw a and b inclusive
    {
	return a + rand.nextInt (b-a+1);
    }


    static void permute (Coord[] coords)
    {
	// For each array element from 0 to n-2, pick a random element
	// from those to the right of it, and swap.

        for (int i=0; i<coords.length-1; i++) {
	    int k = (int) pickRandom (i, coords.length-1);
	    Coord temp = coords[i];
	    coords[i] = coords[k];
	    coords[k] = temp;
	}

    }

}
