package de.fuhlsfield.game.persistence;

class CsvFileProperties {

	private static final String EXPORT_DIR = System.getProperty("user.home");
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String SEPARATOR = ",";
	private static final String CHARSET = "UTF-8";
	private static final String HEADLINE_PLAYERS = "Spieler";
	private static final String HEADLINE_CURRENT_SCORE = "aktueller Spielstand";
	private static final String HEADLINE_SEASON_SCORE = "Saisonspielstand";
	private static final String HEADLINE_ALL_SCORES = "bisherige Spiele";

	private final String shortName;

	CsvFileProperties(String shortName) {
		this.shortName = shortName;
	}

	String getScoreFileName() {
		return getFileName("csv");
	}

	String getConfigFileName() {
		return getFileName("cfg");
	}

	String getSeparator() {
		return SEPARATOR;
	}

	String getCharSet() {
		return CHARSET;
	}

	String getHeadlinePlayers() {
		return HEADLINE_PLAYERS;
	}

	String getHeadlineCurrentScore() {
		return HEADLINE_CURRENT_SCORE;
	}

	String getHeadlineSeasonScore() {
		return HEADLINE_SEASON_SCORE;
	}

	String getHeadlineAllScores() {
		return HEADLINE_ALL_SCORES;
	}

	private String getFileName(String extension) {
		return EXPORT_DIR + FILE_SEPARATOR + "scores." + this.shortName + "." + extension;
	}

}