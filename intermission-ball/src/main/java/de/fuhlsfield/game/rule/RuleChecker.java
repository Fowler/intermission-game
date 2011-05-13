package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;

public class RuleChecker {

	public boolean isAttemptPossible(Ball ball, Game game) {
		for (RuleCheck ruleCheck : game.getGameConfig().getRuleChecks()) {
			if (!ruleCheck.isAttemptPossible(ball, game)) {
				return false;
			}
		}
		return true;
	}

}