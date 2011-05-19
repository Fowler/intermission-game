package de.fuhlsfield.ui;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.ScoreConstants;
import de.fuhlsfield.game.score.SeasonScoreCalculator;
import de.fuhlsfield.game.score.SeasonScoreKeeper;

public class SeasonScoreTableModel extends AbstractScoreTabelModel {

	private static final long serialVersionUID = 1L;
	private static final String TABLE_NAME = "Spiel";

	private final Game game;

	public SeasonScoreTableModel(Game game) {
		this.game = game;
	}

	@Override
	public int getColumnCount() {
		return this.game.getPlayers().size() + 1;
	}

	@Override
	public int getRowCount() {
		return this.game.getNumberOfGames();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return rowIndex + 1;
		}
		SeasonScoreKeeper seasonScoreKeeper = this.game.getSeasonScoreKeeper(getPlayer(columnIndex - 1));
		int score = new SeasonScoreCalculator(this.game.getGameConfig().getScoreCalculator()).calculateScore(
				seasonScoreKeeper, rowIndex);
		if (score == ScoreConstants.UNDEFINED_SCORE) {
			return null;
		}
		return score;
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