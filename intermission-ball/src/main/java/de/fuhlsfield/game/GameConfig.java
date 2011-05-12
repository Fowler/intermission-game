package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.rule.AttemptLeftRuleCheck;
import de.fuhlsfield.game.rule.BallTakesPartRuleCheck;
import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.NextPlayerRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.ScoreCalculator;
import de.fuhlsfield.game.score.StandardScoreCalculator;

public interface GameConfig {
	
	GameConfig FIVE_BALLS = new GameConfig() {
		
		@Override
		public int getTargetPoints() {
			return 14;
		}
		
		@Override
		public ScoreCalculator getScoreCalculator() {
			return new StandardScoreCalculator();
		}
		
		@Override
		public List<RuleCheck> getRuleChecks() {
			return Arrays.asList(new NextPlayerRuleCheck(), new BallTakesPartRuleCheck(), new EachBallAtLeastOnceRuleCheck(), new AttemptLeftRuleCheck(), new ExactCheckoutRuleCheck());
		}
		
		@Override
		public String getName() {
			return "Fünfball Konfiguration";
		}
		
		@Override
		public List<BallValue> getBallValues() {
			return Arrays.asList(new BallValue(Ball.TISCHI_BALLI, 4), new BallValue(Ball.SCHWAMMI, 3), new BallValue(Ball.BASKI, 3), new BallValue(Ball.FROESCHI, 2), new BallValue(Ball.BUNTI, 2));
		}
		
	};

	String getName();

	int getTargetPoints();

	ScoreCalculator getScoreCalculator ();

	List<BallValue> getBallValues();

	List<RuleCheck> getRuleChecks();

}