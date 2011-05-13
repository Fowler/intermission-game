package de.fuhlsfield.game.rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.AttemptResult;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.BallValue;
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
		Set<BallValue> usedBalls = determineBallValuesFromAttemptResults(game, game.getGameScore(player)
				.getSuccessfulAttempts());
		usedBalls.add(new BallValue(attempt.getBall(), game.getBallValue(attempt.getBall())));
		Set<BallValue> ballsToScore = removeSet(determineBallValues(gameConfig.getBallValueMapper()), usedBalls);
		return ballsToScore.isEmpty() || (pointsToScore > sumBallValuesExceptMin(ballsToScore));
	}

	private Set<BallValue> determineBallValues(BallValueMapper ballValueMapper) {
		HashSet<BallValue> ballValues = new HashSet<BallValue>();
		for (Ball ball : ballValueMapper.getBalls()) {
			ballValues.add(new BallValue(ball, ballValueMapper.getValue(ball)));
		}
		return ballValues;
	}

	private Set<BallValue> removeSet(Set<BallValue> setA, Set<BallValue> setB) {
		HashSet<BallValue> setCopy = new HashSet<BallValue>(setA);
		setCopy.removeAll(setB);
		return setCopy;
	}

	private Set<BallValue> determineBallValuesFromAttemptResults(Game game, List<AttemptResult> attemptResults) {
		HashSet<BallValue> ballValues = new HashSet<BallValue>();
		for (AttemptResult attemptResult : attemptResults) {
			ballValues.add(new BallValue(attemptResult.getBall(), game.getBallValue(attemptResult.getBall())));
		}
		return ballValues;
	}

	private int sumBallValuesExceptMin(Set<BallValue> ballValues) {
		int sum = 0;
		int min = UNDEFINED;
		for (BallValue ballValue : ballValues) {
			int value = ballValue.getValue();
			sum += value;
			if ((min == UNDEFINED) || (value < min)) {
				min = value;
			}
		}
		return sum - min;
	}

}