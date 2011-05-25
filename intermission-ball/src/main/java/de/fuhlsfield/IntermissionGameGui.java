package de.fuhlsfield;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.config.FiveBallsGameConfig;
import de.fuhlsfield.game.config.GameConfig;
import de.fuhlsfield.game.config.SixBallsGameConfig;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.SeasonScoreCalculator;
import de.fuhlsfield.ui.AttemptButtonUpdater;
import de.fuhlsfield.ui.ButtonModel;
import de.fuhlsfield.ui.FinishButtonUpdater;
import de.fuhlsfield.ui.GameScoreTableModel;
import de.fuhlsfield.ui.SeasonScoreTableModel;
import de.fuhlsfield.ui.UndoButtonUpdater;
import de.fuhlsfield.ui.actionListener.FailureActionListener;
import de.fuhlsfield.ui.actionListener.FinishActionListener;
import de.fuhlsfield.ui.actionListener.FromCsvActionListener;
import de.fuhlsfield.ui.actionListener.SuccessActionListener;
import de.fuhlsfield.ui.actionListener.ToCsvActionListener;
import de.fuhlsfield.ui.actionListener.UndoActionListener;

/**
 * First draft of game gui.
 * 
 * @author juergen
 */
public class IntermissionGameGui {

	private final Map<Player, Map<Ball, List<JButton>>> jButtons = new HashMap<Player, Map<Ball, List<JButton>>>();
	private Game game;
	private GameScoreCalculator gameScoreCalculator;
	private GameScoreTableModel gameScoreTableModel;
	private SeasonScoreTableModel seasonScoreTableModel;
	private ButtonModel buttonModel;
	private JFrame frame;

	public IntermissionGameGui() {
		initSixBallGame();
	}

	public void initGame(Game game) {
		this.game = game;
		GameConfig gameConfig = game.getGameConfig();
		this.gameScoreCalculator = gameConfig.getGameScoreCalculator();
		this.gameScoreTableModel = new GameScoreTableModel(this.game, this.gameScoreCalculator);
		this.seasonScoreTableModel = new SeasonScoreTableModel(this.game, new SeasonScoreCalculator(gameConfig
				.getGameScoreCalculator(), gameConfig.getBonusPoints()));
		this.buttonModel = new ButtonModel(this.game);
		JFrame oldFrame = this.frame;
		this.frame = new JFrame();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = this.frame.getContentPane();
		int containerColumns = Math.max(3, this.game.getPlayers().size());
		container.setLayout(new GridLayout(2, containerColumns));
		container.add(createGameScoreComponent());
		container.add(createSeasonScoreComponent());
		container.add(createOverviewComponent());
		for (int i = containerColumns; i < this.game.getPlayers().size(); i++) {
			container.add(createEmptyComponent());
		}
		for (Player player : this.game.getPlayers()) {
			container.add(createPanelWithButtons(player));
		}
		for (int i = this.game.getPlayers().size(); i < containerColumns; i++) {
			container.add(createEmptyComponent());
		}
		this.frame.setSize(250 * containerColumns, Math.max(gameConfig.getMaxAttempts() + 1, gameConfig
				.getNumberOfGames()) * 40);
		this.frame.setVisible(true);
		this.frame.setTitle("Intermission Game, enjoy your lunch break...");
		if (oldFrame != null) {
			this.frame.setLocation(oldFrame.getLocation().x, oldFrame.getLocation().y);
			oldFrame.setVisible(false);
			oldFrame.dispose();
		}
		this.buttonModel.updateModel();
	}

	private void initFiveBallGame() {
		initGame(new Game(new FiveBallsGameConfig(), Arrays.asList(new Player("Jürgen"), new Player("Marcus"))));
	}

	private void initSixBallGame() {
		initGame(new Game(new SixBallsGameConfig(), Arrays.asList(new Player("Jürgen"), new Player("Marcus"))));
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
		panel.setLayout(new GridLayout(3, 2));
		JButton finishButton = new JButton("Spiel beenden");
		finishButton.addActionListener(new FinishActionListener(this.game, this.gameScoreTableModel,
				this.seasonScoreTableModel, this.buttonModel));
		FinishButtonUpdater finishButtonUpdater = new FinishButtonUpdater();
		finishButtonUpdater.addButton(finishButton);
		this.buttonModel.addUpdater(finishButtonUpdater);
		panel.add(finishButton);
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new UndoActionListener(this.game, this.gameScoreTableModel, this.buttonModel));
		UndoButtonUpdater undoButtonUpdater = new UndoButtonUpdater();
		undoButtonUpdater.addButton(undoButton);
		this.buttonModel.addUpdater(undoButtonUpdater);
		panel.add(undoButton);
		JButton exportToCsv = new JButton("Export");
		exportToCsv.addActionListener(new ToCsvActionListener(this.game));
		panel.add(exportToCsv);
		JButton importFromCsv = new JButton("Import");
		importFromCsv.addActionListener(new FromCsvActionListener(this.game, this));
		panel.add(importFromCsv);
		JButton fiveBallsConfigButton = new JButton(new FiveBallsGameConfig().getName());
		fiveBallsConfigButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				initFiveBallGame();
			}
		});
		panel.add(fiveBallsConfigButton);
		JButton sixBallsConfigButton = new JButton(new SixBallsGameConfig().getName());
		sixBallsConfigButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initSixBallGame();
			}
		});
		panel.add(sixBallsConfigButton);
		return panel;
	}

	private JPanel createPanelWithButtons(Player player) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(this.game.getBalls().size() + 1, 2));
		panel.add(new JLabel(player.getName()));
		panel.add(createEmptyComponent());
		this.jButtons.put(player, new HashMap<Ball, List<JButton>>());
		AttemptButtonUpdater attemptButtonUpdater = new AttemptButtonUpdater();
		for (Ball ball : this.game.getBalls()) {
			String ballName = ball.getName();
			JButton attemptSuccessButton = new JButton(ballName);
			attemptSuccessButton.addActionListener(new SuccessActionListener(this.game, ball, player,
					this.gameScoreTableModel, this.buttonModel));
			attemptButtonUpdater.addSuccessButton(attemptSuccessButton, player, ball);
			JButton attemptFailureButton = new JButton("-" + ballName);
			attemptFailureButton.addActionListener(new FailureActionListener(this.game, ball, player,
					this.gameScoreTableModel, this.buttonModel));
			attemptButtonUpdater.addFailureButton(attemptFailureButton, player, ball);
			panel.add(attemptSuccessButton);
			panel.add(attemptFailureButton);
			this.jButtons.get(player).put(ball, Arrays.asList(attemptSuccessButton, attemptFailureButton));
		}
		this.buttonModel.addUpdater(attemptButtonUpdater);
		return panel;
	}

	private JComponent createEmptyComponent() {
		return new JPanel();
	}

}