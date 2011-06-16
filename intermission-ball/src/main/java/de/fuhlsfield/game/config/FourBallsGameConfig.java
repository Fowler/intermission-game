package de.fuhlsfield.game.config;

import java.util.HashSet;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.StandardGameScoreCalculator;

public class FourBallsGameConfig extends AbstractGameConfig {

	private static final long serialVersionUID = -198362472565740106L;

	private static final String NAME = "Vierball Konfiguration";
	private static final String SHORT_NAME = "fourBall";
	private static final int TARGET_POINTS = 21;
	private static final int BONUS_POINTS = 3;
	private static final int MAX_ATTEMPTS = 10;
	private static final int NUMBER_OF_GAMES = 10;

	@Override
	public String getName () {
		return NAME;
	}

	@Override
	public String getShortName () {
		return SHORT_NAME;
	}

	@Override
	public int getTargetPoints () {
		return TARGET_POINTS;
	}

	@Override
	public int getBonusPoints () {
		return BONUS_POINTS;
	}

	@Override
	public int getMaxAttempts () {
		return MAX_ATTEMPTS;
	}

	@Override
	public int getNumberOfGames () {
		return NUMBER_OF_GAMES;
	}

	@Override
	public GameScoreCalculator getGameScoreCalculator () {
		return new StandardGameScoreCalculator(this.ballValueMapper);
	}

	@Override
	protected void addBallValues () {
		this.ballValueMapper.addBall(Ball.BUNTI, 2);
		this.ballValueMapper.addBall(Ball.BASKI, 3);
		this.ballValueMapper.addBall(Ball.TISCHI_BALLI, 4);
		this.ballValueMapper.addBall(Ball.SCHWAMMI, 5);
	}

	@Override
	protected Set<RuleCheck> createRuleChecks () {
		HashSet<RuleCheck> ruleChecks = new HashSet<RuleCheck>();
		ruleChecks.add(new EachBallAtLeastOnceRuleCheck(getGameScoreCalculator(), TARGET_POINTS, new HashSet<Ball>(
				getAllowedBalls())));
		ruleChecks.add(new ExactCheckoutRuleCheck(getGameScoreCalculator(), TARGET_POINTS));
		return ruleChecks;
	}

}