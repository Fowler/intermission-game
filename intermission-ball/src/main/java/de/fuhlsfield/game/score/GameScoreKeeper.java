package de.fuhlsfield.game.score;

import de.fuhlsfield.game.AttemptResult;
import de.fuhlsfield.game.Ball;

public class GameScoreKeeper {

	public static final int NO_ATTEMPT = -1;

	private AttemptResult[] rounds;

	private int index = 0;
	private int maxRounds;

	public GameScoreKeeper(int maxRounds) {
		super();
		this.maxRounds = maxRounds;
		rounds = new AttemptResult[maxRounds];
	}

	public void add(AttemptResult attemptResult) {
		this.rounds[index++] = attemptResult;
	}

	public Ball getIndexed(int index) {

		AttemptResult result = rounds[index];

		return result == null ? null : result.getBall();
	}

	public int getIndexOfLastAttempt() {
		if (rounds.length > 0) {
			return rounds.length - 1;
		}
		return NO_ATTEMPT;
	}

	public int getMaxRounds() {
		return maxRounds;
	}

}