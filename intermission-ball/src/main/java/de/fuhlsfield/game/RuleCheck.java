package de.fuhlsfield.game;

import de.fuhlsfield.game.score.GameScoreKeeper;

public interface RuleCheck {
	
	boolean isTryPossible (GameScoreKeeper gameScoreKeeper, Attempt attempt);
	
}