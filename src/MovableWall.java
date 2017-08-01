import java.awt.*;
import java.awt.image.BufferedImage;


public class MovableWall extends GamePiece
{

	public MovableWall(RodentGame game)
	{
		super(game);
	}


	/*Overrides canMove: Returns true when the new location is 1 square away up /down/ left/ right and that
	 location stores a null, cheese or
	 another movable wall that can also move in the same direction. Otherwise the method returns false.*/
	public boolean canMove(int x, int y)
	{
		GamePiece gp;
		gp=game.get(x, y);

		//directional vectors
		int xd,yd;
		xd=x-this.getX();
		yd=y-this.getY();

		if((game.get(x, y) instanceof Wall) || (game.get(x, y) instanceof Cat))
			return false;
		if (x>game.grid.length || x<1 ||y>game.grid.length || y<1 )
			return false;
		if (gp instanceof MovableWall)//Cheese
			return (gp.canMove(x + xd, y + yd));

		return true;
	}

	/*Overrides move(int x, int y): if can move is true for the given location the
	 moveable wall will move the new location. When the new location has a movable wall
	 that movable wall will be moved before the current movable wall is moved.*/
	public void move(int x, int y)
	{
		GamePiece gp;
		int xd,yd;

		//d vectors to keep directional info
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
	public  void act()
	{

	}
	public String toString()
	{
		return "mWall";
	}

}
