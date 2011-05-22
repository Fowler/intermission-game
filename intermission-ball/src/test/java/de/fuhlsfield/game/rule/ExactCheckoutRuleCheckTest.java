package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.StandardGameScoreCalculator;

public class ExactCheckoutRuleCheckTest extends AbstractRuleCheckTest<ExactCheckoutRuleCheck> {

	@Test
	public void testIsPossibleAttemptsWhenScoreLessTargetPointsWithoutSuccessfulAttempts() {
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreEqualsTargetPointsWithoutSuccessfulAttempts() {
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreGreaterTargetPointsWithoutSuccessfulAttempts() {
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI);
		assertFalse(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreLessTargetPointsWithSuccessfulAttempts() {
		addAttempts(Ball.BASKI);
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreEqualsTargetPointsWithSuccessfulAttempts() {
		addAttempts(Ball.BASKI);
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI, Ball.SCHWAMMI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreGreaterTargetPointsWithSuccessfulAttempts() {
		addAttempts(Ball.BUNTI);
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI);
		assertFalse(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Override
	protected ExactCheckoutRuleCheck createInstanceUnderTest() {
		return new ExactCheckoutRuleCheck(new StandardGameScoreCalculator(this.ballValueMapper), TARGET_POINTS);
	}

}
