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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.balls == null) ? 0 : this.balls.hashCode());
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
		EachBallAtLeastOnceRuleCheck other = (EachBallAtLeastOnceRuleCheck) obj;
		if (this.balls == null) {
			if (other.balls != null)
				return false;
		} else if (!this.balls.equals(other.balls))
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