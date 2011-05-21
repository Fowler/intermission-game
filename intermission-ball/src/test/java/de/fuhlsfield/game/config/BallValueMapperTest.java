package de.fuhlsfield.game.config;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

import de.fuhlsfield.game.Ball;

public class BallValueMapperTest {

	@Test
	public void testAddBallTwoDifferentBalls() {
		BallValueMapper ballValueMapper = createInstanceUnderTest();
		LinkedList<Ball> expectedBalls = new LinkedList<Ball>();
		expectedBalls.add(Ball.BASKI);
		ballValueMapper.addBall(Ball.BASKI, 1);
		expectedBalls.add(Ball.BUNTI);
		ballValueMapper.addBall(Ball.BUNTI, 1);
		expectedBalls.add(Ball.SCHWAMMI);
		ballValueMapper.addBall(Ball.SCHWAMMI, 1);
		assertEquals(expectedBalls, ballValueMapper.getBalls());
	}

	@Test
	public void testAddBallSameBallTwoTimes() {
		BallValueMapper ballValueMapper = createInstanceUnderTest();
		LinkedList<Ball> expectedBalls = new LinkedList<Ball>();
		expectedBalls.add(Ball.BASKI);
		ballValueMapper.addBall(Ball.BASKI, 1);
		ballValueMapper.addBall(Ball.BASKI, 2);
		assertEquals(expectedBalls, ballValueMapper.getBalls());
		assertEquals(2, ballValueMapper.getValue(Ball.BASKI));
	}

	@Test
	public void testGetValueWhenBallAdded() {
		BallValueMapper ballValueMapper = createInstanceUnderTest();
		ballValueMapper.addBall(Ball.BASKI, 1);
		ballValueMapper.addBall(Ball.BUNTI, 2);
		assertEquals(1, ballValueMapper.getValue(Ball.BASKI));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetValueWhenBallNotAdded() {
		BallValueMapper ballValueMapper = createInstanceUnderTest();
		ballValueMapper.addBall(Ball.BASKI, 1);
		ballValueMapper.addBall(Ball.BUNTI, 2);
		assertEquals(1, ballValueMapper.getValue(Ball.SCHWAMMI));
	}

	private BallValueMapper createInstanceUnderTest() {
		return new BallValueMapper();
	}

}