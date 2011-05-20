package de.fuhlsfield.game.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.GameConfig;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class RuleCheckStateDeterminer {

	private final RuleChecker ruleChecker;
	private final List<Ball> allowedBalls;

	public RuleCheckStateDeterminer(GameConfig gameConfig, int maxAttempts) {
		this.ruleChecker = new RuleChecker(gameConfig.getRuleChecks(), gameConfig.getScoreCalculator(), gameConfig
				.getTargetPoints(), maxAttempts);
		this.allowedBalls = gameConfig.getBallValueMapper().getBalls();
	}

	public Map<Ball, RuleCheckState> determineRuleCheckStates(GameScoreKeeper gameScoreKeeper) {
		HashMap<Ball, RuleCheckState> ruleCheckStates = new HashMap<Ball, RuleCheckState>();
		for (Ball ball : this.allowedBalls) {
			ruleCheckStates.put(ball, this.ruleChecker
					.determineRuleCheckState(ball, gameScoreKeeper, this.allowedBalls));
		}
		return ruleCheckStates;
	}

}