package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class EachBallAtLeastOnceRuleCheck implements RuleCheck {

	private final List<Ball> balls;
	private final int targetPoints;

	public EachBallAtLeastOnceRuleCheck(List<Ball> balls, int targetPoints) {
		this.balls = balls;
		this.targetPoints = targetPoints;
	}

	@Override
	public List<List<Ball>> selectPossibleAttempts(List<List<Ball>> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		ArrayList<List<Ball>> resultPossibleLefts = new ArrayList<List<Ball>>(possibleBallsLeft);
		List<Ball> successfulAttempts = gameScoreKeeper.getSuccessfulAttempts();
		for (List<Ball> ballsLeft : possibleBallsLeft) {
			if (gameScoreKeeper.forecastScore(ballsLeft) >= this.targetPoints) {
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