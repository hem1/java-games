package TicTacToe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameLogic
{
	private static final int X = 1;
	private static final int O = 2;
	private static final int E = 0;
	private int winner = 0;
	private List<List<Integer>> tree = new ArrayList<List<Integer>>();

	public boolean checkRows(List<Integer> board)
	{
		boolean retVal = false;
		int v = 3;
		for (int i = 0; i < 3; i++)
		{
			if (board.get(v * i) != E && board.get(v * i) == board.get(v * i + 1)
					&& board.get(v * i) == board.get(v * i + 2))
			{
				this.winner = board.get(v * i);
				retVal = true;
				break;
			}
		}
		return retVal;
	}

	public boolean checkColumns(List<Integer> board)
	{
		boolean retVal = false;
		for (int i = 0; i < 3; i++)
		{
			if (board.get(i) != E && board.get(i) == board.get(i + 3) && board.get(i) == board.get(i + 6))
			{
				this.winner = board.get(i);
				retVal = true;
				break;
			}
		}
		return retVal;
	}

	public boolean checkDiagonals(List<Integer> board)
	{
		boolean retVal = false;
		if ((board.get(0) != E && board.get(0) == board.get(4) && board.get(0) == board.get(8)))
		{
			winner = board.get(0);

			retVal = true;
		}

		else if ((board.get(2) != E && board.get(2) == board.get(4) && board.get(2) == board.get(6)))
		{
			winner = board.get(2);
			retVal = true;
		}
		return retVal;
	}

	public int getWinner()
	{
		return this.winner;
	}

	public boolean isGameOver(List<Integer> board)
	{
		if (getPossibleMoves(board, O).size() == 0
				& !(checkRows(board) || checkColumns(board) || checkDiagonals(board)))
		{
			winner = 0;
			return true;
		}

		return checkRows(board) || checkColumns(board) || checkDiagonals(board);
	}

	public void printBoard(List<Integer> b)
	{
		List<String> str = new ArrayList<>();
		int index = 0;
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (b.get(index) == 1)
					str.add("X");
				else if (b.get(index) == 2)
					str.add("O");
				else
					str.add("_");
				index++;
			}
			str.clear();
		}
	}

	public List<List<Integer>> getPossibleMoves(List<Integer> b, int p)
	{
		List<List<Integer>> pMoves = new ArrayList<List<Integer>>();

		for (int i = 0; i < b.size(); i++)
		{
			if (b.get(i) == 0)
			{
				List<Integer> cur = new ArrayList<>(b);
				cur.set(i, p);
				pMoves.add(cur);
				cur = new ArrayList<>(b);
			}
		}
		return pMoves;
	}

	public List<Integer> miniMax(List<Integer> b)
	{
		List<List<Integer>> l = new ArrayList<List<Integer>>();
		HashMap<Integer, List<Integer>> hm = new HashMap<>();
		Integer v;
		List<Integer> li = new ArrayList<>();

		l = getPossibleMoves(b, O);
		v = -10000;
		for (List<Integer> t : l)
		{
			if (isGameOver(t))
			{
				if (winner == X)
					v = -1;
				else if (winner == O)
					v = 1;
				else
					v = 0;
			} else
				v = minValue(t);
			hm.put(v, t);
		}

		for (Integer i : hm.keySet())
		{
			v = Math.max(v, i);
		}

		return hm.get(v);

	}

	public int maxValue(List<Integer> b)
	{
		if (isGameOver(b))
		{
			if (winner == X)
				return -1;
			else if (winner == O)
				return 1;
			else
				return 0;
		}
		int v = -100000;
		List<List<Integer>> possibleMoves = new ArrayList<List<Integer>>();
		possibleMoves = getPossibleMoves(b, O);
		tree.addAll(possibleMoves);
		for (int i = 0; i < possibleMoves.size(); i++)
		{
			v = Math.max(v, minValue(possibleMoves.get(i)));
		}
		return v;
	}

	public int minValue(List<Integer> b)
	{
		if (isGameOver(b))
		{

			if (winner == X)
				return -1;
			else if (winner == 0)
				return 1;
			else
				return 0;
		}
		int v = 100000;
		List<List<Integer>> possibleMoves = new ArrayList<List<Integer>>();
		possibleMoves = getPossibleMoves(b, X);

		tree.addAll(possibleMoves);

		for (int i = 0; i < possibleMoves.size(); i++)
		{
			v = Math.min(v, maxValue(possibleMoves.get(i)));
		}
		return v;
	}

	public List<Integer> getMove(List<Integer> b)
	{
		return miniMax(b);
	}

	// public static void main(String[] args)
	// {
	// GameLogic game = new GameLogic();
	// List<Integer> l = new ArrayList<Integer>(Arrays.asList(X, E, X,
	// O, O, X,
	// X, E, O));
	// List<Integer> li = new ArrayList<Integer>(Arrays.asList(X, X, E,
	// E, O, E,
	// E, O, E));
	//// System.out.println(game.isGameOver(li));
	//
	// li = game.getMove(l);
	// System.out.println(li);
	//// System.out.println(game.maxValue(new
	// ArrayList<Integer>(Arrays.asList(1, 1, 2, 2, 2, 1, 1, 2, 2))));
	// }
}
