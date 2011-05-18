package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.List;

public class SeasonScoreKeeper {

	private final List<GameScoreKeeper> gameScoreKeepers;

	public SeasonScoreKeeper() {
		this.gameScoreKeepers = new ArrayList<GameScoreKeeper>();
	}

	public void addGameScoreKeeper(GameScoreKeeper gameScoreKeeper) {
		this.gameScoreKeepers.add(gameScoreKeeper);
	}

	public Integer calculateScore(int index) {
		if (index < this.gameScoreKeepers.size()) {
			int score = 0;
			for (int i = 0; i <= index; i++) {
				score += this.gameScoreKeepers.get(i).calculateScore();
			}
			return score;
		}
		return null;
	}

	public int getNumberOfGameScores() {
		return this.gameScoreKeepers.size();
	}

	public List<GameScoreKeeper> getGameScoreKeepers() {
		return this.gameScoreKeepers;
	}

}