package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Game;

public class BallTakesPartRuleCheck implements RuleCheck {

	@Override
	public boolean isAttemptPossible(Attempt attempt, Game game) {
		return game.getGameConfig().getBallValueMapper().getValue(
				attempt.getBall()) != null;
	}

}