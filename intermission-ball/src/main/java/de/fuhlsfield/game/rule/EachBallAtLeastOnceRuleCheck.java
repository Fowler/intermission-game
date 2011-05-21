package de.fuhlsfield.game.rule;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class EachBallAtLeastOnceRuleCheck extends AbstractRuleCheck {

	private final Set<Ball> ballsToPlay;

	public EachBallAtLeastOnceRuleCheck(GameScoreCalculator gameScoreCalculator, int targetPoints, Set<Ball> balls) {
		super(gameScoreCalculator, targetPoints);
		this.ballsToPlay = balls;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.ballsToPlay == null) ? 0 : this.ballsToPlay.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
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
	public boolean isPossibleAttempts(List<Ball> balls, GameScoreKeeper gameScoreKeeper) {
		if (this.gameScoreCalculator.calculateScore(gameScoreKeeper, balls) >= this.targetPoints) {
			return isEachBallAtLeastOnce(balls, gameScoreKeeper);
		}
		return isNoBallPlayedTwice(balls, gameScoreKeeper);
	}

	private boolean isEachBallAtLeastOnce(List<Ball> balls, GameScoreKeeper gameScoreKeeper) {
		Set<Ball> ballsPlayed = gameScoreKeeper.getSuccessfulPlayedBalls();
		ballsPlayed.addAll(balls);
		LinkedList<Ball> ballsToPlayCopy = new LinkedList<Ball>(this.ballsToPlay);
		ballsToPlayCopy.removeAll(ballsPlayed);
		return ballsToPlayCopy.isEmpty();
	}

	private boolean isNoBallPlayedTwice(List<Ball> balls, GameScoreKeeper gameScoreKeeper) {
		LinkedList<Ball> ballsPlayed = new LinkedList<Ball>(gameScoreKeeper.getSuccessfulPlayedBalls());
		ballsPlayed.addAll(balls);
		for (Ball ball : this.ballsToPlay) {
			ballsPlayed.remove(ball);
			if (ballsPlayed.contains(ball)) {
				return false;
			}
		}
		return true;
	}

}