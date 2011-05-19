package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class GameScoreKeeperTest {

	@Test
	public void testAddAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		LinkedList<Attempt> expectedAttempts = new LinkedList<Attempt>();
		Attempt attemptA = new Attempt(Ball.BASKI, true);
		expectedAttempts.add(attemptA);
		gameScoreKeeper.addAttempt(attemptA);
		Attempt attemptB = new Attempt(Ball.BASKI, true);
		expectedAttempts.add(attemptB);
		gameScoreKeeper.addAttempt(attemptB);
		Attempt attemptC = new Attempt(Ball.BUNTI, false);
		expectedAttempts.add(attemptC);
		gameScoreKeeper.addAttempt(attemptC);
		assertEquals(expectedAttempts, gameScoreKeeper.getAttempts());
	}

	@Test
	public void testUndoLastAttemptWhenOneAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.addAttempt(new Attempt(Ball.BASKI, true));
		gameScoreKeeper.undoLastAttempt();
		assertTrue(gameScoreKeeper.getAttempts().isEmpty());
	}

	@Test
	public void testUndoLastAttemptWhenMoreThanOneAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		LinkedList<Attempt> expectedAttempts = new LinkedList<Attempt>();
		Attempt attemptA = new Attempt(Ball.BASKI, true);
		expectedAttempts.add(attemptA);
		gameScoreKeeper.addAttempt(attemptA);
		Attempt attemptB = new Attempt(Ball.SCHWAMMI, true);
		expectedAttempts.add(attemptB);
		gameScoreKeeper.addAttempt(attemptB);
		gameScoreKeeper.addAttempt(new Attempt(Ball.BUNTI, false));
		gameScoreKeeper.undoLastAttempt();
		assertEquals(expectedAttempts, gameScoreKeeper.getAttempts());
	}

	@Test
	public void testUndoLastAttemptWhenNoAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.undoLastAttempt();
		assertTrue(gameScoreKeeper.getAttempts().isEmpty());
	}

	@Test
	public void testIsUndoOfLastAttemptPossibleWhenOneAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.addAttempt(new Attempt(Ball.BASKI, true));
		assertTrue(gameScoreKeeper.isUndoLastAttemptPossible());
	}

	@Test
	public void testIsUndoOfLastAttemptPossibleWhenNoAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		assertFalse(gameScoreKeeper.isUndoLastAttemptPossible());
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
	public void testGetNumberOfAttempts() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.addAttempt(new Attempt(Ball.BASKI, true));
		gameScoreKeeper.addAttempt(new Attempt(Ball.BASKI, true));
		gameScoreKeeper.addAttempt(new Attempt(Ball.BUNTI, false));
		assertEquals(3, gameScoreKeeper.getNumberOfAttempts());
	}

	@Test
	public void testIsBallSuccessfulPlayedWhenNoAttempts() {
		assertFalse(createInstanceUnderTest().isBallSuccessfulPlayed(Ball.BASKI));
	}

	@Test
	public void testIsBallSuccessfulPlayedWhenSuccessfulAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.addAttempt(new Attempt(Ball.BASKI, true));
		assertTrue(gameScoreKeeper.isBallSuccessfulPlayed(Ball.BASKI));
	}

	@Test
	public void testIsBallSuccessfulPlayedWhenFailedAttempt() {
		GameScoreKeeper gameScoreKeeper = createInstanceUnderTest();
		gameScoreKeeper.addAttempt(new Attempt(Ball.BASKI, false));
		assertFalse(gameScoreKeeper.isBallSuccessfulPlayed(Ball.BASKI));
	}

	private GameScoreKeeper createInstanceUnderTest() {
		return new GameScoreKeeper();
	}

}