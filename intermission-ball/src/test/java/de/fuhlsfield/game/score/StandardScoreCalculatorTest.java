package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class StandardScoreCalculatorTest {

	private final BallValueMapper ballValueMapper = mock(BallValueMapper.class);

	@Before
	public void beforeTest() {
		when(this.ballValueMapper.getValue(Ball.NORMALI)).thenReturn(1);
		when(this.ballValueMapper.getValue(Ball.BASKI)).thenReturn(3);
		when(this.ballValueMapper.getValue(Ball.SCHWAMMI)).thenReturn(7);
	}

	@Test
	public void whenCalculateScoreForGamescoreKeeper() {
		GameScoreKeeper gameScoreKeeper = new GameScoreKeeper();
		gameScoreKeeper.addAttempt(new Attempt(Ball.NORMALI, true));
		gameScoreKeeper.addAttempt(new Attempt(Ball.BASKI, false));
		gameScoreKeeper.addAttempt(new Attempt(Ball.SCHWAMMI, true));
		assertEquals(8, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	private StandardScoreCalculator createInstanceUnderTest() {
		return new StandardScoreCalculator(this.ballValueMapper);
	}

}