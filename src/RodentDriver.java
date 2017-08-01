import java.io.IOException;

/**
 * Created by Avi on 9/24/2015.
 */
public class RodentDriver
{
    public static void main(String[] args) throws IOException {
        RodentGame g= new RodentGame(1);
        RodentFrame f=new RodentFrame("Rodent's Revenge",g);
    }
}
