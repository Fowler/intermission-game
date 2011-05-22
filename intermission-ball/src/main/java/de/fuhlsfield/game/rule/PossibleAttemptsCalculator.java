package de.fuhlsfield.game.rule;

import java.util.HashSet;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

class PossibleAttemptsCalculator {

	private final GameScoreCalculator gameScoreCalculator;
	private final int targetPoints;
	private final int maxAttempts;
	private final Set<Ball> balls;

	PossibleAttemptsCalculator(GameScoreCalculator gameScoreCalculator, int targetPoints, int maxAttempts,
			Set<Ball> balls) {
		this.gameScoreCalculator = gameScoreCalculator;
		this.targetPoints = targetPoints;
		this.maxAttempts = maxAttempts;
		this.balls = balls;
	}

	Set<PossibleAttempts> determinePossibleAttempts(Ball ball, GameScoreKeeper gameScoreKeeper) {
		return determinePossibleAttempts(new PossibleAttempts(ball), gameScoreKeeper);
	}

	private Set<PossibleAttempts> determinePossibleAttempts(PossibleAttempts possibleAttempts,
			GameScoreKeeper gameScoreKeeper) {
		HashSet<PossibleAttempts> resultPossibleAttempts = new HashSet<PossibleAttempts>();
		resultPossibleAttempts.add(possibleAttempts);
		if (isAttemptsLeft(gameScoreKeeper, possibleAttempts)
				&& !isTargetPointsReached(gameScoreKeeper, possibleAttempts)) {
			resultPossibleAttempts.remove(possibleAttempts);
			for (Ball ball : this.balls) {
				if ((possibleAttempts.size() == 1) || (ball.compareTo(possibleAttempts.getLastAddedAttempt()) <= 0)) {
					PossibleAttempts newPossibleAttempts = possibleAttempts.copy();
					newPossibleAttempts.add(ball);
					resultPossibleAttempts.addAll(determinePossibleAttempts(newPossibleAttempts, gameScoreKeeper));
				}
			}
		}
		return resultPossibleAttempts;
	}

	private boolean isAttemptsLeft(GameScoreKeeper gameScoreKeeper, PossibleAttempts possibleAttempts) {
		return (this.maxAttempts - gameScoreKeeper.getNumberOfAttempts() - possibleAttempts.size()) > 0;
	}

	private boolean isTargetPointsReached(GameScoreKeeper gameScoreKeeper, PossibleAttempts possibleAttempts) {
		return this.gameScoreCalculator.calculateScore(gameScoreKeeper, possibleAttempts.toList()) >= this.targetPoints;
	}

}