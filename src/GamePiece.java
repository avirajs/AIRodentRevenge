import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public abstract class GamePiece
{
    int x;	///Stores the x position of the object
    int y;	//Stores the y position of the object
    int state;	//Stores data about how the is object should behave.
    RodentGame game;	//Stores the game.
    public GamePiece(RodentGame game)	//	Sets game, state, x ,y, and loads images into the images list
    {
        this.game=game;
        //loads images into arraylist images ARRAYLIst
    }

    public abstract void act();	//Method to be overwritten to create object behavior

    public abstract boolean canMove(int x, int y);//Method to be overwritten to determine if the object can move to the given location.


    public int getState()	//Returns the state of the object.
    {
        return state;
    }

    public int getX()	//Returns x.
    {
        return x;
    }
    public int getY()	//Returns y.
    {
        return y;
    }
    public void setX(int x)	//Changes x to the received value.
    {
        this.x=x;
    }
    public void setY(int y)	//Changes y to the received value.
    {
        this.y=y;
    }
    public void setState(int state)	//Changes state to the received value.
    {
        this.state=state;
    }
    public void move(int x, int y)	//Moves the object to the given location if can move is true for the location.
    {

        if (canMove(x,y))
        {
            game.set(this, x, y);
            this.setX(x);
            this.setY(y);
        }

    }

    //Note: Uses game.set



}
