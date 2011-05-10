package de.fuhlsfield.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class ScoreTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<GameScoreKeeper> gameScoreKeepers = new ArrayList<GameScoreKeeper>();

	private int maxRounds;

	private List<Integer> roundsIndexes = new ArrayList<Integer>();
	private List<Player> players;

	public ScoreTableModel(List<GameScoreKeeper> keepers, List<Player> players,
			int maxRounds) {
		gameScoreKeepers = keepers;
		this.maxRounds = maxRounds;

		for (int i = 0; i < maxRounds; i++) {
			roundsIndexes.add(i + 1);
		}
		this.players = players;
	}

	@Override
	public int getColumnCount() {
		return gameScoreKeepers.size() + 1;
	}

	@Override
	public int getRowCount() {
		return maxRounds;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {

		if (arg1 == 0) {
			return roundsIndexes.get(arg0);
		}

		return gameScoreKeepers.get(arg1 - 1).getIndexed(arg0);

		// return null;
	}

	public String getColumnName(int index) {

		if (index == 0) {
			return "Runde";
		}

		return players.get(index - 1).getName();

	}

}
