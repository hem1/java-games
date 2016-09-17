package TicTacToe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainBoard extends JFrame implements ActionListener
{
	private JLabel winner;
	private TicTacToeBoard ttt;
	private static int HUMAN_START = 1;

	public MainBoard(TicTacToeBoard t)
	{
		this.ttt = t;
		INIT_UI();
	}

	void INIT_UI()
	{
		JPanel p = new JPanel();
		winner = new JLabel("Game on");
		JButton reset = new JButton("Reset");

		winner.setFont(new Font("Serif", Font.PLAIN, 24));
		reset.addActionListener(this);

		p.setLayout(new BorderLayout());
		p.add(winner, BorderLayout.CENTER);
		p.add(reset, BorderLayout.WEST);

		this.getContentPane().add(p, new BorderLayout().NORTH);
		this.getContentPane().add(ttt, new BorderLayout().CENTER);

		this.pack();
		setTitle("Tic Tac Toe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	void declareWinner(int winner)
	{
		if (winner == 1)
			this.winner.setText("You Won");
		else if (winner == 2)
			this.winner.setText("Computer Won");
		else
			this.winner.setText("Draw");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		HUMAN_START = ~HUMAN_START;
		ttt.setTurn(HUMAN_START);
		ttt.resetBoard();
		winner.setText("Game on");

	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		TicTacToeBoard ttt = new TicTacToeBoard(new Dimension(3, 3), new Dimension(200, 120));
		MainBoard b = new MainBoard(ttt);
		ttt.setBoardObject(b);

	}

}
