package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class CheckoutWithAllowedBallRuleCheck implements RuleCheck {

	private final Set<Ball> allowedBalls;
	private final int targetPoints;
	private final GameScoreCalculator scoreCalculator;

	public CheckoutWithAllowedBallRuleCheck(Set<Ball> allowedBalls, int targetPoints,
			GameScoreCalculator scoreCalculator) {
		this.allowedBalls = allowedBalls;
		this.targetPoints = targetPoints;
		this.scoreCalculator = scoreCalculator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.allowedBalls == null) ? 0 : this.allowedBalls.hashCode());
		result = prime * result + ((this.scoreCalculator == null) ? 0 : this.scoreCalculator.hashCode());
		result = prime * result + this.targetPoints;
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
		CheckoutWithAllowedBallRuleCheck other = (CheckoutWithAllowedBallRuleCheck) obj;
		if (this.allowedBalls == null) {
			if (other.allowedBalls != null)
				return false;
		} else if (!this.allowedBalls.equals(other.allowedBalls))
			return false;
		if (this.scoreCalculator == null) {
			if (other.scoreCalculator != null)
				return false;
		} else if (!this.scoreCalculator.equals(other.scoreCalculator))
			return false;
		if (this.targetPoints != other.targetPoints)
			return false;
		return true;
	}

	@Override
	public List<List<Ball>> selectPossibleAttempts(List<List<Ball>> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		ArrayList<List<Ball>> resultPossibleLefts = new ArrayList<List<Ball>>(possibleBallsLeft);
		for (List<Ball> ballsLeft : possibleBallsLeft) {
			if ((this.scoreCalculator.calculateScore(gameScoreKeeper, ballsLeft) >= this.targetPoints)
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