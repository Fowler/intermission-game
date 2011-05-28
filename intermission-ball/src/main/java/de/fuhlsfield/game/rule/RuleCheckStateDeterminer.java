package de.fuhlsfield.game.rule;

import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

class RuleCheckStateDeterminer {

	private final GameScoreCalculator gameScoreCalculator;
	private final int targetPoints;

	RuleCheckStateDeterminer(GameScoreCalculator gameScoreCalculator, int targetPoints) {
		this.gameScoreCalculator = gameScoreCalculator;
		this.targetPoints = targetPoints;
	}

	RuleCheckState determineRuleCheckState(Ball ball, Set<PossibleAttempts> possibleAttempts,
			GameScoreKeeper gameScoreKeeper) {
		RuleCheckState ruleCheckState;
		if (possibleAttempts.isEmpty()) {
			ruleCheckState = RuleCheckState.NOT_ALLOWED;
		} else if (isCheckoutPossible(possibleAttempts, gameScoreKeeper)) {
			ruleCheckState = RuleCheckState.CHECKOUT;
		} else {
			ruleCheckState = RuleCheckState.ALLOWED;
		}
		return determineRuleCheckState(ball, ruleCheckState, gameScoreKeeper);
	}

	RuleCheckState determineRuleCheckState(Ball ball, RuleCheckState ruleCheckState, GameScoreKeeper gameScoreKeeper) {
		if (gameScoreKeeper.getSuccessfulPlayedBalls().contains(ball)) {
			switch (ruleCheckState) {
			case ALLOWED:
				return RuleCheckState.ALLOWED_AND_PLAYED;
			case NOT_ALLOWED:
				return RuleCheckState.NOT_ALLOWED_AND_PLAYED;
			}
		}
		return ruleCheckState;
	}

	private boolean isCheckoutPossible(Set<PossibleAttempts> possibleAttempts, GameScoreKeeper gameScoreKeeper) {
		for (PossibleAttempts possibleAttempt : possibleAttempts) {
			if ((possibleAttempt.size() == 1)
					&& (this.gameScoreCalculator.calculateScore(gameScoreKeeper, possibleAttempt.toList()) >= this.targetPoints)) {
				return true;
			}
		}
		return false;
	}

}