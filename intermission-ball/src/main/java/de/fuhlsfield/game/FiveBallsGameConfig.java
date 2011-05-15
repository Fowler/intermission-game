package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.BallValueMapper;
import de.fuhlsfield.game.score.ScoreCalculator;
import de.fuhlsfield.game.score.StandardScoreCalculator;

public class FiveBallsGameConfig implements GameConfig {

	private static String NAME = "Fünfball Konfiguration";
	private static int TARGET_POINTS = 14;

	@Override
	public int getTargetPoints() {
		return TARGET_POINTS;
	}

	@Override
	public ScoreCalculator getScoreCalculator() {
		return new StandardScoreCalculator(getBallValueMapper());
	}

	@Override
	public List<RuleCheck> getRuleChecks() {
		return Arrays.asList(new EachBallAtLeastOnceRuleCheck(getBallValueMapper().getBalls(), getScoreCalculator(),
				TARGET_POINTS), new ExactCheckoutRuleCheck(getScoreCalculator(), TARGET_POINTS));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public BallValueMapper getBallValueMapper() {
		BallValueMapper ballValueMapper = new BallValueMapper();
		ballValueMapper.add(Ball.BUNTI, 2);
		ballValueMapper.add(Ball.FROESCHI, 2);
		ballValueMapper.add(Ball.BASKI, 3);
		ballValueMapper.add(Ball.SCHWAMMI, 3);
		ballValueMapper.add(Ball.TISCHI_BALLI, 4);
		return ballValueMapper;
	}

}