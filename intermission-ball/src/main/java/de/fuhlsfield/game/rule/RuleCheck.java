package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Game;

public interface RuleCheck {
	
	boolean isAttemptPossible (Attempt attempt, Game game);
	
}