package de.fuhlsfield.game.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.Ball;

public class BallValueMapper {

	private final List<Ball> balls = new LinkedList<Ball>();
	private final Map<Ball, Integer> ballValues = new HashMap<Ball, Integer>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.ballValues == null) ? 0 : this.ballValues.hashCode());
		result = prime * result + ((this.balls == null) ? 0 : this.balls.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BallValueMapper other = (BallValueMapper) obj;
		if (this.ballValues == null) {
			if (other.ballValues != null)
				return false;
		} else if (!this.ballValues.equals(other.ballValues))
			return false;
		if (this.balls == null) {
			if (other.balls != null)
				return false;
		} else if (!this.balls.equals(other.balls))
			return false;
		return true;
	}

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