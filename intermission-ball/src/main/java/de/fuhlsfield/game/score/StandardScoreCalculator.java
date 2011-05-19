package de.fuhlsfield.game.score;

import java.util.ArrayList;
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
		return calculateScore(gameScoreKeeper, gameScoreKeeper.getIndexOfLastAttempt());
	}

	@Override
	public int calculateScore(GameScoreKeeper gameScoreKeeper, int index) {
		ArrayList<Ball> balls = new ArrayList<Ball>();
		for (int i = 0; i <= index; i++) {
			Attempt attempt = gameScoreKeeper.getIndexed(i);
			if (attempt.isSuccessful()) {
				balls.add(attempt.getBall());
			}
		}
		return calculateScore(balls);
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
		int score = 0;
		for (int i = 0; i <= index; i++) {
			score += calculateScore(seasonScoreKeeper.getGameScoreKeepers().get(i));
		}
		return score;
	}

}