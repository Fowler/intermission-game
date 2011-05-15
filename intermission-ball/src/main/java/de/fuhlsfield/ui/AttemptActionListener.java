package de.fuhlsfield.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Game;

public abstract class AttemptActionListener implements ActionListener {

	protected final Game game;
	protected final AbstractTableModel[] tableModels;

	public AttemptActionListener(Game game, AbstractTableModel... tableModels) {
		this.game = game;
		this.tableModels = tableModels;
	}

	public void actionPerformed(ActionEvent e) {
		modifyGameScore();
		for (AbstractTableModel tableModel : this.tableModels) {
			tableModel.fireTableDataChanged();
		}
	}

	protected abstract void modifyGameScore();

}