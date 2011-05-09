package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.GameConfig;

public class RuleChecker {
	
	public boolean isAttemptPossible (Attempt attempt, Game game) {
		GameConfig gameConfig = game.getGameConfig();
		for (RuleCheck ruleCheck : gameConfig.getRuleChecks()) {
			if (!ruleCheck.isAttemptPossible(attempt, game)) {
				return false;
			}
		}
		return true;
	}
	
}