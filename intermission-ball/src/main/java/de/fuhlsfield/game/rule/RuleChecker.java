package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.config.GameConfig;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class RuleChecker {

	private final RuleCheckDisposer ruleCheckDisposer;
	private final List<Ball> allowedBalls;
	private final GameScoreCalculator gameScoreCalculator;
	private final int targetPoints;
	private final int maxAttempts;

	public RuleChecker(GameConfig gameConfig, int maxAttempts) {
		this.ruleCheckDisposer = new RuleCheckDisposer(gameConfig.getRuleChecks());
		this.allowedBalls = gameConfig.getAllowedBalls();
		this.gameScoreCalculator = gameConfig.getGameScoreCalculator();
		this.targetPoints = gameConfig.getTargetPoints();
		this.maxAttempts = maxAttempts;
	}

	public Map<Ball, RuleCheckState> determineRuleCheckStates(GameScoreKeeper gameScoreKeeper) {
		HashMap<Ball, RuleCheckState> result = new HashMap<Ball, RuleCheckState>();
		for (Ball ball : this.allowedBalls) {
			if (isAllowedPreCheck(ball, gameScoreKeeper)) {
				result.put(ball, RuleCheckState.ALLOWED);
			} else {
				List<List<Ball>> balls = determineRuleCheckState(ball, gameScoreKeeper);
				result.put(ball, determineRuleCheckState(balls, gameScoreKeeper));
			}
		}
		return result;
	}

	private List<List<Ball>> determineRuleCheckState(Ball ball, GameScoreKeeper gameScoreKeeper) {
		List<List<Ball>> possibleBallsLeft = determinePossibleBallsLeft(Arrays.asList(ball), gameScoreKeeper);
		return this.ruleCheckDisposer.disposeRuleChecks(possibleBallsLeft, gameScoreKeeper);
	}

	private RuleCheckState determineRuleCheckState(List<List<Ball>> balls, GameScoreKeeper gameScoreKeeper) {
		if (balls.isEmpty()) {
			return RuleCheckState.NOT_ALLOWED;
		} else if ((balls.size() == 1) && (balls.get(0).size() == 1)
				&& (this.gameScoreCalculator.calculateScore(gameScoreKeeper, balls.get(0)) >= this.targetPoints)) {
			return RuleCheckState.CHECKOUT;
		}
		return RuleCheckState.ALLOWED;
	}

	private boolean isAllowedPreCheck(Ball ball, GameScoreKeeper gameScoreKeeper) {
		int remainingPoints = this.targetPoints
				- this.gameScoreCalculator.calculateScore(gameScoreKeeper, Arrays.asList(ball));
		return remainingPoints >= this.gameScoreCalculator.calculateScore(this.allowedBalls);
	}

	private List<List<Ball>> determinePossibleBallsLeft(List<Ball> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		LinkedList<List<Ball>> resultPossibleBallsLeft = new LinkedList<List<Ball>>();
		resultPossibleBallsLeft.add(possibleBallsLeft);
		int remainingAttempts = this.maxAttempts - gameScoreKeeper.getNumberOfAttempts() - possibleBallsLeft.size();
		if ((remainingAttempts > 0)
				&& (this.gameScoreCalculator.calculateScore(gameScoreKeeper, possibleBallsLeft) < this.targetPoints)) {
			resultPossibleBallsLeft.remove(possibleBallsLeft);
			for (Ball ball : this.allowedBalls) {
				if ((possibleBallsLeft.size() == 1)
						|| (ball.compareTo(possibleBallsLeft.get(possibleBallsLeft.size() - 1)) <= 0)) {
					List<Ball> newPossibleBallsLeft = new ArrayList<Ball>(possibleBallsLeft);
					newPossibleBallsLeft.add(ball);
					resultPossibleBallsLeft.addAll(determinePossibleBallsLeft(newPossibleBallsLeft, gameScoreKeeper));
				}
			}
		}
		return resultPossibleBallsLeft;
	}

}