package de.fuhlsfield.game.persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.SeasonScoreCalculator;
import de.fuhlsfield.game.score.SeasonScoreKeeper;

public class ScoreCsvExporter {

	private static final String EXPORT_DIR = System.getProperty("user.home");
	private static final String EOL = System.getProperty("line.separator");
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String SEPARATOR = ",";

	private final List<Player> players;
	private final SeasonScoreCalculator seasonScoreCalculator;
	private final GameScoreCalculator gameScoreCalculator;

	public ScoreCsvExporter(List<Player> players, SeasonScoreCalculator seasonScoreCalculator,
			GameScoreCalculator gameScoreCalculator) {
		this.players = players;
		this.seasonScoreCalculator = seasonScoreCalculator;
		this.gameScoreCalculator = gameScoreCalculator;
	}

	public void export(Map<Player, SeasonScoreKeeper> seasonScoreKeepers, Map<Player, GameScoreKeeper> gameScoreKeepers) {
		Writer writer = null;
		try {
			writer = new FileWriter(EXPORT_DIR + FILE_SEPARATOR + "scores.csv");
			writeHeadline(writer);
			writer.write(EOL);
			writeCurrentGameScore(writer, gameScoreKeepers);
			writer.write(EOL);
			writeSeasonScore(writer, seasonScoreKeepers);
			writer.write(EOL);
			writeSeasonGameScores(writer, seasonScoreKeepers);
			writer.flush();
			writer.close();
		} catch (IOException e) {
		} finally {
			closeWriter(writer);
		}
	}

	private void writeHeadline(Writer writer) throws IOException {
		writer.append(SEPARATOR);
		for (Player player : this.players) {
			writer.append(player.getName());
			writer.append(SEPARATOR);
			writer.append(SEPARATOR);
		}
		writer.append(EOL);
	}

	private void writeCurrentGameScore(Writer writer, Map<Player, GameScoreKeeper> gameScoreKeepers) throws IOException {
		writer.write("aktueller Spielstand");
		writer.write(EOL);
		writer.write(EOL);
		writeGameScore(writer, gameScoreKeepers);
	}

	private void writeSeasonScore(Writer writer, Map<Player, SeasonScoreKeeper> seasonScoreKeepers) throws IOException {
		writer.write("Saisonspielstand");
		writer.write(EOL);
		writer.write(EOL);
		for (int i = 0; i < seasonScoreKeepers.get(this.players.get(0)).getNumberOfGameScoreKeepers(); i++) {
			writer.write(String.valueOf(i + 1));
			writer.write(SEPARATOR);
			for (Player player : this.players) {
				writer.write(String.valueOf(this.seasonScoreCalculator.calculateScore(seasonScoreKeepers, player, i)));
				writer.write(SEPARATOR);
				writer.write(SEPARATOR);
			}
			writer.write(EOL);
		}
	}

	private void writeSeasonGameScores(Writer writer, Map<Player, SeasonScoreKeeper> seasonScoreKeepers)
			throws IOException {
		writer.write("bisherige Spiele");
		writer.write(EOL);
		writer.write(EOL);
		for (int i = 0; i < seasonScoreKeepers.get(this.players.get(0)).getNumberOfGameScoreKeepers(); i++) {
			HashMap<Player, GameScoreKeeper> gameScoreKeepersToWrite = new HashMap<Player, GameScoreKeeper>();
			for (Player player : this.players) {
				gameScoreKeepersToWrite.put(player, seasonScoreKeepers.get(player).getGameScoreKeeperByIndex(i));
			}
			writeGameScore(writer, gameScoreKeepersToWrite);
			writer.write(EOL);
		}
	}

	private void writeGameScore(Writer writer, Map<Player, GameScoreKeeper> gameScoreKeepers) throws IOException {
		for (int i = 0; i < gameScoreKeepers.get(this.players.get(0)).getNumberOfAttempts(); i++) {
			writer.write(String.valueOf(i + 1));
			writer.write(SEPARATOR);
			for (Player player : this.players) {
				GameScoreKeeper gameScoreKeeper = gameScoreKeepers.get(player);
				Attempt attempt = gameScoreKeeper.getAttemptByIndex(i);
				writer.write(attempt.getBall().getName());
				writer.write(SEPARATOR);
				writer.write(String.valueOf(this.gameScoreCalculator.calculateScoreForAttempt(gameScoreKeeper, i)));
				writer.write(SEPARATOR);
			}
			writer.write(EOL);
		}
	}

	private void closeWriter(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}

}