package com.hospital.frames;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hospital.dbconn.DBConnection;
import com.hospital.util.ResultSetToJTable;

public class GUI_test implements ActionListener {

	// Database Globals
	DBConnection CONEX;
	Statement STATE;
	ResultSet RESULT;

	// Gui Globals

	JFrame MainWindow;
	private JPanel tablePanel;
	
	JLabel JL_eID;
	JLabel JL_firstName;
	JLabel JL_lastName;
	JLabel JL_telephoneNR;
	JLabel JL_dateofEMPL;

	JTextField TF_eID;
	JTextField TF_firstName;
	JTextField TF_lastName;
	JTextField TF_telephoneNR;
	JTextField TF_dateofEMPL;
	JTextField TF_SEARCH = new JTextField(10);

	JButton B_NEXT = new JButton("NEXT");
	JButton B_PREV = new JButton("PREV");
	JButton B_FIRST = new JButton("FIRST");
	JButton B_LAST = new JButton("LAST");
	JButton B_UPDATE = new JButton("UPDATE");
	JButton B_DELETE = new JButton("DELETE");
	JButton B_NEW = new JButton("NEW");
	JButton B_SAVE = new JButton("SAVE");
	JButton B_SEARCH = new JButton("SEARCH");

	ButtonGroup SearchChoices = new ButtonGroup();
	JRadioButton RB_eID = new JRadioButton("EmploymentID");
	JRadioButton RB_firstName = new JRadioButton("First name");
	JRadioButton RB_lastName = new JRadioButton("Last name");
	JRadioButton RB_telephoneNR = new JRadioButton("Telephone nr");
	JRadioButton RB_dateofEMPL = new JRadioButton("Date of employment");
	private JPanel northPanel;
	private JPanel southPanel;
	

	// ------------------------------------------------------------------------------------------------------------------

	public GUI_test() {
		
		

		Connect();
		SelectData();
		BuildGUI();
		AddTable(RESULT);
		DisplayData();

	}

	// ------------------------------------------------------------------------------------------------------------------
	
	public void init() {		
	}
	
