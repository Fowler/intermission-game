package de.fuhlsfield.ui;

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.StatisticKeeper;

public class SeasonStatisticTableModel extends AbstractStatisticTableModel {

	private static final long serialVersionUID = 1L;
	private static final String TABLE_NAME = "Saisonstatistik";

	public SeasonStatisticTableModel(Game game) {
		super(game);
	}

	@Override
	protected StatisticKeeper getStatisticKeeper(Player player) {
		return this.game.getSeasonStatisticKeeper(player);
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

}