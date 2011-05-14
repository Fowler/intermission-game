package de.fuhlsfield.game.score;

import de.fuhlsfield.game.Attempt;

public class PenaltyPointScoreCalculator extends StandardScoreCalculator {

	private final static int PENALTY_POINTS = 1;

	public PenaltyPointScoreCalculator(BallValueMapper ballValueMapper) {
		super(ballValueMapper);
	}

	@Override
	public int calculateScore(GameScoreKeeper gameScoreKeeper) {
		int score = super.calculateScore(gameScoreKeeper);
		boolean lastAttemptFails = false;
		for (int i = 0; i <= gameScoreKeeper.getIndexOfLastAttempt(); i++) {
			Attempt attemptResult = gameScoreKeeper.getIndexed(i);
			if (!attemptResult.isSuccessful()) {
				if (lastAttemptFails) {
					score -= PENALTY_POINTS;
					lastAttemptFails = false;
				} else {
					lastAttemptFails = true;
				}
			} else {
				lastAttemptFails = false;
			}
		}
		return score;
	}

}