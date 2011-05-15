package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.ScoreCalculator;

public class EachBallAtLeastOnceRuleCheck implements RuleCheck {

	private final List<Ball> balls;
	private final ScoreCalculator scoreCalculator;
	private final int targetPoints;

	public EachBallAtLeastOnceRuleCheck(List<Ball> balls, ScoreCalculator scoreCalculator, int targetPoints) {
		this.balls = balls;
		this.scoreCalculator = scoreCalculator;
		this.targetPoints = targetPoints;
	}

	@Override
	public List<List<Ball>> selectPossibleAttempts(List<List<Ball>> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		ArrayList<List<Ball>> resultPossibleLefts = new ArrayList<List<Ball>>(possibleBallsLeft);
		List<Ball> successfulAttempts = gameScoreKeeper.getSuccessfulAttempts();
		for (List<Ball> ballsLeft : possibleBallsLeft) {
			if (this.scoreCalculator.forecastScore(gameScoreKeeper, ballsLeft) >= this.targetPoints) {
				for (Ball ball : this.balls) {
					if (!ballsLeft.contains(ball) && !successfulAttempts.contains(ball)) {
						resultPossibleLefts.remove(ballsLeft);
					}
				}
			} else {
				if (successfulAttempts.contains(ballsLeft.get(0))) {
					resultPossibleLefts.remove(ballsLeft);
				}
			}
		}
		return resultPossibleLefts;
	}

}