package de.fuhlsfield.game.score;

public class SeasonScoreCalculator {

	private final GameScoreCalculator gameScoreCalculator;

	public SeasonScoreCalculator(GameScoreCalculator gameScoreCalculator) {
		this.gameScoreCalculator = gameScoreCalculator;
	}

	public int calculateScore(SeasonScoreKeeper seasonScoreKeeper, int index) {
		if (index < seasonScoreKeeper.getGameScoreKeepers().size()) {
			int score = 0;
			for (int i = 0; i <= index; i++) {
				score += this.gameScoreCalculator.calculateScore(seasonScoreKeeper.getGameScoreKeepers().get(i));
			}
			return score;
		}
		return ScoreConstants.UNDEFINED_SCORE;
	}

}