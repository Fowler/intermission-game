package de.fuhlsfield.game.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.config.GameConfig;

public class ScoreCsvImporter {

	private final CsvFileProperties csvFileProperties;

	public ScoreCsvImporter(GameConfig gameConfig) {
		this.csvFileProperties = new CsvFileProperties(gameConfig.getShortName());
	}

	public Game importScoreAndConfig() {
		GameConfig gameConfig = importConfig();
		List<Player> players = importPlayers();
		Game game = new Game(gameConfig, players);
		importSeasonGameScores(game);
		importCurrentGameScore(game);
		for (Player player : players) {
			game.upateBallRuleCheckStates(player);
		}
		return game;
	}

	private GameConfig importConfig() {
		File file = new File(this.csvFileProperties.getConfigFileName());
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(file));
			return (GameConfig) inputStream.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		} finally {
			closeInputStream(inputStream);
		}
		return null;
	}

	private List<Player> importPlayers() {
		LinkedList<Player> players = new LinkedList<Player>();
		BufferedReader reader = null;
		try {
			reader = createBufferedReaderForScore();
			if (readUpToMarker(reader, this.csvFileProperties.getHeadlinePlayers())) {
				List<String> playersArray = split(reader.readLine());
				for (String player : playersArray) {
					if (!player.isEmpty()) {
						players.add(new Player(player));
					}
				}
			}
		} catch (IOException e) {
		} finally {
			closeReader(reader);
		}
		return players;
	}

	private void importCurrentGameScore(Game game) {
		BufferedReader reader = null;
		try {
			reader = createBufferedReaderForScore();
			if (readUpToMarker(reader, this.csvFileProperties.getHeadlineCurrentScore())) {
				String row;
				while (((row = reader.readLine()) != null)
						&& !row.equals(this.csvFileProperties.getHeadlineSeasonScore())) {
					importAttempt(game, row);
				}
			}
		} catch (IOException e) {
		} finally {
			closeReader(reader);
		}
	}

	private void importSeasonGameScores(Game game) {
		BufferedReader reader = null;
		try {
			reader = createBufferedReaderForScore();
			if (readUpToMarker(reader, this.csvFileProperties.getHeadlineAllScores())) {
				String row;
				while (((row = reader.readLine()) != null)
						&& !row.equals(this.csvFileProperties.getHeadlineStatistic())) {
					importAttempt(game, row);
					if (game.isGameFinished()) {
						game.finishGame();
					}
				}
			}
		} catch (IOException e) {
		} finally {
			closeReader(reader);
		}
	}

	private BufferedReader createBufferedReaderForScore() throws IOException {
		File file = new File(this.csvFileProperties.getScoreFileName());
		return new BufferedReader(new InputStreamReader(new FileInputStream(file), this.csvFileProperties.getCharSet()));
	}

	private void importAttempt(Game game, String row) {
		List<Player> players = game.getPlayers();
		List<String> scoreArray = split(row);
		for (Player player : players) {
			int index = players.indexOf(player) * 2 + 1;
			if (index < scoreArray.size()) {
				Ball ball = Ball.getBallByName(scoreArray.get(index));
				boolean isSuccessful = Integer.valueOf(scoreArray.get(index + 1)) > 0;
				game.getGameScoreKeeper(player).addAttempt(new Attempt(ball, isSuccessful));
			}
		}
	}

	private boolean readUpToMarker(BufferedReader reader, String marker) throws IOException {
		String row;
		while (((row = reader.readLine()) != null) && !marker.equals(row)) {
		}
		return marker.equals(row);
	}

	private List<String> split(String stringToSplit) {
		LinkedList<String> stringResult = new LinkedList<String>();
		for (String s : stringToSplit.split(this.csvFileProperties.getSeparator())) {
			if (!s.isEmpty()) {
				stringResult.add(s);
			}
		}
		return stringResult;
	}

	private void closeReader(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}

	private void closeInputStream(InputStream inputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}

}