import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;

public class RodentGame
{

		public static final int PLAYING=0;
		public static final int DEAD=1;
		public static final int WIN=2;
		Mouse mouse;
		GamePiece[][] grid;
		ArrayList<Integer> catTimes;
		String[] levelNames;
		int score;
		int time;
		int status;//status of the game
		int level;

			public RodentGame(int level) throws IOException
			{
				status=PLAYING;
				grid=new GamePiece[23][23];
				this.level=level;
				score=0;
				loadLevel(level);

			}
			public void addTime()
			{
				time+=5;
			}
			public ArrayList<Integer> getCatTimes()
			{
				return catTimes;
			}
			public int getTime()
			{
				return time;
			}
		   public GamePiece get(int x, int y)	//Returns the piece at loation (x,y) or null if the location is invalid
		   {
				return grid[y][x];
		   }
		   public int getLevel()	//Returns the current level number
		   {
			   return level;
		   }
	       public GamePiece[][] getGrid()
		   {
			   return grid;
		   }
		   public int nextSpawn()	//Returns a string containing the number of moves until new cats spawn or �NA� if there are no more cats left in the level.
		   {
			   if (this.getCatTimes().size()>0)
			  	 return this.getCatTimes().get(0)-time;
			   else
				   return -1;
		   }
		   public GamePiece set(GamePiece gp, int x, int y)
		   {
			   //if (x>grid.length || x<0 ||y>grid.length || y<0 )//Returns null if (x,y) is out of bounds
				 //  return null;



			   GamePiece replaced;
			   replaced=grid[y][x];

			   grid[y][x]=gp;//The gp is placed in spot (x,y)
			   return replaced;//o	the old value of the location is return 
		   }
		
		//�	When gp is null it will put null in spot (x,y) and return the old value of the location.
		//�	When gp is not null 

		   public Mouse getMouse()	//Returns the mouse
		   {
			   for (int r=0;r<grid.length;r++)
			   {
				   for (int c=0;c<grid.length;c++)
				   {

					   if(grid[r][c] instanceof Mouse)
						   return (Mouse) grid[r][c];
				   }

			   }
			   return null;
		   }
		   public boolean loadLevel(int level)throws IOException	//Loads the given level number using the levelNames array to determine what file should be read in.
		   {
			   String[][] bd = new String[23][23];//initialize
			   //sets up file
			   String name;
			   name="Level_"+level+".txt";
			   Scanner scn= new Scanner((new File(name)));
			   
		
			   int i=0;
			   String []ctimes=new String[5];//string of cat times
			   while(scn.hasNextLine())
			   {
				   if (i<23)
				   {

					   bd[i]=scn.nextLine().split(",");
					   i++;
				   }
				   else
					   ctimes=scn.nextLine().split(",");

			   }

			   //cat times
			   ArrayList<Integer> list = new ArrayList<>();
			   for (String times:ctimes)
			   {
				   System.out.println(Integer.parseInt(times));
				  list.add(Integer.parseInt(times));
			   }

			   catTimes=list;
			   System.out.println(catTimes);
			   
			   
			   //puts objects into array of pieces

			   for (int r=0;r<bd.length;r++)
			   {
				   for (int c=0;c<bd[r].length;c++)
				   {
					   if (bd[r][c].equals("1"))
						   grid[r][c]=new Wall(this);
					   if (bd[r][c].equals("0"))
						   grid[r][c]=null;
					   if (bd[r][c].equals("2"))
						  grid[r][c]= new MovableWall(this);
					   if (bd[r][c].equals("m"))
						   grid[r][c]=new Mouse(this);
					   if (bd[r][c].equals("c"))
						   grid[r][c]=new Cat(this);
					   if (bd[r][c].equals("y"))
						   grid[r][c]=new Yarn(this);
					   if (bd[r][c].equals("t"))
						   grid[r][c]=new Trap(this);
					   if (bd[r][c].equals("o"))
						   grid[r][c] = new Hole(this);
					   if (grid[r][c]!=null) {
						   grid[r][c].setX(c);
						   grid[r][c].setY(r);
					   }
					   System.out.printf("|%s",(grid[r][c]));
					}
				   System.out.println("|");
			   }

			   return true;
		   }
/*
		1 � Wall
		0 � Open Ground
		2 � Movable Wall
		m � Mouse
		y � Yarn
		c � Cat
		d � Dead Mouse
		h � Cheese
		o � Hole
		t � Trap*/

		//Returns true if successful and false if unsuccessful.

		   public void update()//When the status is playing will call update on all items in the grid and take care of any other needed game changes.
		   {
			   time++;
			   //moves cats and dangers
			   ArrayList<GamePiece>pieces=getPieces();
			   ArrayList<Cat>cats=getCats();
			   for (int i=0;i< pieces.size();i++)
			   {
				   pieces.get(i).act();
			   }
			   //gets cats if time limit reached

			   if (this.getCatTimes().size()>0)
				   if(this.getCatTimes().get(0)==time)
				   {
					   catTimes.remove(0);

					   int co[]=(this.getMouse()).respawn();

					   Cat c=new Cat(this);
					   this.set(c, co[0], co[1]);
					   c.setX(co[0]);c.setY(co[1]);
				   }
			   // ready is true because all cats are trapped
			   boolean ready=true;
			   for(Cat cat :cats)
				   if (cat.getState()!=(cat.TRAPPED))
					   ready=false;
			   if(cats.size()==0)
				   ready=false;

			   if (ready)
			   {
				   //turn to cheese
				   for(Cat cat :cats)
					   set(new Cheese(this),cat.getX(),cat.getY());
				   //spawn new cats ahead of time when ready
				  	//
				  	 catTimes.remove(0);
				   int co[]=(this.getMouse()).respawn();

				   Cat c=new Cat(this);
				   this.set(c, co[0], co[1]);
				   c.setX(co[0]);c.setY(co[1]);


			   }
			   ArrayList<Cat>ncats=getCats();
				if (ncats.size()==0)
					this.setStatus(WIN);
			   	if (this.getMouse().getLives()<0)
					this.setStatus(DEAD);

		   }
		   public int getStatus()	//Returns the game�s status
		   {
			   return status;
		   }
		   public ArrayList<GamePiece> getPieces()	//Returns an ArrayList containing all the items in the game�s grid.
		   {
			   ArrayList<GamePiece>a =new ArrayList<>();

			   for (int r=0;r<grid.length;r++)
				   for (int c=0;c<grid.length;c++)
					   if(grid[r][c]!=null)
						   a.add(grid[r][c]);


			   return a;
		   }
		   public void setScore(int score)	//Changes the score to the received value
		   {
			   this.score=score;
		   }
		   public int getScore()	//Returns the score
		   {
			   return score;
		   }
		   public void setStatus(int status)	//Changes the status to the received value
		   {
			   this.status=status;
		   }
		   public ArrayList<Cat> getCats()	//Returns an ArrayList of the Cats in the game�s grid
		   {
			   ArrayList<Cat>cats=new ArrayList<Cat>();

			   for (int r=0;r<grid.length;r++)
				   for (int c=0;c<grid[r].length;c++)
					   if (grid[r][c] instanceof Cat)
							cats.add((Cat) grid[r][c]);

			   return cats;
		   }
		   public int distanceTo(Point p, GamePiece gp)	//Returns the distance from gp to the specified point.
		   {
			   //return (int) Math.hypot(gp.getX() - p.getX(), p.getY() - gp.getY());

			   //using manhattan distance
			   return abs(gp.getX() - (int)p.getX())+abs((int)p.getY() - (gp.getY()));
		   }

}
