package de.fuhlsfield;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.FiveBallsGameConfig;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.ui.CurrentScoreTableModel;
import de.fuhlsfield.ui.FailureActionListener;
import de.fuhlsfield.ui.ScoreTableModel;
import de.fuhlsfield.ui.SuccessActionListener;

/**
 * First draft of game gui.
 * 
 * @author juergen
 */
public class IntermissionGameGui {

	private static final Game GAME = new Game(new FiveBallsGameConfig(), 10, new Player("Jürgen"), new Player("Marcus"));

	private final Map<Player, Map<Ball, List<JButton>>> jButtons = new HashMap<Player, Map<Ball, List<JButton>>>();
	private ScoreTableModel scoreTableModel;
	private CurrentScoreTableModel currentScoreTableModel;

	public void create() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = frame.getContentPane();
		int containerColumns = Math.min(3, GAME.getPlayers().size());
		container.setLayout(new GridLayout(2, containerColumns));
		container.add(createGameScoreComponent());
		container.add(createSeasonScoreComponent());
		container.add(createOverviewComponent());
		for (int i = containerColumns; i < GAME.getPlayers().size(); i++) {
			container.add(createEmptyComponent());
		}
		for (Player player : GAME.getPlayers()) {
			container.add(createPanelWithButtons(player));
		}
		for (int i = GAME.getPlayers().size(); i < containerColumns; i++) {
			container.add(createEmptyComponent());
		}
		frame.setSize(250 * GAME.getPlayers().size(), GAME.getMaxAttempts() * 40);
		frame.setVisible(true);
		frame.setTitle("Intermission Game, enjoy your lunch break...");
	}

	private JComponent createGameScoreComponent() {
		this.scoreTableModel = new ScoreTableModel(GAME);
		JTable gameScoreTabel = new JTable(this.scoreTableModel);
		return new JScrollPane(gameScoreTabel);
	}

	private JComponent createSeasonScoreComponent() {
		String[][] columns = createColumns();
		String[] columnNamesSeason = new String[GAME.getPlayers().size() + 1];
		columnNamesSeason[0] = "Spiel";
		int i = 1;
		for (Player player : GAME.getPlayers()) {
			columnNamesSeason[i++] = player.getName();
		}
		JTable seasonScoreTable = new JTable(columns, columnNamesSeason);
		return new JScrollPane(seasonScoreTable);
	}

	private JComponent createOverviewComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.add(createCurrentScoreComponent());
		panel.add(new JButton("Spiel beenden"));
		panel.add(new JButton("Undo"));
		return panel;
	}

	private JComponent createCurrentScoreComponent() {
		this.currentScoreTableModel = new CurrentScoreTableModel(GAME);
		JTable currentScoreTable = new JTable(this.currentScoreTableModel);
		return new JScrollPane(currentScoreTable);
	}

	private JPanel createPanelWithButtons(Player player) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(GAME.getBalls().size() + 1, 2));
		panel.add(new JLabel(player.getName()));
		panel.add(createEmptyComponent());
		this.jButtons.put(player, new HashMap<Ball, List<JButton>>());
		for (Ball ball : GAME.getBalls()) {
			String ballName = ball.getName();
			JButton ballSuccess = new JButton(ballName);
			ballSuccess.addActionListener(new SuccessActionListener(GAME, ball, player, this.scoreTableModel,
					this.currentScoreTableModel));
			JButton ballFailed = new JButton("-" + ballName);
			ballFailed.addActionListener(new FailureActionListener(GAME, ball, player, this.scoreTableModel,
					this.currentScoreTableModel));
			panel.add(ballSuccess);
			panel.add(ballFailed);
			this.jButtons.get(player).put(ball, Arrays.asList(ballSuccess, ballFailed));
		}
		return panel;
	}

	private JComponent createEmptyComponent() {
		return new JPanel();
	}

	private String[][] createColumns() {
		String[][] columns = new String[][] { new String[] { "1", null, null }, new String[] { "2", null, null },
				new String[] { "3", null, null }, new String[] { "4", null, null }, new String[] { "5", null, null },
				new String[] { "6", null, null }, new String[] { "7", null, null }, new String[] { "8", null, null },
				new String[] { "9", null, null }, new String[] { "10", null, null }, };
		return columns;
	}

}