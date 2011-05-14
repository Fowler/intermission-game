package de.fuhlsfield.game.rule;

public enum RuleCheckState {

	ALLOWED, //
	CHECKOUT, //
	NOT_ALLOWED;

	public boolean isAllowed() {
		return (this == ALLOWED) || (this == CHECKOUT);
	}

	public boolean isCheckoutPossible() {
		return this == CHECKOUT;
	}

}