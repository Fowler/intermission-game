package de.fuhlsfield.game;

public class Attempt {
	
	private final Player player;
	private final Ball ball;
	
	public Attempt (Player player, Ball ball) {
		this.ball = ball;
		this.player = player;
	}
	
	public Player getPlayer () {
		return this.player;
	}
	
	public Ball getBall () {
		return this.ball;
	}

}