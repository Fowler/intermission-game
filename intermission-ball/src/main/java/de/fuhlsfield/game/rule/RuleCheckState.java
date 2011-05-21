package de.fuhlsfield.game.rule;

public enum RuleCheckState {

	CHECKOUT, //
	ALLOWED, //
	ALLOWED_AND_PLAYED, //
	NOT_ALLOWED;

	public boolean isAllowed() {
		return this != NOT_ALLOWED;
	}

}