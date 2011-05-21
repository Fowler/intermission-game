package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.config.BallValueMapper;

public abstract class AbstractGameScoreCalculatorTest<T extends GameScoreCalculator> {

	protected final BallValueMapper ballValueMapper = mock(BallValueMapper.class);

	@Before
	public void beforeTest() {
		when(this.ballValueMapper.getValue(Ball.NORMALI)).thenReturn(1);
		when(this.ballValueMapper.getValue(Ball.BASKI)).thenReturn(3);
		when(this.ballValueMapper.getValue(Ball.SCHWAMMI)).thenReturn(7);
		when(this.ballValueMapper.getValue(Ball.TISCHI_BALLI)).thenReturn(13);
	}

	@Test
	public final void testCalculateScoreForGamescoreKeeper() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(8, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Test
	public final void testCalculateScoreForSuccessfulAttempts() {
		LinkedList<Ball> balls = new LinkedList<Ball>();
		balls.add(Ball.BASKI);
		balls.add(Ball.TISCHI_BALLI);
		assertEquals(16, createInstanceUnderTest().calculateScore(balls));
	}

	@Test
	public final void testCalculateScoreForGamescoreKeeperAndAdditionalSuccessfulAttempts() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		LinkedList<Ball> balls = new LinkedList<Ball>();
		balls.add(Ball.TISCHI_BALLI);
		balls.add(Ball.BASKI);
		assertEquals(24, createInstanceUnderTest().calculateScore(gameScoreKeeper, balls));
	}

	@Test
	public final void testCalculateScoreForAttemptFirstAttempt() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true));
		assertEquals(1, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 0));
	}

	@Test
	public final void testCalculateScoreForAttemptLastAttempt() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(7, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 2));
	}

	@Test
	public final void testCalculateScoreForAttemptAfterLastAttempt() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper();
		assertEquals(ScoreConstants.UNDEFINED_SCORE, createInstanceUnderTest().calculateScoreForAttempt(
				gameScoreKeeper, 0));
	}

	protected GameScoreKeeper createGameScoreKeeper(Attempt... attempts) {
		GameScoreKeeper gameScoreKeeper = new GameScoreKeeper();
		for (Attempt attempt : attempts) {
			gameScoreKeeper.addAttempt(attempt);
		}
		return gameScoreKeeper;
	}

	protected abstract T createInstanceUnderTest();

}