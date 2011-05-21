package de.fuhlsfield.game.rule;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class RuleCheckDisposer {

	private final Set<RuleCheck> ruleChecks;

	RuleCheckDisposer(Set<RuleCheck> ruleChecks) {
		this.ruleChecks = ruleChecks;
	}

	List<List<Ball>> disposeRuleChecks(List<List<Ball>> allBalls, GameScoreKeeper gameScoreKeeper) {
		LinkedList<List<Ball>> resultAllBalls = new LinkedList<List<Ball>>(allBalls);
		for (RuleCheck ruleCheck : this.ruleChecks) {
			for (List<Ball> balls : allBalls) {
				if (!ruleCheck.isPossibleAttempts(balls, gameScoreKeeper)) {
					resultAllBalls.remove(balls);
				}
			}
		}
		return resultAllBalls;
	}

}