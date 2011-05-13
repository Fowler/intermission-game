package de.fuhlsfield;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.GameConfig;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.ui.FailureActionListener;
import de.fuhlsfield.ui.ScoreTableModel;
import de.fuhlsfield.ui.SuccessActionListener;

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

	private static final Game GAME_KEEPER = new Game(GameConfig.FIVE_BALLS, 10, PLAYER_ONE, PLAYER_TWO);

	private JTable jTableSeason;
	private JTable jTableGame;

	private ScoreTableModel model;

	public void create() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = f.getContentPane();

		c.setLayout(new GridLayout(2, 2));

		String[] columnNamesSeason = new String[] { GAME, GAME_KEEPER.getPlayers().get(0).getName(),
				GAME_KEEPER.getPlayers().get(1).getName() };

		String[][] columns = createColumns();

		this.jTableSeason = new JTable(columns, columnNamesSeason);

		List<GameScoreKeeper> keepers = new ArrayList<GameScoreKeeper>();
		keepers.add(GAME_KEEPER.getGameScore(PLAYER_ONE));
		keepers.add(GAME_KEEPER.getGameScore(PLAYER_TWO));

		this.model = new ScoreTableModel(keepers, GAME_KEEPER.getPlayers(), GAME_KEEPER.getMaxRounds());

		this.jTableGame = new JTable(this.model);

		c.add(new JScrollPane(this.jTableGame));
		c.add(new JScrollPane(this.jTableSeason));

		c.add(createPanelWithButtons(PLAYER_ONE));
		c.add(createPanelWithButtons(PLAYER_TWO));

		f.setSize(500, 500);
		f.setVisible(true);
		f.setTitle("Intermission Game, enjoy your lunch break...");
	}

	private JPanel createPanelWithButtons(Player player) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));

		for (Ball ball : GAME_KEEPER.getGameConfig().getBallValueMapper().getBalls()) {
			String ballName = ball.getName();
			JButton ballSuccess = new JButton(ballName);
			ballSuccess.addActionListener(new SuccessActionListener(GAME_KEEPER, ball, player, this.model));
			JButton ballFailed = new JButton("-" + ballName);
			ballFailed.addActionListener(new FailureActionListener(GAME_KEEPER, ball, player, this.model));
			panel.add(ballSuccess);
			panel.add(ballFailed);

		}
		return panel;
	}

	/**
	 * needs to be moved to business class
	 * 
	 * @return
	 */
	private String[][] createColumns() {
		String[][] columns = new String[][] { new String[] { "1", null, null }, new String[] { "2", null, null },
				new String[] { "3", null, null }, new String[] { "4", null, null }, new String[] { "5", null, null },
				new String[] { "6", null, null }, new String[] { "7", null, null }, new String[] { "8", null, null },
				new String[] { "9", null, null }, new String[] { "10", null, null }, };
		return columns;
	}

}
