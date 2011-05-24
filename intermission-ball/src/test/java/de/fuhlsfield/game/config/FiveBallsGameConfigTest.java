package de.fuhlsfield.game.config;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.StandardGameScoreCalculator;

public class FiveBallsGameConfigTest extends AbstractGameConfigTest<FiveBallsGameConfig> {

	private static int EXPECTED_TARGET_POINTS = 14;

	@Test
	public void testGetName() {
		assertEquals("Fünfball Konfiguration", createInstanceUnderTest().getName());
	}

	@Test
	public void testGetTargetPoints() {
		assertEquals(EXPECTED_TARGET_POINTS, createInstanceUnderTest().getTargetPoints());
	}

	@Test
	public void testGetBonusPoints() {
		assertEquals(2, createInstanceUnderTest().getBonusPoints());
	}

	@Test
	public void testGetMaxAttempts() {
		assertEquals(10, createInstanceUnderTest().getMaxAttempts());
	}

	@Test
	public void testGetNumberOgGames() {
		assertEquals(10, createInstanceUnderTest().getNumberOfGames());
	}

	@Test
	public void testGetRuleChecks() {
		HashSet<RuleCheck> expectedRuleChecks = new HashSet<RuleCheck>();
		expectedRuleChecks.add(new EachBallAtLeastOnceRuleCheck(new StandardGameScoreCalculator(
				createExpectedBallValueMapper()), EXPECTED_TARGET_POINTS, new HashSet<Ball>(createExpectedBalls())));
		expectedRuleChecks.add(new ExactCheckoutRuleCheck(new StandardGameScoreCalculator(
				createExpectedBallValueMapper()), EXPECTED_TARGET_POINTS));
		assertEquals(expectedRuleChecks, createInstanceUnderTest().getRuleChecks());
	}

	@Override
	protected GameScoreCalculator createExpectedGameScoreCalculator() {
		return new StandardGameScoreCalculator(createExpectedBallValueMapper());
	}

	@Override
	protected BallValueMapper createExpectedBallValueMapper() {
		BallValueMapper expectedBallValueMapper = new BallValueMapper();
		expectedBallValueMapper.addBall(Ball.BUNTI, 2);
		expectedBallValueMapper.addBall(Ball.FROESCHI, 2);
		expectedBallValueMapper.addBall(Ball.BASKI, 3);
		expectedBallValueMapper.addBall(Ball.SCHWAMMI, 3);
		expectedBallValueMapper.addBall(Ball.TISCHI_BALLI, 4);
		return expectedBallValueMapper;
	}

	@Override
	protected FiveBallsGameConfig createInstanceUnderTest() {
		return new FiveBallsGameConfig();
	}

}