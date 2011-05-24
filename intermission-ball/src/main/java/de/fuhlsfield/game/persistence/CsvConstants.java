package de.fuhlsfield.game.persistence;

interface CsvConstants {

	static final String EXPORT_DIR = System.getProperty("user.home");
	static final String EOL = System.getProperty("line.separator");
	static final String FILE_SEPARATOR = System.getProperty("file.separator");
	static final String SEPARATOR = ",";
	static final String CHARSET = "UTF-8";
	static final String HEADLINE_CURRENT_SCORE = "aktueller Spielstand";
	static final String HEADLINE_ALL_SCORES = "bisherige Spiele";

}