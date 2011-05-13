package de.fuhlsfield.game.score;

import de.fuhlsfield.game.AttemptResult;

public class StandardScoreCalculator implements ScoreCalculator {

	protected final BallValueMapper ballValueMapper;

	public StandardScoreCalculator(BallValueMapper ballValueMapper) {
		this.ballValueMapper = ballValueMapper;
	}

	@Override
	public int calculateScore(GameScoreKeeper gameScoreKeeper) {
		int score = 0;
		for (int i = 0; i <= gameScoreKeeper.getIndexOfLastAttempt(); i++) {
			AttemptResult attemptResult = gameScoreKeeper.getIndexed(i);
			if (attemptResult.isSuccess()) {
				score += this.ballValueMapper.getValue(attemptResult.getBall());
			}
		}
		return score;
	}

}