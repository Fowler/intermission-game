package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;
import de.fuhlsfield.game.score.ScoreCalculator;

public class ExactCheckoutRuleCheck implements RuleCheck {

	private final int targetPoints;
	private final ScoreCalculator scoreCalculator;

	public ExactCheckoutRuleCheck(int targetPoints, ScoreCalculator scoreCalculator) {
		this.targetPoints = targetPoints;
		this.scoreCalculator = scoreCalculator;
	}

	@Override
	public List<List<Ball>> selectPossibleAttempts(List<List<Ball>> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		List<List<Ball>> resultPossibleLefts = new ArrayList<List<Ball>>(possibleBallsLeft);
		for (List<Ball> ballsLeft : possibleBallsLeft) {
			if (this.scoreCalculator.calculateScore(gameScoreKeeper, ballsLeft) > this.targetPoints) {
				resultPossibleLefts.remove(ballsLeft);
			}
		}
		return resultPossibleLefts;
	}

}