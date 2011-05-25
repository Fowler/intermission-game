package de.fuhlsfield.game.score;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class StatisticKeeper {

	private static final MathContext MATH_CONTEXT = new MathContext(3);

	private class SuccessFailure {

		private int successCounter;
		private int failureCounter;

		private void addAttempt(boolean isSuccessfull) {
			if (isSuccessfull) {
				this.successCounter++;
			} else {
				this.failureCounter++;
			}
		}

		private void removeAttempt(boolean isSuccessfull) {
			if (isSuccessfull) {
				this.successCounter--;
			} else {
				this.failureCounter--;
			}
		}

		private void incSuccessCounter(int value) {
			this.successCounter += value;
		}

		private void incFailureCounter(int value) {
			this.failureCounter += value;
		}

		private void decSuccessCounter(int value) {
			this.successCounter -= value;
		}

		private void decFailureCounter(int value) {
			this.failureCounter -= value;
		}

		private int sum() {
			return this.successCounter + this.failureCounter;
		}

	}

	private final Map<Ball, SuccessFailure> statistic = new HashMap<Ball, SuccessFailure>();

	public void addAttempt(Attempt attempt) {
		Ball ball = attempt.getBall();
		if (!this.statistic.containsKey(ball)) {
			this.statistic.put(ball, new SuccessFailure());
		}
		this.statistic.get(ball).addAttempt(attempt.isSuccessful());
	}

	public void removeAttempt(Attempt attempt) {
		Ball ball = attempt.getBall();
		if (this.statistic.containsKey(ball)) {
			this.statistic.get(ball).removeAttempt(attempt.isSuccessful());
		}
	}

	public void addSuccessfulAttempts(Ball ball, int times) {
		if (!this.statistic.containsKey(ball)) {
			this.statistic.put(ball, new SuccessFailure());
		}
		this.statistic.get(ball).incSuccessCounter(times);
	}

	public void addFailedAttempts(Ball ball, int times) {
		if (!this.statistic.containsKey(ball)) {
			this.statistic.put(ball, new SuccessFailure());
		}
		this.statistic.get(ball).incFailureCounter(times);
	}

	public void addStatisticKeeper(StatisticKeeper statisticKeeper) {
		for (Ball ball : statisticKeeper.statistic.keySet()) {
			if (!this.statistic.containsKey(ball)) {
				this.statistic.put(ball, new SuccessFailure());
			}
			this.statistic.get(ball).incSuccessCounter(statisticKeeper.getSuccessCounter(ball));
			this.statistic.get(ball).incFailureCounter(statisticKeeper.getFailureCounter(ball));
		}
	}

	public void removeStatisticKeeper(StatisticKeeper statisticKeeper) {
		for (Ball ball : statisticKeeper.statistic.keySet()) {
			if (this.statistic.containsKey(ball)) {
				this.statistic.get(ball).decSuccessCounter(statisticKeeper.getSuccessCounter(ball));
				this.statistic.get(ball).decFailureCounter(statisticKeeper.getFailureCounter(ball));
			}
		}
	}

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

}