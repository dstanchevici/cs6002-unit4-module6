import java.util.*;

public class MazeMaker5 {

    static int desiredPathLength;
    static Maze maze;
    static Random rand = new Random ();

    //static int solutionFound = 0;
    //static int deadEndCount;
    static int recursionNum = -1;
    
    public static void main (String[] argv)
    {
	//int experimentNum = 1000;
	//int deadEndTotal = 0;
	// Repeat for experiments
	//for (int i=0; i<experimentNum; i++) {
	//deadEndCount = 0;
	    
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
	    
	    //deadEndTotal += deadEndCount;
	    //} // end-for experiments
	    //System.out.println ("In " + experimentNum + " experiments, there were " + (deadEndTotal/experimentNum) + " on average.");
    }


    // A path is a list of cells, i.e., a list of Coord instances.
    static LinkedList<Coord> solutionPath;


    static void solveMaze (Maze maze, Coord start, Coord end)
    {	
        // We'll mark visited cells as we go along our path. Initially:
	maze.markAllUnvisited ();

        // Mark the start cell as visited.
	maze.markVisited (start);

        // Create the list.
	solutionPath = new LinkedList<Coord> ();

        // Recursively find the path and fill in coord's into the list.
	recursivelyFindPath (maze, start, end);

        // The start node gets added at the very end. Why?
        solutionPath.addFirst (start);
    }


    static boolean recursivelyFindPath (Maze maze, Coord c, Coord end)
    {
	recursionNum++;
	System.out.println ("-------------------------------");
	System.out.println ("Recursion #=" + recursionNum);
	System.out.println ("c=" + c);


	
        // If we've reached the end, we're done.
	if ( (c.row == end.row) && (c.col == end.col) ) {
	    
	    ////// Explore recursions
	    System.out.println ("(c.row == end.row): " + (c.row == end.row) + "(c.col == end.col): " + (c.col == end.col));
	    ///////

	    return true;
	}

        // Otherwise, let's find a neighbor to explore.
	Coord[] validNeighbors = maze.getUnvisitedOpenNeighbors (c);
	
	
	if (validNeighbors == null) {
            // If we couldn't find any neighbors to explore, we're stuck.

	    /////////
	    System.out.println ("validNeighbors == nul: " + (validNeighbors == null));
	    ////////
	    
	    return false;
	}


	//////////////
	// Explore recursion
	System.out.print ("validNeighbors: ");
	for (Coord validNeighbor: validNeighbors) {
	    System.out.print (validNeighbor + " ");
	}
	System.out.println ();
	/////////////

        // Try each neighbor, as many as needed.

	for (int i=0; i<validNeighbors.length; i++) {

            // Try neighbor i.
	    maze.markVisited (validNeighbors[i]);

	    ///////////////
	    System.out.println ("Entered for-loop. i=" + i);
	    /////////////
	    
	    boolean found = recursivelyFindPath (maze, validNeighbors[i], end);

	    if (found) {

		//////
		System.out.println ("After the recursive call(s) inside iteration " + i + " " + validNeighbors[i] + " is ready to be added. Return true.");
		/////
		
                // Notice that we add this to the front of the list, and only
                // after the solution has been found up to here.
		solutionPath.addFirst (validNeighbors[i]);
		return true;
	    }
            
            // If neighbor i didn't work out, un-do the visit and continue.
	    ///////////
	    System.out.println (validNeighbors[i] + " did not work. Make it Unvisited and move to next");
	    //////////
	    
	    maze.markUnvisited (validNeighbors[i]);

	    // Count the number of dead ends hit by the search.
	    //deadEndCount++;
	}

        // If we reached here, then none of the neighbors worked out.
	///////////
	System.out.println ("After the for-loop, none of the neighbors worked. Return false.");
	//////////
	
	return false;
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
