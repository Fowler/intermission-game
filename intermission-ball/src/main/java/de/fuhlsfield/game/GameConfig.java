package de.fuhlsfield.game;

import java.util.List;

import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.BallValueMapper;
import de.fuhlsfield.game.score.GameScoreCalculator;

public interface GameConfig {

	String getName();

	int getTargetPoints();

	GameScoreCalculator getScoreCalculator();

	BallValueMapper getBallValueMapper();

	List<RuleCheck> getRuleChecks();

}