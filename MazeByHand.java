
import java.util.*;

public class MazeByHand {

    public static void main (String[] argv)
    {
        Maze maze = new Maze (5, 5);

	// Draw path from (3,4) to (1,1)
	maze.breakWall ( new Coord (3,4), new Coord (2,4) );
	maze.breakWall ( new Coord (2,4), new Coord (1,4) );
	maze.breakWall ( new Coord (1,4), new Coord (1,3) );
	maze.breakWall ( new Coord (1,3), new Coord (1,2) );
	maze.breakWall ( new Coord (1,2), new Coord (1,1) );

	// Create solution path
	LinkedList<Coord> solutionPath = new LinkedList<> ();
	solutionPath.add (new Coord (3,4));
	solutionPath.add (new Coord (2,4));
	solutionPath.add (new Coord (1,4));
	solutionPath.add (new Coord (1,3));
	solutionPath.add (new Coord (1,2));
	solutionPath.add (new Coord (1,1));

	maze.setSolutionPath (solutionPath);
	
        maze.display ();
        
    }

}

