package de.fuhlsfield.game.rule;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class EachBallAtLeastOnceRuleCheck extends AbstractRuleCheck {

	private static final long serialVersionUID = -4488388208656383270L;

	private final Set<Ball> ballsToPlay;

	public EachBallAtLeastOnceRuleCheck(GameScoreCalculator gameScoreCalculator, int targetPoints, Set<Ball> balls) {
		super(gameScoreCalculator, targetPoints);
		this.ballsToPlay = balls;
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.ballsToPlay == null) ? 0 : this.ballsToPlay.hashCode());
		return result;
	}

	@Override
	public boolean equals (Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		EachBallAtLeastOnceRuleCheck other = (EachBallAtLeastOnceRuleCheck) obj;
		if (this.ballsToPlay == null) {
			if (other.ballsToPlay != null) {
				return false;
			}
		} else if (!this.ballsToPlay.equals(other.ballsToPlay)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isPossibleAttempts (PossibleAttempts balls, GameScoreKeeper gameScoreKeeper) {
		if (this.gameScoreCalculator.calculateScore(gameScoreKeeper, balls.toList()) >= this.targetPoints) {
			return isEachBallAtLeastOnce(balls, gameScoreKeeper);
		}
		return isNoBallPlayedTwice(balls, gameScoreKeeper);
	}

	private boolean isEachBallAtLeastOnce (PossibleAttempts balls, GameScoreKeeper gameScoreKeeper) {
		HashSet<Ball> playedBalls = new HashSet<Ball>(gameScoreKeeper.getSuccessfulPlayedBalls());
		playedBalls.addAll(balls.toList());
		LinkedList<Ball> remainingBalls = new LinkedList<Ball>(this.ballsToPlay);
		remainingBalls.removeAll(playedBalls);
		return remainingBalls.isEmpty();
	}

	private boolean isNoBallPlayedTwice (PossibleAttempts balls, GameScoreKeeper gameScoreKeeper) {
		LinkedList<Ball> playedBalls = new LinkedList<Ball>(gameScoreKeeper.getSuccessfulPlayedBalls());
		LinkedList<Ball> remainingBalls = new LinkedList<Ball>(this.ballsToPlay);
		remainingBalls.removeAll(playedBalls);
		if (remainingBalls.isEmpty()) {
			return true;
		}
		if (playedBalls.contains(balls.getFirstAttempt())) {
			return false;
		}
		remainingBalls.remove(balls.getFirstAttempt());
		LinkedList<Ball> nextBalls = new LinkedList<Ball>(balls.nextAttemptsToList());
		for (Ball ball : balls.nextAttemptsToList()) {
			if (remainingBalls.remove(ball)) {
				nextBalls.remove(ball);
			}
		}
		if (remainingBalls.isEmpty()) {
			return true;
		}
		return nextBalls.isEmpty();
	}

}