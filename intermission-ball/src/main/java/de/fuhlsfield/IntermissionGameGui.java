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
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.ui.AttemptButtonUpdater;
import de.fuhlsfield.ui.ButtonModel;
import de.fuhlsfield.ui.CurrentScoreTableModel;
import de.fuhlsfield.ui.FinishButtonUpdater;
import de.fuhlsfield.ui.GameScoreTableModel;
import de.fuhlsfield.ui.SeasonScoreTableModel;
import de.fuhlsfield.ui.UndoButtonUpdater;
import de.fuhlsfield.ui.actionListener.FailureActionListener;
import de.fuhlsfield.ui.actionListener.FinishActionListener;
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
	private GameScoreTableModel gameScoreTableModel;
	private SeasonScoreTableModel seasonScoreTableModel;
	private CurrentScoreTableModel currentScoreTableModel;
	private ButtonModel buttonModel;

	public void create(Game game) {
		this.game = game;
		this.gameScoreTableModel = new GameScoreTableModel(this.game);
		this.seasonScoreTableModel = new SeasonScoreTableModel(this.game);
		this.currentScoreTableModel = new CurrentScoreTableModel(this.game);
		this.buttonModel = new ButtonModel(this.game);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = frame.getContentPane();
		int containerColumns = Math.min(3, this.game.getPlayers().size());
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

		addExport(container);

		frame.setSize(330 * containerColumns, this.game.getMaxAttempts() * 40);
		frame.setVisible(true);
		frame.setTitle("Intermission Game, enjoy your lunch break...");
		this.buttonModel.updateModel();

	}

	private void addExport(Container container) {
		JPanel tocsv = new JPanel();
		JButton exportToCsv = new JButton("export");
		exportToCsv.addActionListener(new ToCsvActionListener(this.game));
		tocsv.add(exportToCsv);
		container.add(tocsv);
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
		finishButton.addActionListener(new FinishActionListener(this.game, this.gameScoreTableModel,
				this.currentScoreTableModel, this.seasonScoreTableModel, this.buttonModel));
		FinishButtonUpdater finishButtonUpdater = new FinishButtonUpdater();
		finishButtonUpdater.addButton(finishButton);
		this.buttonModel.addUpdater(finishButtonUpdater);
		panel.add(finishButton);
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new UndoActionListener(this.game, this.gameScoreTableModel,
				this.currentScoreTableModel, this.buttonModel));
		UndoButtonUpdater undoButtonUpdater = new UndoButtonUpdater();
		undoButtonUpdater.addButton(undoButton);
		this.buttonModel.addUpdater(undoButtonUpdater);
		panel.add(undoButton);
		return panel;
	}

	private JComponent createCurrentScoreComponent() {
		JTable currentScoreTable = new JTable(this.currentScoreTableModel);
		return new JScrollPane(currentScoreTable);
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
					this.gameScoreTableModel, this.currentScoreTableModel, this.buttonModel));
			attemptButtonUpdater.addSuccessButton(attemptSuccessButton, player, ball);
			JButton attemptFailureButton = new JButton("-" + ballName);
			attemptFailureButton.addActionListener(new FailureActionListener(this.game, ball, player,
					this.gameScoreTableModel, this.currentScoreTableModel, this.buttonModel));
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