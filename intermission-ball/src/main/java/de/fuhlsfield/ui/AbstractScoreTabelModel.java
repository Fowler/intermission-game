package de.fuhlsfield.ui;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractScoreTabelModel extends AbstractTableModel implements GameModel {

	private static final long serialVersionUID = 1L;

	@Override
	public void updateModel() {
		fireTableDataChanged();
	}

}