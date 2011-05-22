package de.fuhlsfield.game.rule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.config.GameConfig;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class RuleChecker {

	private final List<Ball> allowedBalls;
	private final GameScoreCalculator gameScoreCalculator;
	private final int targetPoints;
	private final PossibleAttemptsCalculator possibleAttemptsCalculator;
	private final RuleCheckDisposer ruleCheckDisposer;

	public RuleChecker(GameConfig gameConfig, int maxAttempts) {
		this.allowedBalls = gameConfig.getAllowedBalls();
		this.gameScoreCalculator = gameConfig.getGameScoreCalculator();
		this.targetPoints = gameConfig.getTargetPoints();
		this.possibleAttemptsCalculator = new PossibleAttemptsCalculator(this.gameScoreCalculator, this.targetPoints,
				maxAttempts, new HashSet<Ball>(this.allowedBalls));
		this.ruleCheckDisposer = new RuleCheckDisposer(gameConfig.getRuleChecks());
	}

	public Map<Ball, RuleCheckState> determineRuleCheckStates(GameScoreKeeper gameScoreKeeper) {
		HashMap<Ball, RuleCheckState> result = new HashMap<Ball, RuleCheckState>();
		for (Ball ball : this.allowedBalls) {
			if (isAllowedPreCheck(ball, gameScoreKeeper)) {
				result.put(ball, RuleCheckState.ALLOWED);
			} else {
				Set<PossibleAttempts> balls = determineRuleCheckState(ball, gameScoreKeeper);
				result.put(ball, determineRuleCheckState(balls, gameScoreKeeper));
			}
		}
		return result;
	}

	private Set<PossibleAttempts> determineRuleCheckState(Ball ball, GameScoreKeeper gameScoreKeeper) {
		Set<PossibleAttempts> possibleBallsLeft = this.possibleAttemptsCalculator.determinePossibleAttempts(ball,
				gameScoreKeeper);
		return this.ruleCheckDisposer.disposeRuleChecks(possibleBallsLeft, gameScoreKeeper);
	}

	private RuleCheckState determineRuleCheckState(Set<PossibleAttempts> balls, GameScoreKeeper gameScoreKeeper) {
		if (balls.isEmpty()) {
			return RuleCheckState.NOT_ALLOWED;
		} else if (isCheckoutPossible(balls, gameScoreKeeper)) {
			return RuleCheckState.CHECKOUT;
		}
		return RuleCheckState.ALLOWED;
	}

	private boolean isAllowedPreCheck(Ball ball, GameScoreKeeper gameScoreKeeper) {
		int remainingPoints = this.targetPoints
				- this.gameScoreCalculator.calculateScore(gameScoreKeeper, Arrays.asList(ball));
		return remainingPoints >= this.gameScoreCalculator.calculateScore(this.allowedBalls);
	}

	private boolean isCheckoutPossible(Set<PossibleAttempts> allBalls, GameScoreKeeper gameScoreKeeper) {
		for (PossibleAttempts balls : allBalls) {
			if ((balls.size() == 1)
					&& (this.gameScoreCalculator.calculateScore(gameScoreKeeper, balls.toList()) >= this.targetPoints)) {
				return true;
			}
		}
		return false;
	}

}