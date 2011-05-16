package de.fuhlsfield.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

public abstract class AbstractButtonModel implements GameModel {

	protected List<JButton> buttons = new ArrayList<JButton>();

	@Override
	public void updateModel() {
		for (JButton button : this.buttons) {
			updateButtonState(button);
		}
	}

	protected void addButton(JButton button) {
		this.buttons.add(button);
	}

	protected abstract void updateButtonState(JButton button);

}