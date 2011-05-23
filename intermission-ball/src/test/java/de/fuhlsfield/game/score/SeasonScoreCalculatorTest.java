package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.fuhlsfield.game.Player;

public class SeasonScoreCalculatorTest {

	private final Map<Player, SeasonScoreKeeper> seasonScoreKeepers = new HashMap<Player, SeasonScoreKeeper>();
	private final GameScoreCalculator gameScoreCalculator = mock(GameScoreCalculator.class);
	private final Player playerA = mock(Player.class);
	private final Player playerB = mock(Player.class);

	@Test
	public void testCalculateScoreWhenSeasonScoreKeeperFirstGame() {
		addSeasonScoreKeeper(this.playerA, createGameScoreKeeper(8));
		assertEquals(8, createInstanceUnderTest(0).calculateScore(this.seasonScoreKeepers, this.playerA, 0));
	}

	@Test
	public void testCalculateScoreWhenSeasonScoreKeeperLastGame() {
		addSeasonScoreKeeper(this.playerA, createGameScoreKeeper(8), createGameScoreKeeper(13),
				createGameScoreKeeper(32));
		assertEquals(53, createInstanceUnderTest(0).calculateScore(this.seasonScoreKeepers, this.playerA, 2));
	}

	@Test
	public void testCalculateScoreWhenSeasonScoreKeeperAfterLastGame() {
		addSeasonScoreKeeper(this.playerA);
		assertEquals(GameScoreCalculator.UNDEFINED_SCORE, createInstanceUnderTest(0).calculateScore(
				this.seasonScoreKeepers, this.playerA, 0));
	}

	@Test
	public void testCalculateScoreWhenPlayerAMorePointsThanPlayerBOneGame() {
		addSeasonScoreKeeper(this.playerA, createGameScoreKeeper(8));
		addSeasonScoreKeeper(this.playerB, createGameScoreKeeper(7));
		assertEquals(10, createInstanceUnderTest(2).calculateScore(this.seasonScoreKeepers, this.playerA, 0));
		assertEquals(7, createInstanceUnderTest(2).calculateScore(this.seasonScoreKeepers, this.playerB, 0));
	}

	@Test
	public void testCalculateScoreWhenPlayerASamePointsAsPlayerBOneGame() {
		addSeasonScoreKeeper(this.playerA, createGameScoreKeeper(8));
		addSeasonScoreKeeper(this.playerB, createGameScoreKeeper(8));
		assertEquals(10, createInstanceUnderTest(2).calculateScore(this.seasonScoreKeepers, this.playerA, 0));
		assertEquals(10, createInstanceUnderTest(2).calculateScore(this.seasonScoreKeepers, this.playerB, 0));
	}

	@Test
	public void testCalculateScoreWhenPlayerALessPointsAsPlayerBOneGame() {
		addSeasonScoreKeeper(this.playerA, createGameScoreKeeper(7));
		addSeasonScoreKeeper(this.playerB, createGameScoreKeeper(8));
		assertEquals(7, createInstanceUnderTest(2).calculateScore(this.seasonScoreKeepers, this.playerA, 0));
		assertEquals(10, createInstanceUnderTest(2).calculateScore(this.seasonScoreKeepers, this.playerB, 0));
	}

	@Test
	public void testCalculateScoreWithBonusPointsMoreThanOneGame() {
		addSeasonScoreKeeper(this.playerA, createGameScoreKeeper(6), createGameScoreKeeper(8));
		addSeasonScoreKeeper(this.playerB, createGameScoreKeeper(8), createGameScoreKeeper(7));
		assertEquals(16, createInstanceUnderTest(2).calculateScore(this.seasonScoreKeepers, this.playerA, 1));
		assertEquals(17, createInstanceUnderTest(2).calculateScore(this.seasonScoreKeepers, this.playerB, 1));
	}

	private void addSeasonScoreKeeper(Player player, GameScoreKeeper... gameScoreKeepers) {
		SeasonScoreKeeper seasonScoreKeeper = new SeasonScoreKeeper();
		for (GameScoreKeeper gameScoreKeeper : gameScoreKeepers) {
			seasonScoreKeeper.addGameScoreKeeper(gameScoreKeeper);
		}
		this.seasonScoreKeepers.put(player, seasonScoreKeeper);
	}

	private GameScoreKeeper createGameScoreKeeper(int score) {
		GameScoreKeeper gameScoreKeeper = mock(GameScoreKeeper.class);
		when(this.gameScoreCalculator.calculateScore(gameScoreKeeper)).thenReturn(score);
		return gameScoreKeeper;
	}

	private SeasonScoreCalculator createInstanceUnderTest(int bonusPoints) {
		return new SeasonScoreCalculator(this.gameScoreCalculator, bonusPoints);
	}

}