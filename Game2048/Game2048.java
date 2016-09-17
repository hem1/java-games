package Game2048;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Game2048 extends JComponent implements KeyListener
{
	private static final int MAX_WIDTH = 600;
	private static final int MAX_HEIGHT = 600;

	private List<Point> possibleTiles = new ArrayList<Point>(); // list of
																// possible tile
																// moves
	private boolean tilesMoved = true; // checks if the tiles have moved or not,
										// if tiles not moved donot generate
										// a new number

	private Dimension boardSize, tileSize;
	private Color selectedColor = Color.gray;
	private Point tilePos = new Point(0, 0); // Point var to hold the position
												// of tile to be generated at
	public static int score = 0;
	private int[][] board = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

	public Game2048(Dimension boardSize, Dimension tileSize)
	{
		super();
		this.boardSize = boardSize;
		this.tileSize = tileSize;
		setSize(boardSize.width * tileSize.width, boardSize.height * tileSize.height);
		setOpaque(true);
		this.addKeyListener(this);
		// updatePossibleMoves();
	}

	public void paintComponent(Graphics g)
	{
		if (isOpaque())
		{ // paint background
			g.setColor(Color.orange); // paint the board orange
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		for (int i = 0; i < boardSize.width; i++)
		{
			for (int j = 0; j < boardSize.height; j++)
			{
				g.setColor(Color.black);
				// draw squares
				g.draw3DRect(i * tileSize.width, j * tileSize.height, tileSize.width - 1, tileSize.height - 1, false);
			}

		}

		g.setColor(selectedColor);
		g.setFont(new Font("TimesRoman", Font.BOLD, 30));

		for (int i = 0; i < boardSize.height; i++)
		{
			for (int j = 0; j < boardSize.width; j++)
			{
				if (board[i][j] != 0)
				{
					g.setColor(selectedColor);
					// fill the square with gray color where the tile is
					// generated
					g.fill3DRect(j * tileSize.width + 1, i * tileSize.height + 1, tileSize.width - 2,
							tileSize.height - 2, true);
					g.setColor(Color.black);
					// draw the number in the square
					g.drawString(Integer.toString(board[i][j]), j * tileSize.width + 50 - 15,
							i * tileSize.height + 40 + 10);

				}
			}

		}

		// updatePossibleMoves();
		// System.out.println("Size of poss "+possibleTiles.size());
	}

	//////////////////////////////////////////////////
	// update board //
	//////////////////////////////////////////////////

	// update possible moves left after the number is generated
	public void updatePossibleMoves()
	{
		for (int i = 0; i < boardSize.width; i++)
		{
			for (int j = 0; j < boardSize.height; j++)
			{
				if (board[i][j] != 0 && possibleTiles.contains(new Point(i, j)))
				{
					possibleTiles.remove(new Point(i, j));
				}
				if (board[i][j] == 0 && !possibleTiles.contains(new Point(i, j)))
				{
					possibleTiles.add(new Point(i, j));
				}

			}
		}
	}

	// update possible moves

	// add a tile to the board
	public void addTile(int val)
	{
		this.board[tilePos.x][tilePos.y] = val;

//		 System.out.println(Arrays.deepToString(this.board));
//		for (int i = 0; i < 4; i++)
//		{
//			System.out.println(Arrays.toString(this.board[i]));
//		}

		repaint();
	}

	// generate tile randomly and also random numbers(2,4)
	public void generateTile()
	{
		updatePossibleMoves();

		if (this.tilesMoved == true)
		{
			Random rand = new Random();
			int pos = rand.nextInt(possibleTiles.size());
			// int val = rand.nextInt(2) + 1;
			int val = Math.random() < 0.9 ? 2 : 4; 
			tilePos = possibleTiles.get(pos);
//			System.out.println(tilePos);
			addTile(val);
			this.tilesMoved = false;
		}
		
		// donot generate tiles if the board is full and the tiles has not moved
		else if (this.tilesMoved == false && possibleTiles.size() == 0)
		{
			System.out.println("GAME OVER!!!!!!!!!");
		}
	}

	// slide all the tiles to the left
	public void slideTiles()
	{
		for (int i = 0; i < boardSize.height; i++)
		{
			ArrayList<Integer> myList = new ArrayList<>();
			boolean added = false; // check if two tiles have added to a single
									// tile
			for (int j = 0; j < boardSize.width; j++)
			{
				if (board[i][j] != 0)
				{
					if (!myList.isEmpty() && board[i][j] == myList.get(myList.size() - 1) && added == false)
					{
						// int k = myList.get(myList.size()-1) + board[i][j];
						myList.remove(myList.size() - 1);
						myList.add(2 * board[i][j]);
						score += 2 * board[i][j];
						added = true;
					} else
					{
						myList.add(board[i][j]);
						added = false;
					}

					if (myList.size() < j + 1)
					{
						// System.out.println("Tiles did move ...." + j);
						this.tilesMoved = true;
					}

				}
				// else myList.add(board[i][j]);
			}

			for (int j = 0; j < boardSize.width; j++)
			{
				if (j < myList.size())
				{
					board[i][j] = myList.get(j);

				} else
				{
					board[i][j] = 0;
				}
			}
		}
		System.out.println("Score is " + score);
	}

	// rotate the board clockwise
	public void rotateBoard()
	{
		int n = 4;
		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < 2; j++)
			{
				int temp = board[i][j];
				board[i][j] = board[j][n - 1 - i];
				board[j][n - 1 - i] = board[n - 1 - i][n - 1 - j];
				board[n - 1 - i][n - 1 - j] = board[n - 1 - j][i];
				board[n - 1 - j][i] = temp;
			}
		}
	}

	//////////////////////////////////////////
	// KeyEvent //
	//////////////////////////////////////////

	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();

		switch (keyCode)
		{
		case KeyEvent.VK_LEFT:
		{
			slideTiles();
			generateTile();
			break;
		}
		case KeyEvent.VK_UP:
		{
			rotateBoard();
			slideTiles();
			rotateBoard();
			rotateBoard();
			rotateBoard();
			generateTile();
			break;
		}
		case KeyEvent.VK_DOWN:
		{
			rotateBoard();
			rotateBoard();
			rotateBoard();
			slideTiles();
			rotateBoard();
			generateTile();
			break;
		}
		case KeyEvent.VK_RIGHT:
		{
			rotateBoard();
			rotateBoard();

			slideTiles();
			rotateBoard();
			rotateBoard();
			generateTile();
			break;
		}
		}
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(boardSize.width * tileSize.width, boardSize.height * tileSize.height);
	}
	public Dimension getMinimumSize()
	{
		return new Dimension(boardSize.width * tileSize.width, boardSize.height * tileSize.height);
	}
	public Dimension getMaximumSize()
	{
		return new Dimension(MAX_WIDTH, MAX_HEIGHT);
	}

	public static void main(String[] args)
	{
		JFrame mainWindow = new JFrame("Tile Board");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game2048 board = new Game2048(new Dimension(4, 4), new Dimension(100, 80));
		board.setFocusable(true);
		mainWindow.getContentPane().add(board);
		mainWindow.pack();
		mainWindow.setLocationRelativeTo(null);

		mainWindow.setVisible(true);
		board.updatePossibleMoves();
		board.generateTile();

	}

}