	private void AddTable(ResultSet resultSet) {		
		JScrollPane scrollPane = new ResultSetToJTable(resultSet).getTable();
		tablePanel.add(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	private void DisplayData() {		
		try {
			RESULT.next();

			TF_eID.setText(RESULT.getString("EmploymentID"));
			TF_firstName.setText(RESULT.getString("First name"));
			TF_lastName.setText(RESULT.getString("Last name"));
			TF_telephoneNR.setText(RESULT.getString("Telephone nr"));
			TF_dateofEMPL.setText(RESULT.getString("Date of employment"));
		} catch (Exception X) {
			// TODO: handle exception
		}
	}

	// ------------------------------------------------------------------------------------------------------------------

	private void BuildGUI() {

		MainWindow = new JFrame("Hospital DB");
		//MainWindow.setSize(1700, 800);
		
		ImageIcon imgIcon = new ImageIcon("res/icon.gif");
		MainWindow.setIconImage(imgIcon.getImage());
		 
		MainWindow.setResizable(true);
		MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel BG = new JPanel();
		BG.setLayout(new BorderLayout(0, 0));

		MainWindow.getContentPane().setLayout(new BorderLayout());

		MainWindow.getContentPane().add(BG);
		
		tablePanel = new JPanel();
		BG.add(tablePanel);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		northPanel = new JPanel();
		BG.add(northPanel, BorderLayout.NORTH);
		
				JL_eID = new JLabel("EmploymentID:");
				northPanel.add(JL_eID);
				
						TF_eID = new JTextField(10);
						northPanel.add(TF_eID);
						JL_firstName = new JLabel("First name:");
						northPanel.add(JL_firstName);
						TF_firstName = new JTextField(10);
						northPanel.add(TF_firstName);
						JL_lastName = new JLabel("Last name:");
						northPanel.add(JL_lastName);
						TF_lastName = new JTextField(10);
						northPanel.add(TF_lastName);
						JL_telephoneNR = new JLabel("Telephone nr:");
						northPanel.add(JL_telephoneNR);
						TF_telephoneNR = new JTextField(10);
						northPanel.add(TF_telephoneNR);
						JL_dateofEMPL = new JLabel("Date of employment:");
						northPanel.add(JL_dateofEMPL);
						TF_dateofEMPL = new JTextField(10);
						northPanel.add(TF_dateofEMPL);
								northPanel.add(B_NEXT);
								northPanel.add(B_PREV);
								northPanel.add(B_FIRST);
								northPanel.add(B_LAST);
								B_LAST.addActionListener(this);
								B_FIRST.addActionListener(this);
								B_PREV.addActionListener(this);
						
								B_NEXT.addActionListener(this);
						
						southPanel = new JPanel();
						BG.add(southPanel, BorderLayout.SOUTH);
								southPanel.add(B_UPDATE);
								southPanel.add(B_DELETE);
								southPanel.add(B_NEW);
										southPanel.add(B_SAVE);
								
										B_SAVE.addActionListener(this);
								B_NEW.addActionListener(this);
								B_DELETE.addActionListener(this);
						
								B_UPDATE.addActionListener(this);
		MainWindow.setVisible(true);
		MainWindow.setSize(new Dimension(800, 600));
		MainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	// ------------------------------------------------------------------------------------------------------------------
	public void Connect() {
		CONEX = DBConnection.getInstance();
		RESULT = null;
	}

	// ------------------------------------------------------------------------------------------------------------------

	public void SelectData() {
		String query = "SELECT * FROM employee";
		
		RESULT = CONEX.getResultSet(query);
	}

	// ------------------------------------------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent X) {

		Object SRC = X.getSource();

		if (SRC == B_NEXT) {
			B_NEXT_ACTION();
		}

		if (SRC == B_PREV) {
			B_PREV_ACTION();
		}

		if (SRC == B_FIRST) {
			B_FIRST_ACTION();
		}

		if (SRC == B_LAST) {
			B_LAST_ACTION();
		}

		if (SRC == B_UPDATE) {
			B_UPDATE_ACTION();
		}

		if (SRC == B_DELETE) {
			B_DELETE_ACTION();
		}

		if (SRC == B_NEW) {
			B_NEW_ACTION();
		}

		if (SRC == B_SAVE) {
			B_SAVE_ACTION();
		}

	}

	// ------------------------------------------------------------------------------------------------------------------

	public void B_NEXT_ACTION() {

		try {
			if (RESULT.next()) {

				TF_eID.setText(RESULT.getString("employeeid"));
				TF_firstName.setText(RESULT.getString("first_name"));
				TF_lastName.setText(RESULT.getString("last_name"));
				TF_telephoneNR.setText(RESULT.getString("telephone_number"));
				TF_dateofEMPL.setText(RESULT.getString("date_of_employment"));
			}

			else {

				RESULT.first();
				TF_eID.setText(RESULT.getString("employeeid"));
				TF_firstName.setText(RESULT.getString("first_name"));
				TF_lastName.setText(RESULT.getString("last_name"));
				TF_telephoneNR.setText(RESULT.getString("telephone_number"));
				TF_dateofEMPL.setText(RESULT.getString("date_of_employment"));
//				JOptionPane.showMessageDialog(null, "Reached Last Record");

			}

		}

		catch (Exception X) {
			System.out.print(X);
		}

	}

	// ------------------------------------------------------------------------------------------------------------------
	public void B_PREV_ACTION() {

		try {
			if (RESULT.previous()) {

				TF_eID.setText(RESULT.getString("employeeid"));
				TF_firstName.setText(RESULT.getString("first_name"));
				TF_lastName.setText(RESULT.getString("last_name"));
				TF_telephoneNR.setText(RESULT.getString("telephone_number"));
				TF_dateofEMPL.setText(RESULT.getString("date_of_employment"));

			}

			else {

				RESULT.last();

				TF_eID.setText(RESULT.getString("employeeid"));
				TF_firstName.setText(RESULT.getString("first_name"));
				TF_lastName.setText(RESULT.getString("last_name"));
				TF_telephoneNR.setText(RESULT.getString("telephone_number"));
				TF_dateofEMPL.setText(RESULT.getString("date_of_employment"));

//				JOptionPane.showMessageDialog(null, "Reached First Record!");

			}

		}

		catch (Exception X) {
			System.out.print(X);
		}

	}

	// ------------------------------------------------------------------------------------------------------------------
	public void B_FIRST_ACTION() {

		try {

			RESULT.first();
			TF_eID.setText(RESULT.getString("employeeid"));
			TF_firstName.setText(RESULT.getString("first_name"));
			TF_lastName.setText(RESULT.getString("last_name"));
			TF_telephoneNR.setText(RESULT.getString("telephone_number"));
			TF_dateofEMPL.setText(RESULT.getString("date_of_employment"));

		}

		catch (Exception X) {

			System.out.print(X);

		}

	}

	// ------------------------------------------------------------------------------------------------------------------
	public void B_LAST_ACTION() {

		try {

			RESULT.last();
			TF_eID.setText(RESULT.getString("employeeid"));
			TF_firstName.setText(RESULT.getString("first_name"));
			TF_lastName.setText(RESULT.getString("last_name"));
			TF_telephoneNR.setText(RESULT.getString("telephone_number"));
			TF_dateofEMPL.setText(RESULT.getString("date_of_employment"));

		}

		catch (Exception X) {

			System.out.print(X);

		}

	}

	// ------------------------------------------------------------------------------------------------------------------
	public void B_UPDATE_ACTION() {

		try {

			RESULT.updateInt("employeeid", Integer.parseInt(TF_eID.getText()));
			RESULT.updateString("first_name", TF_firstName.getText());
			RESULT.updateString("last_name", TF_lastName.getText());
			RESULT.updateString("telephone_number", TF_telephoneNR.getText());
			RESULT.updateString("date_of_employment", TF_dateofEMPL.getText());

			RESULT.updateRow();
			JOptionPane.showMessageDialog(null, "UPDATED!");

		}

		catch (Exception X) {

			System.out.print(X);

		}

	}

	// ------------------------------------------------------------------------------------------------------------------

	public void B_DELETE_ACTION() {

		try {
			RESULT.deleteRow();
			RESULT.previous();
			DisplayData();
		} catch (Exception X) {
			System.out.println(X);
		}

	}

	// ------------------------------------------------------------------------------------------------------------------
	public void B_NEW_ACTION() {

		TF_eID.setText("");
		TF_firstName.setText("");
		TF_lastName.setText("");
		TF_telephoneNR.setText("");
		TF_dateofEMPL.setText("");

	}

	// ------------------------------------------------------------------------------------------------------------------

	public void B_SAVE_ACTION() {
		try {
			System.out.println("Saving....");
			RESULT.moveToInsertRow();

			RESULT.updateInt("employeeid", Integer.parseInt(TF_eID.getText()));
			RESULT.updateString("first_name", TF_firstName.getText());
			RESULT.updateString("last_name", TF_lastName.getText());
			RESULT.updateString("telephone_number", TF_telephoneNR.getText());
			RESULT.updateString("date_of_employment", TF_dateofEMPL.getText());

			RESULT.insertRow();

			STATE.close();
			SelectData();

			RESULT.last();
			DisplayData();

			JOptionPane.showMessageDialog(null, "Saved!");

		}

		catch (Exception X) {

			System.out.print(X);

		}
	}
}