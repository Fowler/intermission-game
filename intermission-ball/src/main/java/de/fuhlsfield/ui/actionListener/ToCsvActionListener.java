package de.fuhlsfield.ui.actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.persistence.GameScoreCsvExporter;
import de.fuhlsfield.game.persistence.SeasonScoreCsvExporter;
import de.fuhlsfield.game.score.GameScoreCalculator;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.SeasonScoreKeeper;

public class ToCsvActionListener implements ActionListener {

	private final Map<Player, SeasonScoreKeeper> seasonScoreKeepers;
	private final Map<Player, GameScoreKeeper> gameScoreKeepers;
	private final GameScoreCalculator gameScoreCalculator;

	public ToCsvActionListener(Map<Player, SeasonScoreKeeper> seasonScoreKeepers,
			Map<Player, GameScoreKeeper> gameScoreKeepers, GameScoreCalculator gameScoreCalculator) {
		this.seasonScoreKeepers = seasonScoreKeepers;
		this.gameScoreKeepers = gameScoreKeepers;
		this.gameScoreCalculator = gameScoreCalculator;
	}

	public void actionPerformed(ActionEvent e) {
		new GameScoreCsvExporter().export(this.gameScoreKeepers, this.gameScoreCalculator);
		new SeasonScoreCsvExporter().export(this.seasonScoreKeepers, this.gameScoreCalculator);
	}

}