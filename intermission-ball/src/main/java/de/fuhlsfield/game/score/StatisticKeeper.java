package de.fuhlsfield.game.score;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import de.fuhlsfield.game.Ball;

public class StatisticKeeper {

	private static final MathContext MATH_CONTEXT = new MathContext(3);

	private class SuccessFailure {

		private int successCounter;
		private int failureCounter;

		private void incSuccessCounter(int value) {
			this.successCounter += value;
			this.successCounter = Math.max(0, this.successCounter);
		}

		private void incFailureCounter(int value) {
			this.failureCounter += value;
			this.failureCounter = Math.max(0, this.failureCounter);
		}

		private int sum() {
			return this.successCounter + this.failureCounter;
		}

	}

	private final Map<Ball, SuccessFailure> statistic = new HashMap<Ball, SuccessFailure>();

	public int getSuccessCounter(Ball ball) {
		if (this.statistic.containsKey(ball)) {
			return this.statistic.get(ball).successCounter;
		}
		return 0;
	}

	public int getFailureCounter(Ball ball) {
		if (this.statistic.containsKey(ball)) {
			return this.statistic.get(ball).failureCounter;
		}
		return 0;
	}

	public BigDecimal calculateSuccessRate(Ball ball) {
		if (this.statistic.containsKey(ball)) {
			SuccessFailure successFailure = this.statistic.get(ball);
			if (successFailure.sum() > 0) {
				return new BigDecimal(successFailure.successCounter).multiply(new BigDecimal(100)).divide(
						new BigDecimal(successFailure.sum()), MATH_CONTEXT).setScale(1);
			}
		}
		return null;
	}

	void addSuccessfulAttempts(Ball ball, int times) {
		if (!this.statistic.containsKey(ball)) {
			this.statistic.put(ball, new SuccessFailure());
		}
		this.statistic.get(ball).incSuccessCounter(times);
	}

	void addFailedAttempts(Ball ball, int times) {
		if (!this.statistic.containsKey(ball)) {
			this.statistic.put(ball, new SuccessFailure());
		}
		this.statistic.get(ball).incFailureCounter(times);
	}

	void addStatisticKeeper(StatisticKeeper statisticKeeper) {
		modifyStatisticKeeper(statisticKeeper, true);
	}

	void removeStatisticKeeper(StatisticKeeper statisticKeeper) {
		modifyStatisticKeeper(statisticKeeper, false);
	}

	private void modifyStatisticKeeper(StatisticKeeper statisticKeeper, boolean isToAdd) {
		for (Ball ball : statisticKeeper.statistic.keySet()) {
			if (!this.statistic.containsKey(ball)) {
				this.statistic.put(ball, new SuccessFailure());
			}
			int factor = isToAdd ? 1 : -1;
			addSuccessfulAttempts(ball, factor * statisticKeeper.getSuccessCounter(ball));
			addFailedAttempts(ball, factor * statisticKeeper.getFailureCounter(ball));
		}
	}

}