package de.fuhlsfield.game.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.config.GameConfig;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.SeasonScoreCalculator;
import de.fuhlsfield.game.score.SeasonScoreKeeper;

public class ScoreCsvExporter {

	private final List<Player> players;
	private final SeasonScoreCalculator seasonScoreCalculator;
	private final GameConfig gameConfig;

	public ScoreCsvExporter(List<Player> players, SeasonScoreCalculator seasonScoreCalculator, GameConfig gameConfig) {
		this.players = players;
		this.seasonScoreCalculator = seasonScoreCalculator;
		this.gameConfig = gameConfig;
	}

	public void exportScoreAndConfig(Map<Player, SeasonScoreKeeper> seasonScoreKeepers,
			Map<Player, GameScoreKeeper> gameScoreKeepers) {
		exportScore(seasonScoreKeepers, gameScoreKeepers);
		exportConfig();
	}

	private void exportScore(Map<Player, SeasonScoreKeeper> seasonScoreKeepers,
			Map<Player, GameScoreKeeper> gameScoreKeepers) {
		File file = new File(getFileName() + ".csv");
		Writer writer = null;
		try {
			file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(outputStream, CsvConstants.CHARSET));
			writeHeadline(writer);
			writeCurrentGameScore(writer, gameScoreKeepers);
			writeSeasonScore(writer, seasonScoreKeepers);
			writeSeasonGameScores(writer, seasonScoreKeepers);
			writer.flush();
		} catch (IOException e) {
		} finally {
			closeWriter(writer);
		}
	}

	private void exportConfig() {
		File file = new File(getFileName() + ".cfg");
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			file.createNewFile();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this.gameConfig);
			fileOutputStream.flush();
		} catch (IOException e) {
		} finally {
			closeOutputStream(fileOutputStream);
		}
	}

	private String getFileName() {
		return CsvConstants.EXPORT_DIR + CsvConstants.FILE_SEPARATOR + "scores." + this.gameConfig.getShortName();
	}

	private void writeHeadline(Writer writer) throws IOException {
		writer.append(CsvConstants.SEPARATOR);
		for (Player player : this.players) {
			writer.append(player.getName());
			writer.append(CsvConstants.SEPARATOR);
			writer.append(CsvConstants.SEPARATOR);
		}
		writer.append(CsvConstants.EOL);
	}

	private void writeCurrentGameScore(Writer writer, Map<Player, GameScoreKeeper> gameScoreKeepers) throws IOException {
		writer.write(CsvConstants.HEADLINE_CURRENT_SCORE);
		writer.write(CsvConstants.EOL);
		writeGameScore(writer, gameScoreKeepers);
	}

	private void writeSeasonScore(Writer writer, Map<Player, SeasonScoreKeeper> seasonScoreKeepers) throws IOException {
		writer.write("Saisonspielstand");
		writer.write(CsvConstants.EOL);
		for (int i = 0; i < seasonScoreKeepers.get(this.players.get(0)).getNumberOfGameScoreKeepers(); i++) {
			writer.write(String.valueOf(i + 1));
			writer.write(CsvConstants.SEPARATOR);
			for (Player player : this.players) {
				writer.write(String.valueOf(this.seasonScoreCalculator.calculateScore(seasonScoreKeepers, player, i)));
				writer.write(CsvConstants.SEPARATOR);
				writer.write(CsvConstants.SEPARATOR);
			}
			writer.write(CsvConstants.EOL);
		}
	}

	private void writeSeasonGameScores(Writer writer, Map<Player, SeasonScoreKeeper> seasonScoreKeepers)
			throws IOException {
		writer.write(CsvConstants.HEADLINE_ALL_SCORES);
		writer.write(CsvConstants.EOL);
		for (int i = 0; i < seasonScoreKeepers.get(this.players.get(0)).getNumberOfGameScoreKeepers(); i++) {
			HashMap<Player, GameScoreKeeper> gameScoreKeepersToWrite = new HashMap<Player, GameScoreKeeper>();
			for (Player player : this.players) {
				gameScoreKeepersToWrite.put(player, seasonScoreKeepers.get(player).getGameScoreKeeperByIndex(i));
			}
			writeGameScore(writer, gameScoreKeepersToWrite);
		}
	}

	private void writeGameScore(Writer writer, Map<Player, GameScoreKeeper> gameScoreKeepers) throws IOException {
		for (int i = 0; i < gameScoreKeepers.get(this.players.get(0)).getNumberOfAttempts(); i++) {
			writer.write(String.valueOf(i + 1));
			writer.write(CsvConstants.SEPARATOR);
			for (Player player : this.players) {
				GameScoreKeeper gameScoreKeeper = gameScoreKeepers.get(player);
				Attempt attempt = gameScoreKeeper.getAttemptByIndex(i);
				if (attempt != null) {
					writer.write(attempt.getBall().getName());
				}
				writer.write(CsvConstants.SEPARATOR);
				if (attempt != null) {
					writer.write(String.valueOf(this.gameConfig.getGameScoreCalculator().calculateScoreForAttempt(
							gameScoreKeeper, i)));
				}
				writer.write(CsvConstants.SEPARATOR);
			}
			writer.write(CsvConstants.EOL);
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

	private void closeOutputStream(OutputStream outputStream) {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}

}