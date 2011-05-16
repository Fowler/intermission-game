package de.fuhlsfield.ui.actionListener;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.ui.GameModel;

public class FinishActionListener extends GameActionListener {

	public FinishActionListener(Game game, GameModel... gameModels) {
		super(game, gameModels);
	}

	@Override
	protected void modifyGameState() {
		this.game.finishGame();
	}

}