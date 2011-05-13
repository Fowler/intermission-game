package de.fuhlsfield.game.score;

import de.fuhlsfield.game.Attempt;

public class StandardScoreCalculator implements ScoreCalculator {

	protected final BallValueMapper ballValueMapper;

	public StandardScoreCalculator(BallValueMapper ballValueMapper) {
		this.ballValueMapper = ballValueMapper;
	}

	@Override
	public int calculateScore(GameScoreKeeper gameScoreKeeper) {
		int score = 0;
		for (int i = 0; i <= gameScoreKeeper.getIndexOfLastAttempt(); i++) {
			Attempt attempt = gameScoreKeeper.getIndexed(i);
			if (attempt.isSuccessful()) {
				score += this.ballValueMapper.getValue(attempt.getBall());
			}
		}
		return score;
	}

}