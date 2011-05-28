package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.List;

public class SeasonScoreKeeper {

	private final List<GameScoreKeeper> gameScoreKeepers = new ArrayList<GameScoreKeeper>();

	public void addGameScoreKeeper(GameScoreKeeper gameScoreKeeper) {
		this.gameScoreKeepers.add(gameScoreKeeper);
	}

	public int getNumberOfGameScoreKeepers() {
		return this.gameScoreKeepers.size();
	}

	public GameScoreKeeper getGameScoreKeeperByIndex(int index) {
		if ((index < 0) || (index >= this.gameScoreKeepers.size())) {
			return null;
		}
		return this.gameScoreKeepers.get(index);
	}

}