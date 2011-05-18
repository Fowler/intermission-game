package de.fuhlsfield.game.rule;

public enum RuleCheckState {

	CHECKOUT, //
	ALLOWED, //
	ALLOWED_AND_PLAYED, //
	ALLOWED_BUT_NOT_YET, // TODO how to determine this state?
	NOT_ALLOWED;

	public boolean isAllowed() {
		return (this == ALLOWED) || (this == ALLOWED_AND_PLAYED) || (this == CHECKOUT);
	}

}