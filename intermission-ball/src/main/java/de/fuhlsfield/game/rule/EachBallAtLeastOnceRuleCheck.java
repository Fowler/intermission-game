package de.fuhlsfield.game.rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;

public class EachBallAtLeastOnceRuleCheck implements RuleCheck {

	private static final int UNDEFINED = -1;

	@Override
	public boolean isAttemptPossible(Ball ball, Game game) {
		Player player = game.determineNextPlayer();
		int ballValue = game.getBallValue(ball);
		int currentScore = game.getScoreCalculator().calculateScore(game.getGameScore(player));
		int pointsToScore = game.getTargetPoints() - currentScore - ballValue;
		Set<Ball> usedBalls = determineBallValuesFromAttemptResults(game, game.getGameScore(player)
				.getSuccessfulAttempts());
		usedBalls.add(ball);
		Set<Ball> ballsToScore = removeSet(game.getBalls(), usedBalls);
		return ballsToScore.isEmpty() || (pointsToScore > sumBallValuesExceptMin(game, ballsToScore));
	}

	private Set<Ball> removeSet(List<Ball> setA, Set<Ball> setB) {
		HashSet<Ball> setCopy = new HashSet<Ball>(setA);
		setCopy.removeAll(setB);
		return setCopy;
	}

	private Set<Ball> determineBallValuesFromAttemptResults(Game game, List<Attempt> attemptResults) {
		HashSet<Ball> balls = new HashSet<Ball>();
		for (Attempt attemptResult : attemptResults) {
			balls.add(attemptResult.getBall());
		}
		return balls;
	}

	private int sumBallValuesExceptMin(Game game, Set<Ball> balls) {
		int sum = 0;
		int min = UNDEFINED;
		for (Ball ball : balls) {
			int value = game.getBallValue(ball);
			sum += value;
			if ((min == UNDEFINED) || (value < min)) {
				min = value;
			}
		}
		return sum - min;
	}

}