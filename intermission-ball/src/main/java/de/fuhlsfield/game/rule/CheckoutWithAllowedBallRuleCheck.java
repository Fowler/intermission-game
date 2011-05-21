package de.fuhlsfield.game.rule;

import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class CheckoutWithAllowedBallRuleCheck extends AbstractRuleCheck {

	private final Set<Ball> allowedBalls;

	public CheckoutWithAllowedBallRuleCheck(GameScoreCalculator gameScoreCalculator, int targetPoints,
			Set<Ball> allowedBalls) {
		super(gameScoreCalculator, targetPoints);
		this.allowedBalls = allowedBalls;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.allowedBalls == null) ? 0 : this.allowedBalls.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		CheckoutWithAllowedBallRuleCheck other = (CheckoutWithAllowedBallRuleCheck) obj;
		if (this.allowedBalls == null) {
			if (other.allowedBalls != null) {
				return false;
			}
		} else if (!this.allowedBalls.equals(other.allowedBalls)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isPossibleAttempts(List<Ball> balls, GameScoreKeeper gameScoreKeeper) {
		if ((this.gameScoreCalculator.calculateScore(gameScoreKeeper, balls) >= this.targetPoints)
				&& !isCheckoutWithAllowedBall(balls)) {
			return false;
		}
		return true;
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