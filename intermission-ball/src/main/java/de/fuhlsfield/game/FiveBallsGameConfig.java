package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.BallValueMapper;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.StandardGameScoreCalculator;

public class FiveBallsGameConfig implements GameConfig {

	private static String NAME = "Fünfball Konfiguration";
	private static int TARGET_POINTS = 14;

	@Override
	public int getTargetPoints() {
		return TARGET_POINTS;
	}

	@Override
	public GameScoreCalculator getScoreCalculator() {
		return new StandardGameScoreCalculator(getBallValueMapper());
	}

	@Override
	public List<RuleCheck> getRuleChecks() {
		return Arrays.asList(new EachBallAtLeastOnceRuleCheck(getBallValueMapper().getBalls(), TARGET_POINTS,
				getScoreCalculator()), new ExactCheckoutRuleCheck(TARGET_POINTS, getScoreCalculator()));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public BallValueMapper getBallValueMapper() {
		BallValueMapper ballValueMapper = new BallValueMapper();
		ballValueMapper.addBall(Ball.BUNTI, 2);
		ballValueMapper.addBall(Ball.FROESCHI, 2);
		ballValueMapper.addBall(Ball.BASKI, 3);
		ballValueMapper.addBall(Ball.SCHWAMMI, 3);
		ballValueMapper.addBall(Ball.TISCHI_BALLI, 4);
		return ballValueMapper;
	}

}