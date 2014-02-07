package com.hospital.dbconn;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


public class DBConnection {

	private static DBConnection dbConnection = null;
	private static Connection connection = null;
	
	private MysqlDataSource dataSource;	
	private Statement statement;
	private ResultSet resultSet;
	
	private String username;
	private String password;
	
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
		flag = 1;
		try {
			//DO NOT CALL BEFORE ALL IS SET!
			connection = dataSource.getConnection(this.username, this.password);
		} catch (SQLException e) {
			System.err.println("Error: Couldn't establish a connection to database. " + e.getMessage());
			return;
		}
	}
	
	public void createStatement() {
		statement = null;
		try {			
			statement = connection.createStatement();
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
