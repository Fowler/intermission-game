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

	private static Map<RuleCheckState, ButtonColor> BUTTON_COLORS = new HashMap<RuleCheckState, ButtonColor>();

	private static class ButtonColor {

		private final Color backgroundColor;
		private final Color fontColor;

		private ButtonColor(Color backgroundColor, Color fontColor) {
			this.backgroundColor = backgroundColor;
			this.fontColor = fontColor;
		}

	}

	static {
		BUTTON_COLORS.put(RuleCheckState.CHECKOUT, new ButtonColor(new Color(135, 255, 135), new Color(215, 245, 215)));
		BUTTON_COLORS.put(RuleCheckState.ALLOWED, new ButtonColor(new Color(130, 200, 255), new Color(220, 230, 240)));
		BUTTON_COLORS.put(RuleCheckState.ALLOWED_AND_PLAYED, new ButtonColor(new Color(100, 120, 255), new Color(210,
				220, 230)));
		BUTTON_COLORS.put(RuleCheckState.NOT_ALLOWED,
				new ButtonColor(new Color(255, 150, 80), new Color(240, 230, 220)));
		BUTTON_COLORS.put(RuleCheckState.NOT_ALLOWED_AND_PLAYED, new ButtonColor(new Color(255, 90, 90), new Color(235,
				200, 200)));
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

	public void addSuccessButton (JButton button, Player player, Ball ball) {
		addButton(button);
		this.buttonMappings.put(button, new PlayerBall(player, ball, true));
	}

	public void addFailureButton (JButton button, Player player, Ball ball) {
		addButton(button);
		this.buttonMappings.put(button, new PlayerBall(player, ball, false));
	}

	@Override
	protected void updateButtonState (Game game, JButton button) {
		if (this.buttonMappings.containsKey(button)) {
			PlayerBall playerBall = this.buttonMappings.get(button);
			if (playerBall.isSuccessButton) {
				button
						.setBackground(BUTTON_COLORS.get(game.getRuleCheckState(playerBall.player, playerBall.ball)).backgroundColor);
				button
						.setForeground(BUTTON_COLORS.get(game.getRuleCheckState(playerBall.player, playerBall.ball)).fontColor);
			}
			button.setEnabled(game.isAttemptAllowed(playerBall.player, playerBall.ball));
		}
	}

}