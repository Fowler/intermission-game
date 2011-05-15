package de.fuhlsfield.ui;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;

public class FailureActionListener extends AttemptActionListener {

	private final Ball ball;
	private final Player player;

	public FailureActionListener(Game game, Ball ball, Player player, AbstractTableModel... tableModels) {
		super(game, tableModels);
		this.ball = ball;
		this.player = player;
	}

	@Override
	protected void modifyScore() {
		this.game.addAttempt(this.player, new Attempt(this.ball, false));
	}

}