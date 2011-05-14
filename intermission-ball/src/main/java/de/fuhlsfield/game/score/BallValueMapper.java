package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Ball;

public class BallValueMapper {

	private class BallValue {

		private final Ball ball;
		private final Integer value;

		private BallValue(Ball ball, int value) {
			this.ball = ball;
			this.value = value;
		}

	}

	private final List<BallValue> ballValues;

	public BallValueMapper() {
		this.ballValues = new ArrayList<BallValue>();
	}

	public void add(Ball ball, int value) {
		this.ballValues.add(new BallValue(ball, value));
	}

	public Integer getValue(Ball ball) {
		for (BallValue ballValue : this.ballValues) {
			if (ballValue.ball == ball) {
				return ballValue.value;
			}
		}
		return null;
	}

	public List<Ball> getBalls() {
		ArrayList<Ball> balls = new ArrayList<Ball>();
		for (BallValue ballValue : this.ballValues) {
			balls.add(ballValue.ball);
		}
		return balls;
	}

}