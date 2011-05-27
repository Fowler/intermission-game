package de.fuhlsfield.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class DefaultTableCellRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int rowIndex, int columnIndex) {
		if (value != null) {
			setText(value.toString());
		} else {
			setText(null);
		}
		setForeground(new Color(50, 50, 50));
		return this;
	}

	@Override
	public void validate() {
	}

	@Override
	public void revalidate() {
	}

	@Override
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
	}

	@Override
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
	}

}