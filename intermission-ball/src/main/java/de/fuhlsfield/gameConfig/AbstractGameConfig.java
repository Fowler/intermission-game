package de.fuhlsfield.gameConfig;

import java.util.List;

import de.fuhlsfield.game.Ball;

public abstract class AbstractGameConfig implements GameConfig {

	protected final BallValueMapper ballValueMapper = new BallValueMapper();

	@Override
	public final List<Ball> getAllowedBalls() {
		return this.ballValueMapper.getBalls();
	}

	protected abstract void addBallValues();

}