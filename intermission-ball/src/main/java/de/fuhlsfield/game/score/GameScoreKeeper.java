package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class GameScoreKeeper {

	private final List<Attempt> attempts;

	public GameScoreKeeper() {
		this.attempts = new ArrayList<Attempt>();
	}

	public void addAttempt(Attempt attempt) {
		this.attempts.add(attempt);
	}

	public void undoLastAttempt() {
		if (isUndoLastAttemptPossible()) {
			this.attempts.remove(this.attempts.size() - 1);
		}
	}

	public boolean isUndoLastAttemptPossible() {
		return this.attempts.size() > 0;
	}

	public Attempt getIndexed(int index) {
		if ((index < 0) || (index > getIndexOfLastAttempt())) {
			return null;
		}
		return this.attempts.get(index);
	}

	public int getIndexOfLastAttempt() {
		if (this.attempts.isEmpty()) {
			return -1;
		}
		return this.attempts.size() - 1;
	}

	public List<Ball> getSuccessfulAttempts() {
		ArrayList<Ball> successfulAttempts = new ArrayList<Ball>();
		for (int i = 0; i <= getIndexOfLastAttempt(); i++) {
			Attempt attempt = getIndexed(i);
			if (attempt.isSuccessful()) {
				successfulAttempts.add(attempt.getBall());
			}
		}
		return successfulAttempts;
	}

	public List<Attempt> getAttempts() {
		return this.attempts;
	}

}