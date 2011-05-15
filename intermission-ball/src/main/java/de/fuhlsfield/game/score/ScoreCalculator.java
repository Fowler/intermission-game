package de.fuhlsfield.game.score;

import java.util.List;

import de.fuhlsfield.game.Ball;

public interface ScoreCalculator {

	int calculateScore(GameScoreKeeper gameScoreKeeper);

	int forecastScore(GameScoreKeeper gameScoreKeeper, List<Ball> balls);

}