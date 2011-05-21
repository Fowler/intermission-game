package de.fuhlsfield.game;

public class Player {

	public static final Player NO_PLAYER = new Player("") {
	};

	private final String name;

	public Player(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}

	public String getName() {
		return this.name;
	}

}