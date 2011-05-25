package de.fuhlsfield.ui;

import java.math.BigDecimal;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.StatisticKeeper;

public class SeasonStatisticTableModel extends AbstractScoreTabelModel {

	private static final long serialVersionUID = 1L;
	private static final String TABLE_NAME = "Saisonstatistik";

	private final Game game;

	public SeasonStatisticTableModel(Game game) {
		this.game = game;
	}

	@Override
	public int getColumnCount() {
		return this.game.getPlayers().size() + 1;
	}

	@Override
	public int getRowCount() {
		return this.game.getBalls().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ball ball = this.game.getBalls().get(rowIndex);
		if (columnIndex == 0) {
			return ball.getName();
		}
		StatisticKeeper statisticKeeper = this.game.getSeasonStatisticKeeper(getPlayer(columnIndex));
		BigDecimal successRate = statisticKeeper.calculateSuccessRate(ball);
		if (successRate != null) {
			return successRate + " (" + statisticKeeper.getSuccessCounter(ball) + " / "
					+ (statisticKeeper.getSuccessCounter(ball) + statisticKeeper.getFailureCounter(ball)) + ")";
		}
		return null;
	}

	@Override
	public String getColumnName(int index) {
		if (index == 0) {
			return TABLE_NAME;
		}
		return getPlayer(index).getName();
	}

	private Player getPlayer(int columnIndex) {
		return this.game.getPlayers().get(columnIndex - 1);
	}

}