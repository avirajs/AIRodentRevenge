

public class Cheese extends GamePiece
{
    public Cheese(RodentGame game)
    {
        super(game);

        this.game=game;


    }

    public void act() {

    }

    public boolean canMove(int x, int y) {
        return false;
    }
    public String toString()
    {
        return "Cheese";
    }

}

