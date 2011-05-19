package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.ScoreCalculator;

public class EachBallAtLeastOnceRuleCheck implements RuleCheck {

	private final List<Ball> balls;
	private final int targetPoints;
	private final ScoreCalculator scoreCalculator;

	public EachBallAtLeastOnceRuleCheck(List<Ball> balls, int targetPoints, ScoreCalculator scoreCalculator) {
		this.balls = balls;
		this.targetPoints = targetPoints;
		this.scoreCalculator = scoreCalculator;
	}

	@Override
	public List<List<Ball>> selectPossibleAttempts(List<List<Ball>> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		ArrayList<List<Ball>> resultPossibleLefts = new ArrayList<List<Ball>>(possibleBallsLeft);
		List<Ball> successfulAttempts = gameScoreKeeper.getSuccessfulAttempts();
		for (List<Ball> ballsLeft : possibleBallsLeft) {
			if (this.scoreCalculator.calculateScore(gameScoreKeeper, ballsLeft) >= this.targetPoints) {
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