package de.fuhlsfield;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.GameConfig;
import de.fuhlsfield.game.Player;

/**
 * First draft of game gui.
 * 
 * @author juergen
 * 
 */
public class IntermissionGameGui {

	private static final String ROUND = "Runde";
	private static final String GAME = "Spiel";

	private static final int NUMBER_OF_GAMES_PER_SEASON = 10;
	
	private static final Game GAME_KEEPER = new Game(GameConfig.FIVE_BALLS, new Player("Jürgen"), new Player("Marcus"));
	
	private JTable jTableSeason;
	private JTable jTableGame;

	public void create() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = f.getContentPane();

		c.setLayout(new GridLayout(2, 2));

		String[] columnNamesSeason = new String[] { GAME, GAME_KEEPER.getPlayers().get(0).getName(),
				GAME_KEEPER.getPlayers().get(1).getName() };
		String[] columnNamesGame = new String[] { ROUND, GAME_KEEPER.getPlayers().get(0).getName(), GAME_KEEPER.getPlayers().get(1).getName() };
		String[][] columns = createColumns();

		jTableSeason = new JTable(columns, columnNamesGame);
		jTableGame = new JTable(columns, columnNamesSeason);

		c.add(new JScrollPane(jTableGame));
		c.add(new JScrollPane(jTableSeason));

		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(5, 2));

		JButton buttonOne = new JButton("TischiBalli");
		JButton buttonOneNeg = new JButton("-TischiBalli");
		JButton buttonTwo = new JButton("Bunti");
		JButton buttonTwoNeg = new JButton("-Bunti");
		JButton buttonThree = new JButton("Baski");
		JButton buttonThreeNeg = new JButton("-Baski");
		JButton buttonFour = new JButton("Schwammi");
		JButton buttonFourNeg = new JButton("-Schwammi");
		JButton buttonFive = new JButton("Fröschi");
		JButton buttonFiveNeg = new JButton("-Fröschi");

		panel.add(buttonOne);
		panel.add(buttonOneNeg);
		panel.add(buttonTwo);
		panel.add(buttonTwoNeg);
		panel.add(buttonThree);
		panel.add(buttonThreeNeg);
		panel.add(buttonFour);
		panel.add(buttonFourNeg);
		panel.add(buttonFive);
		panel.add(buttonFiveNeg);

		c.add(panel);

		JPanel right = new JPanel();

		f.setSize(500, 500);
		f.setVisible(true);
	}

	/**
	 * needs to be moved to business class
	 * 
	 * @return
	 */
	private String[][] createColumns() {
		String[][] columns = new String[][] { new String[] { "1", null, null },
				new String[] { "2", null, null },
				new String[] { "3", null, null },
				new String[] { "4", null, null },
				new String[] { "5", null, null },
				new String[] { "6", null, null },
				new String[] { "7", null, null },
				new String[] { "8", null, null },
				new String[] { "9", null, null },
				new String[] { "10", null, null }, };
		return columns;
	}

}
