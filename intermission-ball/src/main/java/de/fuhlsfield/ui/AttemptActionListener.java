package de.fuhlsfield.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Game;

public abstract class AttemptActionListener implements ActionListener {
	
	protected final Game game;
	protected final Attempt attempt;
	protected final AbstractTableModel model;
	
	public AttemptActionListener (Game game, Attempt attempt, AbstractTableModel model) {
		this.game = game;
		this.attempt = attempt;
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent e) {
		modifyGameScore();
		this.model.fireTableDataChanged();
	}
	
	protected abstract void modifyGameScore ();

}