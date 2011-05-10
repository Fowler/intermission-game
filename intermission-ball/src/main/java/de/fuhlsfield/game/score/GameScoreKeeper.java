package de.fuhlsfield.game.score;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.AttemptResult;
import de.fuhlsfield.game.Ball;

public class GameScoreKeeper {
	
	public static final int NO_ATTEMPT = -1;

	private List<AttemptResult> rounds = new ArrayList<AttemptResult>();

	private int index = 0;

	public void add(AttemptResult attemptResult) {
		this.rounds.add(index++, attemptResult);
	}

	public Ball getIndexed(int index) {
		return rounds.get(index).getBall();
	}
	
	public int getIndexOfLastAttempt () {
		if (rounds.size() > 0) {
		return rounds.size() - 1;
		}
		return NO_ATTEMPT;
	}

}