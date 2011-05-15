package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class ExactCheckoutRuleCheck implements RuleCheck {

	private final int targetPoints;

	public ExactCheckoutRuleCheck(int targetPoints) {
		this.targetPoints = targetPoints;
	}

	@Override
	public List<List<Ball>> selectPossibleAttempts(List<List<Ball>> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		List<List<Ball>> resultPossibleLefts = new ArrayList<List<Ball>>(possibleBallsLeft);
		for (List<Ball> ballsLeft : possibleBallsLeft) {
			if (gameScoreKeeper.forecastScore(ballsLeft) > this.targetPoints) {
				resultPossibleLefts.remove(ballsLeft);
			}
		}
		return resultPossibleLefts;
	}

}