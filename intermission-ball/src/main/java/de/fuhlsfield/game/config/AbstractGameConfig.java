package de.fuhlsfield.game.config;

import java.util.List;

import de.fuhlsfield.game.Ball;

public abstract class AbstractGameConfig implements GameConfig {

	protected final BallValueMapper ballValueMapper = new BallValueMapper();

	public AbstractGameConfig() {
		addBallValues();
	}

	@Override
	public final List<Ball> getAllowedBalls() {
		return this.ballValueMapper.getBalls();
	}

	protected abstract void addBallValues();

}