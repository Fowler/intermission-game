package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Game;

public class AttemptLeftRuleCheck implements RuleCheck {

	@Override
	public boolean isAttemptPossible(Attempt attempt, Game game) {
		return game.getGameScore(attempt.getPlayer()).getIndexOfLastAttempt() + 1 < game.getGameConfig().getMaxRounds(); 
	}

}