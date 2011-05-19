package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class EachBallAtLeastOnceRuleCheck implements RuleCheck {

	private final List<Ball> balls;
	private final int targetPoints;
	private final GameScoreCalculator scoreCalculator;

	public EachBallAtLeastOnceRuleCheck(List<Ball> balls, int targetPoints, GameScoreCalculator scoreCalculator) {
		this.balls = balls;
		this.targetPoints = targetPoints;
		this.scoreCalculator = scoreCalculator;
	}

	@Override
	public List<List<Ball>> selectPossibleAttempts(List<List<Ball>> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		ArrayList<List<Ball>> resultPossibleLefts = new ArrayList<List<Ball>>(possibleBallsLeft);
		for (List<Ball> ballsLeft : possibleBallsLeft) {
			if (this.scoreCalculator.calculateScore(gameScoreKeeper, ballsLeft) >= this.targetPoints) {
				for (Ball ball : this.balls) {
					if (!ballsLeft.contains(ball) && !gameScoreKeeper.isBallSuccessfulPlayed(ball)) {
						resultPossibleLefts.remove(ballsLeft);
					}
				}
			} else {
				if (!gameScoreKeeper.isBallSuccessfulPlayed(ballsLeft.get(0))) {
					resultPossibleLefts.remove(ballsLeft);
				}
			}
		}
		return resultPossibleLefts;
	}

}