package de.fuhlsfield.game.rule;

import de.fuhlsfield.game.score.GameScoreCalculator;

abstract class AbstractRuleCheck implements RuleCheck {

	private static final long serialVersionUID = 3843237611564556171L;

	protected final GameScoreCalculator gameScoreCalculator;
	protected final int targetPoints;

	AbstractRuleCheck(GameScoreCalculator gameScoreCalculator, int targetPoints) {
		this.gameScoreCalculator = gameScoreCalculator;
		this.targetPoints = targetPoints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.gameScoreCalculator == null) ? 0 : this.gameScoreCalculator.hashCode());
		result = prime * result + this.targetPoints;
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
		AbstractRuleCheck other = (AbstractRuleCheck) obj;
		if (this.gameScoreCalculator == null) {
			if (other.gameScoreCalculator != null) {
				return false;
			}
		} else if (!this.gameScoreCalculator.equals(other.gameScoreCalculator)) {
			return false;
		}
		if (this.targetPoints != other.targetPoints) {
			return false;
		}
		return true;
	}

}