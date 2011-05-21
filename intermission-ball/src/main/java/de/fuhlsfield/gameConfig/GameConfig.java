package de.fuhlsfield.gameConfig;

import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.GameScoreCalculator;

public interface GameConfig {

	String getName();

	int getTargetPoints();

	GameScoreCalculator getGameScoreCalculator();

	List<Ball> getAllowedBalls();

	List<RuleCheck> getRuleChecks();

}