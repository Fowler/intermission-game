package de.fuhlsfield.ui;

import javax.swing.JButton;

import de.fuhlsfield.game.Game;

public class FinishButtonUpdater extends AbstractButtonUpdater {

	@Override
	public void addButton(JButton button) {
		super.addButton(button);
	}

	@Override
	protected void updateButtonState(Game game, JButton button) {
		button.setEnabled(game.isGameFinished());
	}

}