import java.awt.*;

import static java.lang.Math.abs;

public class Cat extends GamePiece
{

    public static final double MIN_TEMPERATURE = 1;
    public static double TEMPERATURE = 1000;
    public static final double COOLING_RATE = 0.02;

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
        System.out.println(TEMPERATURE+"    ");
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
            int rand=getRandom();
            int randx=0;
            int randy=0;
            for (int i = 0; i < posx.length; i++)
            {
                if (min > game.distanceTo(new Point(posx[i], posy[i]), game.getMouse())&&canMove(posx[i], posy[i]))
                {
                    min = game.distanceTo(new Point(posx[i], posy[i]), game.getMouse());
                    bestx = posx[i];
                    besty = posy[i];
                }
            }
            randx=posx[rand];
            randy=posy[rand];
            while(!canMove(randx,randy))
            {
                rand=getRandom();
                randx=posx[rand];
                randy=posy[rand];
            }

            if( acceptanceProbability(min, game.distanceTo(new Point(randx, randy), game.getMouse()) ,TEMPERATURE)> Math.random() )
            {
                eat(randx,randy);
            }
            else
                eat(bestx,besty);
            TEMPERATURE *= 1-COOLING_RATE;

            this.setState(MOVING);

        }
    }
    private int getEnergy(int x, int y, int x1, int y1) {
        return f(x,y,x1,y1);
    }
    private int getRandom() {
        int random = (int )(Math.random() * 8 + 0);
        return random;
    }
    private int f(int x, int y, int x1, int y1){
        return abs(x-x1)+abs(y-y1);
    }
    public double acceptanceProbability(double energy, double newEnergy, double temperature) {

        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }

        // If the new solution is worse, calculate an acceptance probability
        // T is small: we accept worse solutions with lower probability !!!




        return Math.exp((energy - newEnergy) / temperature);
    }
    public String toString()
    {
        return "Cat";
    }
}
