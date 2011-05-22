package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class ExactCheckoutRuleCheck extends AbstractRuleCheck {

	public ExactCheckoutRuleCheck(GameScoreCalculator gameScoreCalculator, int targetPoints) {
		super(gameScoreCalculator, targetPoints);
	}

	@Override
	public boolean isPossibleAttempts(PossibleAttempts balls, GameScoreKeeper gameScoreKeeper) {
		return this.gameScoreCalculator.calculateScore(gameScoreKeeper, balls.toList()) <= this.targetPoints;
	}
}