package de.fuhlsfield.game.rule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import de.fuhlsfield.game.Attempt;
import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.config.BallValueMapper;
import de.fuhlsfield.game.score.GameScoreKeeper;

public abstract class AbstractRuleCheckTest<T extends RuleCheck> {

	protected static final int TARGET_POINTS = 11;

	protected final GameScoreKeeper gameScoreKeeper = new GameScoreKeeper();
	protected final BallValueMapper ballValueMapper = mock(BallValueMapper.class);

	@Before
	public void beforeTest() {
		when(this.ballValueMapper.getValue(Ball.BUNTI)).thenReturn(1);
		when(this.ballValueMapper.getValue(Ball.BASKI)).thenReturn(3);
		when(this.ballValueMapper.getValue(Ball.SCHWAMMI)).thenReturn(7);
	}

	protected List<Ball> createBallsLeft(Ball... balls) {
		return Arrays.asList(balls);
	}

	protected void addAttempts(Ball... balls) {
		for (Ball ball : balls) {
			this.gameScoreKeeper.addAttempt(new Attempt(ball, true));
		}
	}

	protected abstract T createInstanceUnderTest();

}