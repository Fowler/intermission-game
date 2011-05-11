package de.fuhlsfield.game.score;

import de.fuhlsfield.game.AttemptResult;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;

public class StandardScoreCalculator implements ScoreCalculator {

	@Override
	public int calculateScore (Game game, Player player) {
		GameScoreKeeper gameScoreKeeper = game.getGameScore(player);
		int score = 0;
		for (int i = 0; i <= gameScoreKeeper.getIndexOfLastAttempt(); i++) {
			AttemptResult attemptResult = gameScoreKeeper.getIndexed(i);
			if (attemptResult.isSuccess()) {
				score += game.getBallValue(attemptResult.getBall()).getValue();
			}
		}
		return score;
	}
	
}