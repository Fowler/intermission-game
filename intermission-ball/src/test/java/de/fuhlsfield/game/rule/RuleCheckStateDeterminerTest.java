package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.config.BallValueMapper;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.StandardGameScoreCalculator;

public class RuleCheckStateDeterminerTest {

	private static final int TARGET_POINTS = 11;

	private final GameScoreKeeper gameScoreKeeper = new GameScoreKeeper();
	private final BallValueMapper ballValueMapper = mock(BallValueMapper.class);

	@Before
	public void beforeTest() {
		when(this.ballValueMapper.getValue(Ball.BUNTI)).thenReturn(1);
		when(this.ballValueMapper.getValue(Ball.BASKI)).thenReturn(3);
		when(this.ballValueMapper.getValue(Ball.SCHWAMMI)).thenReturn(7);
	}

	@Test
	public void testDetermineRuleCheckStateForStateNotAllowed() {
		HashSet<PossibleAttempts> possibleAttempts = new HashSet<PossibleAttempts>();
		assertEquals(RuleCheckState.NOT_ALLOWED, createInstanceUnderTest().determineRuleCheckState(Ball.BASKI,
				possibleAttempts, this.gameScoreKeeper));
	}

	@Test
	public void testDetermineRuleCheckStateForStateNotAllowedAndPlayed() {
		HashSet<PossibleAttempts> possibleAttempts = new HashSet<PossibleAttempts>();
		addSuccessfulPlayedBalls(Ball.BASKI);
		assertEquals(RuleCheckState.NOT_ALLOWED_AND_PLAYED, createInstanceUnderTest().determineRuleCheckState(
				Ball.BASKI, possibleAttempts, this.gameScoreKeeper));
	}

	@Test
	public void testDetermineRuleCheckStateForStateAllowed() {
		HashSet<PossibleAttempts> possibleAttempts = new HashSet<PossibleAttempts>();
		possibleAttempts.add(new PossibleAttempts(Ball.BUNTI));
		addSuccessfulPlayedBalls(Ball.BASKI, Ball.BASKI, Ball.BASKI);
		assertEquals(RuleCheckState.ALLOWED, createInstanceUnderTest().determineRuleCheckState(Ball.BUNTI,
				possibleAttempts, this.gameScoreKeeper));
	}

	@Test
	public void testDetermineRuleCheckStateForStateAllowedAndPlayed() {
		HashSet<PossibleAttempts> possibleAttempts = new HashSet<PossibleAttempts>();
		possibleAttempts.add(new PossibleAttempts(Ball.BASKI));
		addSuccessfulPlayedBalls(Ball.BASKI, Ball.BUNTI, Ball.BASKI);
		assertEquals(RuleCheckState.ALLOWED_AND_PLAYED, createInstanceUnderTest().determineRuleCheckState(Ball.BASKI,
				possibleAttempts, this.gameScoreKeeper));
	}

	@Test
	public void testDetermineRuleCheckStateForStateCheckoutWhenOnePossibleAttempts() {
		HashSet<PossibleAttempts> possibleAttempts = new HashSet<PossibleAttempts>();
		possibleAttempts.add(new PossibleAttempts(Ball.BASKI));
		addSuccessfulPlayedBalls(Ball.BUNTI, Ball.SCHWAMMI);
		assertEquals(RuleCheckState.CHECKOUT, createInstanceUnderTest().determineRuleCheckState(Ball.BASKI,
				possibleAttempts, this.gameScoreKeeper));
	}

	@Test
	public void testDetermineRuleCheckStateForStateCheckoutWhenMoreThanOnePossibleAttempts() {
		HashSet<PossibleAttempts> possibleAttempts = new HashSet<PossibleAttempts>();
		possibleAttempts.add(new PossibleAttempts(Ball.BUNTI));
		possibleAttempts.add(new PossibleAttempts(Ball.BASKI));
		addSuccessfulPlayedBalls(Ball.BUNTI, Ball.SCHWAMMI);
		assertEquals(RuleCheckState.CHECKOUT, createInstanceUnderTest().determineRuleCheckState(Ball.BASKI,
				possibleAttempts, this.gameScoreKeeper));
	}

	@Test
	public void testDetermineRuleCheckStateForStateCheckoutForStateAllowedWhenGivenStateAllowed() {
		addSuccessfulPlayedBalls(Ball.BUNTI, Ball.SCHWAMMI);
		assertEquals(RuleCheckState.ALLOWED, createInstanceUnderTest().determineRuleCheckState(Ball.BASKI,
				RuleCheckState.ALLOWED, this.gameScoreKeeper));
	}

	@Test
	public void testDetermineRuleCheckStateForStateCheckoutForStateAllowedAndPlayedWhenGivenStateAllowed() {
		addSuccessfulPlayedBalls(Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI);
		assertEquals(RuleCheckState.ALLOWED_AND_PLAYED, createInstanceUnderTest().determineRuleCheckState(Ball.BASKI,
				RuleCheckState.ALLOWED, this.gameScoreKeeper));
	}

	@Test
	public void testDetermineRuleCheckStateForStateCheckoutForStateNowAllowedWhenGivenStateNotAllowed() {
		addSuccessfulPlayedBalls(Ball.BUNTI, Ball.SCHWAMMI);
		assertEquals(RuleCheckState.NOT_ALLOWED, createInstanceUnderTest().determineRuleCheckState(Ball.BASKI,
				RuleCheckState.NOT_ALLOWED, this.gameScoreKeeper));
	}

	@Test
	public void testDetermineRuleCheckStateForStateCheckoutForStateNowAllowedAndPlayedWhenGivenStateNotAllowed() {
		addSuccessfulPlayedBalls(Ball.BUNTI, Ball.BASKI, Ball.SCHWAMMI);
		assertEquals(RuleCheckState.NOT_ALLOWED_AND_PLAYED, createInstanceUnderTest().determineRuleCheckState(
				Ball.BASKI, RuleCheckState.NOT_ALLOWED, this.gameScoreKeeper));
	}

	private void addSuccessfulPlayedBalls(Ball... balls) {
		for (Ball ball : balls) {
			this.gameScoreKeeper.addAttempt(new Attempt(ball, true));
		}
	}

	private RuleCheckStateDeterminer createInstanceUnderTest() {
		return new RuleCheckStateDeterminer(new StandardGameScoreCalculator(this.ballValueMapper), TARGET_POINTS);
	}

}