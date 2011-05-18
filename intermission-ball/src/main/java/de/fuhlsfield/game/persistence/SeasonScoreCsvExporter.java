package de.fuhlsfield.game.persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.SeasonScoreKeeper;

public class SeasonScoreCsvExporter {

	private static final String EOL = "\n";
	private static final String SEPARATOR = ",";

	public void export(Game game) {
		FileWriter writer;
		try {
			writer = new FileWriter("/tmp/season-scores.csv");

			Map<Player, SeasonScoreKeeper> gameScoreKeepers = game
					.getSeasonScoreKeepers();

			for (Map.Entry<Player, SeasonScoreKeeper> keeperValues : gameScoreKeepers
					.entrySet()) {

				for (GameScoreKeeper keeper : keeperValues.getValue()
						.getGameScoreKeepers()) {

					writer.append(keeperValues.getKey().getName());
					writer.append(SEPARATOR);

					for (Attempt attempt : keeper.getAttempts()) {

						writer.append(attempt.isSuccessful() ? attempt
								.getBall().getName() : "-"
								+ attempt.getBall().getName());
						writer.append(SEPARATOR);
						Integer valueOfAttept = attempt.isSuccessful() ? game
								.getGameConfig().getBallValueMapper().getValue(
										attempt.getBall()) : 0;
						writer.append(String.valueOf(valueOfAttept));
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
