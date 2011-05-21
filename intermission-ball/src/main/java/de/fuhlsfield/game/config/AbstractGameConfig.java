package de.fuhlsfield.game.config;

import java.util.List;
import java.util.Set;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.rule.RuleCheck;

public abstract class AbstractGameConfig implements GameConfig {

	protected final BallValueMapper ballValueMapper = new BallValueMapper();
	private final Set<RuleCheck> ruleChecks;

	public AbstractGameConfig() {
		addBallValues();
		this.ruleChecks = createRuleChecks();
	}

	@Override
	public final List<Ball> getAllowedBalls() {
		return this.ballValueMapper.getBalls();
	}

	@Override
	public final Set<RuleCheck> getRuleChecks() {
		return this.ruleChecks;
	}

	protected abstract void addBallValues();

	protected abstract Set<RuleCheck> createRuleChecks();

}