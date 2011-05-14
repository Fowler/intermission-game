package de.fuhlsfield.game.rule;

import java.util.HashSet;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;

public class ExactCheckoutRuleCheck implements RuleCheck {

	private static final int UNDEFINED = -1;

	@Override
	public boolean isAttemptPossible(Ball ball, Game game) {
		int ballValue = game.getBallValue(ball);
		int currentScore = game.getScoreCalculator().calculateScore(game.getGameScore(game.determineNextPlayer()));
		int pointsToScore = game.getTargetPoints() - currentScore - ballValue;
		return isTargetPointsAchievable(pointsToScore, determineAtomicPoints(game), new HashSet<Integer>());
	}

	private Set<Integer> determineAtomicPoints(Game game) {
		HashSet<Integer> atomicPoints = new HashSet<Integer>();
		for (Ball ball : game.getBalls()) {
			atomicPoints.add(game.getBallValue(ball));
		}
		return atomicPoints;
	}

	private boolean isTargetPointsAchievable(int targetPoints, Set<Integer> atomicPoints, Set<Integer> combinedPoints) {
		if ((targetPoints == 0) || combinedPoints.contains(targetPoints)) {
			return true;
		}
		int min = determineMinimum(combinedPoints);
		if ((min != UNDEFINED) && (min > targetPoints)) {
			return false;
		}
		return isTargetPointsAchievable(targetPoints, atomicPoints, calculatePossibleSums(atomicPoints, combinedPoints));
	}

	private int determineMinimum(Set<Integer> values) {
		int min = UNDEFINED;
		for (int value : values) {
			if ((min == UNDEFINED) || (value < min)) {
				min = value;
			}
		}
		return min;
	}

	private Set<Integer> calculatePossibleSums(Set<Integer> valuesA, Set<Integer> valuesB) {
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