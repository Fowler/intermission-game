package de.fuhlsfield.game.rule;

import java.util.HashSet;
import java.util.Set;

import de.fuhlsfield.game.score.GameScoreKeeper;

public class RuleCheckDisposer {

	private final Set<RuleCheck> ruleChecks;

	RuleCheckDisposer(Set<RuleCheck> ruleChecks) {
		this.ruleChecks = ruleChecks;
	}

	Set<PossibleAttempts> disposeRuleChecks(Set<PossibleAttempts> allBalls, GameScoreKeeper gameScoreKeeper) {
		HashSet<PossibleAttempts> resultAllBalls = new HashSet<PossibleAttempts>(allBalls);
		for (RuleCheck ruleCheck : this.ruleChecks) {
			for (PossibleAttempts balls : allBalls) {
				if (!ruleCheck.isPossibleAttempts(balls, gameScoreKeeper)) {
					resultAllBalls.remove(balls);
				}
			}
		}
		return resultAllBalls;
	}

}