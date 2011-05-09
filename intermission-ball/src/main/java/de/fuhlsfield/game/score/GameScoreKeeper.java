package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.Ball;

public class GameScoreKeeper {

	private List<Ball> rounds = new ArrayList<Ball>(Arrays.asList(Ball.NONE,
			Ball.NONE, Ball.NONE, Ball.NONE, Ball.NONE, Ball.NONE, Ball.NONE,
			Ball.NONE, Ball.NONE, Ball.NONE));

	private int index = 0;

	public void add(Ball ball) {
		if (allowed(ball)) {
			this.rounds.add(index++, ball);
		}
	}

	public boolean allowed(Ball ball) {
		if (!ball.isValid() || !rounds.contains(ball)) {
			return true;
		}
		return false;
	}

	public Ball getIndexed(int index) {
		return rounds.get(index);
	}

}
