package de.fuhlsfield.ui;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;

public class FailureActionListener extends GameActionListener {

	private final Ball ball;
	private final Player player;

	public FailureActionListener(Game game, Ball ball, Player player, GameModel... gameModels) {
		super(game, gameModels);
		this.ball = ball;
		this.player = player;
	}

	@Override
	protected void modifyGameState() {
		this.game.addAttempt(this.player, new Attempt(this.ball, false));
	}

}