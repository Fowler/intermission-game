package de.fuhlsfield.game;

import java.util.HashSet;
import java.util.Set;

import de.fuhlsfield.game.rule.BallTakesPartRuleCheck;
import de.fuhlsfield.game.rule.PlayerTakesPartRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;

public enum GameConfig {
	
	FIVE_BALLS("Fünf Bälle", 14, 10);
	
	static {
		FIVE_BALLS.addBallValue(new BallValue(Ball.TISCHI_BALLI, 4));
		FIVE_BALLS.addBallValue(new BallValue(Ball.SCHWAMMI, 3));
		FIVE_BALLS.addBallValue(new BallValue(Ball.BASKI, 3));
		FIVE_BALLS.addBallValue(new BallValue(Ball.FROESCHI, 2));
		FIVE_BALLS.addBallValue(new BallValue(Ball.BUNTI, 2));
	}
	
	static {
		FIVE_BALLS.addRuleCheck(new PlayerTakesPartRuleCheck());
		FIVE_BALLS.addRuleCheck(new BallTakesPartRuleCheck());
	}
	
	private final String name;
	private final int targetPoints;
	private final int maxRounds;
	private Set<BallValue> ballValues;
	private Set<RuleCheck> ruleChecks;
	
	private GameConfig (String name, int targetPoints, int maxRounds){
		this.name = name;
		this.targetPoints = targetPoints;
		this.maxRounds = maxRounds;
		this.ballValues = new HashSet<BallValue>();
		this.ruleChecks = new HashSet<RuleCheck>();
	}
	
	public String getName () {
		return this.name;
	}
	
	public int getTargetPoints () {
		return this.targetPoints;
	}
	
	public int getMaxRounds () {
		return this.maxRounds;
	}
	
	public Set<BallValue> getBallValues () {
		return this.ballValues;
	}
	
	public Set<RuleCheck> getRuleChecks () {
		return this.ruleChecks;
	}
	
	private void addBallValue (BallValue ballValue) {
		this.ballValues.add(ballValue);
	}
	
	private void addRuleCheck (RuleCheck ruleCheck) {
		this.ruleChecks.add(ruleCheck);
	}

}