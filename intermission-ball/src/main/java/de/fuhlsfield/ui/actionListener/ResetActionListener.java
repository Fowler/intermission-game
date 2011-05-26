package de.fuhlsfield.ui.actionListener;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.ui.GameModel;

public class ResetActionListener extends GameActionListener {

	public ResetActionListener(Game game, GameModel... gameModels) {
		super(game, gameModels);
	}

	@Override
	protected void modifyGameState() {
		this.game.reset();
	}

}