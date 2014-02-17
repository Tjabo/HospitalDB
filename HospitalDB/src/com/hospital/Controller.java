package com.hospital;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.hospital.dbconn.DBConnection;
import com.hospital.frames.AddDialog;
import com.hospital.frames.LoginFrame;
import com.hospital.frames.MainFrame;


public class Controller {
	
	private DBConnection dbc;
	private LoginFrame loginFrame;
	private MainFrame mainFrame;
	
	@SuppressWarnings("unused") // Lazy initialization makes Eclipse go bananas. :(
	private AddDialog addDialog; 
	
	private String query = 
			"SELECT employee.employeeid, first_name, last_name, telephone_number, date_of_employment, workplace_name FROM employee INNER JOIN employee_workplace ON employee.employeeid = employee_workplace.employeeid INNER JOIN workplace ON workplace.workplaceid = employee_workplace.workplaceid  UNION" + 
			" SELECT employee.employeeid, first_name, last_name, telephone_number, date_of_employment, workplace_name FROM employee INNER JOIN employee_workplace ON employee.employeeid = employee_workplace.employeeid INNER JOIN workplace ON workplace.workplaceid = employee_workplace.workplaceid  UNION" +  
			" SELECT employee.employeeid, first_name, last_name, telephone_number, date_of_employment, workplace_name FROM employee INNER JOIN employee_workplace ON employee.employeeid = employee_workplace.employeeid INNER JOIN workplace ON workplace.workplaceid = employee_workplace.workplaceid  ORDER BY employeeid;";
	
	public Controller() {	
	}
	
	public void init() {
		initLoginFrame();
		dbc = DBConnection.getInstance();
	}
	
	private void initLoginFrame() {
		loginFrame = new LoginFrame();
		loginFrame.addListenerToButton(new connectBtnListener(), loginFrame.getConnectBtn());		
	}
	
	private void initMainFrame() {
		DBConnection.getInstance().selectDB(loginFrame.getBox().getSelectedItem().toString());			
		loginFrame.dispose();			
		
		mainFrame = new MainFrame();
		mainFrame.addListenerToButton(new updateBtnListener(), mainFrame.getBtn("update"));
		mainFrame.addListenerToButton(new searchBtnListener(), mainFrame.getBtn("search"));
		mainFrame.addListenerToButton(new addBtnListener(), mainFrame.getBtn("add"));
		mainFrame.addListenerToButton(new removeBtnListener(), mainFrame.getBtn("remove"));		
		mainFrame.AddTable(DBConnection.getInstance().getResultSet(query));
	}
	
	private void initAddDialog() {
		addDialog = new AddDialog(mainFrame.getFrame());		
	}
	
	class searchBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String searchString = mainFrame.getSearchBox().getText();
			query = "SELECT employee.employeeid, first_name, last_name, telephone_number, date_of_employment, workplace_name FROM employee INNER JOIN employee_workplace ON employee.employeeid = employee_workplace.employeeid INNER JOIN workplace ON workplace.workplaceid = employee_workplace.workplaceid WHERE MATCH(first_name) AGAINST ";
			query += "(" + "\"" + searchString + "\"" + ")";
			query += "OR MATCH(last_name) AGAINST " + "(" + "\"" + searchString + "\"" + ")";
			
			System.out.println(query);
			
			mainFrame.AddTable(DBConnection.getInstance().getResultSet(query));
		}
	}
	
	class updateBtnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			query = "SELECT employee.employeeid, first_name, last_name, telephone_number, date_of_employment, workplace_name FROM employee";
			if (mainFrame.getDoctors() == true || mainFrame.getNurses() == true || mainFrame.getAssNurses() == true) {				
				ArrayList<String> titleFilterList = new ArrayList<String>();
				if (mainFrame.getDoctors() == true) titleFilterList.add(" NATURAL JOIN doctor");
				if (mainFrame.getNurses() == true) titleFilterList.add(" NATURAL JOIN nurse");
				if (mainFrame.getAssNurses() == true) titleFilterList.add(" NATURAL JOIN assistant_nurse");
				
				query += titleFilterList.get(0);
				query += " INNER JOIN employee_workplace ON employee.employeeid = employee_workplace.employeeid INNER JOIN workplace ON workplace.workplaceid = employee_workplace.workplaceid";
				for (int i = 1; i < titleFilterList.size(); i++) {
					query += " UNION SELECT employee.employeeid, first_name, last_name, telephone_number, date_of_employment, workplace_name FROM employee";
					query += titleFilterList.get(i);
					query += " INNER JOIN employee_workplace ON employee.employeeid = employee_workplace.employeeid INNER JOIN workplace ON workplace.workplaceid = employee_workplace.workplaceid";
				}
			}
			
			else if (!mainFrame.getDoctors() && !mainFrame.getNurses() && !mainFrame.getAssNurses()) {
				query += " INNER JOIN employee_workplace ON employee.employeeid = employee_workplace.employeeid INNER JOIN workplace ON workplace.workplaceid = employee_workplace.workplaceid";
			}
			query += " ORDER BY last_name COLLATE utf8_swedish_ci;";
			
			System.out.println(query);
						
			mainFrame.AddTable(DBConnection.getInstance().getResultSet(query));
		}		
	}
	
	class selectDatabaseBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			initMainFrame();
		}
	}
	
	class comboBoxSelectionListener implements ItemListener {
	    @Override
	    public void itemStateChanged(ItemEvent event) {
	       if (event.getStateChange() == ItemEvent.SELECTED) {
	          Object item = event.getItem();
	          item.toString();	          
	       }
	    }
	}
	
	class addBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			initAddDialog();	
		}
	}
	
	class removeBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int i = JOptionPane.showConfirmDialog(mainFrame.getFrame(), "Really remove employee?", "Confirm...", JOptionPane.OK_CANCEL_OPTION);
			if (i == 0) {
				dbc.removeEmployee(mainFrame.getSelectedID());
				MainFrame.update();
			}
			else
				return;			
		}
	}
	
	class connectBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if (dbc.getFlag() == 0) {
				dbc.setIP(loginFrame.getIP());
				dbc.setPort(loginFrame.getPort());
				dbc.setUser(loginFrame.getUser());
				dbc.setPassword(loginFrame.getPassword());
				dbc.connect();
				dbc.createStatement();
				
				loginFrame.createDBSelectPanel(dbc.getDBNamesAsArrayList());
				loginFrame.connect();
				loginFrame.addListenerToCombobox(new comboBoxSelectionListener(), loginFrame.getBox());
				loginFrame.addListenerToButton(new selectDatabaseBtnListener(), loginFrame.getUseBtn());
			}
			else {
				loginFrame.disconnect();
				dbc.close();
			}
		}
	}
	
	public void getStatus() {
		System.out.println("Running...");
	}
}
