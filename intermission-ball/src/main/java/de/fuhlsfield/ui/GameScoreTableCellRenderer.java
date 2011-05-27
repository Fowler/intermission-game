package de.fuhlsfield.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import de.fuhlsfield.game.Attempt;

public class GameScoreTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int rowIndex, int columnIndex) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, columnIndex);
		if ((value != null) && value.getClass().equals(Attempt.class)) {
			Attempt attempt = (Attempt) value;
			if (attempt.isSuccessful()) {
				setForeground(new Color(30, 200, 60));
			} else {
				setForeground(new Color(200, 60, 30));
			}
		}
		return this;
	}

}