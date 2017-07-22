import java.awt.*;

public class Cat extends GamePiece
{

    public static final int TRAPPED=1;
    public static final int MOVING=0;

    public Cat(RodentGame game)
    {
        super(game);
        this.state=MOVING;
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
        if (this.isTrapped())
            this.setState(TRAPPED);
        else
        {
            //finds position nearest mouse
            int[] posx = {this.getX() + 1, this.getX(), this.getX(), this.getX() - 1, this.getX() + 1, this.getX() - 1, this.getX() - 1, this.getX() + 1};
            int[] posy = {this.getY(), this.getY() + 1, this.getY() - 1, this.getY(), this.getY() + 1, this.getY() - 1, this.getY() + 1, this.getY() - 1};
            int min = 100;
            int bestx=0;
            int besty=0;

            for (int i = 0; i < posx.length; i++)
            {
             
                if (min > game.distanceTo(new Point(posx[i], posy[i]), game.getMouse())&&canMove(posx[i], posy[i]))
                {
                    min = game.distanceTo(new Point(posx[i], posy[i]), game.getMouse());
                    bestx = posx[i];
                    besty = posy[i];
                }
            }
            this.setState(MOVING);
            eat(bestx,besty);
        }
    }
    public String toString()
    {
        return "Cat";
    }
}
