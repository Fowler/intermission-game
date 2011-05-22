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
		return this.game.getPlayers().size();
	}

	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Player player = this.game.getPlayers().get(columnIndex);
		return this.gameScoreCalculator.calculateScore(this.game.getGameScoreKeeper(player));
	}

	@Override
	public String getColumnName(int index) {
		return this.game.getPlayers().get(index).getName();
	}

}