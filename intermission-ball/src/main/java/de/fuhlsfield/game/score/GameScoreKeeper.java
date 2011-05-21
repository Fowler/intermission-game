package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class GameScoreKeeper {

	private final List<Attempt> attempts = new ArrayList<Attempt>();

	public void addAttempt(Attempt attempt) {
		this.attempts.add(attempt);
	}

	public List<Attempt> getAttempts() {
		return this.attempts;
	}

	public void undoLastAttempt() {
		if (this.attempts.size() > 0) {
			this.attempts.remove(this.attempts.size() - 1);
		}
	}

	public Attempt getAttemptByIndex(int index) {
		if ((index < 0) || (index >= getNumberOfAttempts())) {
			return null;
		}
		return this.attempts.get(index);
	}

	public int getNumberOfAttempts() {
		return this.attempts.size();
	}

	public boolean isBallSuccessfulPlayed(Ball ball) {
		for (Attempt attempt : this.attempts) {
			if ((attempt.getBall() == ball) && attempt.isSuccessful()) {
				return true;
			}
		}
		return false;
	}

}