package de.fuhlsfield.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class ScoreTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<GameScoreKeeper> gameScoreKeepers = new ArrayList<GameScoreKeeper>();

	private final int maxRounds;

	private final List<Integer> roundsIndexes = new ArrayList<Integer>();
	private final List<Player> players;

	public ScoreTableModel(List<GameScoreKeeper> keepers, List<Player> players, int maxRounds) {
		this.gameScoreKeepers = keepers;
		this.maxRounds = maxRounds;

		for (int i = 0; i < maxRounds; i++) {
			this.roundsIndexes.add(i + 1);
		}
		this.players = players;
	}

	@Override
	public int getColumnCount() {
		return this.gameScoreKeepers.size() + 1;
	}

	@Override
	public int getRowCount() {
		return this.maxRounds;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {

		if (arg1 == 0) {
			return this.roundsIndexes.get(arg0);
		}

		Attempt attempt = this.gameScoreKeepers.get(arg1 - 1).getIndexed(arg0);
		if (attempt == Attempt.NO_ATTEMPT) {
			return null;
		}
		if (attempt.isSuccessful()) {
			return attempt.getBall();
		}
		return "-" + attempt.getBall();
	}

	@Override
	public String getColumnName(int index) {

		if (index == 0) {
			return "Runde";
		}

		return this.players.get(index - 1).getName();

	}

}
