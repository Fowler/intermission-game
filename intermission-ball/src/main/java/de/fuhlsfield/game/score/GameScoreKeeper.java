package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Attempt;

public class GameScoreKeeper {

	public static final int NO_ATTEMPT = -1;

	private final List<Attempt> attempts;

	public GameScoreKeeper() {
		this.attempts = new ArrayList<Attempt>();
	}

	public void add(Attempt attempt) {
		this.attempts.add(attempt);
	}

	public Attempt getIndexed(int index) {
		if ((index < 0) || (index > getIndexOfLastAttempt())) {
			return Attempt.NO_ATTEMPT;
		}
		return this.attempts.get(index);
	}

	public int getIndexOfLastAttempt() {
		if (this.attempts.isEmpty()) {
			return NO_ATTEMPT;
		}
		return this.attempts.size() - 1;
	}

	public List<Attempt> getSuccessfulAttempts() {
		ArrayList<Attempt> successfulAttempts = new ArrayList<Attempt>();
		for (int i = 0; i <= getIndexOfLastAttempt(); i++) {
			Attempt attempt = getIndexed(i);
			if (attempt.isSuccessful()) {
				successfulAttempts.add(attempt);
			}
		}
		return successfulAttempts;
	}

}