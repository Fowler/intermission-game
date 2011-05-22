package de.fuhlsfield.game;

public class Attempt {

	private final Ball ball;
	private final boolean isSuccessful;

	public Attempt(Ball ball, boolean isSuccessful) {
		this.ball = ball;
		this.isSuccessful = isSuccessful;
	}

	public Ball getBall() {
		return this.ball;
	}

	public boolean isSuccessful() {
		return this.isSuccessful;
	}

}