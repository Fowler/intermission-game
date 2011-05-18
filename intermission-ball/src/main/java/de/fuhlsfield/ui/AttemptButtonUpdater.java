package de.fuhlsfield.ui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.rule.RuleCheckState;

public class AttemptButtonUpdater extends AbstractButtonUpdater {

	private static Map<RuleCheckState, Color> BUTTON_COLORS = new HashMap<RuleCheckState, Color>();

	static {
		BUTTON_COLORS.put(RuleCheckState.CHECKOUT, new Color(0, 255, 0));
		BUTTON_COLORS.put(RuleCheckState.ALLOWED, new Color(0, 255, 255));
		BUTTON_COLORS.put(RuleCheckState.ALLOWED_BUT_NOT_YET, new Color(255, 255, 0));
		BUTTON_COLORS.put(RuleCheckState.NOT_ALLOWED, new Color(255, 0, 0));
	}

	private class PlayerBall {

		private final Player player;
		private final Ball ball;
		private final Boolean isSuccessButton;

		private PlayerBall(Player player, Ball ball, boolean isSuccessButton) {
			this.player = player;
			this.ball = ball;
			this.isSuccessButton = isSuccessButton;
		}

	}

	private final Map<JButton, PlayerBall> buttonMappings = new HashMap<JButton, PlayerBall>();

	public void addSuccessButton(JButton button, Player player, Ball ball) {
		addButton(button);
		this.buttonMappings.put(button, new PlayerBall(player, ball, true));
	}

	public void addFailureButton(JButton button, Player player, Ball ball) {
		addButton(button);
		this.buttonMappings.put(button, new PlayerBall(player, ball, false));
	}

	@Override
	protected void updateButtonState(Game game, JButton button) {
		if (this.buttonMappings.containsKey(button)) {
			PlayerBall playerBall = this.buttonMappings.get(button);
			if (playerBall.isSuccessButton) {
				button.setBackground(BUTTON_COLORS.get(game.getRuleCheckState(playerBall.player, playerBall.ball)));
			}
			button.setEnabled(game.isAttemptAllowed(playerBall.player, playerBall.ball));
		}
	}

}