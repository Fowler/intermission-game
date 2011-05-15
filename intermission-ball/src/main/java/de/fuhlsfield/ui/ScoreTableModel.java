package de.fuhlsfield.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class ScoreTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String TABLE_NAME = "Runde";

	private final Game game;
	private final List<GameScoreKeeper> gameScoreKeepers;
	private final List<Integer> roundsIndexes = new ArrayList<Integer>();

	public ScoreTableModel(Game game) {
		this.game = game;
		this.gameScoreKeepers = new ArrayList<GameScoreKeeper>();
		for (Player player : game.getPlayers()) {
			this.gameScoreKeepers.add(game.getGameScoreKeeper(player));
		}
		for (int i = 0; i < game.getMaxAttempts(); i++) {
			this.roundsIndexes.add(i + 1);
		}
	}

	@Override
	public int getColumnCount() {
		return this.gameScoreKeepers.size() + 1;
	}

	@Override
	public int getRowCount() {
		return this.game.getMaxAttempts();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return this.roundsIndexes.get(rowIndex);
		}
		return this.gameScoreKeepers.get(columnIndex - 1).getIndexed(rowIndex);
	}

	@Override
	public String getColumnName(int index) {
		if (index == 0) {
			return TABLE_NAME;
		}
		return this.game.getPlayers().get(index - 1).getName();
	}

}