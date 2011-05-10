package de.fuhlsfield;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.BallValue;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.GameConfig;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.ui.ScoreTableModel;

/**
 * First draft of game gui.
 * 
 * @author juergen
 * 
 */
public class IntermissionGameGui {

	private static final Player PLAYER_TWO = new Player("Marcus");
	private static final Player PLAYER_ONE = new Player("Jürgen");

	private static final String GAME = "Spiel";

	private static final Game GAME_KEEPER = new Game(GameConfig.FIVE_BALLS,
			PLAYER_ONE, PLAYER_TWO);

	private JTable jTableSeason;
	private JTable jTableGame;

	private ScoreTableModel model;

	public void create() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = f.getContentPane();

		c.setLayout(new GridLayout(2, 2));

		String[] columnNamesSeason = new String[] { GAME,
				GAME_KEEPER.getPlayers().get(0).getName(),
				GAME_KEEPER.getPlayers().get(1).getName() };

		String[][] columns = createColumns();

		jTableSeason = new JTable(columns, columnNamesSeason);

		List<GameScoreKeeper> keepers = new ArrayList<GameScoreKeeper>();
		keepers.add(GAME_KEEPER.getGameScore(PLAYER_ONE));
		keepers.add(GAME_KEEPER.getGameScore(PLAYER_TWO));

		model = new ScoreTableModel(keepers, GAME_KEEPER.getPlayers(),
				GAME_KEEPER.getGameConfig().getMaxRounds());

		jTableGame = new JTable(model);

		c.add(new JScrollPane(jTableGame));
		c.add(new JScrollPane(jTableSeason));

		JPanel panel = createPanelWithButtons();

		c.add(panel);

		f.setSize(500, 500);
		f.setVisible(true);
		f.setTitle("Intermission Game, enjoy your lunch break...");
	}

	private JPanel createPanelWithButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		List<BallValue> balls = GAME_KEEPER.getGameConfig().getBallValues();

		for (BallValue value : balls) {
			String ballName = value.getBall().getName();
			JButton ballSuccess = new JButton(ballName);
			JButton ballFailed = new JButton("-" + ballName);
			panel.add(ballSuccess);
			panel.add(ballFailed);

			ballSuccess.addActionListener(new MyActionListener());

		}
		return panel;
	}

	/**
	 * 
	 * Just to see whether updates to table work properly!
	 * 
	 * @author juergen
	 * 
	 */
	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			GAME_KEEPER.check(Ball.BASKI, PLAYER_ONE, true);
			model.fireTableDataChanged();
		}

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
