package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.StandardGameScoreCalculator;

public class EachBallAtLeastOnceRuleCheckTest extends AbstractRuleCheckTest<EachBallAtLeastOnceRuleCheck> {

	@Test
	public void testIsPossibleAttemptsWhenEachBallPlayedAndScoreEqualsTargetPointsWithoutSuccessfulAttempt() {
		List<Ball> ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenEachBallPlayedAndOneBallTwiceAndScoreGreaterTargetPointsWithoutSuccessfulAttempt() {
		List<Ball> ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenNotEachBallPlayedAndScoreLessTargetPointsWithoutSuccessfulAttempt() {
		List<Ball> ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BASKI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenNotEachBallPlayedAndOneBallPlayedTwiceAndScoreLessTargetPointsWithoutSuccessfulAttempt() {
		List<Ball> ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BUNTI, Ball.BASKI);
		assertFalse(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenEachBallPlayedAndOneBallTwiceAndScoreGreaterTargetPointsWithSuccessfulAttempt() {
		addAttempts(Ball.BASKI);
		List<Ball> ballsLeft = createBallsLeft(Ball.BUNTI, Ball.BUNTI, Ball.SCHWAMMI);
		assertTrue(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Test
	public void testIsPossibleAttemptsWhenEachBallPlayedAndOneBallTwiceAndScoreLessTargetPointsWithSuccessfulAttempt() {
		addAttempts(Ball.BUNTI);
		List<Ball> ballsLeft = createBallsLeft(Ball.SCHWAMMI, Ball.BUNTI);
		assertFalse(createInstanceUnderTest().isPossibleAttempts(ballsLeft, this.gameScoreKeeper));
	}

	@Override
	protected EachBallAtLeastOnceRuleCheck createInstanceUnderTest() {
		HashSet<Ball> ballsToPlay = new HashSet<Ball>(Arrays.asList(Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI));
		return new EachBallAtLeastOnceRuleCheck(new StandardGameScoreCalculator(this.ballValueMapper), TARGET_POINTS,
				ballsToPlay);
	}

}