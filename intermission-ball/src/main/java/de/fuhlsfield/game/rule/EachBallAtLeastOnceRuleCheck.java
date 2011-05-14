package de.fuhlsfield.game.rule;

import java.util.ArrayList;
import java.util.List;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class EachBallAtLeastOnceRuleCheck implements RuleCheck {

	private final List<Ball> balls;

	public EachBallAtLeastOnceRuleCheck(List<Ball> balls) {
		this.balls = balls;
	}

	@Override
	public List<List<Ball>> selectPossibleAttempts(List<List<Ball>> possibleBallsLeft, GameScoreKeeper gameScoreKeeper) {
		ArrayList<List<Ball>> resultPossibleLefts = new ArrayList<List<Ball>>(possibleBallsLeft);
		List<Ball> successfulAttempts = gameScoreKeeper.getSuccessfulAttempts();
		for (List<Ball> ballsLeft : possibleBallsLeft) {
			for (Ball ball : this.balls) {
				if (!ballsLeft.contains(ball) && !successfulAttempts.contains(ball)) {
					resultPossibleLefts.remove(ballsLeft);
				}
			}
		}
		return resultPossibleLefts;
	}

}