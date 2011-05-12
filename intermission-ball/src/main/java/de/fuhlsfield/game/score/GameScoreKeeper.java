package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.AttemptResult;

public class GameScoreKeeper {

	public static final int NO_ATTEMPT = -1;

	private final AttemptResult[] rounds;

	private int index = 0;

	public GameScoreKeeper(int maxRounds) {
		super();
		this.rounds = new AttemptResult[maxRounds];
	}

	public void add(AttemptResult attemptResult) {
		this.rounds[this.index++] = attemptResult;
	}

	public AttemptResult getIndexed(int index) {
		if ((index < 0) || (index > getIndexOfLastAttempt())) {
			return AttemptResult.NO_ATTEMPT_RESULT;
		}
		return this.rounds[index];
	}

	public int getIndexOfLastAttempt() {
		if (this.index > 0) {
			return this.index - 1;
		}
		return NO_ATTEMPT;
	}
	
	public List<AttemptResult> getSuccessfulAttempts () {
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