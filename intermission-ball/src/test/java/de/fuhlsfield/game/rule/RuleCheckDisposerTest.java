package de.fuhlsfield.game.rule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.score.GameScoreKeeper;

public class RuleCheckDisposerTest {

	private final GameScoreKeeper gameScoreKeeper = mock(GameScoreKeeper.class);
	private final RuleCheck ruleCheckA = mock(RuleCheck.class);
	private final RuleCheck ruleCheckB = mock(RuleCheck.class);
	private final List<Ball> ballsA = Arrays.asList(Ball.BUNTI);
	private final List<Ball> ballsB = Arrays.asList(Ball.BASKI);
	private final List<List<Ball>> allBalls = new ArrayList<List<Ball>>();

	@Before
	public void beforeTest() {
		this.allBalls.add(this.ballsA);
		this.allBalls.add(this.ballsB);
	}

	@Test
	public void testDisposeRuleChecksWhenAllRuleChecksApplyToOneListOfAttempts() {
		when(this.ruleCheckA.isPossibleAttempts(this.ballsA, this.gameScoreKeeper)).thenReturn(false);
		when(this.ruleCheckA.isPossibleAttempts(this.ballsB, this.gameScoreKeeper)).thenReturn(true);
		when(this.ruleCheckB.isPossibleAttempts(this.ballsA, this.gameScoreKeeper)).thenReturn(true);
		when(this.ruleCheckB.isPossibleAttempts(this.ballsB, this.gameScoreKeeper)).thenReturn(true);
		LinkedList<List<Ball>> expectedBalls = new LinkedList<List<Ball>>();
		expectedBalls.add(this.ballsB);
		assertEquals(expectedBalls, createInstanceUnderTest().disposeRuleChecks(this.allBalls, this.gameScoreKeeper));
	}

	@Test
	public void testDisposeRuleChecksWhenAllRuleChecksApplyToAllListsOfAttempts() {
		when(this.ruleCheckA.isPossibleAttempts(this.ballsA, this.gameScoreKeeper)).thenReturn(true);
		when(this.ruleCheckA.isPossibleAttempts(this.ballsB, this.gameScoreKeeper)).thenReturn(true);
		when(this.ruleCheckB.isPossibleAttempts(this.ballsA, this.gameScoreKeeper)).thenReturn(true);
		when(this.ruleCheckB.isPossibleAttempts(this.ballsB, this.gameScoreKeeper)).thenReturn(true);
		LinkedList<List<Ball>> expectedBalls = new LinkedList<List<Ball>>();
		expectedBalls.add(this.ballsA);
		expectedBalls.add(this.ballsB);
		assertEquals(expectedBalls, createInstanceUnderTest().disposeRuleChecks(this.allBalls, this.gameScoreKeeper));
	}

	@Test
	public void testDisposeRuleChecksWhenAllRuleChecksApplyToNoListOfAttempts() {
		when(this.ruleCheckA.isPossibleAttempts(this.ballsA, this.gameScoreKeeper)).thenReturn(false);
		when(this.ruleCheckA.isPossibleAttempts(this.ballsB, this.gameScoreKeeper)).thenReturn(false);
		when(this.ruleCheckB.isPossibleAttempts(this.ballsA, this.gameScoreKeeper)).thenReturn(false);
		when(this.ruleCheckB.isPossibleAttempts(this.ballsB, this.gameScoreKeeper)).thenReturn(false);
		LinkedList<List<Ball>> expectedBalls = new LinkedList<List<Ball>>();
		assertEquals(expectedBalls, createInstanceUnderTest().disposeRuleChecks(this.allBalls, this.gameScoreKeeper));
	}

	@Test
	public void testDisposeRuleChecksWhenEachRuleCheckApplyToDifferenListOfAttempts() {
		when(this.ruleCheckA.isPossibleAttempts(this.ballsA, this.gameScoreKeeper)).thenReturn(false);
		when(this.ruleCheckA.isPossibleAttempts(this.ballsB, this.gameScoreKeeper)).thenReturn(true);
		when(this.ruleCheckB.isPossibleAttempts(this.ballsA, this.gameScoreKeeper)).thenReturn(true);
		when(this.ruleCheckB.isPossibleAttempts(this.ballsB, this.gameScoreKeeper)).thenReturn(false);
		LinkedList<List<Ball>> expectedBalls = new LinkedList<List<Ball>>();
		assertEquals(expectedBalls, createInstanceUnderTest().disposeRuleChecks(this.allBalls, this.gameScoreKeeper));
	}

	private RuleCheckDisposer createInstanceUnderTest() {
		return new RuleCheckDisposer(new HashSet<RuleCheck>(Arrays.asList(this.ruleCheckA, this.ruleCheckB)));
	}

}