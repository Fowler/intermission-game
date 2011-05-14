package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.rule.AttemptLeftRuleCheck;
import de.fuhlsfield.game.rule.BallTakesPartRuleCheck;
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
		return Arrays.asList(new BallTakesPartRuleCheck(), new EachBallAtLeastOnceRuleCheck(),
				new AttemptLeftRuleCheck(), new ExactCheckoutRuleCheck());
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