package slide;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Search
{

	private List<Integer> goalState = new ArrayList<Integer>();

	private List<List<Integer>> list = new ArrayList<List<Integer>>();
	private List<List<Integer>> seenList = new ArrayList<List<Integer>>();
	private List<List<Integer>> solution;
	private boolean solutionFound = false;
	private boolean solNotPossible = false;

	List<Point> pairs4 = new ArrayList<Point>();
	List<Point> pairs3 = new ArrayList<Point>();

	public Search()
	{
		// this.currentState.addAll( Arrays.asList(curr));
		// this.goalState.addAll(Arrays.asList(goal));

//		 this.goalState.addAll(Arrays.asList(1,2,3,8,0,4,7,6,5));
		this.goalState.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 0));

		// System.out.println(this.currentState);
//		 System.out.println(this.goalState);

		init();

		 
		// this.currentState = getNextState(this.currentState, this.goalState);
		// System.out.println(this.currentState);
	}

	public void init()
	{
		this.pairs4.add(new Point(0, 8));
		this.pairs4.add(new Point(2, 6));

		this.pairs3.add(new Point(2, 3));
		this.pairs3.add(new Point(5, 6));
	}

	public boolean check4(int x, int y)
	{
		if (this.pairs4.contains(new Point(x, y)) || this.pairs4.contains(new Point(y, x)))
		{
			// System.out.println(x+" "+y);
			return true;
		}
		return false;
	}

	public boolean check3(int x, int y)
	{
		if (this.pairs3.contains(new Point(x, y)) || this.pairs3.contains(new Point(y, x)) || Math.abs(x - y) == 5
				|| Math.abs(x - y) == 7)
		{
			// System.out.println(x+" "+y);
			return true;
		}
		return false;
	}

	public boolean check2(int x, int y)
	{
		if ((x % 2) == (y % 2))
		{
			// System.out.println(x+" "+y);
			return true;
		}

		return false;
	}

	public int getHeuristic(List<Integer> curr)
	{
		int h = 0;

		for (int k = 1; k < curr.size(); k++)
		{
			int i = curr.indexOf(k);
			int j = this.goalState.indexOf(k);
			// System.out.println(i+ " -- "+ j);

			if (i == j)
				continue;

			if (check4(i, j) == true)
			{
				h += 4;
				// System.out.println("Heuristic4 is : "+h);
				continue;
			}

			else if (check3(i, j) == true)
			{
				h += 3;
				// System.out.println("heuristic3 is : "+ h);
				continue;
			}

			else if (check2(i, j) == true)
			{
				h += 2;
				// System.out.println("Heuristic2 is : "+h);
			}

			else
			{
				h += 1;
			}
		}
		return h;
	}

	public List<Integer> swapIndices(int i, int j, List<Integer> curr)
	{
		List<Integer> temp = new ArrayList<Integer>();
		temp.addAll(curr);
		int t = temp.get(i);
		temp.set(i, temp.get(j));
		temp.set(j, t);
		return temp;
	}

	public List<List<Integer>> getPossibleStates(List<Integer> curr)
	{
		List<List<Integer>> possibleStates = new ArrayList<List<Integer>>();
		int x = curr.indexOf(0);

		// check columns
		if (((x + 1) % 3) != 0)
		{
			possibleStates.add(swapIndices(x, x + 1, curr));
		}
		if ((x != 0) && (x % 3) != 0)
		{
			// System.out.println("this");
			possibleStates.add(swapIndices(x, x - 1, curr));
		}

		// check rows
		if ((x - 3) >= 0)
			possibleStates.add(swapIndices(x, x - 3, curr));
		if ((x + 3) < 9)
			possibleStates.add(swapIndices(x, x + 3, curr));

		return possibleStates;
	}

	public ArrayList<Integer> getNextState(List<Integer> curr)
	{
		List<List<Integer>> possibleStates = new ArrayList<List<Integer>>();
		Map<Integer, List<List<Integer>>> tmap = new TreeMap<Integer, List<List<Integer>>>();

		possibleStates = getPossibleStates(curr);
//		 System.out.println("size is "+possibleStates.size());

		for (int i = 0; i < possibleStates.size(); i++)
		{
			int h = getHeuristic(possibleStates.get(i));
//			System.out.println("heuristic is "+h);
			List<List<Integer>> li = new ArrayList<List<Integer>>();

			if (tmap.containsKey(h))
			{
				li.addAll(tmap.get(h));
			}
			li.add(possibleStates.get(i));
			tmap.put(h, li);
			if (h == 0)
				this.solutionFound = true;
		}

		// System.out.println("TMap is "+ tmap);

		Set<Integer> keys = tmap.keySet();
		this.list.clear();

		for (Integer key : keys)
		{
			for (int i = 0; i < tmap.get(key).size(); i++)
			{
//				System.out.println(tmap.get(key).get(i));
				if (!this.seenList.contains(tmap.get(key).get(i)))
					this.list.add(tmap.get(key).get(i));
			}
//			 System.out.println("List is: "+this.list);
		}

		if (this.list.isEmpty())
		{
			this.solNotPossible = true;
			return null;
		}
		// System.out.println(this.list.get(0));
		return (ArrayList<Integer>) this.list.get(0);

	}

	public List<List<Integer>> findSolution(List<Integer> curr)
	{
//		this.currentState.addAll(cur);
		this.solution = new ArrayList<List<Integer>>();
		this.solution.add(curr);
		this.seenList.add(curr);
		while (!this.solutionFound && !this.solNotPossible)
		{
			curr = getNextState(curr);

			this.solution.add(curr);
//			 System.out.println("current "+(curr));
			// System.out.println(this.solution);
			this.seenList.add(curr);
		}
		if (this.solNotPossible)
			return null;
		return this.solution;
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		List<Integer> curr = new ArrayList<>(Arrays.asList(0, 2, 1, 6, 7, 4, 3, 8, 5));
		List<List<Integer>> sol = new ArrayList<List<Integer>>();

		Search obj = new Search();
		sol = obj.findSolution(curr);

		if (sol == null)
			System.out.println("No solution possible");
		else
			System.out.println("Solution size is " + sol.size() + " \n" + sol);

//		 System.out.println("Possible "+obj.getPossibleStates(curr));
	}

}
