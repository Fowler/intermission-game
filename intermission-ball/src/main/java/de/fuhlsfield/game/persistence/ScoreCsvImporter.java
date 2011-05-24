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

import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.config.GameConfig;

public class ScoreCsvImporter {

	private static final String EXPORT_DIR = System.getProperty("user.home");
	private static final String EOL = System.getProperty("line.separator");
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String SEPARATOR = ",";

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
		Game game = new Game(gameConfig, importPlayers());
		return game;
	}

	private String getFileName() {
		return EXPORT_DIR + FILE_SEPARATOR + "scores." + this.shortName;
	}

	private List<Player> importPlayers() {
		LinkedList<Player> players = new LinkedList<Player>();
		File file = new File(getFileName() + ".csv");
		BufferedReader reader = null;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
			String[] playersArray = reader.readLine().split(SEPARATOR);
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