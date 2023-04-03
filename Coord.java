
public class Coord {

    public int row=-1, col=-1;

    public Coord (int r, int c)
    {
        row = r;
        col = c;
    }
    
    public String toString ()
    {
        return "[" + row + "," + col + "]";
    }
    
}
