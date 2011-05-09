package de.fuhlsfield.game.rule;

import java.util.Set;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.BallValue;
import de.fuhlsfield.game.Game;

public class BallTakesPartRuleCheck implements RuleCheck {

	@Override
	public boolean isAttemptPossible(Attempt attempt, Game game) {
		Set<BallValue> ballValues = game.getGameConfig().getBallValues();
		for (BallValue ballValue : ballValues) {
			if (ballValue.getBall() == attempt.getBall()) {
				return true;
			}
		}
		return false;
	}

}