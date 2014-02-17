package com.hospital.dbconn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


public class DBConnection {

	private static DBConnection dbConnection = null;
	private static Connection connection = null;
	
	private MysqlDataSource dataSource;	
	private Statement statement;
	private ResultSet resultSet;
	
	private String username = null;
	private String password = null;
	
	private int flag = 0;
	
	private DBConnection() {		
		dataSource = new MysqlDataSource();		
	}
	
	public static DBConnection getInstance() {
		if (dbConnection == null) {
			dbConnection = new DBConnection();
		}
		return dbConnection;
	}
	
	public void connect() {
		connection = null;
		try {
			connection = dataSource.getConnection(this.username, this.password);
			flag = 1;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Could not establish connection: " + e.getMessage());			
			return;
		}
	}
	
	public void createStatement() {
		statement = null;
		try {			
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.err.println("Error! Could not create statement. " + e.getMessage());
			try {
				connection.close();
			} catch (SQLException e1) {
				System.err.println("Warning: Could not close connection. " + e1.getMessage());
			} 
			return;
		}
	}
	
	public void createPreparedStatement(boolean doc, boolean nurse, boolean assnurse) {
		PreparedStatement prepState = null;
		String query = "SELECT * FROM employee NATURAL JOIN ?, ?, ?";
		try {
			prepState = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			if (doc) prepState.setString(1, "doctor");
			if (nurse) prepState.setString(2, "nurse");
			if (assnurse) prepState.setString(3, "assistant_nurse");			
		} catch (SQLException e) {
			System.out.println("Couldn't create prepared statement! " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getDBNamesAsArrayList() {
		ArrayList<String> output = new ArrayList<String>();
		resultSet = null;
		try {
			resultSet = statement.executeQuery("SHOW DATABASES;");
			ResultSetMetaData md = resultSet.getMetaData();
			
			resultSet.beforeFirst();
			while(resultSet.next()){
				for(int i = 1; i < md.getColumnCount() + 1; i++){
					output.add(resultSet.getString(i));
				}
			}		
		} catch (SQLException e) {
			System.out.println("SQL query failed. " + e.getMessage());
			e.printStackTrace();
		}
		return output;
	}
	
	public ResultSet getResultSet(String query) {
		resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SQL query failed. " + e.getMessage());
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public void insertDoctor(String firstName, String lastName, String phone, String date, ArrayList<Integer> spec, int workplaceID) {
		PreparedStatement insertEmployee = null;
		PreparedStatement insertDoctor = null;
		PreparedStatement insertSpecializations = null;
		PreparedStatement insertWorkplace = null;
		
		int id = getLastIndex() + 1;
		
		try {
			connection.setAutoCommit(false);
			// Employee table
			insertEmployee = connection.prepareStatement("INSERT INTO employee (employeeid, first_name, last_name, telephone_number, date_of_employment) VALUES(?, ?, ?, ?, ?);");
			insertEmployee.setInt(1, id);
			insertEmployee.setString(2, firstName);
			insertEmployee.setString(3, lastName);
			insertEmployee.setString(4, phone);
			insertEmployee.setString(5, date);
			insertEmployee.addBatch();
			System.out.println("Inserted " + id + " " + firstName + " " + lastName + " " + phone + " " + date + " into employee.");
			
			// Doctor table			
			insertDoctor = connection.prepareStatement("INSERT INTO doctor VALUES(?);");
			insertDoctor.setInt(1, id);
			insertDoctor.addBatch();
			System.out.println("Inserted " + id + " into doctor");
			
			// Doctor specialization table			
			for (Integer i : spec) {
				insertSpecializations = connection.prepareStatement("INSERT INTO doctor_specialization VALUES(?, ?);");
				insertSpecializations.setInt(1, id);
				insertSpecializations.setInt(2, i + 1); // i comes from arraylist 1st is 0.
				insertSpecializations.addBatch();
				System.out.println("Inserted specialization with id " + i+1 + " for employee " + id);
			}
			
			// Employee_workplace table
			insertWorkplace = connection.prepareStatement("INSERT INTO employee_workplace VALUES(?,?)");
			insertWorkplace.setInt(1, id);
			insertWorkplace.setInt(2, workplaceID);
			insertWorkplace.addBatch();
			System.out.println("Inserted employee " + id + " into workplace with id " + workplaceID);
			
			insertEmployee.executeBatch();
			insertDoctor.executeBatch();
			insertSpecializations.executeBatch();
			insertWorkplace.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not create prepared statement(s)!" + e.getMessage());
		}
	}
	
	public void insertNurse(String firstName, String lastName, String phone, String date, ArrayList<Integer> spec, int workplaceID) {
		PreparedStatement insertEmployee = null;
		PreparedStatement insertNurse = null;
		PreparedStatement insertSpecializations = null;
		PreparedStatement insertWorkplace = null;
		
		int id = getLastIndex() + 1;
		
		try {
			connection.setAutoCommit(false);
			// Employee table
			insertEmployee = connection.prepareStatement("INSERT INTO employee (employeeid, first_name, last_name, telephone_number, date_of_employment) VALUES(?, ?, ?, ?, ?);");
			insertEmployee.setInt(1,  id);
			insertEmployee.setString(2, firstName);
			insertEmployee.setString(3, lastName);
			insertEmployee.setString(4, phone);
			insertEmployee.setString(5, date);
			insertEmployee.addBatch();
			System.out.println("Inserted " + id + " " + firstName + " " + lastName + " " + phone + " " + date + " into employee.");
			
			// Nurse table
			
			insertNurse = connection.prepareStatement("INSERT INTO nurse VALUES(?);");
			insertNurse.setInt(1, id);
			insertNurse.addBatch();
			System.out.println("Inserted " + id + " into nurse");
			
			// Nurse specialization table
			
			for (Integer i : spec) {
				insertSpecializations = connection.prepareStatement("INSERT INTO nurse_specialization VALUES(?, ?);");
				insertSpecializations.setInt(1, id);
				insertSpecializations.setInt(2, i + 1); // i comes from arraylist 1st is 0.
				insertSpecializations.addBatch();
			}
			
			// Employee_workplace table
				insertWorkplace = connection.prepareStatement("INSERT INTO employee_workplace VALUES(?,?)");
				insertWorkplace.setInt(1, id);
				insertWorkplace.setInt(2, workplaceID);
				insertWorkplace.addBatch();
				System.out.println("Inserted employee " + id + " into workplace with id " + workplaceID);
			
			insertEmployee.executeBatch();
			insertNurse.executeBatch();
			insertSpecializations.executeBatch();
			insertWorkplace.executeBatch();
			
			connection.commit();
			connection.setAutoCommit(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not create prepared statement(s)!" + e.getMessage());
		}		
	}
	
	public void insertAssistant(String firstName, String lastName, String phone, String date, int workplaceID) {
		PreparedStatement insertEmployee = null;
		PreparedStatement insertAssistant = null;
		PreparedStatement insertWorkplace = null;
		
		int id = getLastIndex() + 1;
		
		try {
			connection.setAutoCommit(false);
			// Employee table
			insertEmployee = connection.prepareStatement("INSERT INTO employee (employeeid, first_name, last_name, telephone_number, date_of_employment) VALUES(?, ?, ?, ?, ?);");
			insertEmployee.setInt(1,  id);
			insertEmployee.setString(2, firstName);
			insertEmployee.setString(3, lastName);
			insertEmployee.setString(4, phone);
			insertEmployee.setString(5, date);
			insertEmployee.addBatch();
			System.out.println("Inserted " + id + " " + firstName + " " + lastName + " " + phone + " " + date + " into employee.");
			
			// Assistant_Nurse table			
			insertAssistant = connection.prepareStatement("INSERT INTO assistant_nurse VALUES(?);");
			insertAssistant.setInt(1, id);
			insertAssistant.addBatch();
			System.out.println("Inserted " + id + " into assistant_nurse");
			
			// Employee_workplace table
			insertWorkplace = connection.prepareStatement("INSERT INTO employee_workplace VALUES(?,?)");
			insertWorkplace.setInt(1, id);
			insertWorkplace.setInt(2, workplaceID);
			insertWorkplace.addBatch();
			System.out.println("Inserted employee " + id + " into workplace with id " + workplaceID);
			
			insertEmployee.executeBatch();
			insertAssistant.executeBatch();
			insertWorkplace.executeBatch();
			
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not create prepared statement(s)!" + e.getMessage());
		}
	}
	
	public void removeEmployee(int id) {
		PreparedStatement removeEmployee = null;		
		try {
			connection.setAutoCommit(false);
			removeEmployee = connection.prepareStatement("DELETE FROM employee WHERE employeeid = ?;"); // Cascades take care of rest?
			removeEmployee.setInt(1, id);						
			removeEmployee.addBatch();
			removeEmployee.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Could not remove employee! Attempting rollback." + e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Could not perform rollback! Contact database admin!" + e.getMessage());
			}
		}
		try {
			connection.setAutoCommit(true);			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Failed to turn autocommit back on!" + e.getMessage());
		}
	}
	
	public int getLastIndex() {
		int output = -1;
		
		ResultSet index = getResultSet("SELECT MAX(employeeid) AS last_id FROM employee;");
		try {
			index.first();
			output = index.getInt("last_id");
			System.out.println("Last index was: " + output);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Failed to traverse resultset while fetching last index!" + e.getMessage());
		}		
		return output;
	}	
	
	public void close() {
		if(resultSet != null){
			try {
				resultSet.close();				
			} catch (SQLException e) {
				System.out.println("Warning: Couldn't close results.");
			}
		}
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Warning: Couldn't close the statement.");
			}
		}
		if(connection != null){
			try {
				connection.close();
				flag = 0;
			} catch (SQLException e) {
				System.out.println("Warning: Couldn't close the connection.");
			}
		}
			
	}
	
	public int getFlag() {
		return flag;
	}
	
	public void selectDB(String dbname) {
		try {
			statement.executeQuery("USE " + dbname + ";");
		} catch (SQLException e) {
			System.err.print("Failed to select database! " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void setIP(String ip) {
		dataSource.setServerName(ip);		
	}
	
	public void setPort(int port) {
		dataSource.setPort(port);
	}
	
	public void setUser(String username) {
		this.username = username;
		dataSource.setUser(username);
	}
	
	public void setPassword(String password) {
		this.password = password;
		dataSource.setPassword(password);
	}
}
