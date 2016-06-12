package MineSweeper;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.GridLayout;
import java.util.Date;
import java.util.Random;

public class Main 
{
	final static int height = 16, width = 30, numMines = 99;
	
	private static Square[][] squares = new Square[height][width];
	private static int[][] minesData = new int[height][width];
	
	public static void main(String[] args) 
	{	
		for(int i=0; i<height; ++i)
		{
			for(int j=0; j<width; ++j)
			{
				minesData[i][j] = 0;
			}
		}
		
		Random r = new Random( new Date().getTime() );
		for(int k=1; k<=numMines; ++k)
		{
			int i = r.nextInt(height), j = r.nextInt(width);
			if( minesData[i][j] == 0 )
			{	minesData[i][j] = -1;	}
		}
		
		for(int i=0; i<height; ++i)
		{
			for(int j=0; j<width; ++j)
			{
				if(minesData[i][j] != -1)
				{
					int number = 0;
					for(int k=-1; k<=1; ++k)
					{
						for(int l=-1; l<=1; ++l)
						{
							if( i+k>=0 && i+k<height && j+l>=0 && j+l<width 
									&& minesData[i+k][j+l] == -1 )
								
							{	++number;	}
						}
					}
					minesData[i][j] = number;
				}
			}
		}
		
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(height,width));
		for(int i=0; i<height; ++i)
		{
			for(int j=0; j<width; ++j)
			{
				squares[i][j] = new Square(i,j);
				frame.add(squares[i][j]);
			}
		}
		
		frame.setSize(935,520);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static Square getSquare( int row, int col )
	{
		if( row >= 0 && row < height && col >=0 && col < width )
			return squares[row][col];
		else
			return null;
	}
	
	public static int getMineData( int row, int col )
	{	return minesData[row][col];		}
	
	public static void gameOver()
	{
		for(int i=0; i<height; ++i)
		{
			for(int j=0; j<width; ++j)
			{
				Square s = squares[i][j];
				s.removeMouseListener(s);
				if( minesData[i][j] == -1 )
				{
					s.setIcon(new ImageIcon("Images/mine.jpg"));
				}
			}
		}
	}
}