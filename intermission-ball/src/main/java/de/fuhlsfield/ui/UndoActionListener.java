package de.fuhlsfield.ui;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Game;

public class UndoActionListener extends AttemptActionListener {

	public UndoActionListener(Game game, AbstractTableModel... tableModels) {
		super(game, tableModels);
	}

	@Override
	protected void modifyScore() {
		this.game.undoLastAttempt();
	}

}