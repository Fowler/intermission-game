package de.fuhlsfield.ui;

import de.fuhlsfield.game.Game;

public class FinishActionListener extends GameActionListener {

	public FinishActionListener(Game game, GameModel... gameModels) {
		super(game, gameModels);
	}

	@Override
	protected void modifyGameState() {
		this.game.finishGame();
	}

}