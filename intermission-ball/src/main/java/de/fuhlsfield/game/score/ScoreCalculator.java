package de.fuhlsfield.game.score;

import java.util.List;

import de.fuhlsfield.game.Ball;

public interface ScoreCalculator {

	public static int UNDEFINED_SCORE = Integer.MIN_VALUE;

	int calculateScore(GameScoreKeeper gameScoreKeeper);

	int calculateScore(List<Ball> balls);

	int calculateScore(GameScoreKeeper gameScoreKeeper, List<Ball> balls);

	int calculateScore(SeasonScoreKeeper seasonScoreKeeper, int index);

	int calculateScoreForAttempt(GameScoreKeeper gameScoreKeeper, int index);

}