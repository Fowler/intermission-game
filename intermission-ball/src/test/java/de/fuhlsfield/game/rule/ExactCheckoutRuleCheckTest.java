package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.GameConfig;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.score.BallValueMapper;
import de.fuhlsfield.game.score.StandardScoreCalculator;

public class ExactCheckoutRuleCheckTest {

	private static final Player PLAYER_A = new Player("Player A");
	private static final Player PLAYER_B = new Player("Player B");

	private final GameConfig gameConfig = mock(GameConfig.class);
	private final Game game = new Game(this.gameConfig, 10, PLAYER_A, PLAYER_B);

	@Before
	public void setUp() {
		BallValueMapper ballValueMapper = new BallValueMapper();
		ballValueMapper.add(Ball.BUNTI, 2);
		ballValueMapper.add(Ball.SCHWAMMI, 3);
		ballValueMapper.add(Ball.TISCHI_BALLI, 4);
		when(this.gameConfig.getBallValueMapper()).thenReturn(ballValueMapper);
		when(this.gameConfig.getRuleChecks()).thenReturn(new ArrayList<RuleCheck>());
		when(this.gameConfig.getScoreCalculator()).thenReturn(new StandardScoreCalculator(ballValueMapper));
	}

	@Test
	public void thatIsAttemptPossibleWhenExactCheckout() {
		addAttempt(Ball.BUNTI);
		addAttempt(Ball.SCHWAMMI);
		when(this.gameConfig.getTargetPoints()).thenReturn(9);
		assertTrue(createClassUnderTest().isAttemptPossible(Ball.TISCHI_BALLI, this.game));
	}

	@Test
	public void thatIsAttemptPossibleWhenAfterAttemptExactCheckoutPossible() {
		addAttempt(Ball.BUNTI);
		addAttempt(Ball.SCHWAMMI);
		when(this.gameConfig.getTargetPoints()).thenReturn(10);
		assertTrue(createClassUnderTest().isAttemptPossible(Ball.SCHWAMMI, this.game));
	}

	@Test
	public void thatIsAttemptNotPossibleWhenAfterAttemptNoExactCheckoutPossible() {
		addAttempt(Ball.BUNTI);
		addAttempt(Ball.SCHWAMMI);
		when(this.gameConfig.getTargetPoints()).thenReturn(10);
		assertFalse(createClassUnderTest().isAttemptPossible(Ball.TISCHI_BALLI, this.game));
	}

	@Test
	public void thatIsAttemptNotPossibleWhenMorePointsThanNeeded() {
		addAttempt(Ball.BUNTI);
		addAttempt(Ball.SCHWAMMI);
		when(this.gameConfig.getTargetPoints()).thenReturn(8);
		assertFalse(createClassUnderTest().isAttemptPossible(Ball.TISCHI_BALLI, this.game));
	}

	private void addAttempt(Ball ball) {
		this.game.check(ball, PLAYER_A, true);
		this.game.check(ball, PLAYER_B, true);
	}

	private ExactCheckoutRuleCheck createClassUnderTest() {
		return new ExactCheckoutRuleCheck();
	}

}