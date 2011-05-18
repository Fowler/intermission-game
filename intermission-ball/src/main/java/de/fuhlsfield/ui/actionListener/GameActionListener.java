package de.fuhlsfield.ui.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.ui.GameModel;

public abstract class GameActionListener implements ActionListener {

	protected final Game game;
	protected final GameModel[] gameModels;

	public GameActionListener(Game game, GameModel... gameModels) {
		this.game = game;
		this.gameModels = gameModels;
	}

	public void actionPerformed(ActionEvent e) {
		modifyGameState();
		for (GameModel gameModel : this.gameModels) {
			gameModel.updateModel();
		}
	}

	protected abstract void modifyGameState();

}