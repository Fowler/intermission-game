package de.fuhlsfield.game.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.Ball;

public class BallValueMapper {

	private final List<Ball> balls = new LinkedList<Ball>();
	private final Map<Ball, Integer> ballValues = new HashMap<Ball, Integer>();

	public int getValue(Ball ball) {
		if (this.ballValues.containsKey(ball)) {
			return this.ballValues.get(ball);
		}
		throw new IllegalArgumentException("No Mapping for Ball " + ball + "found!");
	}

	void addBall(Ball ball, int value) {
		if (this.ballValues.containsKey(ball)) {
			this.balls.remove(ball);
			this.ballValues.remove(ball);
		}
		this.balls.add(ball);
		this.ballValues.put(ball, value);
	}

	List<Ball> getBalls() {
		return this.balls;
	}

}