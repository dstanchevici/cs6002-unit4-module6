import java.util.*;

public class MazeMaker4 {

    static int desiredPathLength;
    static Maze maze;
    static Random rand = new Random ();

    public static void main (String[] argv)
    {
	    
        generateMaze (5, 5);
	if (maze != null) {
            // We will seek a path from the top-left to the bottom-right corner.
	    Coord start = new Coord (0,0);
	    Coord end = new Coord (4,4);
	    solveMaze (maze, start, end);
	    maze.setSolutionPath (solutionPath);    // Pass the path into the GUI.
	    printSolutionPath ();                   // Print to terminal.
	    maze.display ();
	}
	else {
	    System.out.println ("Maze creation did not work");
	}

    }


    // A path is a list of cells, i.e., a list of Coord instances.
    static LinkedList<Coord> solutionPath;


    static void solveMaze (Maze maze, Coord start, Coord end)
    {
        // We'll mark visited cells as we go along our path. Initially:
	maze.markAllUnvisited ();

        // Mark the start cell as visited.
	maze.markVisited (start); // Was it not done in generateMaze()?

        // Create the list.
	solutionPath = new LinkedList<Coord> ();

        // Recursively find the path and fill in coord's into the list.
	recursivelyFindPath (maze, start, end);
    }


    static boolean recursivelyFindPath (Maze maze, Coord c, Coord end)
    {
        // First add the current cell into the path.
	solutionPath.add (c);

        // If we've reached the end, we're done.
	if ( (c.row == end.row) && (c.col == end.col) ) {
	    return true;
	}

        // Otherwise, let's find a neighbor to explore.
	Coord[] validNeighbors = maze.getUnvisitedOpenNeighbors (c);

	if (validNeighbors == null) {
            // If there aren't any, we're done, but couldn't find a path.
	    return false;
	}

	// Take the first one and explore from there.
	maze.markVisited (validNeighbors[0]);

	return recursivelyFindPath (maze, validNeighbors[0], end);
    }


    //////////////////////////////////////////////////////////////
    // 
    // Maze generation code 


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
                //int m = UniformRandom.uniform (0, validNeighbors.length-1);
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
        for (int i=0; i<coords.length-1; i++) {
	    int k = (int) pickRandom (i, coords.length-1);
	    Coord temp = coords[i];
	    coords[i] = coords[k];
	    coords[k] = temp;
	}
    }

    static void printSolutionPath ()
    {	
	int stepCount = 1;
	for (int i=0; i<solutionPath.size()-1; i++) {
	    System.out.println (stepCount + ": Move from " + solutionPath.get(i) + " to " +  solutionPath.get(i+1));
	    stepCount++;
	}
    }

}
