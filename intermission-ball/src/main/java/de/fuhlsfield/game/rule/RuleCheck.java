package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.score.GameScoreKeeper;

public interface RuleCheck {

	boolean isPossibleAttempts(PossibleAttempts balls, GameScoreKeeper gameScoreKeeper);

}