package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class PenaltyPointGameScoreCalculatorTest extends
		AbstractGameScoreCalculatorTest<PenaltyPointGameScoreCalculator> {

	@Test
	public void testCalculateScoreForGamescoreKeeperWithTwoFailuresInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(7, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Test
	public void testCalculateScoreForGamescoreKeeperWithThreeFailuresInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(
				Ball.SCHWAMMI, true));
		assertEquals(7, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Test
	public void testCalculateScoreForGamescoreKeeperWithFourFailuresInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(6, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Test
	public void testCalculateScoreForGamescoreKeeperWithTwoFailuresNotInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, false), new Attempt(
				Ball.BASKI, true), new Attempt(Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(10, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Test
	public void testCalculateScoreForAttemptFirstAttemptFailure() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, false));
		assertEquals(0, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 0));
	}

	@Test
	public void testCalculateScoreForAttemptSecondAttemptOfTwoFailuresInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, false), new Attempt(
				Ball.BASKI, false));
		assertEquals(-1, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 1));
	}

	@Test
	public void testCalculateScoreForAttemptThirdAttemptOfThirdFailuresInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, false), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, false));
		assertEquals(0, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 2));
	}

	@Test
	public void testCalculateScoreForAttemptThirdAttemptFailurePreviousAttemptSuccess() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, false), new Attempt(
				Ball.BASKI, true), new Attempt(Ball.SCHWAMMI, false));
		assertEquals(0, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 2));
	}

	@Override
	protected PenaltyPointGameScoreCalculator createInstanceUnderTest() {
		return new PenaltyPointGameScoreCalculator(this.ballValueMapper);
	}

}