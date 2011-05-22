package de.fuhlsfield.game.persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.SeasonScoreKeeper;

public class SeasonScoreCsvExporter {

	private static final String EOL = "\n";
	private static final String SEPARATOR = ",";

	public void export(Map<Player, SeasonScoreKeeper> seasonScoreKeepers, GameScoreCalculator gameScoreCalculator) {
		FileWriter writer;
		try {
			writer = new FileWriter("/tmp/season-scores.csv");

			for (Map.Entry<Player, SeasonScoreKeeper> keeperValues : seasonScoreKeepers.entrySet()) {

				for (int keeperIndex = 0; keeperIndex < keeperValues.getValue().getNumberOfGameScoreKeepers(); keeperIndex++) {
					GameScoreKeeper keeper = keeperValues.getValue().getGameScoreKeeperByIndex(keeperIndex);
					writer.append(keeperValues.getKey().getName());
					writer.append(SEPARATOR);

					for (int attemptIndex = 0; attemptIndex < keeper.getNumberOfAttempts(); attemptIndex++) {
						Attempt attempt = keeper.getAttemptByIndex(attemptIndex);

						writer.append(attempt.getBall().getName());
						writer.append(SEPARATOR);
						Integer valueOfAttempt = gameScoreCalculator.calculateScoreForAttempt(keeper, attemptIndex);
						writer.append(String.valueOf(valueOfAttempt));
						writer.append(SEPARATOR);
					}
					writer.append(EOL);
				}

			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
