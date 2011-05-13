package de.fuhlsfield.game;

import java.util.Arrays;
import java.util.List;

import de.fuhlsfield.game.rule.AttemptLeftRuleCheck;
import de.fuhlsfield.game.rule.BallTakesPartRuleCheck;
import de.fuhlsfield.game.rule.EachBallAtLeastOnceRuleCheck;
import de.fuhlsfield.game.rule.ExactCheckoutRuleCheck;
import de.fuhlsfield.game.rule.NextPlayerRuleCheck;
import de.fuhlsfield.game.rule.RuleCheck;
import de.fuhlsfield.game.score.BallValueMapper;
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
			return new StandardScoreCalculator(getBallValueMapper());
		}

		@Override
		public List<RuleCheck> getRuleChecks() {
			return Arrays.asList(new NextPlayerRuleCheck(),
					new BallTakesPartRuleCheck(),
					new EachBallAtLeastOnceRuleCheck(),
					new AttemptLeftRuleCheck(), new ExactCheckoutRuleCheck());
		}

		@Override
		public String getName() {
			return "Fünfball Konfiguration";
		}

		@Override
		public BallValueMapper getBallValueMapper() {
			BallValueMapper ballValueMapper = new BallValueMapper();
			ballValueMapper.add(Ball.BUNTI, 2);
			ballValueMapper.add(Ball.FROESCHI, 2);
			ballValueMapper.add(Ball.BASKI, 3);
			ballValueMapper.add(Ball.SCHWAMMI, 3);
			ballValueMapper.add(Ball.TISCHI_BALLI, 4);
			return ballValueMapper;
		}

	};

	String getName();

	int getTargetPoints();

	ScoreCalculator getScoreCalculator();

	BallValueMapper getBallValueMapper();

	List<RuleCheck> getRuleChecks();

}