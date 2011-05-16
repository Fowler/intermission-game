package de.fuhlsfield.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import de.fuhlsfield.game.Game;

public abstract class AbstractButtonUpdater {

	protected List<JButton> buttons = new ArrayList<JButton>();

	public void update(Game game) {
		for (JButton button : this.buttons) {
			updateButtonState(game, button);
		}
	}

	protected void addButton(JButton button) {
		this.buttons.add(button);
	}

	protected abstract void updateButtonState(Game game, JButton button);

}