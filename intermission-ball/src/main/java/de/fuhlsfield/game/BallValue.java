package de.fuhlsfield.game;

public class BallValue {
	
	private final Ball ball;
	private final int value;
	private final boolean isPossibleCheckout;
	
	public BallValue (Ball ball, int value) {
		this(ball, value, true);
	}
	
	public BallValue (Ball ball, int value, boolean isPossibleCheckout) {
		this.ball = ball;
		this.value = value;
		this.isPossibleCheckout = isPossibleCheckout;
	}
	
	public Ball getBall () {
		return this.ball;
	}
	
	public int getValue () {
		return this.value;
	}
	
	public boolean isPossibleCheckout () {
		return this.isPossibleCheckout;
	}

}