package de.fuhlsfield.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class GameButton extends JButton implements MouseListener {

	private static final long serialVersionUID = 1L;

	private final boolean accentDisabled;
	private boolean isPressed;

	public GameButton(String label) {
		this(label, true);
	}

	public GameButton(String label, boolean accentDisabled) {
		super(label);
		addMouseListener(this);
		setBackground(new Color(215, 230, 245));
		setForeground(new Color(225, 235, 245));
		this.accentDisabled = accentDisabled;
	}

	@Override
	protected void paintComponent (Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setFont(new Font("Arial", Font.BOLD, 12));
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (isEnabled() || !this.accentDisabled) {
			if (this.isPressed) {
				setPaint(graphics2d, getBackground().darker());
			} else {
				setPaint(graphics2d, getBackground());
			}
		} else {
			setPaint(graphics2d, getBackground().brighter());
		}
		graphics2d.fillRect(0, 0, getWidth(), getHeight());
		if (isEnabled()) {
			if (this.isPressed) {
				graphics2d.setColor(getForeground().darker());
			} else {
				graphics2d.setColor(getForeground());
			}
		} else {
			graphics2d.setColor(getForeground().darker().darker().darker());
		}
		int width = getFontMetrics(getFont()).stringWidth(getText());
		int height = getFont().getSize();
		graphics2d.drawString(getText(), (getWidth() - width) / 2, (getHeight() + height) / 2);
	}

	private void setPaint (Graphics2D graphics, Color color) {
		graphics.setPaint(new LinearGradientPaint(0.0f, 0.0f, 0.0f, getHeight(),
				new float[] { 0.0f, 0.30f, 0.60f, 0.9f }, new Color[] { color.brighter(), color,
						color.darker().darker(), color.darker() }));
	}

	@Override
	public void mouseClicked (MouseEvent e) {
	}

	@Override
	public void mouseEntered (MouseEvent e) {
	}

	@Override
	public void mouseExited (MouseEvent e) {
	}

	@Override
	public void mousePressed (MouseEvent e) {
		this.isPressed = true;
	}

	@Override
	public void mouseReleased (MouseEvent e) {
		this.isPressed = false;
	}

}