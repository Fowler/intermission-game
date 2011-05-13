package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.AttemptResult;

public class GameScoreKeeper {

	public static final int NO_ATTEMPT = -1;

	private final List<AttemptResult> rounds;

	public GameScoreKeeper() {
		this.rounds = new ArrayList<AttemptResult>();
	}

	public void add(AttemptResult attemptResult) {
		this.rounds.add(attemptResult);
	}

	public AttemptResult getIndexed(int index) {
		if ((index < 0) || (index > getIndexOfLastAttempt())) {
			return AttemptResult.NO_ATTEMPT_RESULT;
		}
		return this.rounds.get(index);
	}

	public int getIndexOfLastAttempt() {
		if (this.rounds.isEmpty()) {
			return NO_ATTEMPT;
		}
		return this.rounds.size() - 1;
	}

	public List<AttemptResult> getSuccessfulAttempts() {
		ArrayList<AttemptResult> attemptResults = new ArrayList<AttemptResult>();
		for (int i = 0; i <= getIndexOfLastAttempt(); i++) {
			AttemptResult attemptResult = getIndexed(i);
			if (attemptResult.isSuccess()) {
				attemptResults.add(attemptResult);
			}
		}
		return attemptResults;
	}

}