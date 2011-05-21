package de.fuhlsfield.game.rule;

import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;

public interface RuleCheck {

	boolean isPossibleAttempts(List<Ball> balls, GameScoreKeeper gameScoreKeeper);

}