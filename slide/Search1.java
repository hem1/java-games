package slide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.attribute.Size2DSyntax;

public class Search1 {
	private LinkedHashMap<List<Integer>,List<Integer>> fringe = new LinkedHashMap<List<Integer>,List<Integer>>();
	private LinkedHashMap<List<Integer>,List<Integer>> seenList = new LinkedHashMap<List<Integer>,List<Integer>>();
	
	public List<List<Integer>> searchDFS(List<Integer> startingState, Search s)
	{
		List<Integer> currState = startingState;
		List<Integer> currVal = new ArrayList<Integer>() ;
		List<Integer> finalState = new ArrayList<Integer>();
		List<List<Integer>> possibleStates = new ArrayList<List<Integer>>();
		List<List<Integer>> solution = new ArrayList<List<Integer>>();
		this.seenList.clear();
		boolean solved = false;
		
		HashMap<List<Integer>, List<Integer>> hm = new HashMap<>();
		LinkedList<HashMap<List<Integer>, List<Integer>>> llFringe = new LinkedList<HashMap<List<Integer>, List<Integer>>>();
		
		hm.put(currState, null);
		llFringe.addLast(hm);
		hm = new HashMap<>();
		
		while(llFringe.size()>0 & !solved)
		{
			for(List<Integer> key: llFringe.getLast().keySet())
				currState = key;
			for(List<Integer> key: llFringe.getLast().values())
				currVal = key;
			
			possibleStates = s.getPossibleStates(currState);
			llFringe.removeLast();
			seenList.put(currState, currVal);
			
			if(s.getHeuristic(currState) == 0)
			{
				finalState = currState;
				solved = true;
			}

			for(int i =0 ; i< possibleStates.size();i++)
			{
				if ( !seenList.containsKey(possibleStates.get(i)) )
					{
						hm.put(possibleStates.get(i), currState);
						llFringe.addLast(hm);
						hm = new HashMap<>();
					}
			}
		}
		
		
		List<Integer> parent = seenList.get(finalState);
		solution.add(finalState);
		System.out.println("Seen "+ seenList.size());

		while(parent != null)
		{
			solution.add(0, parent);
			finalState = parent;
			parent = seenList.get(finalState);
		}
		if(solution.get(0).isEmpty()) return null;
//		System.out.println(solution);
		return solution;
	}
	
	public List<List<Integer>> searchBFS(List<Integer> startingState, Search s)
	{
		List<Integer> currState = startingState;
		List<Integer> currVal = new ArrayList<Integer>() ;
		List<Integer> finalState = new ArrayList<Integer>();
		List<List<Integer>> possibleStates = new ArrayList<List<Integer>>();
		List<List<Integer>> solution = new ArrayList<List<Integer>>();
		boolean solved = false;
		
		fringe.put(currState, null);
	
//		System.out.println(possibleStates);
//		System.out.println("Fringe "+fringe);
		
		while(fringe.entrySet().iterator().hasNext() & !solved)
		{
			currState = fringe.entrySet().iterator().next().getKey();
			currVal = fringe.entrySet().iterator().next().getValue();
			
			possibleStates = s.getPossibleStates(currState);
			fringe.remove(currState);
			seenList.put(currState, currVal);
			
			if(s.getHeuristic(currState) == 0)
			{
				finalState = currState;
				solved = true;
			}

			for(int i =0 ; i< possibleStates.size();i++)
			{
				if ( !seenList.containsKey(possibleStates.get(i)) )
					{
						fringe.put(possibleStates.get(i), currState);
					}
			}
		}

		List<Integer> parent = seenList.get(finalState);
		solution.add(finalState);
		System.out.println("Seen "+ seenList.size());

		while(parent != null)
		{
			solution.add(0, parent);
			finalState = parent;
			parent = seenList.get(finalState);
		}
		if(solution.get(0).isEmpty()) return null;
		return solution;
	}

	
	public static void main(String[] args)
	{
//		List<Integer> n0 = new ArrayList<>(Arrays.asList(0,2,1,6,7,4,3,8,5));
//		List<Integer> n0 = new ArrayList<>(Arrays.asList(7, 0, 3, 1, 2, 5, 6, 4, 8));
		List<Integer> n0 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 0, 5, 7, 8, 6));

		List<Integer> n = new ArrayList<>(Arrays.asList(1,2,3));
		List<Integer> n1 = new ArrayList<>(Arrays.asList(2,3,4));
		List<Integer> n2 = new ArrayList<>(Arrays.asList(3,1,2,3,4));
		List<Integer> n3 = new ArrayList<>(Arrays.asList(4,1,2,3,4));
		
		Search1 s1 = new Search1();
		Search s = new Search();
//		s1.searchBFS(n0, s);
//		s1.searchDFS(n0, s);

//		System.out.println("BFS " + s1.searchBFS(n0, s).size());
		System.out.println("DFS "+ s1.searchDFS(n0, s).size());
	}	
	
}
