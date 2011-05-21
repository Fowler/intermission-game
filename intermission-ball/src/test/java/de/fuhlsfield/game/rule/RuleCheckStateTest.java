package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RuleCheckStateTest {

	@Test
	public void testIsAllowedStateAllowed() {
		assertTrue(RuleCheckState.ALLOWED.isAllowed());
	}

	@Test
	public void testIsAllowedStateCheckout() {
		assertTrue(RuleCheckState.CHECKOUT.isAllowed());
	}

	@Test
	public void testIsAllowedStateNotAllowed() {
		assertFalse(RuleCheckState.NOT_ALLOWED.isAllowed());
	}

	@Test
	public void testIsAllowedStateAllowedAndPlayed() {
		assertTrue(RuleCheckState.ALLOWED_AND_PLAYED.isAllowed());
	}

}