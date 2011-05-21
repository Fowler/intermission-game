package de.fuhlsfield.game.config;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.StandardGameScoreCalculator;

public class FiveBallsGameConfigTest {

	@Test
	public void testGetName() {
		assertEquals("Fünfball Konfiguration", createInstanceUnderTest().getName());
	}

	@Test
	public void testGetTargetPoints() {
		assertEquals(14, createInstanceUnderTest().getTargetPoints());
	}

	@Test
	public void testGetGameScoreCalculator() {
		assertEquals(StandardGameScoreCalculator.class, createInstanceUnderTest().getGameScoreCalculator().getClass());
	}

	@Test
	public void testGetAllowedBalls() {
		LinkedList<Ball> expectedBalls = new LinkedList<Ball>();
		expectedBalls.add(Ball.BUNTI);
		expectedBalls.add(Ball.FROESCHI);
		expectedBalls.add(Ball.BASKI);
		expectedBalls.add(Ball.SCHWAMMI);
		expectedBalls.add(Ball.TISCHI_BALLI);
		assertEquals(expectedBalls, createInstanceUnderTest().getAllowedBalls());
	}

	private FiveBallsGameConfig createInstanceUnderTest() {
		return new FiveBallsGameConfig();
	}

}