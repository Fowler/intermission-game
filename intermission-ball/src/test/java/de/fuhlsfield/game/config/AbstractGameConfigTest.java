package de.fuhlsfield.game.config;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreCalculator;

public abstract class AbstractGameConfigTest<T extends GameConfig> {

	@Test
	public final void testGetAllowedBalls() {
		assertEquals(createExpectedBalls(), createInstanceUnderTest().getAllowedBalls());
	}

	@Test
	public final void testGetGameScoreCalculator() {
		assertEquals(createExpectedGameScoreCalculator(), createInstanceUnderTest().getGameScoreCalculator());
	}

	protected final List<Ball> createExpectedBalls() {
		return createExpectedBallValueMapper().getBalls();
	}

	protected abstract GameScoreCalculator createExpectedGameScoreCalculator();

	protected abstract BallValueMapper createExpectedBallValueMapper();

	protected abstract T createInstanceUnderTest();

}