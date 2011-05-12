package de.fuhlsfield.game.rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.BallValue;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.GameConfig;

public class ExactCheckoutRuleCheck implements RuleCheck {
	
	private static final int UNDEFINED = -1;

	@Override
	public boolean isAttemptPossible(Attempt attempt, Game game) {
		GameConfig gameConfig = game.getGameConfig();
		int ballValue = game.getBallValue(attempt.getBall()).getValue();
		int currentScore = gameConfig.getScoreCalculator().calculateScore(game, attempt.getPlayer());
		int pointsToScore = gameConfig.getTargetPoints() - currentScore - ballValue;
		return isTargetPointsAchievable(pointsToScore, determineAtomicPoints(gameConfig.getBallValues()), new HashSet<Integer>());
	}
	
	private Set<Integer> determineAtomicPoints (List<BallValue> ballValues) {
		HashSet<Integer> atomicPoints = new HashSet<Integer>();
		for (BallValue ballValue : ballValues) {
			atomicPoints.add(ballValue.getValue());
		}
		return atomicPoints;
	}
	
	private boolean isTargetPointsAchievable (int targetPoints, Set<Integer> atomicPoints, Set<Integer> combinedPoints) {
		if ((targetPoints == 0) || combinedPoints.contains(targetPoints)) {
			return true;
		}
		int min = determineMinimum(combinedPoints);
		if ((min != UNDEFINED) && (min > targetPoints)) {
			return false;
		}
		return isTargetPointsAchievable (targetPoints, atomicPoints, calculatePossibleSums(atomicPoints, combinedPoints));
	}
	
	private int determineMinimum (Set<Integer> values) {
		int min = UNDEFINED;
		for (int value : values) {
			if ((min == UNDEFINED) || (value < min)) {
				min = value;
			}
		}
		return min;
	}
	
	private Set<Integer> calculatePossibleSums (Set<Integer> valuesA, Set<Integer> valuesB) {
		if (valuesB.isEmpty()) {
			return valuesA;
		}
		HashSet<Integer> possibleSums = new HashSet<Integer>();
		for (int valueA : valuesA) {
			for (int valueB : valuesB) {
				possibleSums.add(valueA + valueB);
			}
		}
		return possibleSums;
	}
	
}