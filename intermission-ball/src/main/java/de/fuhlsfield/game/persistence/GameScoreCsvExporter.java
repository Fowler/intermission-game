package de.fuhlsfield.game.persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class GameScoreCsvExporter {

	private static final String EOL = "\n";
	private static final String SEPARATOR = ",";

	public void export(Map<Player, GameScoreKeeper> gameScoreKeepers, GameScoreCalculator gameScoreCalculator) {
		FileWriter writer;
		try {
			writer = new FileWriter("/tmp/game-scores.csv");

			for (Map.Entry<Player, GameScoreKeeper> keeperValues : gameScoreKeepers.entrySet()) {
				writer.append(keeperValues.getKey().getName());
				writer.append(SEPARATOR);

				for (int i = 0; i < keeperValues.getValue().getNumberOfAttempts(); i++) {
					Attempt attempt = keeperValues.getValue().getAttemptByIndex(i);

					writer.append(attempt.getBall().getName());
					writer.append(SEPARATOR);
					Integer valueOfAttempt = gameScoreCalculator.calculateScoreForAttempt(keeperValues.getValue(), i);
					writer.append(String.valueOf(valueOfAttempt));
					writer.append(SEPARATOR);
				}

				writer.append(EOL);

			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
