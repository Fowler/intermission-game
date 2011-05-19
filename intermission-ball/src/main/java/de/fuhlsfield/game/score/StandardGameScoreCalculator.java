package de.fuhlsfield.game.score;

import java.util.List;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class StandardGameScoreCalculator implements GameScoreCalculator {

	protected final BallValueMapper ballValueMapper;

	public StandardGameScoreCalculator(BallValueMapper ballValueMapper) {
		this.ballValueMapper = ballValueMapper;
	}

	@Override
	public final int calculateScore(GameScoreKeeper gameScoreKeeper) {
		int score = 0;
		for (int i = 0; i < gameScoreKeeper.getNumberOfAttempts(); i++) {
			score += calculateScoreForAttempt(gameScoreKeeper, i);
		}
		return score;
	}

	@Override
	public final int calculateScore(List<Ball> balls) {
		int score = 0;
		for (Ball ball : balls) {
			score += this.ballValueMapper.getValue(ball);
		}
		return score;
	}

	@Override
	public final int calculateScore(GameScoreKeeper gameScoreKeeper, List<Ball> balls) {
		return calculateScore(gameScoreKeeper) + calculateScore(balls);
	}

	@Override
	public int calculateScoreForAttempt(GameScoreKeeper gameScoreKeeper, int index) {
		Attempt attempt = gameScoreKeeper.getAttemptByIndex(index);
		if (attempt == null) {
			return ScoreConstants.UNDEFINED_SCORE;
		}
		if (attempt.isSuccessful()) {
			return this.ballValueMapper.getValue(attempt.getBall());
		}
		return 0;
	}

}