package de.fuhlsfield.game;

public class BallValue {
	
	private final Ball ball;
	private final int value;
	private final boolean isPossibleCheckout;
	
	public BallValue (Ball ball, int value) {
		this(ball, value, true);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.ball == null) ? 0 : this.ball.hashCode());
		result = prime * result + (this.isPossibleCheckout ? 1231 : 1237);
		result = prime * result + this.value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BallValue other = (BallValue) obj;
		if (this.ball == null) {
			if (other.ball != null)
				return false;
		} else if (!this.ball.equals(other.ball))
			return false;
		if (this.isPossibleCheckout != other.isPossibleCheckout)
			return false;
		if (this.value != other.value)
			return false;
		return true;
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