package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class StandardGameScoreCalculatorTest extends AbstractGameScoreCalculatorTest<StandardGameScoreCalculator> {

	@Test
	public void whenCalculateScoreForGamescoreKeeperWithTwoFailurenInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(8, createInstanceUnderTest().calculateScore(gameScoreKeeper));
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
		assertEquals(0, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 1));
	}

	@Override
	protected StandardGameScoreCalculator createInstanceUnderTest() {
		return new StandardGameScoreCalculator(this.ballValueMapper);
	}

}