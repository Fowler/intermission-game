package de.fuhlsfield.game;

public class Attempt {

	private final Ball ball;
	private final boolean isSuccessful;

	public Attempt(Ball ball, boolean isSuccessful) {
		this.ball = ball;
		this.isSuccessful = isSuccessful;
	}

	@Override
	public String toString() {
		if (this.isSuccessful) {
			return this.ball.getName();
		}
		return "-" + this.ball.getName();
	}

	public Ball getBall() {
		return this.ball;
	}

	public boolean isSuccessful() {
		return this.isSuccessful;
	}

}