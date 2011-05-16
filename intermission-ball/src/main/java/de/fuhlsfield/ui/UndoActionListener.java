package de.fuhlsfield.ui;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Game;

public class UndoActionListener extends GameActionListener {

	public UndoActionListener(Game game, AbstractTableModel... tableModels) {
		super(game, tableModels);
	}

	@Override
	protected void modifyScore() {
		this.game.undoLastAttempt();
	}

}