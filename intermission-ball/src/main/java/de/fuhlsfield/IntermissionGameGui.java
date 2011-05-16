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
import de.fuhlsfield.ui.BallButtonModel;
import de.fuhlsfield.ui.CurrentScoreTableModel;
import de.fuhlsfield.ui.FailureActionListener;
import de.fuhlsfield.ui.FinishActionListener;
import de.fuhlsfield.ui.GameScoreTableModel;
import de.fuhlsfield.ui.SeasonScoreTableModel;
import de.fuhlsfield.ui.SuccessActionListener;
import de.fuhlsfield.ui.UndoActionListener;

/**
 * First draft of game gui.
 * 
 * @author juergen
 */
public class IntermissionGameGui {

	private static final Game GAME = new Game(new FiveBallsGameConfig(), 10, 1, new Player("Jürgen"), new Player(
			"Marcus"));

	private final Map<Player, Map<Ball, List<JButton>>> jButtons = new HashMap<Player, Map<Ball, List<JButton>>>();
	private final GameScoreTableModel gameScoreTableModel = new GameScoreTableModel(GAME);
	private final SeasonScoreTableModel seasonScoreTableModel = new SeasonScoreTableModel(GAME);
	private final CurrentScoreTableModel currentScoreTableModel = new CurrentScoreTableModel(GAME);
	private final BallButtonModel gameButtonModel = new BallButtonModel(GAME);

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
		JTable gameScoreTable = new JTable(this.gameScoreTableModel);
		return new JScrollPane(gameScoreTable);
	}

	private JComponent createSeasonScoreComponent() {
		JTable seasonScoreTable = new JTable(this.seasonScoreTableModel);
		return new JScrollPane(seasonScoreTable);
	}

	private JComponent createOverviewComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.add(createCurrentScoreComponent());
		JButton finishButton = new JButton("Spiel beenden");
		finishButton.addActionListener(new FinishActionListener(GAME, this.gameScoreTableModel,
				this.currentScoreTableModel, this.seasonScoreTableModel, this.gameButtonModel));
		panel.add(finishButton);
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new UndoActionListener(GAME, this.gameScoreTableModel,
				this.currentScoreTableModel, this.gameButtonModel));
		panel.add(undoButton);
		return panel;
	}

	private JComponent createCurrentScoreComponent() {
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
			JButton attemptSuccessButton = new JButton(ballName);
			attemptSuccessButton.addActionListener(new SuccessActionListener(GAME, ball, player,
					this.gameScoreTableModel, this.currentScoreTableModel, this.gameButtonModel));
			this.gameButtonModel.addButton(attemptSuccessButton, player, ball);
			JButton attemptFailureButton = new JButton("-" + ballName);
			attemptFailureButton.addActionListener(new FailureActionListener(GAME, ball, player,
					this.gameScoreTableModel, this.currentScoreTableModel, this.gameButtonModel));
			this.gameButtonModel.addButton(attemptFailureButton, player, ball);
			panel.add(attemptSuccessButton);
			panel.add(attemptFailureButton);
			this.jButtons.get(player).put(ball, Arrays.asList(attemptSuccessButton, attemptFailureButton));
		}
		this.gameButtonModel.updateModel();
		return panel;
	}

	private JComponent createEmptyComponent() {
		return new JPanel();
	}

}