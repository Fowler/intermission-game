package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class NextPlayerRuleCheck implements RuleCheck {

	@Override
	public boolean isAttemptPossible(Attempt attempt, Game game) {
		return determineNextPlayer(game).equals(attempt.getPlayer());
	}
	
	private Player determineNextPlayer (Game game) {
		if (game.getPlayers().size() > 0) {
			Player nextPlayer = game.getPlayers().get(0);
			int maxIndex = GameScoreKeeper.NO_ATTEMPT;
			for (Player player : game.getPlayers()) {
				GameScoreKeeper gameScore = game.getGameScore(player);
				int index = gameScore.getIndexOfLastAttempt();
				if (index > maxIndex) {
					maxIndex = index;
					nextPlayer = player;
				}
			}
			return nextPlayer;
		}
		return Player.NO_PLAYER;
	}
	
}