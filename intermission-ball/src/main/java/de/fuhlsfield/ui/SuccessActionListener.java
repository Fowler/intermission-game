package de.fuhlsfield.ui;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;

public class SuccessActionListener extends AttemptActionListener {

	public SuccessActionListener(Game game, Ball ball, Player player, AbstractTableModel model) {
		super(game, ball, player, model);
	}

	@Override
	protected void modifyGameScore() {
		this.game.addAttempt(this.player, new Attempt(this.ball, true));
	}

}
