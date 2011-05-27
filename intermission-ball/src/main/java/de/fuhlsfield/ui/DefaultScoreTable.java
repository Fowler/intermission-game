package de.fuhlsfield.ui;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class DefaultScoreTable extends JTable {

	private static final long serialVersionUID = 1L;

	public DefaultScoreTable(AbstractTableModel tableModel) {
		super(tableModel);
		setCellSelectionEnabled(false);
		setFocusable(false);
		this.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
	}

}