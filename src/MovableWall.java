import java.awt.*;
import java.awt.image.BufferedImage;


public class MovableWall extends GamePiece
{

	
	  public MovableWall(RodentGame game)
	  {
		  super(game);
	  }
	  //Call super to set data 
	   	//Adds the wall Image to images
	   

	    
	  //Overrides getImages: returns the movable wall image 
	    public boolean canMove(int x, int y)	//Overrides canMove: Returns true when the new location is 1 square away up /down/ left/ right and that location stores a null, cheese or another movable wall that can also move in the same direction. Otherwise the method returns false.
	    {
			GamePiece gp;
			int xd,yd;
			xd=x-this.getX();
			yd=y-this.getY();
			gp=game.get(x, y);

			if((game.get(x, y) instanceof Wall) || (game.get(x, y) instanceof Cat))
				return false;

			if (x>game.grid.length || x<1 ||y>game.grid.length || y<1 )
				return false;
			if (gp instanceof MovableWall)//Cheese
			{
				return (gp.canMove(x + xd, y + yd));
			}

			return true;
	    }
	    public void move(int x, int y)	//Overrides move(int x, int y): if can move is true for the given location the moveable wall will move the new location. When the new location has a movable wall that movable wall will be moved before the current movable wall is moved. 
	    {
			GamePiece gp;
			int xd,yd;
			xd=x-this.getX();
			yd=y-this.getY();



			if (canMove(x,y))
			{
				gp= game.set(this, x, y);
				if (gp instanceof MovableWall)//Cheese
				{

					gp.move(x + xd, y + yd);
				}
				if (gp instanceof Cat) {
					//gp.move(x + xd, y + yd);
				}
				this.setX(x);
				this.setY(y);
			}

	    }
	    public  void act()	//Overrides act: has no code
	    {
	    	
	    }
		public String toString()
		{
			return "mWall";
		}
	
}
