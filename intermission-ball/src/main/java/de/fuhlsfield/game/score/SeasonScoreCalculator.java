package de.fuhlsfield.game.score;

import java.util.Map;

import de.fuhlsfield.game.Player;

public class SeasonScoreCalculator {

	private final GameScoreCalculator gameScoreCalculator;
	private final int bonusPoints;

	public SeasonScoreCalculator(GameScoreCalculator gameScoreCalculator, int bonusPoints) {
		this.gameScoreCalculator = gameScoreCalculator;
		this.bonusPoints = bonusPoints;
	}

	public int calculateScore(Map<Player, SeasonScoreKeeper> seasonScoreKeepers, Player player, int index) {
		SeasonScoreKeeper seasonScoreKeeper = seasonScoreKeepers.get(player);
		if (index < seasonScoreKeeper.getNumberOfGameScoreKeepers()) {
			int totalScore = 0;
			for (int i = 0; i <= index; i++) {
				int maxScore = calculateMaxScore(seasonScoreKeepers, i);
				int score = this.gameScoreCalculator.calculateScore(seasonScoreKeeper.getGameScoreKeeperByIndex(i));
				totalScore += score;
				if (score == maxScore) {
					totalScore += this.bonusPoints;
				}
			}
			return totalScore;
		}
		return GameScoreCalculator.UNDEFINED_SCORE;
	}

	private int calculateMaxScore(Map<Player, SeasonScoreKeeper> seasonScoreKeepers, int index) {
		int maxScore = GameScoreCalculator.UNDEFINED_SCORE;
		for (SeasonScoreKeeper seasonScoreKeeper : seasonScoreKeepers.values()) {
			int score = this.gameScoreCalculator.calculateScore(seasonScoreKeeper.getGameScoreKeeperByIndex(index));
			if ((maxScore == GameScoreCalculator.UNDEFINED_SCORE) || (score > maxScore)) {
				maxScore = score;
			}
		}
		return maxScore;
	}

}