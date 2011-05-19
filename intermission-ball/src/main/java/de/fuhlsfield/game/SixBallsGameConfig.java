package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.rule.CheckoutWithAllowedBallRuleCheck;
import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.BallValueMapper;
import de.fuhlsfield.game.score.PenaltyPointGameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreCalculator;

public class SixBallsGameConfig implements GameConfig {

	private static String NAME = "Sechsball Konfiguration";
	private static int TARGET_POINTS = 30;

	@Override
	public int getTargetPoints() {
		return TARGET_POINTS;
	}

	@Override
	public GameScoreCalculator getScoreCalculator() {
		return new PenaltyPointGameScoreCalculator(getBallValueMapper());
	}

	@Override
	public List<RuleCheck> getRuleChecks() {
		return Arrays.asList(new EachBallAtLeastOnceRuleCheck(getBallValueMapper().getBalls(), TARGET_POINTS,
				getScoreCalculator()), new ExactCheckoutRuleCheck(TARGET_POINTS, getScoreCalculator()),
				new CheckoutWithAllowedBallRuleCheck(Arrays.asList(Ball.SCHRAEGI, Ball.BASKI, Ball.FLUMMI,
						Ball.TISCHI_BALLI, Ball.SCHWAMMI), TARGET_POINTS, getScoreCalculator()));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public BallValueMapper getBallValueMapper() {
		BallValueMapper ballValueMapper = new BallValueMapper();
		ballValueMapper.add(Ball.NORMALI, 1);
		ballValueMapper.add(Ball.SCHRAEGI, 2);
		ballValueMapper.add(Ball.BASKI, 3);
		ballValueMapper.add(Ball.FLUMMI, 4);
		ballValueMapper.add(Ball.TISCHI_BALLI, 5);
		ballValueMapper.add(Ball.SCHWAMMI, 6);
		return ballValueMapper;
	}

}