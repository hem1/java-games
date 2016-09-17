package slide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Slide extends JComponent {
	private static final int MAX_WIDTH=600;
	private static final int MAX_HEIGHT=600;
	private static final int DELAY_TIME = 50;
	
	private Dimension boardSize, tileSize;
	private List<Integer> currentState =new ArrayList<Integer>();
	private Search obj;
//	private int[] finalState = { 1,2,3,8,0,4,7,6,5}; 

	public Slide( Dimension boardSize, Dimension tileSize, List<Integer> curr)
	{
		this.currentState.addAll(curr);
		this.boardSize = boardSize;
		this.tileSize = tileSize;

		setSize(boardSize.width*tileSize.width+100, boardSize.height*tileSize.height+100);
		setOpaque(true);
	}
	
	public Slide(List<Integer> state)
	{
		this.currentState.addAll(state);
	}
	
	public void paintComponent(Graphics g){
		if (isOpaque()){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		Font myFont = new Font ("Courier New", 1, 50);
		g.setFont (myFont);
		int curr = 0;
		int x, y;
		if(!this.currentState.isEmpty())
		{
		for(int i=0; i<boardSize.width;i++)
		{
			for(int j=0; j<boardSize.height;j++){
				if(this.currentState.get(curr) == 0){
					g.setColor(new Color(200,200,200));
				}
				else
				{
					g.setColor(Color.GRAY);
					String str = Integer.toString(this.currentState.get(curr));
										
					g.fill3DRect(j*tileSize.width + 1,
						i*tileSize.height + 1,
						tileSize.width-2,
						tileSize.height-2,
						false);
					
					g.setColor(Color.BLACK);
					x = j*tileSize.width +80;
					y = i*tileSize.height+80 ;
					g.drawString(str, x,y);
				}
				curr++;
			}
		}
		}
		
	}
	
	public void updateCurrentState(List<Integer> state)
	{
		
//		for(List<Integer> temp : state)
			{
				this.currentState.clear();
				this.currentState.addAll(state);
				repaint();
			}
		
		
	}
	
	public List<Integer> randomNumbers()
	{
		List<Integer> list = new ArrayList<Integer>();
//		list.addAll(this.currentState);
		
		Random rand = new Random();
		int index;
		System.out.println(this.currentState);
		
		while(this.currentState.size() >0)
		{
			index = rand.nextInt(this.currentState.size());
			list.add(this.currentState.get(index));
			this.currentState.remove(index);
		}
		this.currentState.clear();
		this.currentState.addAll(list);
		System.out.println("After random" + this.currentState);
		
		repaint();
		return this.currentState;
	}
	
	public void animate( List<List<Integer>> sol)
	{
		if(sol == null)
	  		System.out.println("No solution possible");
	  	else
	  	{
//	  		System.out.println("Solution is \n"+sol);
			for(List<Integer> temp : sol)
			{
				try {
			        Thread.sleep(DELAY_TIME);
		
			      } catch (InterruptedException ie) {
			        ie.printStackTrace();}
				
				this.updateCurrentState(temp);
			}
			System.out.println("No of steps : "+ (sol.size()-1));
	  	}
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(boardSize.width*tileSize.width,
									boardSize.height*tileSize.height);
	}
	public Dimension getMinimumSize(){
		return new Dimension(boardSize.width*tileSize.width,
									boardSize.height*tileSize.height);
	}
	public Dimension getMaximumSize(){
		return new Dimension(MAX_WIDTH,MAX_HEIGHT);
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		List<Integer> curr = new ArrayList<>(Arrays.asList(8,7,4,5,6,2,0,1,3));
		List<Integer> curr = new ArrayList<>(Arrays.asList(7, 0, 3, 1, 2, 5, 6, 4, 8));
//		List<Integer> curr = new ArrayList<>(Arrays.asList(0,2,1,6,7,4,3,8,5));
//		List<Integer> curr = new ArrayList<>(Arrays.asList(0, 2, 1, 6, 7, 4, 3, 8, 5));
//		List<Integer> curr = new ArrayList<>(Arrays.asList(7, 3, 4, 0, 5, 1, 6, 8, 2));
//		List<Integer> goal = new ArrayList<>(Arrays.asList(1,2,3,8,0,4,7,6,5));
		
		JFrame mainWindow = new JFrame("Board");
		JPanel p = new JPanel();
		Slide s = new Slide(new Dimension(3,3), new Dimension(200,120), curr);
		
		JButton b = new JButton("Press");
		
		List<List<Integer>> sol = new ArrayList<List<Integer>>();
		b.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  System.out.println("Start!!");
	    	  System.out.println("Done..");
	      }
	    });
		
		
		p.add(b);
		p.add(s);
		mainWindow.getContentPane().add(p, BorderLayout.CENTER);
		mainWindow.pack();
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
//		curr = s.randomNumbers();

		try {
	        Thread.sleep(1000);
	      } catch (InterruptedException ie) {
	        ie.printStackTrace();}
		
		Search sObj = new Search();	
		Search1 s1Obj = new Search1();
		
//		sol = sObj.findSolution(curr);
//		List<Integer> list = new ArrayList<Integer>(Arrays.asList(5, 0, 7, 3, 6, 8, 1, 4, 2));
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(8, 5, 7, 6, 0, 3, 1, 2, 4));
		
		sol = s1Obj.searchBFS(list, sObj);
//		sol = s1Obj.searchDFS(list, sObj); 
//		sol = sObj.findSolution(list);
		
//		sol = s1Obj.searchBFS(curr, sObj);
//		sol = s1Obj.searchDFS(curr, sObj);
//		sol = sObj.findSolution(curr);
		s.animate(sol);
	}
}
