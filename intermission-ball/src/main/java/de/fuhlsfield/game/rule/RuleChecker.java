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
	private final RuleCheckStateDeterminer ruleCheckStateDeterminer;
	private final RuleCheckDisposer ruleCheckDisposer;

	public RuleChecker(GameConfig gameConfig) {
		this.allowedBalls = gameConfig.getAllowedBalls();
		this.gameScoreCalculator = gameConfig.getGameScoreCalculator();
		this.targetPoints = gameConfig.getTargetPoints();
		this.possibleAttemptsCalculator = new PossibleAttemptsCalculator(this.gameScoreCalculator, this.targetPoints,
				gameConfig.getMaxAttempts(), new HashSet<Ball>(this.allowedBalls));
		this.ruleCheckStateDeterminer = new RuleCheckStateDeterminer(this.gameScoreCalculator, this.targetPoints);
		this.ruleCheckDisposer = new RuleCheckDisposer(gameConfig.getRuleChecks());
	}

	public Map<Ball, RuleCheckState> determineRuleCheckStates(GameScoreKeeper gameScoreKeeper) {
		HashMap<Ball, RuleCheckState> ruleCheckStates = new HashMap<Ball, RuleCheckState>();
		for (Ball ball : this.allowedBalls) {
			RuleCheckState ruleCheckState;
			if (!isAllowedPreCheck(ball, gameScoreKeeper)) {
				Set<PossibleAttempts> possibleAttempts = determineRuleCheckState(ball, gameScoreKeeper);
				ruleCheckState = this.ruleCheckStateDeterminer.determineRuleCheckState(ball, possibleAttempts,
						gameScoreKeeper);
			} else {
				ruleCheckState = this.ruleCheckStateDeterminer.determineRuleCheckState(ball, RuleCheckState.ALLOWED,
						gameScoreKeeper);
			}
			ruleCheckStates.put(ball, ruleCheckState);
		}
		return ruleCheckStates;
	}

	private Set<PossibleAttempts> determineRuleCheckState(Ball ball, GameScoreKeeper gameScoreKeeper) {
		Set<PossibleAttempts> possibleBallsLeft = this.possibleAttemptsCalculator.determinePossibleAttempts(ball,
				gameScoreKeeper);
		return this.ruleCheckDisposer.disposeRuleChecks(possibleBallsLeft, gameScoreKeeper);
	}

	private boolean isAllowedPreCheck(Ball ball, GameScoreKeeper gameScoreKeeper) {
		int remainingPoints = this.targetPoints
				- this.gameScoreCalculator.calculateScore(gameScoreKeeper, Arrays.asList(ball));
		return remainingPoints >= this.gameScoreCalculator.calculateScore(this.allowedBalls);
	}

}