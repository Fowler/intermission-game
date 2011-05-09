package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Game;

public class PlayerTakesPartRuleCheck implements RuleCheck {

	@Override
	public boolean isAttemptPossible(Attempt attempt, Game game) {
		return game.getPlayers().contains(attempt.getPlayer());
	}
	
}