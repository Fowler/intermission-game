package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class CheckoutWithAllowedBallRuleCheck implements RuleCheck {

	private final List<Ball> allowedBalls;
	private final int targetPoints;

	public CheckoutWithAllowedBallRuleCheck(List<Ball> allowedBalls, int targetPoints) {
		this.allowedBalls = allowedBalls;
		this.targetPoints = targetPoints;
	}

	@Override
	public List<List<Ball>> selectPossibleAttempts(List<List<Ball>> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		ArrayList<List<Ball>> resultPossibleLefts = new ArrayList<List<Ball>>(possibleBallsLeft);
		for (List<Ball> ballsLeft : possibleBallsLeft) {
			if ((gameScoreKeeper.forecastScore(ballsLeft) >= this.targetPoints)
					&& !isCheckoutWithAllowedBall(ballsLeft)) {
				resultPossibleLefts.remove(ballsLeft);
			}
		}
		return resultPossibleLefts;
	}

	private boolean isCheckoutWithAllowedBall(List<Ball> balls) {
		return ((balls.size() == 1) && this.allowedBalls.contains(balls.get(0)))
				|| isAllowedBallInList(createListWithoutFirstElement(balls));
	}

	private boolean isAllowedBallInList(List<Ball> balls) {
		for (Ball ball : balls) {
			if (this.allowedBalls.contains(ball)) {
				return true;
			}
		}
		return false;
	}

	private List<Ball> createListWithoutFirstElement(List<Ball> balls) {
		return balls.subList(1, balls.size());
	}

}