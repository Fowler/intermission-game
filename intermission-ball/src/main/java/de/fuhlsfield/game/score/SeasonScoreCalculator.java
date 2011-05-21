package de.fuhlsfield.game.score;

public class SeasonScoreCalculator {

	private final GameScoreCalculator gameScoreCalculator;

	public SeasonScoreCalculator(GameScoreCalculator gameScoreCalculator) {
		this.gameScoreCalculator = gameScoreCalculator;
	}

	public int calculateScore(SeasonScoreKeeper seasonScoreKeeper, int index) {
		if (index < seasonScoreKeeper.getNumberOfGameScoreKeepers()) {
			int score = 0;
			for (int i = 0; i <= index; i++) {
				score += this.gameScoreCalculator.calculateScore(seasonScoreKeeper.getGameScoreKeeperByIndex(i));
			}
			return score;
		}
		return ScoreConstants.UNDEFINED_SCORE;
	}

}