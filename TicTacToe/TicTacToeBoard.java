package TicTacToe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

public class TicTacToeBoard extends JComponent implements MouseListener, MouseMotionListener
{
	private static final int MAX_WIDTH = 600;
	private static final int MAX_HEIGHT = 600;
	private static final int CROSS = 1;
	private static final int NOT = 2;
	private static final int EMPTY = 0;
	private static int TURN = 1;
	private boolean gameOver;

	private List<Integer> board;
	private Dimension boardSize, tileSize;
	private MainBoard boardObj;
	private GameLogic gameObj = new GameLogic();

	public TicTacToeBoard(Dimension boardSize, Dimension tileSize)
	{
		super();

		this.boardSize = boardSize;
		this.tileSize = tileSize;
		// this.board = new ArrayList<Integer>();

		resetBoard();
		setSize(boardSize.width * tileSize.width + 100, boardSize.height * tileSize.height + 100);
		setOpaque(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void resetBoard()
	{
		gameOver = false;
		this.board = new ArrayList<>();
		for (int i = 0; i < 9; i++)
		{
			this.board.add(EMPTY);
		}
		repaint();
		if (TURN != 1)
		{
//			Random r = new Random();
//			int i = r.nextInt(9);
//			this.board.set(i, NOT);
			this.board = this.gameObj.getMove(this.board);
			TURN = ~TURN;
			repaint();
		}
	}

	public void setBoardObject(MainBoard b)
	{
		this.boardObj = b;
	}

	public void setTurn(int turn)
	{
		TURN = turn;
	}

	public int getTurn()
	{
		return TURN;
	}

	public void paintComponent(Graphics g)
	{
		if (isOpaque())
		{
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		// g.setColor(Color.GRAY);
		Font myFont = new Font("Courier New", 1, 50);
		g.setFont(myFont);
		int curr = 0;
		int x, y;

		for (int i = 0; i < boardSize.width; i++)
		{
			for (int j = 0; j < boardSize.height; j++)
			{
				g.setColor(new Color(200, 200, 200));

				g.fill3DRect(j * tileSize.width + 1, i * tileSize.height + 1, tileSize.width - 2, tileSize.height - 2,
						true);

				if (this.board.get(curr) == CROSS)
				{
					String str = "X";

					g.setColor(Color.BLACK);
					x = j * tileSize.width + 80;
					y = i * tileSize.height + 80;
					g.drawString(str, x, y);
				} else if (this.board.get(curr) == NOT)
				{
					String str = "O";

					g.setColor(Color.BLACK);
					x = j * tileSize.width + 80;
					y = i * tileSize.height + 80;
					g.drawString(str, x, y);
				}

				curr++;
			}
		}
	}

	public void mousePressed(MouseEvent e)
	{
		int x = e.getX() / (200);
		int y = e.getY() / 120;
		int index = 3 * y + x;

		if (!this.gameOver && this.board.get(index) == EMPTY)
		{
			this.board.set(index, CROSS);
			this.repaint();
			this.gameOver = this.gameObj.isGameOver(this.board);
			if (this.gameOver)
			{
				boardObj.declareWinner(this.gameObj.getWinner());
			}
			TURN = ~TURN;
		}

		if (!this.gameOver && TURN != 1)
		{
			TURN = ~TURN;
			this.board = this.gameObj.getMove(this.board);
			this.repaint();
			this.gameOver = this.gameObj.isGameOver(this.board);
			if (this.gameOver)
			{
				boardObj.declareWinner(this.gameObj.getWinner());
			}
		}

	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseDragged(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseMoved(MouseEvent e)
	{
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

	// public static void main(String[] args)
	// {
	// JFrame mainWindow = new JFrame("Board");
	// TicTacToeBoard tttObj = new TicTacToeBoard(new Dimension(3, 3), new
	// Dimension(200, 120));
	// mainWindow.getContentPane().add(tttObj, BorderLayout.CENTER);
	//
	// // mainWindow.setSize(600,500);
	// mainWindow.pack();
	// mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// mainWindow.setLocationRelativeTo(null);
	// mainWindow.setVisible(true);
	//
	// }

}
