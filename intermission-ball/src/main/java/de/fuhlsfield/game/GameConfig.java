package de.fuhlsfield.game;

import java.util.List;

import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.BallValueMapper;
import de.fuhlsfield.game.score.ScoreCalculator;

public interface GameConfig {

	String getName();

	int getTargetPoints();

	ScoreCalculator getScoreCalculator();

	BallValueMapper getBallValueMapper();

	List<RuleCheck> getRuleChecks();

}