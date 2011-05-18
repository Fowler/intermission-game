package de.fuhlsfield.ui.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.persistence.GameScoreCsvExporter;
import de.fuhlsfield.game.persistence.SeasonScoreCsvExporter;

public class ToCsvActionListener implements ActionListener {
	private final Game game;

	public ToCsvActionListener(Game game) {
		this.game = game;
	}

	public void actionPerformed(ActionEvent e) {
		new GameScoreCsvExporter().export(this.game);
		new SeasonScoreCsvExporter().export(this.game);
	}

}