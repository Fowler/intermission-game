package de.fuhlsfield.game.rule;

import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;

public interface RuleCheck {

	List<List<Ball>> selectPossibleAttempts(List<List<Ball>> possibleBallsLeft, GameScoreKeeper gameScoreKeeper);

}