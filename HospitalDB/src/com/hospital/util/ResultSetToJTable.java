package com.hospital.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

public class ResultSetToJTable {

	private Vector<String> colNames = new Vector<String>();
	private Vector<Object> data = new Vector<Object>();
	private Vector<Integer> ids = new Vector<Integer>();
	private int selectedID = -1;
	private JTable table;
	private JScrollPane scrollPane;
	private TableColumn column;

	public ResultSetToJTable(ResultSet resultSet) {

		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columns = metaData.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				String colHeader = metaData.getColumnName(i).replace("_", " ");
				colHeader = colHeader.substring(0, 1).toUpperCase()
						+ colHeader.substring(1);
				if (!colHeader.equals("Employeeid")) {
					colNames.addElement(colHeader);
				}
			}

			ids.clear();
			while (resultSet.next()) {
				Vector<Object> row = new Vector<Object>(columns);
				for (int i = 1; i <= columns; i++) {
					if (i != 1) {
						row.addElement(resultSet.getObject(i));
					} else {
						ids.addElement((Integer) resultSet.getObject(i));
					}
				}
				data.addElement(row);
			}
		} catch (Exception e) {
			System.out.println("Couldn't convert resultset to JTable! "
					+ e.getMessage());
			e.printStackTrace();
		}

		table = new JTable(data, colNames);

		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			column.setMaxWidth(250);
		}

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel lsm = table.getSelectionModel();
		lsm.addListSelectionListener(new tableSelectionListener());
		scrollPane = new JScrollPane(table);
	}

	public JScrollPane getTable() {
		return scrollPane;

	}
	
	public int getSelectedID() {
		return this.selectedID;
	}
	
	class tableSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			int temp = lsm.getMinSelectionIndex();
			System.out.println("Selected id: " + ids.get(temp));
			selectedID = ids.get(temp);
			
			System.out.println("Table contents: " + data.get(temp));
			System.out.println("Selected index: " + temp);
			// Find employeeid of selected index in table
			// call appropriate method in dbc to remove appropriate stuffs.
			// Suggest using DELETE FROM IF EXISTS?
		}
	}
}
