package de.fuhlsfield.game.score;

import java.util.List;

import de.fuhlsfield.game.Ball;

public interface GameScoreCalculator {

	int UNDEFINED_SCORE = Integer.MIN_VALUE;

	int calculateScore(GameScoreKeeper gameScoreKeeper);

	int calculateScore(List<Ball> balls);

	int calculateScore(GameScoreKeeper gameScoreKeeper, List<Ball> balls);

	int calculateScoreForAttempt(GameScoreKeeper gameScoreKeeper, int index);

}