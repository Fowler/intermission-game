package de.fuhlsfield.ui;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Game;

public class SuccessActionListener extends AttemptActionListener {
	
	public SuccessActionListener (Game game, Attempt attempt, AbstractTableModel model) {
		super(game, attempt, model);
	}
	
	@Override
	protected void modifyGameScore () {
		this.game.check(this.attempt.getBall(), this.attempt.getPlayer(), true);
	}

}
