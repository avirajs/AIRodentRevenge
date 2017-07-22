import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RodentFrame extends JFrame
{



	public RodentFrame(String frameName,RodentGame gm)
	{
		super(frameName);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
			pack();
	
		RodentPanel p = new RodentPanel(gm);
Insets frameInsets = getInsets();

		int frameWidth = p.getWidth()
			+ (frameInsets.left + frameInsets.right);
		int frameHeight = p.getHeight()
			+ (frameInsets.top + frameInsets.bottom);

		setPreferredSize(new Dimension(frameWidth, frameHeight));
		
setLayout(null);
		add(p);
		pack();		
setVisible(true);
	}
}