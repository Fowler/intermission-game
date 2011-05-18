package de.fuhlsfield.ui;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Game;

public class ButtonModel implements GameModel {

	private final Game game;
	private final List<AbstractButtonUpdater> buttonUpdaters = new ArrayList<AbstractButtonUpdater>();

	public ButtonModel(Game game) {
		this.game = game;
	}

	public void addUpdater(AbstractButtonUpdater buttonUpdater) {
		this.buttonUpdaters.add(buttonUpdater);
	}

	@Override
	public void updateModel() {
		for (AbstractButtonUpdater buttonUpdater : this.buttonUpdaters) {
			buttonUpdater.update(this.game);
		}
	}

}