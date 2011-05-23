package de.fuhlsfield.ui.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.fuhlsfield.game.Game;

public class ToCsvActionListener implements ActionListener {

	private final Game game;

	public ToCsvActionListener(Game game) {
		this.game = game;
	}

	public void actionPerformed(ActionEvent e) {
		this.game.export();
	}

}