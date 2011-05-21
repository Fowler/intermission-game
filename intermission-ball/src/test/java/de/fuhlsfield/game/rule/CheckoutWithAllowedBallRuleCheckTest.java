package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.StandardGameScoreCalculator;

public class CheckoutWithAllowedBallRuleCheckTest extends AbstractRuleCheckTest<CheckoutWithAllowedBallRuleCheck> {

	@Test
	public void testIsPossibleAttemptsWhenScoreEqualsTargetPointsAndLastBallDefinitelyNotNotAllowedBallAndNoSuccessfulAttempt() {
		List<Ball> ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreEqualsTargetPointsAndLastBallDefinitelyNotNotAllowedBallAndOneSuccessfulAttempt() {
		addAttempts(Ball.SCHWAMMI);
		List<Ball> ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BASKI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreEqualsTargetPointsAndLastBallPossiblyNotAllowedBall() {
		List<Ball> ballsLeft = createBallsLeft(Ball.BASKI, Ball.SCHWAMMI, Ball.BUNTI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreEqualsTargetPointsAndLastBallDefinitlyNotAllowedAndOneAttemptLeft() {
		addAttempts(Ball.BASKI, Ball.SCHWAMMI);
		List<Ball> ballsLeft = createBallsLeft(Ball.BUNTI);
		assertFalse(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreEqualsTargetPointsAndLastBallDefinitlyNotAllowedAndMoreThanOneAttemptLeft() {
		addAttempts(Ball.SCHWAMMI);
		List<Ball> ballsLeft = createBallsLeft(Ball.BASKI, Ball.BUNTI);
		assertFalse(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreLessTargetPointsAndLastBallDefinitlyNotAllowedAndMoreThanOneAttemptLeftAndNoSuccessfulAttempt() {
		List<Ball> ballsLeft = createBallsLeft(Ball.SCHWAMMI, Ball.BUNTI, Ball.BUNTI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreLessTargetPointsAndLastBallDefinitlyNotAllowedAndMoreThanOneAttemptLeftAndOneSuccessfulAttempt() {
		addAttempts(Ball.SCHWAMMI);
		List<Ball> ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BUNTI, Ball.BUNTI, Ball.BUNTI);
		assertFalse(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenScoreLessTargetPointsAndLastBallDefinitlyNotAllowedAndOneAttemptLeft() {
		addAttempts(Ball.SCHWAMMI);
		List<Ball> ballsLeft = createBallsLeft(Ball.BUNTI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Override
	protected CheckoutWithAllowedBallRuleCheck createInstanceUnderTest() {
		HashSet<Ball> allowedBalls = new HashSet<Ball>(Arrays.asList(Ball.BASKI, Ball.SCHWAMMI));
		return new CheckoutWithAllowedBallRuleCheck(new StandardGameScoreCalculator(this.ballValueMapper),
				TARGET_POINTS, allowedBalls);
	}

}