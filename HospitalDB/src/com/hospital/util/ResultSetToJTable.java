package com.hospital.util;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class ResultSetToJTable {

	private Vector<String> colNames = new Vector<String>();
	private Vector<Object> data = new Vector<Object>();
	private JTable table;
	private JScrollPane scrollPane;

	public ResultSetToJTable(ResultSet resultSet) {
		
		try {			
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columns = metaData.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				colNames.addElement(metaData.getColumnName(i));
			}

			while (resultSet.next()) {
				Vector<Object> row = new Vector<Object>(columns);
				for (int i = 1; i <= columns; i++) {
					row.addElement(resultSet.getObject(i));
				}
				data.addElement(row);
			}
			//resultSet.close();
		} catch (Exception e) {
			System.out.println("Couldn't convert resultset to JTable! " + e.getMessage());
			e.printStackTrace();
		}

		table = new JTable(data, colNames);
		TableColumn column;
		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			column.setMaxWidth(250);
		}
		scrollPane = new JScrollPane(table);
	}
	
	public JScrollPane getTable() {
		return scrollPane;
	}
}
