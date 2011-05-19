package de.fuhlsfield.game.score;

import de.fuhlsfield.game.Attempt;

public class PenaltyPointGameScoreCalculator extends StandardGameScoreCalculator {

	private final static int PENALTY_POINTS = 1;

	public PenaltyPointGameScoreCalculator(BallValueMapper ballValueMapper) {
		super(ballValueMapper);
	}

	@Override
	public int calculateScoreForAttempt(GameScoreKeeper gameScoreKeeper, int index) {
		Attempt lastAttempt = gameScoreKeeper.getAttemptByIndex(index);
		if (lastAttempt == null) {
			return ScoreConstants.UNDEFINED_SCORE;
		}
		if (lastAttempt.isSuccessful()) {
			return this.ballValueMapper.getValue(lastAttempt.getBall());
		}
		boolean isAttemptBeforeSuccessfulOrPenalty = true;
		for (int i = 0; i < index; i++) {
			Attempt attemptBefore = gameScoreKeeper.getAttemptByIndex(i);
			if (attemptBefore.isSuccessful()) {
				isAttemptBeforeSuccessfulOrPenalty = true;
			} else {
				isAttemptBeforeSuccessfulOrPenalty = !isAttemptBeforeSuccessfulOrPenalty;
			}
		}
		if (isAttemptBeforeSuccessfulOrPenalty) {
			return 0;
		}
		return -PENALTY_POINTS;
	}

}