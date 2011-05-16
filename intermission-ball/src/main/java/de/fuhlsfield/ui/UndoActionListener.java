package de.fuhlsfield.ui;

import de.fuhlsfield.game.Game;

public class UndoActionListener extends GameActionListener {

	public UndoActionListener(Game game, GameModel... gameModels) {
		super(game, gameModels);
	}

	@Override
	protected void modifyGameState() {
		this.game.undoLastAttempt();
	}

}