package de.fuhlsfield.game.score;

import de.fuhlsfield.game.AttemptResult;

public class GameScoreKeeper {

	public static final int NO_ATTEMPT = -1;

	private final AttemptResult[] rounds;

	private int index = 0;
	private final int maxRounds;

	public GameScoreKeeper(int maxRounds) {
		super();
		this.maxRounds = maxRounds;
		this.rounds = new AttemptResult[maxRounds];
	}

	public void add(AttemptResult attemptResult) {
		this.rounds[this.index++] = attemptResult;
	}

	public AttemptResult getIndexed(int index) {

		return this.rounds[index];
	}

	public int getIndexOfLastAttempt() {
		if (this.rounds.length > 0) {
			return this.rounds.length - 1;
		}
		return NO_ATTEMPT;
	}

	public int getMaxRounds() {
		return this.maxRounds;
	}

}