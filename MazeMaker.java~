import java.util.*;

public class MazeMaker {

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

        // We want to generate a path of this length:
	desiredPathLength = numRows * numCols;

	// Initially, we'll start with the top left cell and mark that as visited.
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
	    // Done. We've created a maze path of the desired length.
	    return true;
	}

        // Bottom out condition 2: see if there's a neighbor to visit.
	Coord[] validNeighbors = maze.getUnvisitedClosedNeighbors (c);
	if ((validNeighbors == null) || (validNeighbors.length == 0)) {
            // There's no neighbor whose wall we can break. So quit trying.
	    return false;
	}

        // Otherwise, pick a random neighbor and proceed.
	int i = pickRandom (0, validNeighbors.length-1);

        // Break the wall and mark the neighbor as visited.
        maze.breakWall (c, validNeighbors[i]);
        maze.markVisited (validNeighbors[i]);
            
        // Recurse.
        return recursiveGenerate (validNeighbors[i], pathLength+1);
    }


    static int pickRandom (int a, int b)
    {
	return a + rand.nextInt (b-a+1);
    }

}
