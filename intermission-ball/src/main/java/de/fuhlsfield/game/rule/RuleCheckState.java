package de.fuhlsfield.game.rule;

public enum RuleCheckState {

	ALLOWED, //
	CHECKOUT, //
	ALLOWED_BUT_NOT_YET, // TODO how to determine this state?
	NOT_ALLOWED;

	public boolean isAllowed() {
		return (this == ALLOWED) || (this == CHECKOUT);
	}

}