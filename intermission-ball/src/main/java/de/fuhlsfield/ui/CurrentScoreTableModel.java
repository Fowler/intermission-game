package de.fuhlsfield.ui;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreCalculator;

public class CurrentScoreTableModel extends AbstractScoreTabelModel {

	private static final long serialVersionUID = 1L;

	private final Game game;
	private final GameScoreCalculator gameScoreCalculator;

	public CurrentScoreTableModel(Game game, GameScoreCalculator gameScoreCalculator) {
		this.game = game;
		this.gameScoreCalculator = gameScoreCalculator;
	}

	@Override
	public int getColumnCount() {
		return this.game.getPlayers().size() + 1;
	}

	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return "aktuell";
		}
		Player player = this.game.getPlayers().get(columnIndex - 1);
		return this.gameScoreCalculator.calculateScore(this.game.getGameScoreKeeper(player));
	}

	@Override
	public String getColumnName(int index) {
		if (index == 0) {
			return "Spielstand";
		}
		return this.game.getPlayers().get(index - 1).getName();
	}

}