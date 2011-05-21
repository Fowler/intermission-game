package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.config.GameConfig;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class RuleChecker {

	private final Set<RuleCheck> ruleChecks;
	private final List<Ball> allowedBalls;
	private final GameScoreCalculator gameScoreCalculator;
	private final int targetPoints;
	private final int maxAttempts;

	public RuleChecker(GameConfig gameConfig, int maxAttempts) {
		this.ruleChecks = gameConfig.getRuleChecks();
		this.allowedBalls = gameConfig.getAllowedBalls();
		this.gameScoreCalculator = gameConfig.getGameScoreCalculator();
		this.targetPoints = gameConfig.getTargetPoints();
		this.maxAttempts = maxAttempts;

	}

	public Map<Ball, RuleCheckState> determineRuleCheckStates(GameScoreKeeper gameScoreKeeper) {
		HashMap<Ball, RuleCheckState> ruleCheckStates = new HashMap<Ball, RuleCheckState>();
		for (Ball ball : this.allowedBalls) {
			ruleCheckStates.put(ball, determineRuleCheckState(ball, gameScoreKeeper));
		}
		return ruleCheckStates;
	}

	private RuleCheckState determineRuleCheckState(Ball ball, GameScoreKeeper gameScoreKeeper) {
		int remainingPoints = this.targetPoints
				- this.gameScoreCalculator.calculateScore(gameScoreKeeper, Arrays.asList(ball));
		if (remainingPoints >= sumAllBallValues(this.allowedBalls)) {
			if (gameScoreKeeper.isBallSuccessfulPlayed(ball)) {
				return RuleCheckState.ALLOWED_AND_PLAYED;
			}
			return RuleCheckState.ALLOWED;
		}
		List<List<Ball>> possibleBallsLeft = determinePossibleBallsLeft(Arrays.asList(ball), gameScoreKeeper,
				this.allowedBalls);
		for (RuleCheck ruleCheck : this.ruleChecks) {
			possibleBallsLeft = ruleCheck.selectPossibleAttempts(possibleBallsLeft, gameScoreKeeper);
		}
		if (possibleBallsLeft.isEmpty()) {
			return RuleCheckState.NOT_ALLOWED;
		}
		if ((possibleBallsLeft.size() == 1) && (possibleBallsLeft.get(0).size() == 1)) {
			return RuleCheckState.CHECKOUT;
		}
		if (gameScoreKeeper.isBallSuccessfulPlayed(ball)) {
			return RuleCheckState.ALLOWED_AND_PLAYED;
		}
		return RuleCheckState.ALLOWED;
	}

	private List<List<Ball>> determinePossibleBallsLeft(List<Ball> possibleBallsLeft, GameScoreKeeper gameScoreKeeper,
			List<Ball> allowedBalls) {
		ArrayList<List<Ball>> resultPossibleBallsLeft = new ArrayList<List<Ball>>();
		resultPossibleBallsLeft.add(possibleBallsLeft);
		int numberOfAttempts = gameScoreKeeper.getNumberOfAttempts() + possibleBallsLeft.size();
		if ((numberOfAttempts < this.maxAttempts)
				&& (this.gameScoreCalculator.calculateScore(gameScoreKeeper, possibleBallsLeft) < this.targetPoints)) {
			resultPossibleBallsLeft.remove(possibleBallsLeft);
			for (Ball ball : allowedBalls) {
				if ((possibleBallsLeft.size() == 1)
						|| (ball.compareTo(possibleBallsLeft.get(possibleBallsLeft.size() - 1)) <= 0)) {
					List<Ball> newPossibleBallsLeft = new ArrayList<Ball>(possibleBallsLeft);
					newPossibleBallsLeft.add(ball);
					resultPossibleBallsLeft.addAll(determinePossibleBallsLeft(newPossibleBallsLeft, gameScoreKeeper,
							allowedBalls));
				}
			}
		}
		return resultPossibleBallsLeft;
	}

	private int sumAllBallValues(List<Ball> balls) {
		return this.gameScoreCalculator.calculateScore(balls);
	}

}