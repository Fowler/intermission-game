package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class SeasonScoreKeeperTest {

	@Test
	public void testAddGameScoreKeeper() {
		SeasonScoreKeeper seasonScoreKeeper = createInstanceUnderTest();
		GameScoreKeeper gameScoreKeeperA = createGameScoreKeeper();
		seasonScoreKeeper.addGameScoreKeeper(gameScoreKeeperA);
		GameScoreKeeper gameScoreKeeperB = createGameScoreKeeper();
		seasonScoreKeeper.addGameScoreKeeper(gameScoreKeeperB);
		assertEquals(gameScoreKeeperA, seasonScoreKeeper.getGameScoreKeeperByIndex(0));
		assertEquals(gameScoreKeeperB, seasonScoreKeeper.getGameScoreKeeperByIndex(1));
		assertEquals(2, seasonScoreKeeper.getNumberOfGameScoreKeepers());
	}

	@Test
	public void testGetGameScoreKeeperByIndexWhenNegativeIndex() {
		assertNull(createInstanceUnderTest().getGameScoreKeeperByIndex(-1));
	}

	@Test
	public void testGetGameScoreKeeperByIndexWhenLastIndex() {
		SeasonScoreKeeper seasonScoreKeeper = createInstanceUnderTest();
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper());
		GameScoreKeeper expectedGameScoreKeeper = createGameScoreKeeper();
		seasonScoreKeeper.addGameScoreKeeper(expectedGameScoreKeeper);
		assertEquals(expectedGameScoreKeeper, seasonScoreKeeper.getGameScoreKeeperByIndex(1));
	}

	@Test
	public void testGetGameScoreKeeperByIndexWhenGreaterThanLastIndex() {
		SeasonScoreKeeper seasonScoreKeeper = createInstanceUnderTest();
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper());
		assertNull(seasonScoreKeeper.getGameScoreKeeperByIndex(1));
	}

	private GameScoreKeeper createGameScoreKeeper() {
		return mock(GameScoreKeeper.class);
	}

	private SeasonScoreKeeper createInstanceUnderTest() {
		return new SeasonScoreKeeper();
	}

}