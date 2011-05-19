package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class StandardScoreCalculatorTest extends AbstractScoreCalculatorTest<StandardScoreCalculator> {

	@Test
	public void whenCalculateScoreForGamescoreKeeperWithTwoFailurenInSuccession() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(8, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Override
	protected StandardScoreCalculator createInstanceUnderTest() {
		return new StandardScoreCalculator(this.ballValueMapper);
	}

}