package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import de.fuhlsfield.game.GameConfig;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class PlayerSequenceDeterminerTest {

	private static final int TARGET_POINTS = 5;
	private static final int MAX_ATTEMPTS = 2;

	private final GameConfig gameConfig = mock(GameConfig.class);
	private final GameScoreCalculator gameScoreCalculator = mock(GameScoreCalculator.class);
	private final Player playerA = mock(Player.class);
	private final GameScoreKeeper gameScoreKeeperA = mock(GameScoreKeeper.class);
	private final Player playerB = mock(Player.class);
	private final GameScoreKeeper gameScoreKeeperB = mock(GameScoreKeeper.class);

	@Before
	public void beforeTest() {
		when(this.gameConfig.getScoreCalculator()).thenReturn(this.gameScoreCalculator);
		when(this.gameConfig.getTargetPoints()).thenReturn(TARGET_POINTS);
	}

	@Test
	public void testIsGameNotFinishedWhenFirstPlayerReachedTargetPointsAndSecondPlayerHasLessAttempts() {
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperA)).thenReturn(TARGET_POINTS);
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(1);
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperB)).thenReturn(0);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(0);
		assertFalse(createInstanceUnderTest().isGameFinished());
	}

	@Test
	public void testIsGameFinishedWhenFirstPlayerReachedTargetPointsAndSecondPlayerHasSameAttempts() {
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperA)).thenReturn(TARGET_POINTS);
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(1);
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperB)).thenReturn(0);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(1);
		assertTrue(createInstanceUnderTest().isGameFinished());
	}

	@Test
	public void testIsGameNotFinishedWhenFirstPlayerReachedMaxAttemptsAndSecondPlayerHasLessAttempts() {
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperA)).thenReturn(0);
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(MAX_ATTEMPTS);
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperB)).thenReturn(0);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(MAX_ATTEMPTS - 1);
		assertFalse(createInstanceUnderTest().isGameFinished());
	}

	@Test
	public void testIsGameFinishedWhenFirstPlayerReachedMaxAttemptsAndSecondPlayerHasSameAttempts() {
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperA)).thenReturn(0);
		when(this.gameScoreKeeperA.getNumberOfAttempts()).thenReturn(MAX_ATTEMPTS);
		when(this.gameScoreCalculator.calculateScore(this.gameScoreKeeperB)).thenReturn(0);
		when(this.gameScoreKeeperB.getNumberOfAttempts()).thenReturn(MAX_ATTEMPTS);
		assertTrue(createInstanceUnderTest().isGameFinished());
	}

	private PlayerSequenceDeterminer createInstanceUnderTest() {
		PlayerSequenceDeterminer playerSequenceDeterminer = new PlayerSequenceDeterminer(this.gameConfig, MAX_ATTEMPTS);
		playerSequenceDeterminer.addPlayer(this.playerA, this.gameScoreKeeperA);
		playerSequenceDeterminer.addPlayer(this.playerB, this.gameScoreKeeperB);
		return playerSequenceDeterminer;
	}

}