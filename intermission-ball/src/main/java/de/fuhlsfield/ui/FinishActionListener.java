package de.fuhlsfield.ui;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Game;

public class FinishActionListener extends AttemptActionListener {

	public FinishActionListener(Game game, AbstractTableModel... tableModels) {
		super(game, tableModels);
	}

	@Override
	protected void modifyScore() {
		this.game.finishGame();
	}

}