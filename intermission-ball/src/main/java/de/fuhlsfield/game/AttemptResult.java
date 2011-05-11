package de.fuhlsfield.game;

public class AttemptResult extends Attempt {
	
	public static final AttemptResult NO_ATTEMPT_RESULT = new AttemptResult(null, null, false);
	
	private final boolean isSuccess;
	
	public AttemptResult(Player player, Ball ball, boolean isSuccess) {
		super(player, ball);
		this.isSuccess = isSuccess;
	}
	
	public boolean isSuccess () {
		return this.isSuccess;
	}
	
}