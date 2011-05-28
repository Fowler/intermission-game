package de.fuhlsfield.game.score;

import java.util.Map;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;

public class StatisticKeeperFactory {

	public StatisticKeeper createStatisticKeeper() {
		return new StatisticKeeper();
	}

	public StatisticKeeper createStatisticKeeper(GameScoreKeeper gameScoreKeeper) {
		StatisticKeeper statisticKeeper = new StatisticKeeper();
		for (int i = 0; i < gameScoreKeeper.getNumberOfAttempts(); i++) {
			Attempt attempt = gameScoreKeeper.getAttemptByIndex(i);
			if (attempt.isSuccessful()) {
				statisticKeeper.addSuccessfulAttempts(attempt.getBall(), 1);
			} else {
				statisticKeeper.addFailedAttempts(attempt.getBall(), 1);
			}
		}
		return statisticKeeper;
	}

	public StatisticKeeper createStatisticKeeper(SeasonScoreKeeper seasonScoreKeeper) {
		StatisticKeeper statisticKeeper = new StatisticKeeper();
		for (int i = 0; i < seasonScoreKeeper.getNumberOfGameScoreKeepers(); i++) {
			statisticKeeper.addStatisticKeeper(createStatisticKeeper(seasonScoreKeeper.getGameScoreKeeperByIndex(i)));
		}
		return statisticKeeper;
	}

	public StatisticKeeper mergeStatisticKeeper(StatisticKeeper statisticKeeperA, StatisticKeeper statisticKeeperB) {
		StatisticKeeper statisticKeeper = new StatisticKeeper();
		statisticKeeper.addStatisticKeeper(statisticKeeperA);
		statisticKeeper.addStatisticKeeper(statisticKeeperB);
		return statisticKeeper;
	}

	public StatisticKeeper removeStatisticKeeper(StatisticKeeper statisticKeeperA, StatisticKeeper statisticKeeperB) {
		StatisticKeeper statisticKeeper = new StatisticKeeper();
		statisticKeeper.addStatisticKeeper(statisticKeeperA);
		statisticKeeper.removeStatisticKeeper(statisticKeeperB);
		return statisticKeeper;
	}

	public StatisticKeeper createStatisticKeeper(Map<Ball, Integer> successfulAttempts,
			Map<Ball, Integer> failedAttempts) {
		StatisticKeeper statisticKeeper = new StatisticKeeper();
		for (Ball ball : successfulAttempts.keySet()) {
			statisticKeeper.addSuccessfulAttempts(ball, successfulAttempts.get(ball));
		}
		for (Ball ball : failedAttempts.keySet()) {
			statisticKeeper.addFailedAttempts(ball, failedAttempts.get(ball));
		}
		return statisticKeeper;
	}

}