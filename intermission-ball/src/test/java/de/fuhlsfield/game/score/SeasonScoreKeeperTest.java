package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.LinkedList;

import org.junit.Test;

public class SeasonScoreKeeperTest {

	@Test
	public void testAddGameScoreKeeper() {
		SeasonScoreKeeper seasonScoreKeeper = createInstanceUnderTest();
		LinkedList<GameScoreKeeper> expectedGameScoreKeepers = new LinkedList<GameScoreKeeper>();
		GameScoreKeeper gameScoreKeeperA = createGameScoreKeeper();
		expectedGameScoreKeepers.add(gameScoreKeeperA);
		seasonScoreKeeper.addGameScoreKeeper(gameScoreKeeperA);
		GameScoreKeeper gameScoreKeeperB = createGameScoreKeeper();
		expectedGameScoreKeepers.add(gameScoreKeeperB);
		seasonScoreKeeper.addGameScoreKeeper(gameScoreKeeperB);
		assertEquals(expectedGameScoreKeepers, seasonScoreKeeper.getGameScoreKeepers());
	}

	@Test
	public void testGetNumberOfGameScoreKeepers() {
		SeasonScoreKeeper seasonScoreKeeper = createInstanceUnderTest();
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper());
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper());
		assertEquals(2, seasonScoreKeeper.getNumberOfGameScoreKeepers());
	}

	private GameScoreKeeper createGameScoreKeeper() {
		return mock(GameScoreKeeper.class);
	}

	private SeasonScoreKeeper createInstanceUnderTest() {
		return new SeasonScoreKeeper();
	}

}