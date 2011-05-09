package de.fuhlsfield.game;

import java.util.HashSet;
import java.util.Set;

public enum GameConfig {
	
	BIG_BASKET("Großer Korb", 14, 10);
	
	static {
		BIG_BASKET.addBallValue(new BallValue(Ball.TISCHI_BALLI, 4));
		BIG_BASKET.addBallValue(new BallValue(Ball.SCHWAMMI, 3));
		BIG_BASKET.addBallValue(new BallValue(Ball.BASKI, 3));
		BIG_BASKET.addBallValue(new BallValue(Ball.FROESCHI, 2));
		BIG_BASKET.addBallValue(new BallValue(Ball.BUNTI, 2));
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
	
	private void addBallValue (BallValue ballValue) {
		this.ballValues.add(ballValue);
	}
	
	private void addRuleCheck (RuleCheck ruleCheck) {
		this.ruleChecks.add(ruleCheck);
	}

}