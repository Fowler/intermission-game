package de.fuhlsfield.game.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.rule.CheckoutWithAllowedBallRuleCheck;
import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.PenaltyPointGameScoreCalculator;

public class SixBallsGameConfig extends AbstractGameConfig {

	private static String NAME = "Sechsball Konfiguration";
	private static int TARGET_POINTS = 30;
	private static int BONUS_POINTS = 4;
	private static int MAX_ATTEMPTS = 10;
	private static int NUMBER_OF_GAMES = 10;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getTargetPoints() {
		return TARGET_POINTS;
	}

	@Override
	public int getBonusPoints() {
		return BONUS_POINTS;
	}

	@Override
	public int getMaxAttempts() {
		return MAX_ATTEMPTS;
	}

	@Override
	public int getNumberOfGames() {
		return NUMBER_OF_GAMES;
	}

	@Override
	public GameScoreCalculator getGameScoreCalculator() {
		return new PenaltyPointGameScoreCalculator(this.ballValueMapper);
	}

	@Override
	protected void addBallValues() {
		this.ballValueMapper.addBall(Ball.NORMALI, 1);
		this.ballValueMapper.addBall(Ball.SCHRAEGI, 2);
		this.ballValueMapper.addBall(Ball.BASKI, 3);
		this.ballValueMapper.addBall(Ball.FLUMMI, 4);
		this.ballValueMapper.addBall(Ball.TISCHI_BALLI, 5);
		this.ballValueMapper.addBall(Ball.SCHWAMMI, 6);
	}

	@Override
	protected Set<RuleCheck> createRuleChecks() {
		HashSet<RuleCheck> ruleChecks = new HashSet<RuleCheck>();
		ruleChecks.add(new EachBallAtLeastOnceRuleCheck(getGameScoreCalculator(), TARGET_POINTS, new HashSet<Ball>(
				getAllowedBalls())));
		ruleChecks.add(new ExactCheckoutRuleCheck(getGameScoreCalculator(), TARGET_POINTS));
		ruleChecks.add(new CheckoutWithAllowedBallRuleCheck(getGameScoreCalculator(), TARGET_POINTS,
				createAllowedCheckoutBalls()));
		return ruleChecks;
	}

	private Set<Ball> createAllowedCheckoutBalls() {
		return new HashSet<Ball>(Arrays
				.asList(Ball.SCHRAEGI, Ball.BASKI, Ball.FLUMMI, Ball.TISCHI_BALLI, Ball.SCHWAMMI));
	}

}