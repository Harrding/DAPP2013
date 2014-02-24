package company;

/*
 * The table model for an Employee's reports where
 * their data will only be shown when the company
 * is selected on the left box.
 */

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class EmployeeReportModel extends AbstractTableModel{
	
	// Required variables in abstract class
	private String [] columnNames = {"Report ID", "Employee ID", "Name", "Average Score", "Employer"};
	private String [][] data = getData();	
	
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	// Retrieves all the data for the table in a 2d array
	public String[][] getData() {
		String[][] newData;
		String[][] evaluationResults = ReadFromFile.getEvaluationResults();
		newData = new String[evaluationResults.length][5];
		for(int i = 0; i < newData.length; i++) {
				newData[i][0] = evaluationResults[i][0];
				newData[i][1] = evaluationResults[i][1];
				newData[i][2] = getName(Integer.parseInt(evaluationResults[i][1]));
				newData[i][3] = evaluationResults[i][13];
				newData[i][4] = getEmployer(Integer.parseInt(evaluationResults[i][2]));
		}
		return newData;
	}
	
	// Retrieves employer(company) ID
	public String getEmployer(int ID)
	{
		try {
			int employerID = ID;
			if(employerID > 0) {
				if(employerID < ReadFromFile.getNumberFromFile("company.txt")) {
					Company [] checkEmployers = ReadFromFile.findAllCompanyData();
					return checkEmployers[employerID-1].getName();
				}
			}
		}
		catch(NumberFormatException n) {}
		return "";
	}
	
	// Retrieves Name of employee based on the id
	public String getName(int ID)
	{
		try {
			int employeeID = ID;
			if(employeeID > 0) {
				if(employeeID < ReadFromFile.getNumberFromFile("employee.txt")) {
					Employee [] checkEmployees = ReadFromFile.findAllEmployeeData();
					return checkEmployees[employeeID-1].getFirstName() + " " + checkEmployees[employeeID-1].getLastName();
				}
			}
		}
		catch(NumberFormatException n) {}
		return "";
	}
	
	
	public void changeData() {
		data = getData();
	}
}
