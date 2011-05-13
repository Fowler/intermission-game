package de.fuhlsfield.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.AbstractTableModel;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;

public abstract class AttemptActionListener implements ActionListener {

	protected final Game game;
	protected final Ball ball;
	protected final Player player;
	protected final AbstractTableModel model;

	public AttemptActionListener(Game game, Ball ball, Player player, AbstractTableModel model) {
		this.game = game;
		this.ball = ball;
		this.player = player;
		this.model = model;
	}

	public void actionPerformed(ActionEvent e) {
		modifyGameScore();
		this.model.fireTableDataChanged();
	}

	protected abstract void modifyGameScore();

}