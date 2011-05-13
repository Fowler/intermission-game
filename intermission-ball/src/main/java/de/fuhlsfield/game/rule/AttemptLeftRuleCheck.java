package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;

public class AttemptLeftRuleCheck implements RuleCheck {

	@Override
	public boolean isAttemptPossible(Ball ball, Game game) {
		return game.getGameScore(game.determineNextPlayer()).getIndexOfLastAttempt() + 1 < game.getMaxRounds();
	}

}