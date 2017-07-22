import java.awt.*;

import java.util.Random;


public class Mouse extends GamePiece
{

    public static final int TRAPPED=1;	//Stores 1 and is used for state
    public static final int MOVING=0;	//Stores the number of lives the mouse has left
    public int lives=3;

    public Mouse(RodentGame game)
    {
        super(game);
        state=MOVING;
        this.game=game;


    }
    public int[]respawn()
    {
        int x=3, y=3;
        Random rn = new Random();

        if (lives==0)
            game.setStatus(RodentGame.DEAD);
        else
        {
            x = rn.nextInt(23);
            y = rn.nextInt(23);

            while (game.get(x,y)!=null)
            {
                x = rn.nextInt(23);
                y = rn.nextInt(23);
            }
            //game.set(this,x,y);
        }
        int co[]={x,y};
        return co;
    }
    public String toString()
    {
        return "Mouse";
    }
    //•	Call super to set data
    //	Sets lives to 3
    //Adds the moving mouse image to images

    /*Preforms the following operations:
    •	Subtracts 1 from lives
    •	When lives reaches 0 the game is ended
    •	When lives is 1 or more
    o	Respawns the mouse in a random safe location*/
    public int getLives()
    {
        return lives;
    }
    public boolean canMove(int x, int y)
    {
        GamePiece gp;
        int xd,yd;
        xd=x-this.getX();
        yd=y-this.getY();
        gp=game.get(x,y);

        if(game.get(x,y)instanceof Wall||game.get(x,y)instanceof Cat)//yarn
            return false;
        if (x>game.grid.length || x<0 ||y>game.grid.length || y<0 )
            return false;

        if (gp instanceof MovableWall)
        {

            return gp.canMove(x + xd, y + yd);//if wall can move mouse can

        }



        return true;

    }
    public void lowerLife()
    {
        --lives;
    }
    public void move(int x, int y)
    {
        GamePiece gp;
        int xd,yd;
        xd=x-this.getX();
        yd=y-this.getY();

        if (canMove(x,y))
        {

            state=MOVING;
            gp= game.set(game.set(null,this.getX(),this.getY()), x, y);
            //if (!(gp instanceof Hole))
               // gp.setState(MOVING);


            if (gp instanceof MovableWall)
            {
                gp.move(x + xd, y + yd);

            }
            if (gp instanceof Cheese)
            {
                game.setScore(game.getScore()+50);
            }
            if (gp instanceof Hole)
            {

                int hx=gp.getX();int hy=gp.getY();
                game.addTime();
                state=TRAPPED;
                gp.setState(TRAPPED);
                for (int i=0;i<5;i++)
                    game.update();

            }
            if (gp instanceof Trap)
            {

                int hx=gp.getX();int hy=gp.getY();
                game.set(gp,hx,hy);
                gp.setX(hx);gp.setY(hy);

                int co[] =this.respawn();
                this.lowerLife();
                game.set(this, co[0], co[1]);
                this.setX(co[0]);this.setY(co[1]);

            }
            else
            {
                this.setX(x);
                this.setY(y);
            }
        }
    }

    public void act()
    {

    }
}
