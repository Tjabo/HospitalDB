package com.hospital.frames;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.hospital.dbconn.DBConnection;

public class AddDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private DBConnection dbc = DBConnection.getInstance();
	
	private final JDialog dialogPanel;
	private JTextField txtFieldFirstName;
	private JTextField txtFieldLastName;
	private JTextField txtFieldPhone;
	private JLabel lblDate;
	private JTextField txtYear;
	private JTextField txtMonth;
	private JTextField txtDay;
	private JPanel entryPanel;
	private JPanel datePanel;
	private JLabel lblY;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JPanel btnPanel;
	private JButton btnOK;
	private JButton btnCancel;
	private JPanel workplacePanel;
	private JComboBox<Object> workplaceComboBox;
	private JPanel competencePanel;
	private JList<String> list;
	
	private ArrayList<String> workplaces;
	private ArrayList<String> competences;
	private ArrayList<String> titles;
	private ArrayList<Integer> selectedSpecializations;
	
	private JLabel lblSpecializationList;
	private JLabel lblWorkplaceTitle;
	private JComboBox<Object> titleComboBox;
	private JPanel labelPanelOne;
	private JLabel lblNewLabel_2;
	private JPanel labelPanelTwo;
	private JPanel labelPanelThree;
	private JPanel dateLabelPanel;
	
	public AddDialog(JFrame frame) {	
		dialogPanel = new JDialog(frame, true);
		initializeDataLists();
		initializeGUI();
	}
	
	private void initializeDataLists() {
		competences = new ArrayList<String>();
		workplaces = new ArrayList<String>();
		titles = new ArrayList<String>();
		titles.add("Doctor");
		titles.add("Nurse");
		titles.add("Assistant nurse");
		
		String queryCompetences = "SELECT specialization_name FROM specialization;";
		String queryWorkplaces = "SELECT workplace_name FROM workplace;";
		ResultSet competenceResultSet = dbc.getResultSet(queryCompetences);
		
		try {
			while (competenceResultSet.next()) {
				competences.add(competenceResultSet.getString("specialization_name"));				
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Could not load specializations: " + e.getMessage());
		}
		
		ResultSet workplaceResultSet = dbc.getResultSet(queryWorkplaces);
		try {
			while (workplaceResultSet.next()) {
				workplaces.add(workplaceResultSet.getString("workplace_name"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Could not load workplaces: " + e.getMessage());
		}
	}
	
	private void initializeGUI() {
		
		dialogPanel.getContentPane().setLayout(new BoxLayout(dialogPanel.getContentPane(), BoxLayout.Y_AXIS));
		
		labelPanelOne = new JPanel();
		dialogPanel.getContentPane().add(labelPanelOne);
		
		lblNewLabel_2 = new JLabel("Personal Information");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPanelOne.add(lblNewLabel_2);
		
		entryPanel = new JPanel();
		dialogPanel.getContentPane().add(entryPanel);
		entryPanel.setLayout(new GridLayout(3, 2, 5, 5));
		
		JLabel lblFirstName = new JLabel("First name: ");
		entryPanel.add(lblFirstName);
		
		txtFieldFirstName = new JTextField();
		entryPanel.add(txtFieldFirstName);
		txtFieldFirstName.setColumns(10);
		lblFirstName.setLabelFor(txtFieldFirstName);
		
		JLabel lblLastName = new JLabel("Last name:");
		entryPanel.add(lblLastName);
		lblLastName.setLabelFor(txtFieldLastName);
		
		txtFieldLastName = new JTextField();
		entryPanel.add(txtFieldLastName);
		txtFieldLastName.setColumns(10);
		
		JLabel lblPhone = new JLabel("Telephone number:");
		entryPanel.add(lblPhone);
		lblPhone.setLabelFor(txtFieldPhone);
		
		txtFieldPhone = new JTextField();
		entryPanel.add(txtFieldPhone);
		txtFieldPhone.setColumns(10);
		
		dateLabelPanel = new JPanel();
		dialogPanel.getContentPane().add(dateLabelPanel);
		
		lblDate = new JLabel("Date of employment");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		dateLabelPanel.add(lblDate);
		
		datePanel = new JPanel();
		dialogPanel.getContentPane().add(datePanel);
		datePanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblY = new JLabel("YYYY");
		lblY.setHorizontalAlignment(SwingConstants.TRAILING);
		datePanel.add(lblY);
		
		txtYear = new JTextField();
		datePanel.add(txtYear);
		txtYear.setColumns(4);
		
		lblNewLabel = new JLabel("MM");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		datePanel.add(lblNewLabel);
		
		txtMonth = new JTextField();
		datePanel.add(txtMonth);
		txtMonth.setColumns(2);
		
		lblNewLabel_1 = new JLabel("DD");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		datePanel.add(lblNewLabel_1);
		
		txtDay = new JTextField();
		datePanel.add(txtDay);
		txtDay.setColumns(2);
		
		labelPanelTwo = new JPanel();
		dialogPanel.getContentPane().add(labelPanelTwo);
		
		lblWorkplaceTitle = new JLabel("Workplace and title");
		labelPanelTwo.add(lblWorkplaceTitle);
		lblWorkplaceTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		workplacePanel = new JPanel();
		dialogPanel.getContentPane().add(workplacePanel);
		
		workplaceComboBox = new JComboBox<Object>(workplaces.toArray());
		workplacePanel.add(workplaceComboBox);
		
		titleComboBox = new JComboBox<Object>(titles.toArray());
		workplacePanel.add(titleComboBox);
		
		labelPanelThree = new JPanel();
		dialogPanel.getContentPane().add(labelPanelThree);
		
		lblSpecializationList = new JLabel("Specializations");
		labelPanelThree.add(lblSpecializationList);
		lblSpecializationList.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		competencePanel = new JPanel();
		dialogPanel.getContentPane().add(competencePanel);
		competencePanel.setLayout(new BorderLayout(0, 0));
		
		
		list = new JList<String>((String[]) competences.toArray());
		
		ListSelectionModel listSelectionModel = list.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new specializationListSelectionListener());	    
		
		competencePanel.add(list);
		
		btnPanel = new JPanel();
		dialogPanel.getContentPane().add(btnPanel);
		
		btnOK = new JButton("Add Employee");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {						
					System.out.println("test");
					String firstName = txtFieldFirstName.getText();
					String lastName = txtFieldLastName.getText();
					String phone = txtFieldPhone.getText();
					String date = txtYear.getText() + "-" + txtMonth.getText() + "-" + txtDay.getText();				
					
					int workplaceID = getWorkplaceID(); 
					
					if (titleComboBox.getSelectedIndex() == 0) {
						dbc.insertDoctor(firstName, lastName, phone, date, selectedSpecializations, workplaceID);
					}
					else if (titleComboBox.getSelectedIndex() == 1) {
						dbc.insertNurse(firstName, lastName, phone, date, selectedSpecializations, workplaceID);
					}
					else if (titleComboBox.getSelectedIndex() == 2) {
						dbc.insertAssistant(firstName, lastName, phone, date, workplaceID);
					}				
					dialogPanel.dispose();
					MainFrame.update();
			}
		});
		
		btnPanel.add(btnOK);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogPanel.dispose();
			}
		});
		
		btnPanel.add(btnCancel);
		
		dialogPanel.pack();
		dialogPanel.setVisible(true);
		
	}
	
	public void addListenerToButton(ActionListener listen, JButton button) {
		button.addActionListener(listen);
	}
	
	public JButton getBtn(String name) {
		JButton btn = null;
		if (name == "ok") btn = this.btnOK;
		if (name == "cancel") btn = this.btnCancel;
		return btn;
	}
	
	public String getFirstName() {
		return this.txtFieldFirstName.getText();
	}
	
	public String getLastName() {
		return this.txtFieldLastName.getText();		
	}
	
	public String getPhone() {
		return this.txtFieldPhone.getText();
	}
	
	public String getDate() {
		return this.txtYear.getText() + "-" + this.txtMonth.getText() + "-" + this.txtDay.getText();
	}
	
	public int getWorkplaceID() {
		return this.workplaceComboBox.getSelectedIndex() + 1;
	}
	
	public int getJobTitle() {
		System.out.println(this.titleComboBox.getSelectedIndex());
		return this.titleComboBox.getSelectedIndex();
	}
	
	public ArrayList<Integer> getSpecializations() {
		return this.selectedSpecializations;
	}
	
	// Grabs all the clicked specializations in the list and adds them to a class-scope arraylist.
	// We can then poll this arraylist when we want to make our database insertions. 
	class specializationListSelectionListener implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) {
	        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	        
	        selectedSpecializations = new ArrayList<Integer>();
	        if (lsm.isSelectionEmpty()) {
	        	selectedSpecializations.clear();
	        } else {
	            int minIndex = lsm.getMinSelectionIndex();
	            int maxIndex = lsm.getMaxSelectionIndex();
	            for (int i = minIndex; i <= maxIndex; i++) {
	                if (lsm.isSelectedIndex(i)) {
	                	selectedSpecializations.add(i + 1);
	                }
	            }
	        }	        
	    }
	}
}
