package de.fuhlsfield.ui.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.fuhlsfield.IntermissionGameGui;
import de.fuhlsfield.game.Game;

public class FromCsvActionListener implements ActionListener {

	private final Game game;
	private final IntermissionGameGui gameGui;

	public FromCsvActionListener(Game game, IntermissionGameGui gameGui) {
		this.game = game;
		this.gameGui = gameGui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.gameGui.initGame(this.game.importScore());
	}

}