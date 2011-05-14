package de.fuhlsfield.game.score;

import java.util.List;

import de.fuhlsfield.game.Ball;

public interface ScoreCalculator {

	int calculateScore(GameScoreKeeper gameScoreKeeper);

	int preCalculateScore(GameScoreKeeper gameScoreKeeper, List<Ball> balls);

}