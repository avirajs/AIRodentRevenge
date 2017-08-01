import java.awt.image.BufferedImage;

public class Wall extends GamePiece
{
    public Wall(RodentGame game)
    {
        super(game);
        this.game=game;
    }
    public boolean canMove(int x, int y)
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
