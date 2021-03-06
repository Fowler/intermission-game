package de.fuhlsfield.game.config;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.GameScoreCalculator;

public interface GameConfig extends Serializable {

	String getName();

	String getShortName();

	int getTargetPoints();

	int getBonusPoints();

	int getMaxAttempts();

	int getNumberOfGames();

	GameScoreCalculator getGameScoreCalculator();

	List<Ball> getAllowedBalls();

	Set<RuleCheck> getRuleChecks();

}