package de.fuhlsfield.game.rule;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.fuhlsfield.game.Ball;

class PossibleAttempts {

	private final Ball firstAttempt;
	private final Map<Ball, Integer> nextAttempts = new HashMap<Ball, Integer>();
	private Ball lastAddedAttempt;
	private int size;

	PossibleAttempts(Ball ball) {
		this.firstAttempt = ball;
		this.lastAddedAttempt = ball;
		this.size = 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.firstAttempt == null) ? 0 : this.firstAttempt.hashCode());
		result = prime * result + ((this.nextAttempts == null) ? 0 : this.nextAttempts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PossibleAttempts other = (PossibleAttempts) obj;
		if (this.firstAttempt == null) {
			if (other.firstAttempt != null) {
				return false;
			}
		} else if (!this.firstAttempt.equals(other.firstAttempt)) {
			return false;
		}
		if (this.nextAttempts == null) {
			if (other.nextAttempts != null) {
				return false;
			}
		} else if (!this.nextAttempts.equals(other.nextAttempts)) {
			return false;
		}
		return true;
	}

	void add(Ball ball) {
		this.nextAttempts.put(ball, countNextAttempts(ball) + 1);
		this.lastAddedAttempt = ball;
		this.size++;
	}

	Ball getFirstAttempt() {
		return this.firstAttempt;
	}

	Ball getLastAddedAttempt() {
		return this.lastAddedAttempt;
	}

	int size() {
		return this.size;
	}

	PossibleAttempts copy() {
		PossibleAttempts possibleAttempts = new PossibleAttempts(this.firstAttempt);
		for (Ball ball : this.nextAttempts.keySet()) {
			possibleAttempts.nextAttempts.put(ball, countNextAttempts(ball));
		}
		possibleAttempts.lastAddedAttempt = this.lastAddedAttempt;
		return possibleAttempts;
	}

	List<Ball> toList() {
		LinkedList<Ball> list = new LinkedList<Ball>();
		list.add(this.firstAttempt);
		list.addAll(nextAttemptsToList());
		return Collections.unmodifiableList(list);
	}

	List<Ball> nextAttemptsToList() {
		LinkedList<Ball> list = new LinkedList<Ball>();
		for (Ball ball : this.nextAttempts.keySet()) {
			list.addAll(Collections.nCopies(this.nextAttempts.get(ball), ball));
		}
		return Collections.unmodifiableList(list);
	}

	private int countNextAttempts(Ball ball) {
		if (this.nextAttempts.containsKey(ball)) {
			return this.nextAttempts.get(ball);
		}
		return 0;
	}

}