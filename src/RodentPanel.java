import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class RodentPanel extends JPanel implements KeyListener
{

	private BufferedImage wall 	= null;
	private BufferedImage hole 	= null;
	private BufferedImage mouse = null;
	private BufferedImage catm 	= null;
	private BufferedImage catt 	= null;
	private BufferedImage mwall = null;
	private BufferedImage dirt 	= null;
	private BufferedImage ground 	= null;
	private BufferedImage cheese	= null;
	private BufferedImage mhole	= null;
	private BufferedImage trap	= null;
	private BufferedImage yarn	= null;
	RodentGame gm;
	private static final long serialVersionUID = 1L;

		public RodentPanel(RodentGame gm)
		{
			super();
			this.gm=gm;
			setSize(1100, 920);// width,height
			addKeyListener(this);
		}

		public void paint(Graphics g)//paints game world to screen
		{
			// fills in a black background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());

			// draws the directions to the screen;
			g.setColor(Color.CYAN);
			g.drawString("Use w,a,s or d to move the box", 100, 350);
			try
			{

				wall = ImageIO.read((new File("Rodents Revenge pics//Wall.png")));
				catm=(ImageIO.read((new File("Rodents Revenge pics//Cat_Moving.png"))));//0
				catt=(ImageIO.read((new File("Rodents Revenge pics//Cat_Trapped.png"))));
				dirt=(ImageIO.read((new File("Rodents Revenge pics//Dirt.png"))));
				mouse=(ImageIO.read((new File("Rodents Revenge pics//Mouse_Moving.png"))));
				mwall=(ImageIO.read((new File("Rodents Revenge pics//Movable_Wall.png"))));
				ground=(ImageIO.read((new File("Rodents Revenge pics//Floor.gif"))));
				cheese=(ImageIO.read((new File("Rodents Revenge pics//Cheese.png"))));
				hole=(ImageIO.read((new File("Rodents Revenge pics//Hole.png"))));
				mhole=(ImageIO.read((new File("Rodents Revenge pics//Hole With Mouse.png"))));
				yarn=(ImageIO.read((new File("Rodents Revenge pics//Yarn.png"))));
				trap=(ImageIO.read((new File("Rodents Revenge pics//Trap.png"))));

				System.out.println("All images were loaded Properly.");
			}
			catch(Exception e)
			{
				System.out.println("Error Loading Images: " + e.getMessage());
			}


			paintGame(g);
		}
		public void paintGame(Graphics g)
		{

			GamePiece[][] grid=gm.getGrid();
			//ground
			g.setColor(Color.BLUE);
			g.fill3DRect(920, 0, 180, 920, true);
			for (int r=0;r<grid.length;r++)
				for (int c=0;c<grid.length;c++)
						g.drawImage(ground,c * 40, r*40, null);

			//scores
			g.setColor(Color.YELLOW);
			g.drawString("Level " + String.valueOf(gm.getLevel()), 960, 50);
			g.drawString("Score " + String.valueOf(gm.getScore()), 960, 70);
			g.drawString("Time " + String.valueOf(gm.getTime()), 960, 90);
			g.drawString("Lives " + String.valueOf(gm.getMouse().getLives()), 960, 110);
			if (gm.nextSpawn()==-1)
				g.drawString("Next Spawn " + "None", 960, 130);
			else
				g.drawString("Next Spawn " + gm.nextSpawn(), 960, 130);
			//pieces
			for (int r=0;r<grid.length;r++)
			{
				for (int c=0;c<grid.length;c++)
				{
					if(grid[r][c] instanceof Cheese)
						g.drawImage(cheese, c * 40, r*40, null);
					if(grid[r][c] instanceof Yarn)
						g.drawImage(yarn, c * 40, r*40, null);
					if(grid[r][c] instanceof Trap)
						g.drawImage(trap, c * 40, r*40, null);
					if(grid[r][c] instanceof Mouse)
						if (gm.getMouse().getState()==gm.getMouse().MOVING)
							g.drawImage(mouse, c * 40, r*40, null);
						else
							g.drawImage(mhole, c * 40, r * 40, null);

					if(grid[r][c] instanceof Cat)
					{
						if(grid[r][c].getState()==Cat.TRAPPED)
							g.drawImage(catt, c * 40, r * 40, null);
						else
							g.drawImage(catm, c * 40, r * 40, null);
					}
					if(grid[r][c] instanceof  MovableWall)
						g.drawImage(mwall, c * 40, r*40, null);
					if(grid[r][c] instanceof Wall)
						g.drawImage(wall, c * 40, r*40, null);
					if(grid[r][c] instanceof Hole)
					{
						g.drawImage(hole, c * 40, r * 40, null);
					}
					if (grid[r][c]==null)
						g.drawImage(ground,c * 40, r*40, null);

					System.out.printf("|%s", (grid[r][c]));

				}
				System.out.println("|");
			}


		}

		public void keyPressed(KeyEvent e)
		{
			
		}

		public void keyTyped(KeyEvent e)
		{
			// gets the typed key from the user
			char key = e.getKeyChar();
			Mouse m=gm.getMouse();
			// moves the mouse if a,s,d,w was typed
			if(key=='a')
				m.move(m.getX()-1,m.getY());
			if (key=='d')
				m.move(m.getX()+1,m.getY());
			if (key=='w')
				m.move(m.getX(),m.getY()-1);
			if (key=='s')
				m.move(m.getX(),m.getY()+1);


			gm.update();
			try {
				reset();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			repaint();
		}

		public void keyReleased(KeyEvent e)
		{

		}
		
		// This method is being overridden
		// It is called when the frame finishes adding the panel
		public void addNotify()
		{
			// call super so the method still does what it was built to do
			super.addNotify();
			
			// requests focus so that key inputs are sent to this screen
			requestFocus();
		}

	public void reset() throws IOException//	Creates a new game
	{
		int lev=gm.getLevel();
		int score=gm.getScore();

		if(gm.getStatus()==gm.WIN)
		{

			gm = new RodentGame(lev+1);
			gm.setScore(score+1);
		}
		if(gm.getStatus()==gm.DEAD)
		{
			gm = new RodentGame(lev);
			gm.setScore(score);
		}
	}





}