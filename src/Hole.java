
import java.awt.image.BufferedImage;


public class Hole extends GamePiece
{
    public static final int TRAPPED=1;	//Stores 1 and is used for state
    public static final int MOVING=0;	//Stores the number of lives the mouse has left
    public Hole(RodentGame game)
    {

        super(game);
        state=MOVING;
        this.game=game;


    }

    public void act() {

    }

    public boolean canMove(int x, int y)
    {
        return false;
    }
    public String toString()
    {
        return "Hole";
    }

}


