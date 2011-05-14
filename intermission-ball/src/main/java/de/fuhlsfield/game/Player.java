package de.fuhlsfield.game;

public class Player {

	public static final Player NO_PLAYER = new Player(null);

	private final String name;

	public Player(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}