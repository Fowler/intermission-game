package de.fuhlsfield.ui;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreCalculator;

public class GameScoreTableModel extends AbstractScoreTabelModel {

	private static final long serialVersionUID = 1L;
	private static final String TABLE_NAME = "Runde";

	private final Game game;
	private final GameScoreCalculator gameScoreCalculator;

	public GameScoreTableModel(Game game, GameScoreCalculator gameScoreCalculator) {
		this.game = game;
		this.gameScoreCalculator = gameScoreCalculator;
	}

	@Override
	public int getColumnCount() {
		return this.game.getPlayers().size() + 1;
	}

	@Override
	public int getRowCount() {
		return this.game.getMaxAttempts() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex >= this.game.getMaxAttempts()) {
			if (columnIndex == 0) {
				return "Spielstand";
			}
			Player player = this.game.getPlayers().get(columnIndex - 1);
			return this.gameScoreCalculator.calculateScore(this.game.getGameScoreKeeper(player));
		}
		if (columnIndex == 0) {
			return rowIndex + 1;
		}
		return this.game.getGameScoreKeeper(getPlayer(columnIndex - 1)).getAttemptByIndex(rowIndex);
	}

	@Override
	public String getColumnName(int index) {
		if (index == 0) {
			return TABLE_NAME;
		}
		return this.game.getPlayers().get(index - 1).getName();
	}

	private Player getPlayer(int index) {
		int i = 0;
		for (Player player : this.game.getPlayers()) {
			if (index == i) {
				return player;
			}
			i++;
		}
		return null;
	}

}