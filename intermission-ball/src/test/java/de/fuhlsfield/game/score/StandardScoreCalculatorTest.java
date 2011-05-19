package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

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
		when(this.ballValueMapper.getValue(Ball.TISCHI_BALLI)).thenReturn(13);
	}

	@Test
	public void whenCalculateScoreForGamescoreKeeper() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(8, createInstanceUnderTest().calculateScore(gameScoreKeeper));
	}

	@Test
	public void whenCalculateScoreForSuccessfulAttempts() {
		LinkedList<Ball> balls = new LinkedList<Ball>();
		balls.add(Ball.BASKI);
		balls.add(Ball.TISCHI_BALLI);
		assertEquals(16, createInstanceUnderTest().calculateScore(balls));
	}

	@Test
	public void whenCalculateScoreForGamescoreKeeperAndAdditionalSuccessfulAttempts() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		LinkedList<Ball> balls = new LinkedList<Ball>();
		balls.add(Ball.TISCHI_BALLI);
		balls.add(Ball.BASKI);
		assertEquals(24, createInstanceUnderTest().calculateScore(gameScoreKeeper, balls));
	}

	@Test
	public void whenCalculateForSeasonScoreKeeperFirstGame() {
		SeasonScoreKeeper seasonScoreKeeper = new SeasonScoreKeeper();
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true)));
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper(new Attempt(Ball.BASKI, true), new Attempt(
				Ball.SCHWAMMI, false), new Attempt(Ball.TISCHI_BALLI, true)));
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper(new Attempt(Ball.SCHWAMMI, true), new Attempt(
				Ball.TISCHI_BALLI, false), new Attempt(Ball.NORMALI, true)));
		assertEquals(8, createInstanceUnderTest().calculateScore(seasonScoreKeeper, 0));
	}

	@Test
	public void whenCalculateForSeasonScoreKeeperLastGame() {
		SeasonScoreKeeper seasonScoreKeeper = new SeasonScoreKeeper();
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true)));
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper(new Attempt(Ball.BASKI, true), new Attempt(
				Ball.SCHWAMMI, false), new Attempt(Ball.TISCHI_BALLI, true)));
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper(new Attempt(Ball.SCHWAMMI, true), new Attempt(
				Ball.TISCHI_BALLI, false), new Attempt(Ball.NORMALI, true)));
		assertEquals(32, createInstanceUnderTest().calculateScore(seasonScoreKeeper, 2));
	}

	@Test
	public void whenCalculateForSeasonScoreKeeperAfterLastGame() {
		SeasonScoreKeeper seasonScoreKeeper = new SeasonScoreKeeper();
		assertEquals(ScoreCalculator.UNDEFINED_SCORE, createInstanceUnderTest().calculateScore(seasonScoreKeeper, 0));
	}

	@Test
	public void whenCalculateScoreForAttemptFirstAttempt() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(1, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 0));
	}

	@Test
	public void whenCalculateScoreForAttemptLastAttempt() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper(new Attempt(Ball.NORMALI, true), new Attempt(
				Ball.BASKI, false), new Attempt(Ball.SCHWAMMI, true));
		assertEquals(7, createInstanceUnderTest().calculateScoreForAttempt(gameScoreKeeper, 2));
	}

	@Test
	public void whenCalculateScoreForAttemptAfterLastAttempt() {
		GameScoreKeeper gameScoreKeeper = createGameScoreKeeper();
		assertEquals(ScoreCalculator.UNDEFINED_SCORE, createInstanceUnderTest().calculateScoreForAttempt(
				gameScoreKeeper, 0));
	}

	private GameScoreKeeper createGameScoreKeeper(Attempt... attempts) {
		GameScoreKeeper gameScoreKeeper = new GameScoreKeeper();
		for (Attempt attempt : attempts) {
			gameScoreKeeper.addAttempt(attempt);
		}
		return gameScoreKeeper;
	}

	private StandardScoreCalculator createInstanceUnderTest() {
		return new StandardScoreCalculator(this.ballValueMapper);
	}

}