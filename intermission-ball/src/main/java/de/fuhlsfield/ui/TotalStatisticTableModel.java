package de.fuhlsfield.ui;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.StatisticKeeper;

public class TotalStatisticTableModel extends AbstractStatisticTableModel {

	private static final long serialVersionUID = 1L;
	private static final String TABLE_NAME = "Gesamtstatistik";

	public TotalStatisticTableModel(Game game) {
		super(game);
	}

	@Override
	protected StatisticKeeper getStatisticKeeper(Player player) {
		return this.game.getTotalStatisticKeeper(player);
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

}