package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class PlayerSequenceDeterminerTest {

	private static final int TARGET_POINTS = 5;
	private static final int MAX_ATTEMPTS = 2;

	private final GameScoreCalculator gameScoreCalculator = mock(GameScoreCalculator.class);
	private final Map<Player, GameScoreKeeper> gameScoreKeepers = new HashMap<Player, GameScoreKeeper>();
	private final Player playerA = mock(Player.class);
	private final GameScoreKeeper gameScoreKeeperA = mock(GameScoreKeeper.class);
	private final Player playerB = mock(Player.class);
	private final GameScoreKeeper gameScoreKeeperB = mock(GameScoreKeeper.class);

	@Before
	public void beforeTest() {
		this.gameScoreKeepers.put(this.playerA, this.gameScoreKeeperA);
		this.gameScoreKeepers.put(this.playerB, this.gameScoreKeeperB);
	}

	@Test
	public void testDetermineNextPlayerWhenSecondPlayerHasLessAttempts() {
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(1);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(0);
		assertEquals(this.playerB, createInstanceUnderTest().determineNextPlayer(this.gameScoreKeepers));
	}

	@Test
	public void testDetermineNextPlayerWhenSecondPlayerHasEqualAttempts() {
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(1);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(1);
		assertEquals(this.playerA, createInstanceUnderTest().determineNextPlayer(this.gameScoreKeepers));
	}

	@Test
	public void testDetermineNextPlayerWhenGameFinished() {
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(MAX_ATTEMPTS);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(MAX_ATTEMPTS);
		assertEquals(Player.NO_PLAYER, createInstanceUnderTest().determineNextPlayer(this.gameScoreKeepers));
	}

	@Test
	public void testDeterminePreviousPlayerWhenSecondPlayerHasLessAttempts() {
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(1);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(0);
		assertEquals(this.playerA, createInstanceUnderTest().determinePreviousPlayer(this.gameScoreKeepers));
	}

	@Test
	public void testDeterminePreviousPlayerWhenSecondPlayerHasEqualAttempts() {
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(1);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(1);
		assertEquals(this.playerB, createInstanceUnderTest().determinePreviousPlayer(this.gameScoreKeepers));
	}

	@Test
	public void testDeterminePreviousPlayerWhenNoPlayerHasAnyAttempt() {
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(0);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(0);
		assertEquals(Player.NO_PLAYER, createInstanceUnderTest().determinePreviousPlayer(this.gameScoreKeepers));
	}

	@Test
	public void testIsGameNotFinishedWhenFirstPlayerReachedTargetPointsAndSecondPlayerHasLessAttempts() {
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperA)).thenReturn(TARGET_POINTS);
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(1);
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperB)).thenReturn(0);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(0);
		assertFalse(createInstanceUnderTest().isGameFinished(this.gameScoreKeepers));
	}

	@Test
	public void testIsGameFinishedWhenFirstPlayerReachedTargetPointsAndSecondPlayerHasEqualAttempts() {
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperA)).thenReturn(TARGET_POINTS);
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(1);
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperB)).thenReturn(0);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(1);
		assertTrue(createInstanceUnderTest().isGameFinished(this.gameScoreKeepers));
	}

	@Test
	public void testIsGameNotFinishedWhenFirstPlayerReachedMaxAttemptsAndSecondPlayerHasLessAttempts() {
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperA)).thenReturn(0);
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(MAX_ATTEMPTS);
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperB)).thenReturn(0);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(MAX_ATTEMPTS - 1);
		assertFalse(createInstanceUnderTest().isGameFinished(this.gameScoreKeepers));
	}

	@Test
	public void testIsGameFinishedWhenFirstPlayerReachedMaxAttemptsAndSecondPlayerHasEqualAttempts() {
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperA)).thenReturn(0);
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(MAX_ATTEMPTS);
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperB)).thenReturn(0);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(MAX_ATTEMPTS);
		assertTrue(createInstanceUnderTest().isGameFinished(this.gameScoreKeepers));
	}

	private PlayerSequenceDeterminer createInstanceUnderTest() {
		PlayerSequenceDeterminer playerSequenceDeterminer = new PlayerSequenceDeterminer(this.gameScoreCalculator,
				TARGET_POINTS, MAX_ATTEMPTS, Arrays.asList(this.playerA, this.playerB));
		return playerSequenceDeterminer;
	}

}