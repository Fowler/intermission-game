package de.fuhlsfield.game.score;

import java.util.List;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.config.BallValueMapper;

public class StandardGameScoreCalculator implements GameScoreCalculator {

	protected final BallValueMapper ballValueMapper;

	public StandardGameScoreCalculator(BallValueMapper ballValueMapper) {
		this.ballValueMapper = ballValueMapper;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.ballValueMapper == null) ? 0 : this.ballValueMapper.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardGameScoreCalculator other = (StandardGameScoreCalculator) obj;
		if (this.ballValueMapper == null) {
			if (other.ballValueMapper != null)
				return false;
		} else if (!this.ballValueMapper.equals(other.ballValueMapper))
			return false;
		return true;
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