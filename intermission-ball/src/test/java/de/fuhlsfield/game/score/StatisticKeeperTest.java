package de.fuhlsfield.game.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import de.fuhlsfield.game.Ball;

public class StatisticKeeperTest {

	@Test
	public void testGetSuccessCounterWhenNoSuccefulAttempt() {
		assertEquals(0, createInstanceUnderTest().getSuccessCounter(Ball.BASKI));
	}

	@Test
	public void testGetFailureCounterWhenNoFailedAttempt() {
		assertEquals(0, createInstanceUnderTest().getFailureCounter(Ball.BASKI));
	}

	@Test
	public void testCalculateSuccessRate() {
		StatisticKeeper statisticKeeper = createInstanceUnderTest();
		statisticKeeper.addSuccessfulAttempts(Ball.BASKI, 1);
		statisticKeeper.addFailedAttempts(Ball.BASKI, 3);
		assertEquals(new BigDecimal("25.0"), statisticKeeper.calculateSuccessRate(Ball.BASKI));
	}

	@Test
	public void testCalculateSuccessRateWhenRounded() {
		StatisticKeeper statisticKeeper = createInstanceUnderTest();
		statisticKeeper.addSuccessfulAttempts(Ball.BASKI, 1);
		statisticKeeper.addFailedAttempts(Ball.BASKI, 2);
		assertEquals(new BigDecimal("33.3"), statisticKeeper.calculateSuccessRate(Ball.BASKI));
	}

	@Test
	public void testCalculateSuccessRateWhenNoAttempts() {
		assertNull(createInstanceUnderTest().calculateSuccessRate(Ball.BASKI));
	}

	@Test
	public void testAddStatisticKeeper() {
		StatisticKeeper statisticKeeper = createInstanceUnderTest();
		statisticKeeper.addSuccessfulAttempts(Ball.BASKI, 1);
		statisticKeeper.addFailedAttempts(Ball.BASKI, 2);
		StatisticKeeper statisticKeeperToAdd = new StatisticKeeper();
		statisticKeeperToAdd.addSuccessfulAttempts(Ball.BASKI, 2);
		statisticKeeperToAdd.addFailedAttempts(Ball.BASKI, 3);
		statisticKeeper.addStatisticKeeper(statisticKeeperToAdd);
		assertEquals(3, statisticKeeper.getSuccessCounter(Ball.BASKI));
		assertEquals(5, statisticKeeper.getFailureCounter(Ball.BASKI));
	}

	@Test
	public void testRemoveStatisticKeeper() {
		StatisticKeeper statisticKeeper = createInstanceUnderTest();
		statisticKeeper.addSuccessfulAttempts(Ball.BASKI, 3);
		statisticKeeper.addFailedAttempts(Ball.BASKI, 5);
		StatisticKeeper statisticKeeperToRemove = new StatisticKeeper();
		statisticKeeperToRemove.addSuccessfulAttempts(Ball.BASKI, 1);
		statisticKeeperToRemove.addFailedAttempts(Ball.BASKI, 2);
		statisticKeeper.removeStatisticKeeper(statisticKeeperToRemove);
		assertEquals(2, statisticKeeper.getSuccessCounter(Ball.BASKI));
		assertEquals(3, statisticKeeper.getFailureCounter(Ball.BASKI));
	}

	@Test
	public void testRemoveStatisticKeeperWithMoreAttempts() {
		StatisticKeeper statisticKeeper = createInstanceUnderTest();
		statisticKeeper.addSuccessfulAttempts(Ball.BASKI, 1);
		statisticKeeper.addFailedAttempts(Ball.BASKI, 2);
		StatisticKeeper statisticKeeperToRemove = new StatisticKeeper();
		statisticKeeperToRemove.addSuccessfulAttempts(Ball.BASKI, 2);
		statisticKeeperToRemove.addFailedAttempts(Ball.BASKI, 3);
		statisticKeeper.removeStatisticKeeper(statisticKeeperToRemove);
		assertEquals(0, statisticKeeper.getSuccessCounter(Ball.BASKI));
		assertEquals(0, statisticKeeper.getFailureCounter(Ball.BASKI));
	}

	private StatisticKeeper createInstanceUnderTest() {
		return new StatisticKeeper();
	}

}