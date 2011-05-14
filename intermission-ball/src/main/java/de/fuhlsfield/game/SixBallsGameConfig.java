package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.rule.CheckoutWithAllowedBallRuleCheck;
import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.BallValueMapper;
import de.fuhlsfield.game.score.PenaltyPointScoreCalculator;
import de.fuhlsfield.game.score.ScoreCalculator;

public class SixBallsGameConfig implements GameConfig {

	private static String NAME = "Sechsball Konfiguration";
	private static int TARGET_POINTS = 30;

	@Override
	public int getTargetPoints() {
		return TARGET_POINTS;
	}

	@Override
	public ScoreCalculator getScoreCalculator() {
		return new PenaltyPointScoreCalculator(getBallValueMapper());
	}

	@Override
	public List<RuleCheck> getRuleChecks() {
		return Arrays.asList(new EachBallAtLeastOnceRuleCheck(getBallValueMapper().getBalls()),
				new ExactCheckoutRuleCheck(getScoreCalculator(), TARGET_POINTS), new CheckoutWithAllowedBallRuleCheck(
						Arrays.asList(Ball.SCHRAEGI, Ball.BASKI, Ball.FLUMMI, Ball.TISCHI_BALLI, Ball.SCHWAMMI)));
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