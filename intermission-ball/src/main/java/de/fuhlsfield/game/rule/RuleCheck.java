package de.fuhlsfield.game.rule;

import java.io.Serializable;

import de.fuhlsfield.game.score.GameScoreKeeper;

public interface RuleCheck extends Serializable {

	boolean isPossibleAttempts(PossibleAttempts balls, GameScoreKeeper gameScoreKeeper);

}