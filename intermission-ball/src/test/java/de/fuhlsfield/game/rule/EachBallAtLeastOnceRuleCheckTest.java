package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.StandardGameScoreCalculator;

public class EachBallAtLeastOnceRuleCheckTest extends AbstractRuleCheckTest<EachBallAtLeastOnceRuleCheck> {

	private int targetPoints;

	@Override
	@Before
	public void beforeTest () {
		super.beforeTest();
		this.targetPoints = TARGET_POINTS;
	}

	@Test
	public void testIsPossibleAttemptsWhenEachBallPlayedAndScoreEqualsTargetPointsWithoutSuccessfulAttempt () {
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenEachBallPlayedAndOneBallTwiceAndScoreGreaterTargetPointsWithoutSuccessfulAttempt () {
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenNotEachBallPlayedAndScoreLessTargetPointsWithoutSuccessfulAttempt () {
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BASKI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenNotEachBallPlayedAndOneBallPlayedTwiceAndScoreLessTargetPointsWithoutSuccessfulAttempt () {
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BUNTI, Ball.BASKI);
		assertFalse(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenEachBallPlayedAndOneBallTwiceAndScoreGreaterTargetPointsWithSuccessfulAttempt () {
		addAttempts(Ball.BASKI);
		PossibleAttempts ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BUNTI, Ball.SCHWAMMI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenNotEachBallPlayedAndOneBallTwiceAndScoreLessTargetPointsWithSuccessfulAttempt () {
		addAttempts(Ball.BUNTI);
		PossibleAttempts ballsLeft = createBallsLeft(Ball.SCHWAMMI, Ball.BUNTI);
		assertFalse(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptWhenNotEachBallPlayedAndNextBallLastRemainingBallAndScoreLessTargetPointsWithSuccessfulAttempt () {
		this.targetPoints = 13;
		addAttempts(Ball.BUNTI);
		addAttempts(Ball.BASKI);
		PossibleAttempts ballsLeft = createBallsLeft(Ball.SCHWAMMI, Ball.BUNTI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Override
	protected EachBallAtLeastOnceRuleCheck createInstanceUnderTest () {
		HashSet<Ball> ballsToPlay = new HashSet<Ball>(Arrays.asList(Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI));
		return new EachBallAtLeastOnceRuleCheck(new StandardGameScoreCalculator(this.ballValueMapper),
				this.targetPoints, ballsToPlay);
	}

}