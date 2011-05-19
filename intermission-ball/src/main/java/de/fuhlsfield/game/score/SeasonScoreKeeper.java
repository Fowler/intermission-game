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

	public List<GameScoreKeeper> getGameScoreKeepers() {
		return this.gameScoreKeepers;
	}

}