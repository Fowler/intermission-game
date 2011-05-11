package de.fuhlsfield.game.score;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;

public interface ScoreCalculator {
	
	int calculateScore (Game game, Player player);

}