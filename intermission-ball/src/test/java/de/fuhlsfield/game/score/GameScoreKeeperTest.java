package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class GameScoreKeeperTest {

	@Test
	public void testAddAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		Attempt attemptA = new Attempt(Ball.BASKI, true);
		gameScoreKeeper.addAttempt(attemptA);
		Attempt attemptB = new Attempt(Ball.BASKI, true);
		gameScoreKeeper.addAttempt(attemptB);
		Attempt attemptC = new Attempt(Ball.BUNTI, false);
		gameScoreKeeper.addAttempt(attemptC);
		assertEquals(attemptA, gameScoreKeeper.getAttemptByIndex(0));
		assertEquals(attemptB, gameScoreKeeper.getAttemptByIndex(1));
		assertEquals(attemptC, gameScoreKeeper.getAttemptByIndex(2));
		assertEquals(3, gameScoreKeeper.getNumberOfAttempts());
	}

	@Test
	public void testUndoLastAttemptWhenOneAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.addAttempt(new Attempt(Ball.BASKI, true));
		gameScoreKeeper.undoLastAttempt();
		assertEquals(0, gameScoreKeeper.getNumberOfAttempts());
	}

	@Test
	public void testUndoLastAttemptWhenMoreThanOneAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		Attempt attemptA = new Attempt(Ball.BASKI, true);
		gameScoreKeeper.addAttempt(attemptA);
		Attempt attemptB = new Attempt(Ball.SCHWAMMI, true);
		gameScoreKeeper.addAttempt(attemptB);
		gameScoreKeeper.addAttempt(new Attempt(Ball.BUNTI, false));
		gameScoreKeeper.undoLastAttempt();
		assertEquals(attemptA, gameScoreKeeper.getAttemptByIndex(0));
		assertEquals(attemptB, gameScoreKeeper.getAttemptByIndex(1));
		assertEquals(2, gameScoreKeeper.getNumberOfAttempts());
	}

	@Test
	public void testUndoLastAttemptWhenNoAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.undoLastAttempt();
		assertEquals(0, gameScoreKeeper.getNumberOfAttempts());
	}

	@Test
	public void testGetAttemptByIndexWhenNegativeIndex() {
		assertNull(createInstanceUnderTest().getAttemptByIndex(-1));
	}

	@Test
	public void testGetAttemptByIndexWhenLastIndex() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.addAttempt(new Attempt(Ball.BUNTI, true));
		Attempt expectedAttempt = new Attempt(Ball.BASKI, true);
		gameScoreKeeper.addAttempt(expectedAttempt);
		assertEquals(expectedAttempt, gameScoreKeeper.getAttemptByIndex(1));
	}

	@Test
	public void testGetAttemptByIndexWhenGreaterThanLastIndex() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.addAttempt(new Attempt(Ball.BUNTI, true));
		assertNull(gameScoreKeeper.getAttemptByIndex(1));
	}

	@Test
	public void testGetSuccessfulPlayedBallsWhenNoAttempts() {
		HashSet<Ball> expectedBalls = new HashSet<Ball>();
		assertEquals(expectedBalls, createInstanceUnderTest().getSuccessfulPlayedBalls());
	}

	@Test
	public void testGetSuccessfulPlayedBallsWhenSuccessfulAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.addAttempt(new Attempt(Ball.BASKI, true));
		HashSet<Ball> expectedBalls = new HashSet<Ball>(Arrays.asList(Ball.BASKI));
		assertEquals(expectedBalls, gameScoreKeeper.getSuccessfulPlayedBalls());
	}

	@Test
	public void testGetSuccessfulPlayedBallsWhenFailedAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.addAttempt(new Attempt(Ball.BASKI, false));
		HashSet<Ball> expectedBalls = new HashSet<Ball>();
		assertEquals(expectedBalls, gameScoreKeeper.getSuccessfulPlayedBalls());
	}

	private GameScoreKeeper createInstanceUnderTest() {
		return new GameScoreKeeper();
	}

}