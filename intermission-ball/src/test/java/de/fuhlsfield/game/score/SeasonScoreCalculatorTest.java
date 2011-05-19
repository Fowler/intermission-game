package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class SeasonScoreCalculatorTest {

	private final GameScoreCalculator gameScoreCalculator = mock(GameScoreCalculator.class);

	@Test
	public void whenCalculateForSeasonScoreKeeperFirstGame() {
		SeasonScoreKeeper seasonScoreKeeper = new SeasonScoreKeeper();
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper(8));
		assertEquals(8, createInstanceUnderTest().calculateScore(seasonScoreKeeper, 0));
	}

	@Test
	public void whenCalculateForSeasonScoreKeeperLastGame() {
		SeasonScoreKeeper seasonScoreKeeper = new SeasonScoreKeeper();
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper(8));
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper(13));
		seasonScoreKeeper.addGameScoreKeeper(createGameScoreKeeper(32));
		assertEquals(53, createInstanceUnderTest().calculateScore(seasonScoreKeeper, 2));
	}

	@Test
	public void whenCalculateForSeasonScoreKeeperAfterLastGame() {
		SeasonScoreKeeper seasonScoreKeeper = new SeasonScoreKeeper();
		assertEquals(ScoreConstants.UNDEFINED_SCORE, createInstanceUnderTest().calculateScore(seasonScoreKeeper, 0));
	}

	private GameScoreKeeper createGameScoreKeeper(int score) {
		GameScoreKeeper gameScoreKeeper = mock(GameScoreKeeper.class);
		when(this.gameScoreCalculator.calculateScore(gameScoreKeeper)).thenReturn(score);
		return gameScoreKeeper;
	}

	private SeasonScoreCalculator createInstanceUnderTest() {
		return new SeasonScoreCalculator(this.gameScoreCalculator);
	}

}