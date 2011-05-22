package de.fuhlsfield.game.rule;

public enum RuleCheckState {

	CHECKOUT, //
	ALLOWED, //
	ALLOWED_AND_PLAYED, //
	NOT_ALLOWED, //
	NOT_ALLOWED_AND_PLAYED;

	public boolean isAllowed() {
		return (this == CHECKOUT) || (this == ALLOWED) || (this == ALLOWED_AND_PLAYED);
	}

}