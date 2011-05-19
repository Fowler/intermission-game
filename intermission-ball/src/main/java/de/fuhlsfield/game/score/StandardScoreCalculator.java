package de.fuhlsfield.game.score;

import java.util.List;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class StandardScoreCalculator implements ScoreCalculator {

	protected final BallValueMapper ballValueMapper;

	public StandardScoreCalculator(BallValueMapper ballValueMapper) {
		this.ballValueMapper = ballValueMapper;
	}

	@Override
	public int calculateScore(GameScoreKeeper gameScoreKeeper) {
		int score = 0;
		for (int i = 0; i <= gameScoreKeeper.getIndexOfLastAttempt(); i++) {
			score += calculateScoreForAttempt(gameScoreKeeper, i);
		}
		return score;
	}

	@Override
	public int calculateScore(List<Ball> balls) {
		int score = 0;
		for (Ball ball : balls) {
			score += this.ballValueMapper.getValue(ball);
		}
		return score;
	}

	@Override
	public int calculateScore(GameScoreKeeper gameScoreKeeper, List<Ball> balls) {
		return calculateScore(gameScoreKeeper) + calculateScore(balls);
	}

	@Override
	public int calculateScore(SeasonScoreKeeper seasonScoreKeeper, int index) {
		if (index < seasonScoreKeeper.getGameScoreKeepers().size()) {
			int score = 0;
			for (int i = 0; i <= index; i++) {
				score += calculateScore(seasonScoreKeeper.getGameScoreKeepers().get(i));
			}
			return score;
		}
		return ScoreCalculator.UNDEFINED_SCORE;
	}

	@Override
	public int calculateScoreForAttempt(GameScoreKeeper gameScoreKeeper, int index) {
		Attempt attempt = gameScoreKeeper.getIndexed(index);
		if (attempt == null) {
			return ScoreCalculator.UNDEFINED_SCORE;
		}
		if (attempt.isSuccessful()) {
			return this.ballValueMapper.getValue(attempt.getBall());
		}
		return 0;
	}

}