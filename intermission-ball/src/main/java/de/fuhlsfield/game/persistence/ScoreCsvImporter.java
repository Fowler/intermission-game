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

	private final String shortName;

	public ScoreCsvImporter(GameConfig gameConfig) {
		this.shortName = gameConfig.getShortName();
	}

	public Game importScoreAndConfig() {
		File file = new File(getFileName() + ".cfg");
		FileInputStream fileInputStream = null;
		GameConfig gameConfig = null;
		try {
			fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			gameConfig = (GameConfig) objectInputStream.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		} finally {
			closeInputStream(fileInputStream);
		}
		List<Player> players = importPlayers();
		Game game = new Game(gameConfig, players);
		importSeasonGameScores(game);
		return game;
	}

	private String getFileName() {
		return CsvConstants.EXPORT_DIR + CsvConstants.FILE_SEPARATOR + "scores." + this.shortName;
	}

	private List<Player> importPlayers() {
		LinkedList<Player> players = new LinkedList<Player>();
		File file = new File(getFileName() + ".csv");
		BufferedReader reader = null;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(inputStream, CsvConstants.CHARSET));
			List<String> playersArray = split(reader.readLine());
			for (String player : playersArray) {
				if (!player.isEmpty()) {
					players.add(new Player(player));
				}
			}
			reader.close();
		} catch (IOException e) {
		} finally {
			closeReader(reader);
		}
		return players;
	}

	private void importSeasonGameScores(Game game) {
		List<Player> players = game.getPlayers();
		File file = new File(getFileName() + ".csv");
		BufferedReader reader = null;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(inputStream, CsvConstants.CHARSET));
			String row;
			while (((row = reader.readLine()) != null) && !CsvConstants.HEADLINE_ALL_SCORES.equals(row)) {
			}
			if (CsvConstants.HEADLINE_ALL_SCORES.equals(row)) {
				while ((row = reader.readLine()) != null) {
					System.out.println(row);
					List<String> scoreArray = split(row);
					for (Player player : players) {
						int playerIndex = players.indexOf(player);
						Ball ball = Ball.getBallByName(scoreArray.get(2 * playerIndex + 1));
						boolean isSuccessful = Integer.valueOf(scoreArray.get(2 * playerIndex + 2)) > 0;
						game.getGameScoreKeeper(player).addAttempt(new Attempt(ball, isSuccessful));
					}
					if (game.isGameFinished()) {
						game.finishGame();
					}
				}
			}
			reader.close();
		} catch (IOException e) {
		} finally {
			closeReader(reader);
		}
	}

	private List<String> split(String stringToSplit) {
		LinkedList<String> stringResult = new LinkedList<String>();
		for (String s : stringToSplit.split(CsvConstants.SEPARATOR)) {
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