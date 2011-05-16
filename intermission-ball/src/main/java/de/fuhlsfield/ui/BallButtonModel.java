package de.fuhlsfield.ui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

import de.fuhlsfield.game.Ball;
import de.fuhlsfield.game.Game;
import de.fuhlsfield.game.Player;
import de.fuhlsfield.game.rule.RuleCheckState;

public class BallButtonModel extends AbstractButtonModel {

	private static Map<RuleCheckState, Color> BUTTON_COLORS = new HashMap<RuleCheckState, Color>();

	static {
		BUTTON_COLORS.put(RuleCheckState.CHECKOUT, Color.GREEN);
		BUTTON_COLORS.put(RuleCheckState.ALLOWED, Color.BLUE);
		BUTTON_COLORS.put(RuleCheckState.NOT_ALLOWED, Color.RED);
	}

	private class PlayerBall {

		private final Player player;
		private final Ball ball;

		private PlayerBall(Player player, Ball ball) {
			this.player = player;
			this.ball = ball;
		}

	}

	private final Game game;
	private final Map<JButton, PlayerBall> buttonProperties = new HashMap<JButton, PlayerBall>();

	public BallButtonModel(Game game) {
		this.game = game;
	}

	public void addButton(JButton button, Player player, Ball ball) {
		addButton(button);
		this.buttonProperties.put(button, new PlayerBall(player, ball));
	}

	@Override
	protected void updateButtonState(JButton button) {
		PlayerBall playerBall = this.buttonProperties.get(button);
		button.setBackground(BUTTON_COLORS.get(this.game.getRuleCheckState(playerBall.player, playerBall.ball)));
	}

}