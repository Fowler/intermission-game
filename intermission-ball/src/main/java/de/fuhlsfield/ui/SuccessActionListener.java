package de.fuhlsfield.ui;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;

public class SuccessActionListener extends AttemptActionListener {

	public SuccessActionListener(Game game, Ball ball, Player player, AbstractTableModel model) {
		super(game, ball, player, model);
	}

	@Override
	protected void modifyGameScore() {
		this.game.check(this.ball, this.player, true);
	}

}
