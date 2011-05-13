package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import de.fuhlsfield.game.Ball;

public class BallValueMapper {

	private final HashMap<Ball, Integer> ballValues;

	public BallValueMapper() {
		this.ballValues = new HashMap<Ball, Integer>();
	}

	public void add(Ball ball, int value) {
		this.ballValues.put(ball, value);
	}

	public Integer getValue(Ball ball) {
		return this.ballValues.get(ball);
	}

	public List<Ball> getBalls() {
		Iterator<Entry<Ball, Integer>> iterator = this.ballValues.entrySet().iterator();
		ArrayList<Ball> balls = new ArrayList<Ball>();
		while (iterator.hasNext()) {
			balls.add(iterator.next().getKey());
		}
		return balls;
	}

}