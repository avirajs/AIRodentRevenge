import java.awt.image.BufferedImage;

public class Wall extends GamePiece
{

    public Wall(RodentGame game)
    {
        super(game);

        this.game=game;


    }
    //•	Call super to set data
    //	Sets lives to 3
    //Adds the moving mouse image to images





    /*Preforms the following operations:
    •	Subtracts 1 from lives
    •	When lives reaches 0 the game is ended
    •	When lives is 1 or more
    o	Respawns the mouse in a random safe location*/

    public boolean canMove(int x, int y)	//Overrides canMove: Returns true when the new location is 1 square away up /down/ left/ right and that location stores a null, cheese, trap, hole or a movable wall that can also move in the same direction. Otherwise the method returns false.
    {

        return false;

    }


    public void act()	//Overrides act: has no code
    {

    }
    public String toString()
    {
        return "wall";
    }
}
