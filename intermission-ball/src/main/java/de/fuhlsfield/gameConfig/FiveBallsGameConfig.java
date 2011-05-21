package de.fuhlsfield.gameConfig;

import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.StandardGameScoreCalculator;

public class FiveBallsGameConfig extends AbstractGameConfig {

	private static String NAME = "Fünfball Konfiguration";
	private static int TARGET_POINTS = 14;

	@Override
	public int getTargetPoints() {
		return TARGET_POINTS;
	}

	@Override
	public GameScoreCalculator getGameScoreCalculator() {
		return new StandardGameScoreCalculator(this.ballValueMapper);
	}

	@Override
	public List<RuleCheck> getRuleChecks() {
		return Arrays.asList(new EachBallAtLeastOnceRuleCheck(getAllowedBalls(), TARGET_POINTS,
				getGameScoreCalculator()), new ExactCheckoutRuleCheck(TARGET_POINTS, getGameScoreCalculator()));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected void addBallValues() {
		this.ballValueMapper.addBall(Ball.BUNTI, 2);
		this.ballValueMapper.addBall(Ball.FROESCHI, 2);
		this.ballValueMapper.addBall(Ball.BASKI, 3);
		this.ballValueMapper.addBall(Ball.SCHWAMMI, 3);
		this.ballValueMapper.addBall(Ball.TISCHI_BALLI, 4);
	}

}