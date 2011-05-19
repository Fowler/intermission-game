package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class PenaltyPointScoreCalculatorTest extends AbstractScoreCalculatorTest<PenaltyPointScoreCalculator> {

	@Test
	public void whenCalculateScoreForGamescoreKeeperWithTwoFailuresInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(7, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Test
	public void whenCalculateScoreForGamescoreKeeperWithThreeFailuresInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(
				Ball.SCHWAMMI, true));
		assertEquals(7, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Test
	public void whenCalculateScoreForGamescoreKeeperWithFourFailuresInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(6, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Test
	public void whenCalculateScoreForGamescoreKeeperWithTwoFailuresNotInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, false), new Attempt(
				Ball.BASKI, true), new Attempt(Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(10, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Test
	public void whenCalculateScoreForAttemptFirstAttemptFailure() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, false));
		assertEquals(0, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 0));
	}

	@Test
	public void whenCalculateScoreForAttemptSecondAttemptOfTwoFailuresInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, false), new Attempt(
				Ball.BASKI, false));
		assertEquals(-1, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 1));
	}

	@Test
	public void whenCalculateScoreForAttemptThirdAttemptOfThirdFailuresInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, false), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, false));
		assertEquals(0, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 2));
	}

	@Test
	public void whenCalculateScoreForAttemptThirdAttemptFailurePreviousAttemptSuccess() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, false), new Attempt(
				Ball.BASKI, true), new Attempt(Ball.SCHWAMMI, false));
		assertEquals(0, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 2));
	}

	@Override
	protected PenaltyPointScoreCalculator createInstanceUnderTest() {
		return new PenaltyPointScoreCalculator(this.ballValueMapper);
	}

}