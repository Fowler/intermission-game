package de.fuhlsfield.game.config;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.rule.CheckoutWithAllowedBallRuleCheck;
import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.PenaltyPointGameScoreCalculator;

public class SixBallsGameConfigTest extends AbstractGameConfigTest<SixBallsGameConfig> {

	private static int EXPECTED_TARGET_POINTS = 30;

	@Test
	public void testGetName() {
		assertEquals("Sechsball Konfiguration", createInstanceUnderTest().getName());
	}

	@Test
	public void testGetTargetPoints() {
		assertEquals(EXPECTED_TARGET_POINTS, createInstanceUnderTest().getTargetPoints());
	}

	@Test
	public void testGetBonusPoints() {
		assertEquals(4, createInstanceUnderTest().getBonusPoints());
	}

	@Test
	public void testGetMaxAttempts() {
		assertEquals(15, createInstanceUnderTest().getMaxAttempts());
	}

	@Test
	public void testGetNumberOgGames() {
		assertEquals(10, createInstanceUnderTest().getNumberOfGames());
	}

	@Test
	public void testGetRuleChecks() {
		HashSet<RuleCheck> expectedRuleChecks = new HashSet<RuleCheck>();
		expectedRuleChecks.add(new EachBallAtLeastOnceRuleCheck(createExpectedGameScoreCalculator(),
				EXPECTED_TARGET_POINTS, new HashSet<Ball>(createExpectedBalls())));
		expectedRuleChecks.add(new ExactCheckoutRuleCheck(createExpectedGameScoreCalculator(), EXPECTED_TARGET_POINTS));
		expectedRuleChecks.add(new CheckoutWithAllowedBallRuleCheck(createExpectedGameScoreCalculator(),
				EXPECTED_TARGET_POINTS, createExpectedAllowedCheckoutBalls()));
		assertEquals(expectedRuleChecks, createInstanceUnderTest().getRuleChecks());
	}

	@Override
	protected GameScoreCalculator createExpectedGameScoreCalculator() {
		return new PenaltyPointGameScoreCalculator(createExpectedBallValueMapper());
	}

	@Override
	protected BallValueMapper createExpectedBallValueMapper() {
		BallValueMapper expectedBallValueMapper = new BallValueMapper();
		expectedBallValueMapper.addBall(Ball.NORMALI, 1);
		expectedBallValueMapper.addBall(Ball.SCHRAEGI, 2);
		expectedBallValueMapper.addBall(Ball.BASKI, 3);
		expectedBallValueMapper.addBall(Ball.FLUMMI, 4);
		expectedBallValueMapper.addBall(Ball.TISCHI_BALLI, 5);
		expectedBallValueMapper.addBall(Ball.SCHWAMMI, 6);
		return expectedBallValueMapper;
	}

	@Override
	protected SixBallsGameConfig createInstanceUnderTest() {
		return new SixBallsGameConfig();
	}

	private Set<Ball> createExpectedAllowedCheckoutBalls() {
		return new HashSet<Ball>(Arrays
				.asList(Ball.SCHRAEGI, Ball.BASKI, Ball.FLUMMI, Ball.TISCHI_BALLI, Ball.SCHWAMMI));
	}

}