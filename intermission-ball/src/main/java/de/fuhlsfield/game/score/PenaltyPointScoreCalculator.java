package de.fuhlsfield.game.score;

import de.fuhlsfield.game.AttemptResult;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;

public class PenaltyPointScoreCalculator extends StandardScoreCalculator {
	
	private final static int PENALTY_POINTS = 1;
	
	@Override
	public int calculateScore (Game game, Player player) {
		int score = super.calculateScore(game, player);
		GameScoreKeeper gameScoreKeeper = game.getGameScore(player);
		boolean lastAttemptFails = false;
		for (int i = 0; gameScoreKeeper.getIndexed(i) != AttemptResult.NO_ATTEMPT_RESULT; i++) {
			AttemptResult attemptResult = gameScoreKeeper.getIndexed(i);
			if (!attemptResult.isSuccess()) {
				if (lastAttemptFails) {
					score -= PENALTY_POINTS;
					lastAttemptFails = false;
				} else {
					lastAttemptFails = true;
				}
			} else {
				lastAttemptFails = false;
			}
		}
		return score;
	}

}