package MineSweeper;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

public class Square extends JLabel implements MouseListener
{
	private static final long serialVersionUID = 1L;
	/*  these numbers can be thought of as representing 
	    enum directions {UP, UP-RIGHT, RIGHT, DOWN-RIGHT, DOWN, DOWN-LEFT, LEFT,
	    UP-LEFT}
	    and is the convention adopted in nextDirection( int relRow, int relCol )
	*/
	private static final int[][] toGoNext = { {6,7,0,1,2}, {7,0,1,2,3}, {0,1,2,3,4},
			{1,2,3,4,5}, {2,3,4,5,6}, {3,4,5,6,7}, {4,5,6,7,0}, {5,6,7,0,1} };
	
	private int row, col;
	private boolean[] traversedInDir = { false, false, false, false, false, false,
			false, false };
	
	public Square( int row, int col )
	{
		this.row = row;
		this.col = col;
		
		addMouseListener( this );
		
		setIcon(new ImageIcon("Images/LightGrey.jpg"));
		setHorizontalTextPosition(SwingConstants.CENTER);
		
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int source = e.getButton();
		switch( source )
		{
			case MouseEvent.BUTTON1:
			{
				removeMouseListener( this );
				
				int minesNearHere = Main.getMineData( row, col );
				if( minesNearHere > 0 )
				{
					setIcon( new ImageIcon( "Images/DarkGrey.jpg" ) );
					setText( Integer.toString(minesNearHere) );
				}
				else if( minesNearHere == 0 )
				{
					setIcon( new ImageIcon( "Images/DarkGrey.jpg" ) );
					//  traverse all surrounding mines that are blank
					for(int i=-1; i<=1; ++i)
					{
						for(int j=-1; j<=1; ++j)
						{
							if( !(i==0 && j==0) )
							{
								Square s = Main.getSquare(row+i, col+j);
								if( s !=  null )
									s.traverseSquares( nextDirection(i,j) );
							}
						}
					}
				}
				else
					Main.gameOver();
				break;
			}
			case MouseEvent.BUTTON3:
			{
				//  mark it with an exclamation mark, or question mark
				break;
			}
			default:
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	public void traverseSquares( int toGetHere )
	{
		setIcon( new ImageIcon("Images/DarkGrey.jpg") );
		
		int minesNearHere = Main.getMineData(row,col);
		if(  minesNearHere > 0 )
		{
			setText( Integer.toString(minesNearHere) );
			return;
		}
		else
		//  it must be 0, because it cannot be -1
		{
			int[] traverseNext = toGoNext[toGetHere];
			for(int i=0; i<traverseNext.length; ++i)
			{
				//  recursively traverse through neighboring squares until we
				//  get a non-zero number, without repetition 
				if( !traversedInDir[ traverseNext[i] ] )
				{
					traversedInDir[ traverseNext[i] ] = true;
					int[] nextCoords = nextDirection( traverseNext[i] );
					Square next = Main.getSquare( row+nextCoords[0], col+nextCoords[1] );
					if( next != null )
						next.traverseSquares( traverseNext[i] );
				}
			}
		}
	}

	private int nextDirection( int relRow, int relCol )
	{
		//  now i and j will both take values 0..2
		int i = relRow+1, j = relCol+1;
		//  the -1 refers to THIS Square, but is never referenced, and so is 
		//  just a placeholder
		int[] direction = { 7, 0, 1, 6, -1, 2, 5, 4, 3 };
		
		return direction[3*i+j];
	}
	
	private int[] nextDirection( int direction )
	{
		int[][] nextDirection = { {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1},
				{0,-1}, {-1,-1} };
		return nextDirection[direction];
	}
}