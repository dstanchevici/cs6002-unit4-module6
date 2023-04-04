import java.util.*;

public class MazeMaker3 {

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

        // Break a few more walls randomly.
        breakRandomWalls (maze, 10);
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

 
    static void breakRandomWalls (Maze maze, int numWalls)
    {
	for (int k=0; k<numWalls; k++) {
            // Create random coordinates, i.e., identify a random cell.
	    int x = pickRandom (0, maze.numRows-1);
	    int y = pickRandom (0, maze.numCols-1);
	    Coord c = new Coord (x,y);

            // Get its neighbors that are separated by a wall.
	    Coord[] validNeighbors = maze.getClosedNeighbors (c);
	    if (validNeighbors != null) {
                // Pick one and break the wall.
		int m = pickRandom (0, validNeighbors.length-1);
		maze.breakWall (c, validNeighbors[m]);
	    }
	}
    }


    static int pickRandom (int a, int b)
    {
	return a + rand.nextInt (b-a+1);
    }

    static void permute (Coord[] coords)
    {
	// INSERT YOUR CODE HERE:
	
        for (int i=0; i<coords.length-1; i++) {
	    int k = (int) pickRandom (i, coords.length-1);
	    Coord temp = coords[i];
	    coords[i] = coords[k];
	    coords[k] = temp;
	}
    }

}


