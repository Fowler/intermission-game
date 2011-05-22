package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.fuhlsfield.game.Ball;

public class PossibleAttemptsTest {

	@Test
	public void testAddWhenAddingOneAttempt() {
		PossibleAttempts possibleAttempts = createInstanceUnderTest(Ball.BASKI);
		possibleAttempts.add(Ball.BUNTI);
		assertEquals(Ball.BUNTI, possibleAttempts.getLastAddedAttempt());
		assertEquals(2, possibleAttempts.size());
	}

	@Test
	public void testAddWhenAddingSameAttemptTwice() {
		PossibleAttempts possibleAttempts = createInstanceUnderTest(Ball.BASKI);
		possibleAttempts.add(Ball.BUNTI);
		possibleAttempts.add(Ball.BUNTI);
		assertEquals(Ball.BUNTI, possibleAttempts.getLastAddedAttempt());
		assertEquals(3, possibleAttempts.size());
	}

	@Test
	public void testFirstAttemptWhenNoOtherAttemptAvailable() {
		PossibleAttempts possibleAttempts = createInstanceUnderTest(Ball.BASKI);
		assertEquals(Ball.BASKI, possibleAttempts.getFirstAttempt());
	}

	@Test
	public void testFirstAttemptWhenMoreAttemptsAdded() {
		PossibleAttempts possibleAttempts = createInstanceUnderTest(Ball.BASKI);
		possibleAttempts.add(Ball.BUNTI);
		assertEquals(Ball.BASKI, possibleAttempts.getFirstAttempt());
	}

	@Test
	public void testSizeWhenAddingSameAttemptSeveralTimes() {
		PossibleAttempts possibleAttempts = createInstanceUnderTest(Ball.BUNTI);
		possibleAttempts.add(Ball.BUNTI);
		possibleAttempts.add(Ball.BUNTI);
		assertEquals(3, possibleAttempts.size());
	}

	@Test
	public void testCopyIsEqual() {
		PossibleAttempts possibleAttempts = createInstanceUnderTest(Ball.BUNTI);
		possibleAttempts.add(Ball.BASKI);
		possibleAttempts.add(Ball.SCHWAMMI);
		assertEquals(possibleAttempts, possibleAttempts.copy());
	}

	@Test
	public void testCopyThatModifyingCopyNotModifiesOriginal() {
		PossibleAttempts possibleAttempts = createInstanceUnderTest(Ball.BUNTI);
		PossibleAttempts copyPossibleAttempts = possibleAttempts.copy();
		copyPossibleAttempts.add(Ball.BASKI);
		assertEquals(1, possibleAttempts.size());
	}

	@Test
	public void testToList() {
		PossibleAttempts possibleAttempts = createInstanceUnderTest(Ball.BUNTI);
		possibleAttempts.add(Ball.BASKI);
		possibleAttempts.add(Ball.BASKI);
		List<Ball> expectedList = Arrays.asList(Ball.BUNTI, Ball.BASKI, Ball.BASKI);
		assertTrue(expectedList.containsAll(possibleAttempts.toList()));
		assertTrue(possibleAttempts.toList().containsAll(expectedList));
	}

	@Test
	public void testNextAttemptsToList() {
		PossibleAttempts possibleAttempts = createInstanceUnderTest(Ball.BUNTI);
		possibleAttempts.add(Ball.BASKI);
		possibleAttempts.add(Ball.SCHWAMMI);
		List<Ball> expectedList = Arrays.asList(Ball.BASKI, Ball.SCHWAMMI);
		assertTrue(expectedList.containsAll(possibleAttempts.nextAttemptsToList()));
		assertTrue(possibleAttempts.nextAttemptsToList().containsAll(expectedList));
	}

	private PossibleAttempts createInstanceUnderTest(Ball ball) {
		return new PossibleAttempts(ball);
	}

}