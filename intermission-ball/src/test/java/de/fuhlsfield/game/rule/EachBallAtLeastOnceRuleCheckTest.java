package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.BallValue;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.GameConfig;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.StandardScoreCalculator;

public class EachBallAtLeastOnceRuleCheckTest {

	private static final BallValue BALL_TWO_POINTS = new BallValue(Ball.BUNTI, 2);
	private static final BallValue BALL_THREE_POINTS = new BallValue(Ball.BASKI, 3);
	private static final BallValue BALL_FOUR_POINTS = new BallValue(Ball.TISCHI_BALLI, 4);
	private static final Player PLAYER = new Player("Player");
	
	private final GameConfig gameConfig = mock(GameConfig.class);
	
	@Before
	public void setUp () {
		when(this.gameConfig.getBallValues()).thenReturn(Arrays.asList(BALL_TWO_POINTS, BALL_THREE_POINTS, BALL_FOUR_POINTS));
		when(this.gameConfig.getRuleChecks()).thenReturn(new ArrayList<RuleCheck>());
		when(this.gameConfig.getScoreCalculator()).thenReturn(new StandardScoreCalculator());
	}
	
	@Test
	public void thatIsAttemptPossibleWhenScoreGreaterTargetPoints () {
		Game game = new Game(this.gameConfig, 10, PLAYER);
		game.check(BALL_TWO_POINTS.getBall(), PLAYER, true);
		game.check(BALL_THREE_POINTS.getBall(), PLAYER, true);
		when(this.gameConfig.getTargetPoints()).thenReturn(8);
		assertTrue(createClassUnderTest().isAttemptPossible(new Attempt(PLAYER, BALL_FOUR_POINTS.getBall()), game));
	}
	
	@Test
	public void thatIsAttemptPossibleWhenScoreEqualsTargetPoints () {
		Game game = new Game(this.gameConfig, 10, PLAYER);
		game.check(BALL_TWO_POINTS.getBall(), PLAYER, true);
		game.check(BALL_THREE_POINTS.getBall(), PLAYER, true);
		when(this.gameConfig.getTargetPoints()).thenReturn(9);
		assertTrue(createClassUnderTest().isAttemptPossible(new Attempt(PLAYER, BALL_FOUR_POINTS.getBall()), game));
	}

	@Test
	public void thatIsAttemptNotPossibleWhenTargetPointsCouldBeAccomplishedWithoutAllBalls () {
		Game game = new Game(this.gameConfig, 10, PLAYER);
		game.check(BALL_FOUR_POINTS.getBall(), PLAYER, true);
		when(this.gameConfig.getTargetPoints()).thenReturn(11);
		assertFalse(createClassUnderTest().isAttemptPossible(new Attempt(PLAYER, BALL_FOUR_POINTS.getBall()), game));
	}

	private EachBallAtLeastOnceRuleCheck createClassUnderTest () {
		return new EachBallAtLeastOnceRuleCheck();
	}

}