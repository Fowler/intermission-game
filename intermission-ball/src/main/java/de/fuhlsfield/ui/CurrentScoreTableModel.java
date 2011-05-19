package de.fuhlsfield.ui;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;

public class CurrentScoreTableModel extends AbstractScoreTabelModel {

	private static final long serialVersionUID = 1L;

	private final Game game;

	public CurrentScoreTableModel(Game game) {
		this.game = game;
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
		return this.game.getGameConfig().getScoreCalculator().calculateScore(this.game.getGameScoreKeeper(player));
	}

	@Override
	public String getColumnName(int index) {
		return this.game.getPlayers().get(index).getName();
	}

}