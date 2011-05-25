package de.fuhlsfield.ui;

import java.math.BigDecimal;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.StatisticKeeper;

public abstract class AbstractStatisticTableModel extends AbstractScoreTabelModel {

	private static final long serialVersionUID = 1L;

	protected final Game game;

	public AbstractStatisticTableModel(Game game) {
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
		StatisticKeeper statisticKeeper = getStatisticKeeper(getPlayer(columnIndex));
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
			return getTableName();
		}
		return getPlayer(index).getName();
	}

	protected abstract StatisticKeeper getStatisticKeeper(Player player);

	protected abstract String getTableName();

	private Player getPlayer(int columnIndex) {
		return this.game.getPlayers().get(columnIndex - 1);
	}

}