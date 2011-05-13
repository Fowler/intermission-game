package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;

public class BallTakesPartRuleCheck implements RuleCheck {

	@Override
	public boolean isAttemptPossible(Ball ball, Game game) {
		return game.getGameConfig().getBallValueMapper().getValue(ball) != null;
	}

}