
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
public class Yarn extends GamePiece
{

    public Yarn(RodentGame game)
    {
        super(game);
        this.game=game;


    }
    public boolean canMove(int x, int y)
    {
        if (x>game.grid.length || x<0 ||y>game.grid.length || y<0 )
            return false;
        if(game.get(x,y) instanceof Wall||game.get(x,y) instanceof Cat||game.get(x,y) instanceof MovableWall||game.get(x,y) instanceof Hole||game.get(x,y) instanceof Trap||game.get(x,y) instanceof Yarn)
            return false;
        if(game.distanceTo(new Point(x,y),this)>2)
            return false;

        //movable wall?
        return true;

    }
    public boolean isTrapped()
    {

        int[] posx = {this.getX() + 1, this.getX(), this.getX(), this.getX() - 1, this.getX() + 1, this.getX() - 1, this.getX() - 1, this.getX() + 1};
        int[] posy = {this.getY(), this.getY() + 1, this.getY() - 1, this.getY(), this.getY() + 1, this.getY() - 1, this.getY() + 1, this.getY() - 1};

        for (int i = 0; i < posx.length; i++)
            if (canMove(posx[i], posy[i]))
            {
                return false;
            }

        return true;
    }
    public void eat(int x, int y)
    {
        GamePiece gp;
        gp=game.set(game.set(null,this.getX(),this.getY()), x, y);

        if (gp instanceof Mouse)
        {
            int co[]=((Mouse) gp).respawn();
            ((Mouse) gp).lowerLife();
            game.set(gp,co[0],co[1]);
            gp.setX(co[0]);gp.setY(co[1]);

        }
        this.setX(x);
        this.setY(y);
    }
    public void act()	//moves toward mouse
    {

            Random n =new Random();
            int[] posx = {this.getX() + 1, this.getX(), this.getX(), this.getX() - 1, this.getX() + 1, this.getX() - 1, this.getX() - 1, this.getX() + 1};
            int[] posy = {this.getY(), this.getY() + 1, this.getY() - 1, this.getY(), this.getY() + 1, this.getY() - 1, this.getY() + 1, this.getY() - 1};
            int min = 100;
            int rnx=0;
            int rny=0;

            int i=n.nextInt(7);
            rnx = posx[i];
            rny = posy[i];
            while (!this.canMove(rnx,rny))
            {
                i=n.nextInt(7);
                rnx = posx[i];
                rny = posy[i];
            }

            eat(rnx,rny);

    }
    public String toString()
    {
        return "Yarn";
    }
}

