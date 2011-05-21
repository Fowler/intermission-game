package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class GameScoreKeeper {

	private final List<Attempt> attempts = new ArrayList<Attempt>();

	public void addAttempt(Attempt attempt) {
		this.attempts.add(attempt);
	}

	public void undoLastAttempt() {
		if (this.attempts.size() > 0) {
			this.attempts.remove(this.attempts.size() - 1);
		}
	}

	public Attempt getAttemptByIndex(int index) {
		if ((index < 0) || (index >= this.attempts.size())) {
			return null;
		}
		return this.attempts.get(index);
	}

	public int getNumberOfAttempts() {
		return this.attempts.size();
	}

	public Set<Ball> getSuccessfulPlayedBalls() {
		HashSet<Ball> balls = new HashSet<Ball>();
		for (Attempt attempt : this.attempts) {
			if (attempt.isSuccessful()) {
				balls.add(attempt.getBall());
			}
		}
		return balls;
	}

}