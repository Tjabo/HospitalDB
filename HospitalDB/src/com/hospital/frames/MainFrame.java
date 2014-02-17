package com.hospital.frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.hospital.util.ResultSetToJTable;

public class MainFrame {

	private JFrame frame;
	private JPanel tablePanel;
	private static JButton btnUpdate;
	private JLabel lblFilters;
	private JCheckBox chkDoc;
	private JCheckBox chkNurse;
	private JCheckBox chkAssNurse;
	private boolean DOCTORS = false;
	private boolean NURSES = false;
	private boolean ASSISTANT = false;
	private JButton btnSearch;
	private JLabel lblNewLabel;
	private JTextField txtFieldSearch;
	private JTabbedPane tabbedPane;
	private JPanel searchPanel;
	private JPanel editPanel;
	private JButton btnNew;
	private JButton btnRemove;
	
	private ResultSetToJTable resultSetToJTable;

	public MainFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon imgIcon = new ImageIcon("res/icon.gif");
		frame.setIconImage(imgIcon.getImage());
		frame.setTitle("HospitalDB 0.1" );
		
		tablePanel = new JPanel();
		frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
		
		JPanel controlPanel = new JPanel();
		frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		frame.getContentPane().add(tabbedPane, BorderLayout.EAST);
		
		JPanel filterPanel = new JPanel();
		tabbedPane.addTab("Filters", null, filterPanel, null);
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
		
		lblFilters = new JLabel("FILTERS");
		lblFilters.setFont(new Font("Verdana", Font.BOLD, 10));
		lblFilters.setHorizontalAlignment(SwingConstants.CENTER);
		filterPanel.add(lblFilters);
		
		chkDoc = new JCheckBox("Doctors");
		filterPanel.add(chkDoc);
		
		chkNurse = new JCheckBox("Nurses");
		filterPanel.add(chkNurse);
		
		chkAssNurse = new JCheckBox("Assistant nurses");
		filterPanel.add(chkAssNurse);
		
		btnUpdate = new JButton("Apply Filters");
		btnUpdate.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnUpdate.setAlignmentY(Component.TOP_ALIGNMENT);
		filterPanel.add(btnUpdate);
		
		searchPanel = new JPanel();
		tabbedPane.addTab("Search", null, searchPanel, null);
		GridBagLayout gbl_searchPanel = new GridBagLayout();
		gbl_searchPanel.columnWidths = new int[]{100, 0};
		gbl_searchPanel.rowHeights = new int[]{14, 20, 23, 0};
		gbl_searchPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_searchPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		searchPanel.setLayout(gbl_searchPanel);
		
		lblNewLabel = new JLabel("SEARCH ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		searchPanel.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 10));
		
		txtFieldSearch = new JTextField();
		GridBagConstraints gbc_txtFieldSearch = new GridBagConstraints();
		gbc_txtFieldSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldSearch.insets = new Insets(0, 0, 5, 0);
		gbc_txtFieldSearch.gridx = 0;
		gbc_txtFieldSearch.gridy = 1;
		searchPanel.add(txtFieldSearch, gbc_txtFieldSearch);
		txtFieldSearch.setColumns(10);
		
		btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 2;
		searchPanel.add(btnSearch, gbc_btnSearch);
		btnSearch.setFont(new Font("Verdana", Font.PLAIN, 10));
		
		editPanel = new JPanel();
		tabbedPane.addTab("Edit", null, editPanel, null);
		GridBagLayout gbl_editPanel = new GridBagLayout();
		gbl_editPanel.columnWidths = new int[]{121, 0};
		gbl_editPanel.rowHeights = new int[]{23, 23, 0};
		gbl_editPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_editPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		editPanel.setLayout(gbl_editPanel);
		
		btnNew = new JButton("Add employee");
		btnNew.setAlignmentY(Component.TOP_ALIGNMENT);
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNew.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNew.insets = new Insets(0, 0, 5, 0);
		gbc_btnNew.gridx = 0;
		gbc_btnNew.gridy = 0;
		editPanel.add(btnNew, gbc_btnNew);
		
		btnRemove = new JButton("Remove employee");		
		
		btnRemove.setAlignmentY(Component.TOP_ALIGNMENT);
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 1;
		editPanel.add(btnRemove, gbc_btnRemove);
		
		frame.setVisible(true);		
	}
	
	public void AddTable(ResultSet resultSet) {
		tablePanel.removeAll();
		resultSetToJTable = new ResultSetToJTable(resultSet);
		JScrollPane scrollPane =resultSetToJTable.getTable();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		frame.revalidate();
	}
	
	public static void update() {
		btnUpdate.doClick();
	}
	
	public void addListenerToButton(ActionListener listen, JButton button) {
		button.addActionListener(listen);
	}
	
	public JButton getBtn(String name) {
		JButton btn = null;
		if (name == "update") btn = this.btnUpdate;
		if (name == "search") btn = this.btnSearch;
		if (name == "add") btn = this.btnNew;
		if (name == "remove") btn = this.btnRemove;
		return btn;
	}
	
	public JTextField getSearchBox() {
		return this.txtFieldSearch;
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public int getSelectedID() {
		int id = resultSetToJTable.getSelectedID();
		return id;
	}
	
	public boolean getDoctors() {
		if (this.chkDoc.isSelected()) this.DOCTORS = true;
		else this.DOCTORS = false;
		return this.DOCTORS;
	}
	
	public boolean getNurses() {
		if (this.chkNurse.isSelected()) this.NURSES = true;
		else this.NURSES = false;
		return this.NURSES;
	}
	
	public boolean getAssNurses() {
		if (this.chkAssNurse.isSelected()) this.ASSISTANT = true;
		else this.ASSISTANT = false;
		return this.ASSISTANT;
	}	
}
