package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;

public interface RuleCheck {

	boolean isAttemptPossible(Ball ball, Game game);

}