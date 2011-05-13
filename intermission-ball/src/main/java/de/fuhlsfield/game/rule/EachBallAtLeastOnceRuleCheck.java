package de.fuhlsfield.game.rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.AttemptResult;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.GameConfig;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.BallValueMapper;

public class EachBallAtLeastOnceRuleCheck implements RuleCheck {

	private static final int UNDEFINED = -1;

	@Override
	public boolean isAttemptPossible(Attempt attempt, Game game) {
		GameConfig gameConfig = game.getGameConfig();
		Player player = attempt.getPlayer();
		int ballValue = game.getGameConfig().getBallValueMapper().getValue(attempt.getBall());
		int currentScore = gameConfig.getScoreCalculator().calculateScore(game.getGameScore(player));
		int pointsToScore = gameConfig.getTargetPoints() - currentScore - ballValue;
		Set<Ball> usedBalls = determineBallValuesFromAttemptResults(game, game.getGameScore(player)
				.getSuccessfulAttempts());
		usedBalls.add(attempt.getBall());
		Set<Ball> ballsToScore = removeSet(game.getGameConfig().getBallValueMapper().getBalls(), usedBalls);
		return ballsToScore.isEmpty()
				|| (pointsToScore > sumBallValuesExceptMin(game.getGameConfig().getBallValueMapper(), ballsToScore));
	}

	private Set<Ball> removeSet(List<Ball> setA, Set<Ball> setB) {
		HashSet<Ball> setCopy = new HashSet<Ball>(setA);
		setCopy.removeAll(setB);
		return setCopy;
	}

	private Set<Ball> determineBallValuesFromAttemptResults(Game game, List<AttemptResult> attemptResults) {
		HashSet<Ball> balls = new HashSet<Ball>();
		for (AttemptResult attemptResult : attemptResults) {
			balls.add(attemptResult.getBall());
		}
		return balls;
	}

	private int sumBallValuesExceptMin(BallValueMapper ballValueMapper, Set<Ball> balls) {
		int sum = 0;
		int min = UNDEFINED;
		for (Ball ball : balls) {
			int value = ballValueMapper.getValue(ball);
			sum += value;
			if ((min == UNDEFINED) || (value < min)) {
				min = value;
			}
		}
		return sum - min;
	}

}